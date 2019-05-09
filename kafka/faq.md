# 常见问题

1. Kafka 如何做到高吞吐、低延迟的呢？

   这里提下 Kafka 写数据的大致方式：先写操作系统的页缓存（Page Cache）,然后由操作系统自行决定何时刷到磁盘。

   因此 Kafka 达到高吞吐、低延迟的原因主要有以下 4 点：

   * 页缓存是在内存中分配的，所以消息写入的速度很快。
   * Kafka 不必和底层的文件系统进行交互，所有繁琐的 I/O 操作都由操作系统来处理。
   * Kafka 采用追加写的方式，避免了磁盘随机写操作。
   * 使用以 sendfile 为代表的零拷贝技术提高了读取数据的效率。

   PS: 使用页缓存而非堆内存还有一个好处，就是当 Kafka broker 的进程崩溃时，堆内存的数据会丢失，但是页缓存的数据依然存在，重启 Kafka broker 后可以继续提供服务。

2. Kafka 的 Java 版本的 producer 工作流程？

   ![Java 版本的 producer 工作流程]()

   1. 封装为 `ProducerRecord` 实例 
   2. 序列化 
   3. 由 partitioner 确定具体分区 
   4. 发送到内存缓冲区
   5. 由 producer 的一个专属 I/O 线程去取消息，并将其封装到一个批次 ，发送给对应分区的 kafka broker

3. Kafka 的 consumer 工作流程？

   **TODO**

4. 重要参数有哪些？

   1. acks
      - acks = 0 : 不接收发送结果
      - acks = all 或者 -1: 表示发送消息时，不仅要写入本地日志，还要等待所有副本写入成功。
      - acks = 1: 写入本地日志即可，是上述二者的折衷方案，也是默认值。
   2. retries
      - 默认为 0，即不重试，立即失败。
      - 一个大于 0 的值，表示重试次数。

5. 丢失数据的场景？

   * consumer 端：**不是严格意义的丢失，其实只是漏消费了**。

     设置了 `auto.commit.enable=true` ，当 consumer fetch 了一些数据但还没有完全处理掉的时候，刚好到 commit interval 触发了提交 offset 操作，接着 consumer 挂掉。这时已经fetch的数据还没有处理完成但已经被commit掉，因此没有机会再次被处理，数据丢失。

   * producer 端：

     I/O 线程发送消息之前，producer 崩溃， 则 producer 的内存缓冲区的数据将丢失。

6. 丢失数据如何解决？

   * 同步发送，性能差，不推荐。
   * 仍然异步发送，通过“无消息丢失配置”（来自胡夕的《Apache Kafka 实战》）极大降低丢失的可能性：
     * block.on.buffer.full = true  尽管该参数在0.9.0.0已经被标记为“deprecated”，但鉴于它的含义非常直观，所以这里还是显式设置它为true，使得producer将一直等待缓冲区直至其变为可用。否则如果producer生产速度过快耗尽了缓冲区，producer将抛出异常
     * acks=all  很好理解，所有follower都响应了才认为消息提交成功，即"committed"
     * retries = MAX 无限重试，直到你意识到出现了问题:)
     * max.in.flight.requests.per.connection = 1 限制客户端在单个连接上能够发送的未响应请求的个数。设置此值是1表示kafka broker在响应请求之前client不能再向同一个broker发送请求。注意：设置此参数是为了避免消息乱序
     * 使用KafkaProducer.send(record, callback)而不是send(record)方法   自定义回调逻辑处理消息发送失败
     * callback逻辑中最好显式关闭producer：close(0) 注意：设置此参数是为了避免消息乱序
     * unclean.leader.election.enable=false   关闭unclean leader选举，即不允许非ISR中的副本被选举为leader，以避免数据丢失
     * replication.factor >= 3   这个完全是个人建议了，参考了Hadoop及业界通用的三备份原则
     * min.insync.replicas > 1 消息至少要被写入到这么多副本才算成功，也是提升数据持久性的一个参数。与acks配合使用
     * 保证replication.factor > min.insync.replicas  如果两者相等，当一个副本挂掉了分区也就没法正常工作了。通常设置replication.factor = min.insync.replicas + 1即可

7. 重复数据的场景？

8. 分区策略（即生产消息时如何选择哪个具体的分区）？

9. 乱序的场景？

   消息的重试发送。

10. 乱序如何解决？

   参数配置 `max.in.flight.requests.per.connection = 1` ，但同时会限制 producer 未响应请求的数量，即造成在 broker 响应之前，producer 无法再向该 broker 发送数据。

11. 可重试的异常情况有哪些？

    * 分区的 leader 副本不可用，一般发生再 leader 换届选举时。
    * controller 当前不可用，一般是 controller 在经历新一轮的选举。
    * 网络瞬时故障。

12. controller 的职责有哪些？

    在 kafka 集群中，某个 broker 会被选举承担特殊的角色，即控制器（controller），用于管理和协调 kafka 集群，具体职责如下：

    * 管理副本和分区的状态
    * 更新集群元数据信息
    * 创建、删除 topic
    * 分区重分配
    * leader 副本选举
    * topic 分区扩展
    * broker 加入、退出集群
    * 受控关闭
    * controller leader 选举

    ​



