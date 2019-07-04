# 常见分布式系统的一致性模型

假设一个具有N个节点的分布式系统，当其满足以下条件时，我们说这个系统满足一致性：

1. 全认同(agreement): 所有N个节点都认同一个结果
2. 值合法(validity): 该结果必须由N个节点中的节点提出
3. 可结束(termination): 决议过程在一定时间内结束，不会无休止地进行下去 

但就这样看似简单的事情，分布式系统实现起来并不轻松，因为它面临着这些问题：

* 消息传递异步无序(asynchronous): 现实网络不是一个可靠的信道，存在消息延时、丢失，节点间消息传递做不到同步有序(synchronous)
* 节点宕机(fail-stop): 节点持续宕机，不会恢复
* 节点宕机恢复(fail-recover): 节点宕机一段时间后恢复，在分布式系统中最常见
* 网络分化(network partition): 网络链路出现问题，将N个节点隔离成多个部分
* 拜占庭将军问题(byzantine failure): 节点或宕机或逻辑失败，甚至不按套路出牌抛出干扰决议的信息

## Zookeeper

## Hdfs

## HBase

## Presto

## Kafka



### producer 端的仅仅一次保证

至少一次 + 幂等性

需要配置 `enable.idempotent = true` 。



消费的一致性语义

## TiDB
