# 数据结构大梳理

由于以前没有将数据结构这一块理解透彻，在实际工作中碰到相关的问题老是要重新理解一遍，十分的耽误时间，最近刚好有时间来从头梳理一遍，感觉清晰了不少。

通过梳理常用的数据结构，发现这些数据结构的出现有两个很重要的特点，首先是所有新的数据结构的出现都是为了解决之前已有的数据结构的某些缺点，其次是所有新的数据结构的实现基本上不是凭空而来，而是借助于已有的数据结构而来。

下面我们就从最简单的数据结构——数组，开始说起吧。

## 数组

![](../img/algorithm/array.png)

数组是用一段连续的内存存储的，那么意味着只要数组中每个元素的占用空间相同，那么就可以从第一个元素的地址推断出其他任意元素的地址：`address_i = address_0 + i * element_size ` ，所以我们说根据下标来访问数组的时间复杂度为 `O(1)` 。

> 这里稍微提一个冷门的知识点，为什么数组的下标从 0 开始呢？有人便认为，如果是从 1 开始的话，那么地址计算公式则会变成：`address_i = address_0 + (i - 1) * element_size ` ，这样会多做一次减法运算，而数组作为最基础的数据结构应该优化到极致，所以选择从 0 开始。不过也有人认为是历史原因，最早 C 语言设计从 0 开始，后来者依次效仿而已。

但也正是由于它的优点造就了它的缺点——连续的内存让插入和删除都需要移动指定位置后面所有的元素。如何解决这样的问题呢？

## 链表

![](../img/algorithm/linked-list.png)

既然数组插入和删除元素这么麻烦，那么我们如何解决呢？不好意思，只能从这一弊端的源头解决——抛弃连续内存的存储方式，每个元素使用指针存储下一个元素的地址，这样在插入和删除的时候只需要修改上一个元素的指针即可，时间复杂度一下子从 `O(n)` 降到了 `O(1)` 。当然，我们在改变其缺点的同时，却牺牲了它的优点，访问的复杂度变成了 `O(n)` 。

此外，如果我们只保存了 `next` 指针，但有时候希望反向查找链表，那么是不是可以加一个 `pre` 指针呢？这便是双向链表了。

**注意**： 后面所有的数据结构都是以**数组**或者**链表**进行演变而来。

## 栈

![](../img/algorithm/stack.png)

栈的提出，其实是为了解决一类特殊的问题的，即要求后入先出，常见的有浏览器的回退功能、有效括号的判断等。本质上来说栈其实是为一种算法而专门设计的数据结构，实际中可以由数组或者链表实现。

## 队列

![](../img/algorithm/queue.png)

队列的提出，也是为了解决一类特殊的问题的。很容易想到就是需要排队的地方，也就是 FIFO 的思想——先到先得，不允许插队。本质上来说队也是为一种算法而专门设计的数据结构，可以由数组或者链表实现。

此外，如果存在这样的场景——队伍中有老弱病残，也就是优先级更高的数据，能不能让他插个队呢？这便是优先队列，一般由二叉堆来实现，这将在后面提到。

## 跳表

![](../img/algorithm/skip-list.png)

链表让我们可以从头到尾去搜索一个元素，可是如果链表是有序的，那么我们是不是可以每间隔几个节点提出一个元素作为索引，从而加快搜索呢？当然可以了，以此类推，我们还可以建立二级索引、三级索引……这便是跳表的思想了。它是为了解决什么问题呢？显然是为了优化搜索的时间复杂度，从 `O(n)` 优化到了 `O(log n)` 。

在数据结构的路上，我们记住两条人生中同样适用的哲理：

1. 为了追求某些东西，你可能会失去某些东西；
2. 为了追求某些东西，你可能要付出一些代价。

这里我们失去的东西就是增加了额外的存储空间，是典型的“**以空间换时间**”的做法。另外试想如果在插入或者删除时不更新索引的话，会造成索引中两个节点之间的节点特别多，在极端情况下还可能退化为单链表。为了维持这种高速的搜索，我们付出的代价就是在插入和删除元素的时候需要更新索引。结果是之前插入、删除的复杂度由 `O(1)` 变成了现在的 `O(log n)` 。

## 哈希表

![](../img/algorithm/hash.png)

在数组中，我们能够通过下标立即定位元素，也就是说数组其实就是一个整数到具体数据的映射，那么我们由此进行扩展，构建一个数据到数据的映射呢？这便是哈希表了。哈希表便是由数组进行实现，通过将 key 进行 hash 运算，得到数组的下标，从而快速定位具体的数据。也就是说我们通过一个哈希函数将任意类型的数据 key 映射到了具体的数据。因而哈希表也就有了 `O(1)` 的查找的时间复杂度，而且插入和删除也都是 `O(1)` ，如此高效的数据结构成了我们日常开发中不可或缺的角色。

