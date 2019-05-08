# 分布式一致性算法——Paxos 算法

## Paxos 是什么

Paxos 算法是 Leslie Lamport 在1990年提出的一种基于消息传递的一致性算法。由于算法难以理解，起初并没有引起大家的重视，Lamport 在1998年将论文重新发表到 TOCS 上，即便如此 Paxos 算法还是没有得到重视，2001年 Lamport 用可读性比较强的叙述性语言给出算法描述。

06年 Google 发布了三篇论文，其中在 Chubby 锁服务使用 Paxos 作为 Chubby Cell 中的一致性算法，Paxos 的人气从此一路狂飙。

基于 Paxos 协议的数据同步与传统主备方式最大的区别在于：Paxos 只需超过半数的副本在线且相互通信正常，就可以保证服务的持续可用，且数据不丢失。

Google Chubby 的作者 Mike Burrows 说过这个世界上只有一种一致性算法，那就是 Paxos，其它的算法都是残次品。

## 问题产生的背景

在常见的分布式系统中，总会发生诸如机器宕机或网络异常（包括消息的延迟、丢失、重复、乱序，还有网络分区）等情况。Paxos算法需要解决的问题就是如何在一个可能发生上述异常的分布式系统中，快速且正确地在集群内部对某个数据的值达成一致，并且保证不论发生以上任何异常，都不会破坏整个系统的一致性。

## Paxos 的两个组件

### Proposer

提议发起者，处理客户端请求，将客户端的请求发送到集群中，以便决定这个值是否可以被批准。

### Acceptor

提议批准者，负责处理接收到的提议，他们的回复就是一次投票。会存储一些状态来决定是否接收一个值

## 简单说来
阶段1：确定谁的编号最高，只有编号最高者才有权利提交proposal；

阶段2：编号最高者提交proposal，如果没有其他节点提出更高编号的proposal，则该提案会被顺利通过；否则，整个过程就会重来。

## 详细来说
prepare 阶段：
1. Proposer 选择一个提案编号 n 并将 prepare 请求发送给 Acceptors 中的一个多数派；
2. Acceptor 收到 prepare 消息后，如果提案的编号大于它已经回复的所有 prepare 消息，则 Acceptor 将自己上次接受的提案回复给 Proposer，并承诺不再回复小于 n 的提案；

acceptor阶段：
1. 当一个 Proposer 收到了多数 Acceptors 对 prepare 的回复后，就进入批准阶段。它要向回复 prepare 请求的 Acceptors 发送 accept 请求，包括编号 n 和根据 prepare阶段 决定的 value（如果根据 prepare 没有已经接受的 value，那么它可以自由决定 value）。
2. 在不违背自己向其他 Proposer 的承诺的前提下，Acceptor 收到 accept 请求后即接受这个请求。

prepare阶段有两个目的，第一检查是否有被批准的值，如果有，就改用批准的值。第二如果之前的提议还没有被批准，则阻塞掉他们以便不让他们和我们发生竞争，当然最终由提议ID的大小决定。