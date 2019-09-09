计算



调度



负载均衡



部署



数据导入方式

Tranquility (Stream Push)

Kafka Indexing Service (Stream Pull)



查询 API



## druid的高可用性

1. MetaStore挂掉：无法感知新的Segment生成，不影响老数据
2. Indexing Service挂掉：无法执行新的任务，新数据无法摄入，不影响查询
3. Broker挂掉：本Broker节点不能查询，其他节点Broker继续服务，不影响数据摄入
4. Historical挂掉：Coordinator Node重分配该节点上segment到其它节点
5. Coordinator挂掉：Segment不会被加载和删除，选举新leader
6. Zookeeper挂掉：无法执行新的任务，新数据进不来；Broker有缓存

运维监控



冷热数据分离



与 Superset 集成



#### 丰富的辅助功能

Druid除了基本的核心数据消费和查询功能外，还提供了丰富的辅助功能，以帮助用户更好地基于Druid完成数据处理工作。本文简单列举几个：

**DataSketches aggregator：**近似计算COUNT DISTINCT等问题，如PV、留存等；精度可调节。

**Multi-value dimensions：**对于同一维度列，允许不同行拥有不同数量的数据值：这使得Druid也能够有类似schemaless的功能。



支持数据自动过期吗？类似与 Kafka 那种