然而，这里有些问题不得不提，那就是哈希函数的问题。既然使用了哈希函数，那就难免遇到**哈希冲突**的问题，即不同的 key 哈希成了相同的值。一般常用的解决方法有两种：

1. 开放寻址法
   * 线性探测法
   * 二次探测法
   * 双重哈希法
2. 链地址法

开放寻址法简单来说就是在遇到冲突时，继续寻找下一个可用的位置。具体来说有线性探测法，即按照顺序探测数组中可用的位置对数据进行存储或搜索。二次探测法则是按照一定的步长进行跳跃式探测。而双重哈希法则是使用一组哈希函数而非一个哈希函数，在一个哈希函数遇到冲突时便使用下一个哈希函数。

链地址法是一种更加常用的解决哈希冲突的方法，我们将数组中的每个位置变成一个链表，一旦有冲突了，将冲突的元素放在该位置的链表的末端。

其实这里我们可以看出问题来了，如果哈希函数选择不好，将大多数 key 到哈希到相同的数组位置，那么便退化为一个单链表了，查找的复杂度即为 `O(n)` 。

## 树

![](../img/algorithm/tree.png)

单链表中每个节点只有一个后继节点，试想如果一个节点有多个后继节点，再加一个根节点，那不就是树了吗？当然了，树也可以由数组来实现。一般的树可能没有太多实际应用的意义。所以更常见的是堆、二叉查找树、平衡二叉查找树、红黑树等。

## 堆

![](../img/algorithm/heap.png)

堆是一个完全二叉树，且每个节点总是不大于或者不小于其父节点的值。典型的应用场景就是利用二叉堆来实现上述的优先队列，以及解决 Top K 问题。一般由数组来实现。

## 二叉查找树

![](../img/algorithm/binary-search-tree.png)

首先看看二叉查找树的定义：

1. 若左子树不空，则左子树上所有结点的值均小于它的根结点的值
2. 若右子树不空，则右子树上所有结点的值均大于或等于它的根结点的值
3. 左、右子树也分别为二叉排序树

二叉查找树是二叉树中最常用的类型，顾名思义，二叉查找树为了实现快速查找而生的。同时，它还支持快速删除、插入一个数据，且时间复杂度均为 `O(log n)` 。这里我们思考个极端问题，就是如果每次删除的刚好都是右节点，那就可能退化为一个链表，而此时查找的时间复杂度也就降到了 `O(n)` ，那么如何维持这种 `O(log n)` 的高效查找的性能呢？当然就是**平衡二叉查找树**了！

## 平衡二叉查找树

平衡二叉查找树是**为了解决二叉查找树的退化问题**而生的，即不管怎么删除、插入数据，都能保证左右子树都是比较平衡的。

这里我们思考一个问题，既然已经有了哈希表这样查找、插入、删除的时间复杂度都为 `O(1)` 的数据结构了，为啥还需要一种查找、插入、删除的时间复杂度都为 `O(log n)` 的数据结构？一般来说有以下 3 个原因：

1. 哈希表是无序存储的，如果要输出有序的话，还要进行排序，但是二叉查找树只需要一次中序遍历即可在 `O(n)` 的时间复杂度内完成。
2. 哈希表的扩容由于需要复制元素，所以比较耗时，这也带来不稳定的因素。
3. 哈希表的哈希冲突问题，如果哈希函数的冲突问题严重，加上哈希函数本身的计算耗时，在某些时候不见得比平衡二叉查找树的效率高。

## AVL 树

AVL 树是最早被发明的平衡二叉树，而且它严格符合平衡二叉查找树的定义，即任何节点的左右子树的高度相差不超过 1 ，因此它是**高度平衡**的。它对各种不平衡的情况分别作了左旋或者右旋的处理，概括如下（不需要强加记忆，知道即可）：

1. 左-左型：做右旋。
2. 右-右型：做左旋。
3. 左-右型：先做左旋，后做右旋。
4. 右-左型：先做右旋，再做左旋。

## 红黑树

![](../img/algorithm/red-black-tree.png)

红黑树的结构，我用两个字来形容——复杂！它要满足如下条件：

1. 节点是黑色或者红色；
2. 根节点是黑色的；
3. 每个叶子节点都是黑色的空节点（NIL），即叶子节点不存储数据；
4. 每个红色节点必须有两个黑色的子节点（即从每个叶子到根的所有路径上不能有两个连续的红色节点）；
5. 从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。

