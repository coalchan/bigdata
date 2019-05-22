#### 1. Spark SQL 运行架构

![](D:/other-code/interview-preparation/img/spark/catalyst.png)

1. SqlParser 将 SQL 语句转换为未解析的逻辑查询计划；
2. Analyzer 对逻辑查询计划进行属性和关系关联检验；
3. Optimizer 通过逻辑查询优化将逻辑查询计划转换为优化的逻辑查询计划；
4. QueryPlanner 将优化的逻辑查询计划转换为物理查询计划，再根据过去的性能统计数据，选择最佳的物理执行计划 CostModel，得到 SparkPlan；
5. 生成 RDD 相关代码，并执行。

#### 2. Spark SQL 中Optimizer 的常见优化策略有哪些？

**基于规则的优化（RBO）**

* 列裁剪：利用列式存储，减少网络、内存消耗
* 谓词下推：提前过滤，减少计算数据量
* 常量折叠：减少常量操作，如从 `1+1` 优化为 `2` 避免每一条 record 都需要执行一次 `1+1` 的操作
* 操作合并：合并多个连续的 Filter、limit 等算子，加快计算

**基于 Cost 的优化（CBO）**

Join 方案的选择

#### 3. Join 的几种实现方式？

Broadcast Hash Join 和 Shuffle Hash Join 都是基于 Hash Join 的变体，所以先介绍下 Hash Join：

取一张表作为 Build Table，对其按照 Join Key 构建 Hash Table，另外一张表作为 Probe Table，扫描 Probe Table，对其参与的 Join Key Hash 映射 Hash Table 中的记录，检查 join key 值是否匹配，匹配即 Join 上了。这便是单机版 Hash Join 的基本思想。

**Broadcast Hash Join**

1. Broadcast 阶段：将小表广播分发到大表所在的所有主机。广播算法可以有很多，最简单的是先发给 Driver，Driver 再统一分发给所有 Executor；要不就是基于 bittorrete 的 p2p 思路；
2. Hash Join 阶段：在每个 Executor上执行单机版 Hash Join，小表映射，大表试探。

Spark SQL 规定 Broadcast Hash Join 执行的基本条件为被广播小表必须小于参数 spark.sql.autoBroadcastJoinThreshold，默认为 10M 。

**Shuffle Hash Join**

显然 Broadcast Hash Join 只适合对小表进行广播，如果是大表呢？此时可以按照 Join Key 进行分区，根据 Key 相同必然分区相同的原理，就可以将大表 Join 分而治之，划分为很多小表的 Join，充分利用集群资源并行化。

1. Shuffle 阶段：分别将两个表按照 Join Key 进行分区，将相同 Join Key 的记录重分布到同一分区，这样两张表的数据会被重分布到集群中所有节点，这个过程称为 shuffle；
2. Hash Join 阶段：每个分区节点上的数据单独执行单机 Hash Join 算法。

**Sort Merge Join**

如果两张表都是大表的话，Shuffle Hash Join 在 Hash Join 阶段仍然可能比较吃力，Sort Merge Join 就是针对这一阶段的优化：

1. Shuffle 阶段：同 Shuffle Hash Join 一样分别将两个表根据 Join Key 进行分区；
2. Sort 阶段：对单个分区节点的两个表的数据，分别进行排序；
3. Merge 阶段：对排好序的两张分区表数据执行 Join 操作。Join操作很简单，分别遍历两个有序序列，碰到相同 Join key 就 Merge 输出。

简单来说 Sort Merge Join 就是通过排序加快了 Hash Join。

#### 参考文章

1. [BigData－‘基于代价优化’究竟是怎么一回事？](http://hbasefly.com/2017/05/04/bigdata%EF%BC%8Dcbo/?ymtkti=qul851)