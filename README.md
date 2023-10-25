## 是什么？

本内容是学习了 尚硅谷的 `JDBC`教程，配合 `mysql8.0` + `Druid`德鲁伊数据库来实现了`JDBC`操作。

## 整个框架

---
#### api.statment 中

`Statment`开头的两个文件就是用来练习最低端的和数据库进行操作。里面甚至没有引入`meta`操作列。

而`User`是配合之后的高级`BaseDAO`操作，来使用的公共类，可以暂时忽略。
`PreparedStatementLogin` 和 `PreparedStatementOther` 是来尝试一些稍微高级一点的用法，比如在`PreparedStatementOther`中涉及到了 主键回显，以及`addBatch`的批量插入，时间优化。
---
#### 在Druid中
我们尝试了 德鲁伊的两种配置，这个地方的代码纯粹就是尝试，对整个项目没有任何帮助。
但是其中的`properties`配置文件，则在所有使用`Druid`连接池的地方都使用了。

---
#### 在 Utils中
我们写了一些高级的包装操作，最开始写的是 `JDBCUtils` ,在其中我们尝试了最基本的和 `Druid`连接池的配合，并在同一个文件夹的`BaseUtilsJDBC`中付出了实践。
此阶段我们可以摆脱了每次的`class.forName`，那种大段的连接语句。并且每次主要需要连接，只需和连接池说一下就可以了。

`而JDBCUtilsPro`，则是最终版本的连接池的引入。因为其引入了`ThreadLocal`，当前线程的变量。此变量会存储当前线程的内容，且只有当前线程可以访问自己线程的东西。
目的就是一次很多操作的时候，我们不希望获得到的connection是分别生成的，那样就无法进行事务的操作(因为事务要求至少是一次`connection`中完成)，所以我们每次提供出去`connection`之前，判断一下当前线程里有没有`connection`存储，如果有的话，直接提供现有的即可。
随后这一用法 主要使用在了 `Bank/baseDAO`下

---
#### Bank中

我们最开始写了 `BankDAO`的基础`DAO`语法，用来写加钱和减钱基础的语句操作，然后直接和数据库进行通信，并且供其他高级语法调用。
随后在`BankService`中，我们引入了`事务`的机制，体现在代码中，就是将`setAutoCommit`变为`false`，阻止每句话的自动提交。
在`BaseDAO`下，因为他的名字还是`DAO`，所以我们虽然代码很复杂，但是还是创建了两个连接数据库的最基础的方法。增删改(`update`)，和查(`query`)。
在`update`中，我们使用了`JDBCUtilsPro`提供的连接，无需但心这是个新的连接，因为有`ThreadLocal`的存在。随后进行模板化的增删改操作，最后返回行数。
##### query
这里的`query`很有说法，也是没见过的领域。其中主要是复杂在：每次查询返回的结果不是唯一的，不知道是哪个类。所以要写一个可以接受任何类的写法，也就是使用模板。套用一下。
里面的`clazz.getDeclaredConstructor().newInstance();`语句本来是没有中间的`getDeclaredConstructor()`的。但是它提示已废弃，我就改成这样了，网上查的。
而中间的哪些`field`赋值什么的，就是反射机制。
最后在`preparedStatementCRUD`中试了试效果，很不错，很抽象，总感觉太简单了，句子太少了。

---

### 总结
总体来说 本次的就是 
1. 写一个`JDBCUtilsPro` 这样的和连接池配合的文件，随后把连接数据库、向外提供本线程的`connection`，释放连接操作写出来。
本质是和第三方的连接池配合，写好获得和释放的接口
2. 写一个`DAO`包，用来调用刚刚写好的 控制连接池的语句。本项目是`BaseDAO`。他负责提供一边是数据库，一边是向外伸出很简单的接口，供高级语法调用。
其实本来一个`BaseDAO`就够用，但是我们引入了第三方的德鲁伊，所以要外加一个`JDBCUtilsPro`。
3. `DAO`包写好了，该放到高级语法中去使用了，这里我们在`preparedStatementCRUD`中的`testInsert`和`testSelectPro`中分别试用了。
4. 2023年10月25日 欢迎补充