红黑树并没有像 AVL 树那样严格符合平衡二叉树的定义，它从根节点到叶子节点的最长路径可能比最短路径大一倍，所以说它是**近似平衡**的，即不会退化的很严重。

所以说红黑树其实是对 AVL 树的一种改进，因为 AVL 树为了追求高度平衡，每次的插入和删除都要做很多的调整，这样对于有频繁的插入、删除的数据集合来说 AVL 树的代价就有点高了。而红黑树只是达到近似平衡，这样在维护平衡的成本上，要比 AVL 树低。但是其高度接近 `log n` , 因而也是 `O(log n)` 的时间复杂度，而且经证明其插入和删除非常稳定。

最后说一句，红黑树的实现真的挺复杂，但是和 AVL 树类似也是通过基本的左旋或右旋来保证上面的第4、5 两个条件的。

## B 树

![](../img/algorithm/b-tree.png)

既然有了平衡二叉查找树了，它的查找、删除、插入的速度都是 `O(log n)` 的，为什么在实际的数据库的索引中，我们采取的不是它呢？

因为数据库的索引通常很大，一般存储于磁盘上，当数据量大时，就不能把整个索引全部加载到内存了，只能逐一加载每一个磁盘页（对应索引树的节点）。所以我们要减少 I/O 次数，对于树来说，I/O 次数就是树的高度，那么我们如何减少树的高度呢？思路很简单，可以**在一个节点中存储多个数据**啊，不过这样一来根据二分查找的思想，其子节点必然也会增加，即如果一个节点如果包含3个关键字（数据），则进行查找运算时，将数据与这3个数据进行比较，可能会落到 4 个区间范围内的一个，因此其子节点会有 4 个。这便是 *m* 叉树了。

这样就很自然地引入了 B 树，它是一个 *m* 阶的树。由于不管是内存还是磁盘中的数据，操作系统都是按页（通常一页是 4 KB）来读取的，一次读取一页，如果读取的数据量超过一页的大小，则会触发多次 I/O 操作。所以我们在选择 *m* 的大小时，要尽量让每个节点的大小等于一个页的大小，从而减少 I/O 次数，提高性能。

下面是一个 *m* 阶的 B 树要满足的条件：

1. 每一个节点最多有 *m* 个子节点
2. 每一个非叶子节点（除根节点）最少有 ⌈*m*/2⌉ 个子节点
3. 如果根节点不是叶子节点，那么它至少有两个子节点
4. 有 *k* 个子节点的非叶子节点拥有 *k* − 1 个键
5. 所有的叶子节点都在同一层

同平衡二叉查找树一样，B 树的查找、删除、插入的时间复杂度都是 `O(log n)`。

此外，稍微提一下，为啥不用哈希表来存储索引呢？当然可以了，不过它只支持点查询（Point Search），如果要支持范围查询（Range Search）的话，无序的哈希表便排不上用场了。

## B+ 树

![](../img/algorithm/b-plus-tree.jpg)

顾名思义，B+ 树是对 B 树的一种改进，那么它做了哪些改进呢？

1. **B+ 树的非叶子节点不存储数据，只是索引**。因此能够容纳更多元素，从而让整个树更加“矮胖”，即减少了树的高度。同时 B+ 树必须查找要到叶子节点，而 B 树只要匹配即可，因此 B+ 树的性能更加稳定。
2. **B+ 树的叶子节点使用链表进行连接**。这对于范围查询来说，B+ 树只需遍历叶子节点的链表即可，而 B 树却需要进行中序遍历。

## LSM 树

![](../img/algorithm/log-structured-merge-tree.png)

看起来 B+ 树已经非常优秀了，提供了 `O(log n)` 的稳定读写性能，但是在大数据量写入的场景下，仍然不能满足我们的需求，因为它强调了数据被按照特定的方式进行存放，但是对写操作不友善。事实上磁盘顺序读写的性能要至少比随机读写快 3 个数量级，那我们能否利用这个特性呢？

这便是 LSM 树中关键的思想之一，其实 LSM 树并没有严格的定义，一般来说就是将写入推迟并转换为批量写，首先将大量写入缓存在内存，当积攒到一定程度后，将他们批量写入文件中，这样一次 I/O 可以进行多条数据的写入，充分利用每一次 I/O 。

因此，它是一种分层的组织数据的结构，LSM 树的节点可以分为两种：

* 保存在内存中的称之为 MemTable，由跳表或者 AVL 树等来实现
* 保存在磁盘上的称之为 SSTable（Sorted String Table），类似于 B 树的结构，存储的是一系列的键值对

简单来说，就是先写 MemTable，当 MemTable 大到一定程度会排序后追加写到 SSTable 中，这时候会有定期地对多个 SSTable 进行合并。读操作会先判断是否在 MemTable 上，如果不在则把所有可能的 SSTable 查找一遍。

总体来说，LSM 树就是一种分层的思想，通过牺牲读性能大量提升写性能，利用磁盘的顺序写，内存的快速读，以及局部优化（如给 SSTable 加索引，采用布隆过滤器等），因此特别适合大量插入操作的应用场景，不过在实际的应用中，往往有具体不同的实现和优化。

## 回顾

下面我们对上面的数据结构做一个简单的回顾——

**数组**：基于下标的 `O(1)` 的复杂度是它最大特点，缺点在于添加、删除元素比较麻烦，扩容成本大。

**链表**：让添加、删除元素的问题得到了很好的解决，带来的新问题是查找速度需要从头开始，比较费时。

**栈**：后入先出，为了解决一些特殊问题而生，如浏览器的回退功能、有效括号的判断等。

**队列**：先入先出，也是为了解决一些特殊问题而生，例如需要一些排队的场景，比如秒杀等。

**跳表**：通过“以空间换时间”的做法，在链表的基础上建立多极索引，优化查找性能。

**哈希表**：利用数组的随机访问，加上哈希函数，实现了查找、删除、插入都为 `O(1)` 的高效数据结构。（ps: 它是最爱的数据结构，没有之一！）

**树**：将链表竖起来，就是一棵树；一棵树如果只有左节点或者右节点，那就是一个链表。在一个树是完全二叉树时，使用数组最为节约空间。

**堆**：一棵完全二叉树，且每个节点总是不大于或者不小于其父节点的值，即一个大顶堆或者小顶堆。典型的应用是用于实现优先队列，以及解决 Top K 问题。

**二叉查找树**：二叉树对于左右节点的大小关系有严格的约束，带来的好处就是中序遍历便是一个排序好的结果。问题在于最坏情况会退化为一个单链表。

**平衡二叉查找树**：为了解决二叉查找树的退化问题，保证查找时的性能稳定，我们有了平衡二叉查找树。

**AVL 树**：最早提出的平衡二叉查找树，任何节点的左右子树的高度相差不超过 1 ，是高度平衡的二叉查找树，为了维持平衡需要在删除、插入时做频繁的左旋或者右旋操作。

**红黑树**：一种复杂的平衡二叉树，近似平衡，插入、删除操作很稳定，时间复杂度也是 `O(log n)` 。也是要通过左旋或者右旋来保证其平衡性。

**B 树**：为了解决存储大量数据时，减少 I/O 次数，将多个数据放在一个节点中，从而减少树的高度。

**B+ 树**：在 B 树上进行改进，非叶子节点不存储数据，只是索引，叶子节点使用链表进行连接。带来的好处是存储更多的索引，进一步降低树的高度；另外对于范围查询来说，B+ 树只需遍历叶子节点的链表即可，不需要像 B 树那样进行中序遍历。

**LSM 树**：一种分层的组织数据的结构，先写内存中 MemTable，写到一定程度则推到磁盘上的 SSTable，再定期对磁盘上 SSTable 进行合并。读取的时候依次查询 MemTable 和 SSTable。

## 常用数据结构的时间复杂度

![常用数据结构](../img/algorithm/data-structure.png)

## 总结

以上是我们常用的数据结构，了解其原理并不困难，难的是在于实现，幸好实际工作中并不需要我们自己实现，而且一般面试中也不会让我们去实现像红黑树这种复杂的数据结构。

在我看来，掌握这些数据结构的要点在于了解它们如何产生的，解决了什么问题，带来了什么新的问题，时间复杂度是多少，最坏的情况如何（即退化问题）。

此外，我们提到大部分的数据结构基本上都是由数组或者链表演变而来，那么这些数据结构也会继承数组或链表的优缺点。比如用到了数组的话，就要注意一个扩容的问题，因为数组是预先分配一段指定大小的内存的，所以当数据集合增长到一定程度，就可能要进行扩容，这带来的代价和其他问题都是要注意的。

举例来说，哈希表在扩容后，对 key 哈希的结果会变化，那么我们需要对以前的数据不光是简单的复制，还需要移动到新的哈希结果的位置。这个问题在单机且数据量较少的情况下可能还不严重，但是如果是在一个分布式存储中呢，我们对数据进行了分片，那么在增加一个节点后可能就需要移动所有的数据，这样的代价几乎不能接受。那么如何解决呢？那就需要进行**一致性 hash** 了，简单理解就是将哈希值形成一个环，哈希的结果离的最近的节点作为最终的结果。

因此，我们在使用数据结构的时候，一定要结合实际的场景，选择最适合的数据结构来达到我们的目标。