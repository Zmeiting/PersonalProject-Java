    * [(一) 命名规约]
类命 驼峰式 MarcoPolo

方法名 localValue

常量 大写单词， 单词间_分割，语义清楚 MAX _ STOCK _ COUNT

抽象类 以Abstract /Base开始，异常类用 Exception结束，测试用Test结尾

boolean类型，变量不要用is开头

包名统一英文单词单数形式，不使用缩写

接口中不加修饰，public 不要加

形容能力的接口使用-able结尾

    * [(二) 代码格式]
左小括号/右和字符之间不出现空格，if / for / while / switch / do 等保留字与括号之间都必须加空格

二目、三目运算符的左右两边都需要加一个空格

第二行相对第一行缩进 4 个空格，其他不缩进

传参要多个空格隔开

不同的业务逻辑之间或者不同的语义之间插入一个空行。相同业务逻辑和语义之间不需要插入空行

    * [(三) OOP规约]
访问类中静态方法，不用对象引用类，直接用类名来进行访问。

过时接口，@ Deprecated 注解

不能使用过时的类或方法

常量或确定有值的对象来调用equals，" test " .equals(object);

包装类对象之间值的比较,全部使用 equals 方法比较

对于 Integer var = ?

在-128 至 127 范围内的赋值, Integer 对象是在IntegerCache . cache 产生,会复用已有对象,这个区间内的 Integer 值可以直接使用==进行判断,但是这个区间之外的所有数据,都会在堆上产生,并不会复用已有对象,这是一个大坑,

推荐使用 equals 方法进行判断。

POJO 类属性必须使用包装数据类型，RPC 方法的返回值和参数必须使用包装数据类型

所有的局部变量使用基本数据类型。

定义 DO / DTO / VO 等 POJO 类时,不要设定任何属性默认值

POJO 类必须写 toString 方法。

字符串的连接方式,使用 StringBuilder 的 append 方法进行扩展。

    * [(四) 集合处理]
只要重写 equals ,就必须重写 hashCode 。如果自定义对象做为 Map 的键,那么必须重写 hashCode 和 equals。

ArrayList 的 subList 结果不可强转成 ArrayList ,否则会抛出 ClassCastException异常,即 java . util . 

RandomAccessSubList cannot be cast to java . util . ArrayList .

Arrays . asList() 把数组转换成集合时,不能使用其修改集合相关的方法,它的 add / remove / clear 方法会抛出 UnsupportedOperationException 异常。asList 的返回对象是一个 Arrays 内部类,并没有实现集合的修改方法。

泛型通配符<? extends T >来接收返回的数据,此写法的泛型集合不能使用 add 方法,而 <? super T> 不能使用 get 方法,做为接口调用赋值时易出错。第一、频繁往外读取内容的,适合用<? extends T >。第二、经常往里插入的,适合用 <? super T> 。

不要在 foreach 循环里进行元素的 remove / add 操作。 remove 元素请使用 Iterator方式,如果并发操作,需要对 Iterator 对象加锁。

集合初始化时,指定集合初始值大小。说明: HashMap 使用 HashMap(int initialCapacity) 初始化,
正例:initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子(即 loaderfactor)默认为 0.75, 如果暂时无法确定初始值大小,请设置为 16(即默认值)。

使用 entrySet 遍历 Map 类集合 KV ,而不是 keySet 方式进行遍历。而 entrySet 只是遍历了一次就把 key 和 value 都放到了 entry 中,效率更高。

集合类 | Key | Value | Super | 说明

---|------|------|-----|------|---

Hashtable |不允许为 null | 不允许为 null | Dictionary | 线程安全

ConcurrentHashMap| 不允许为 null| 不允许为 null |AbstractMap | 锁分段技术(JDK8:CAS)

TreeMap| 不允许为 null |允许为 null |AbstractMap| 线程不安全

HashMap |允许为 null| 允许为 null |AbstractMap | 线程不安全

ConcurrentHashMap 存储 null 值时会抛出 NPE 异常.

    * [(五) 并发处理]
线程资源必须通过线程池提供,不允许在应用中自行显式创建线程。

线程池不允许使用 Executors 去创建,而是通过 ThreadPoolExecutor 的方式,这样的处理方式让写的同学更加明确线程池的运行规则,规避资源耗尽的风险。

SimpleDateFormat 是线程不安全的类,一般不要定义为 static 变量,如果定义为static ,必须加锁,或者使用 DateUtils 工具类。

private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
@ Override
protected DateFormat initialValue() {
return new SimpleDateFormat("yyyy-MM-dd");
}
};

jdk8 可以使用DateTimeFormatter代替simpleDateFormat

多线程并行处理定时任务时, Timer 运行多个 TimeTask 时,只要其中之一没有捕获

抛出的异常,其它任务便会自动终止运行,使用 ScheduledExecutorService 则没有这个问题。

避免 Random 实例被多线程使用,虽然共享该实例是线程安全的,但会因竞争同一

seed 导致的性能下降。在 JDK 7 之后,可以直接使用 API ThreadLocalRandom.

在并发场景下,通过双重检查锁 (double - checked locking) 实现延迟初始化的优

化问题隐患 ( 可参考 The " Double - Checked Locking is Broken " Declaration) ,推荐解

决方案中较为简单一种 ( 适用于 JDK 5 及以上版本 ) ,将目标属性声明为 volatile 型 。

volatile 解决多线程内存不可见问题。对于一写多读,是可以解决变量同步问题,

但是如果多写,同样无法解决线程安全问题。如果是 count ++操作,使用如下类实现:

AtomicInteger count = new AtomicInteger(); count . addAndGet( 1 ); 如果是 JDK 8,推

荐使用 LongAdder 对象,比 AtomicLong 性能更好 ( 减少乐观锁的重试次数 ) 。

HashMap 在容量不够进行 resize 时由于高并发可能出现死链,导致 CPU 飙升,在

开发过程中可以使用其它数据结构或加锁来规避此风险。

    * [(六) 控制语句]
表达异常的分支时,少用 if-else 方式 ,这种方式可以改写成

if (condition) {
...
return obj;
}

方法的返回值可以为 null ,不强制返回空集合,或者空对象等,必须添加注释充分

说明什么情况下会返回 null 值。调用方需要进行 null 判断防止 NPE 问题。

定义时区分 unchecked / checked 异常,避免直接抛出 new RuntimeException() ,

更不允许抛出 Exception 或者 Throwable ,应使用有业务含义的自定义异常。

应用中不可直接使用日志系统 (Log 4 j 、 Logback) 中的 API ,而应依赖使用日志框架

SLF 4 J 中的 API ,使用门面模式的日志框架,有利于维护和各个类的日志处理方式统一。

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

private static final Logger logger = LoggerFactory.getLogger(Abc.class);

避免重复打印日志,浪费磁盘空间,务必在 log 4 j . xml 中设置 additivity = false 。

正例:
<logger name="com.taobao.dubbo.config" additivity="false">

    * [(七) 单元测试]
单元测试应该是全自动执行的,并且非交互式的。测试框架通常是定期执行的,执行

过程必须完全自动化才有意义。输出结果需要人工检查的测试不是一个好的单元测试。单元测

试中不准使用 System.out 来进行人肉验证,必须使用 assert 来验证.

    * [(八) MYSQL]
表达是与否概念的字段,必须使用 is _ xxx 的方式命名,数据类型是 unsigned tinyint( 1 表示是,0 表示否 )

主键索引名为 pk_ 字段名;唯一索引名为 uk _字段名 ; 普通索引名则为 idx _字段名。

小数类型为 decimal ,禁止使用 float 和 double 。

表必备三字段: id , gmt _ create , gmt _ modified

表的命名最好是加上“业务名称_表的作用”

超过三个表禁止 join 。需要 join 的字段,数据类型必须绝对一致 ; 多表关联查询时,

保证被关联的字段需要有索引。

页面搜索严禁左模糊或者全模糊,如果需要请走搜索引擎来解决。索引文件具有 B - Tree的最左前缀匹配特

性,如果左边的值未确定,那么无法使用此索引。

不要使用 count( 列名 ) 或 count( 常量 ) 来替代 count( * ) , count( * ) 是 SQL 92 定义的

标准统计行数的语法,跟数据库无关,跟 NULL 和非 NULL 无关。count( * ) 会统计值为 NULL 的行,而 

count( 列名 ) 不会统计此列为 NULL 值的行。

count(distinct col) 计算该列除 NULL 之外的不重复行数,注意 count(distinct

col 1, col 2 ) 如果其中一列全为 NULL ,那么即使另一列有不同的值,也返回为 0。

当某一列的值全是 NULL 时, count(col) 的返回结果为 0,但 sum(col) 的返回结果为

NULL ,因此使用 sum() 时需注意 NPE 问题。可以使用如下方式来避免 sum 的 NPE 问题: SELECT IF

(ISNULL(SUM(g)) ,0, SUM(g)) FROM table;

使用 ISNULL() 来判断是否为 NULL 值。NULL 与任何值的直接比较都为 NULL。

在代码中写分页查询逻辑时,若 count 为 0 应直接返回,避免执行后面的分页语句。

不得使用外键与级联,一切外键概念必须在应用层解决。

    * [(九) 服务器]
高并发的服务器建议调小TCP协议的time_wait超时时间。操作系统默认是240秒才会关闭处于time_wait的

链接，高并发下服务端会因为处于time_wait连接数太多，无法建立新连接，需要调小等待值。

在linux服务器上通过变更/etc/sysctl.conf修改缺省值

net.ipv4.tcp_fin_timeout = 30

调大服务器所支持最大文件的句柄数。主流操作系统将TCP/UDP连接采用与文件一样的连接方式管理，一个连接对应一个fd.

linux 默认 fd数为1024.并发数过大会导致“open too many files”错误。

给JVM设置-XX:+HeapDumpOnOutOfMemoryError参数，让JVM碰到OOM输出Dump

线上JVM的Xms初始堆大小和Xmx最大堆大小一样存储容量，避免GC调整给堆带来压力

服务器内重定向使用forward,外部重定向使用URL拼装工具生成，否则带来URL维护不一致问题。

    * [(十) 二分库依赖]
线上应用不要依赖snapshot版本，不依赖是保证发布的幂等性。

二方库的新增或者升级，保持除功能点之外的其他jar包仲裁结果不变。如果有改变，必须明确评估和验证
，建议进行dependency:resolve前后信息对比，如果仲裁结果完全不一致，通过dependency:tree找出差异点，进行excludes排除jar包。

二方库里可以定义枚举类型，参数可以使用枚举类型，但是接口返回值不允许使用枚举类型，或包含枚举类型的pojo

依赖于一个二方库时，必须定义一个统一的版本，避免版本号的不一致。

    * [(十一) 应用分层]
在Dao层，无法用细粒度异常进行catch,所以使用catch(Exception e) 方式，并throw new 

DAOException(e) 不进行打印。

在manager/service层进行捕获，并打印到日志中，service层将日志输出到磁盘，web层跳转到友好界面。

    * [(十二) ORM映射]
在表进行查询中一律不使用*作为查询字段列表，需要那些字段必须写明。

pojo属性不能加is,数据库字段必须加is_,需要在mybatis生成器中将代码进行修改。

sql.xml配置参数使用 #{}，不要使用${}这种方式容易出现SQL注入

不允许直接拿HashMap和HashTable作为查询结果集的输出。

事务不要滥用，事务影响数据库的QPS,使用事务的地方需要考虑各方面的回滚。

    * [(十三) SQL语句]
count(distinct col) 计算该列除NULL之外的不重复行，注意count(distinct col1,col2)如果其中一列全为

null,即使另一列有不同值也返回0。

当某一列值全为null,count(col)返回结果为0，sum(col)返回结果为NULL,因此Sum(col)要注意NPE问题。

可以用

select if(isnull(sum(g)),0,sum(g)) from table;

使用ISNULL()来判断是否为NULL值，NULL值与任何值比较都为NULL值。

禁止使用存储过程，存储过程难以调试和扩展，更没有移植性。

数据订正时，删除和修改记录，要先select,避免出现误删除，确认无误避免出现误删除。

in操作能避免则避免，实在不能避免要估计in后边集合的数量，控制在1000个之内。

如果有全球化的需要，所有的字符存储以utf-8来进行存储，同时注意

select length("轻松工作")；返回12

select character_length("轻松工作")； 返回4

存储表情用utfmb4来进行存储，注意它与utf-8的区别。

不建议使用truncate

    * [(十四) 索引规约]
业务上具有唯一特性的字段，即使多个字段的组合，也必须构建唯一索引。

在varchar上创建索引，必须指明索引的长度，没有必要对全字段建立索引，根据实际文本区分度决定索引长度即可。

count(distinct left(列名，索引长度))/count(*)

如果有order by 的场景，请注意利用索引的有序性。order by最后的字段是组合索引的一部分，并且放在索引组合顺序的最后，避免出现file_sort的情况，影响查询性能。

索引 a_b_c      where a = ? and b = ? order by c 
索引中有范围查找时，索引的有序性无法利用，where a > 10 order by b; 索引 a_b无法发生。

利用覆盖索引来进行查询，避免回表，能够建立索引的种类：主键索引、唯一索引、普通索引、而覆盖索引是一种查询的一种效果，用explain的结果，extra列会出现，using index

利用延迟关联或者子查询优化差多分页场景。

SQL的性能目标，至少要达到range级别，要求是ref级别，如果可以是consts最好

consts单表中最多只能有一个匹配行，在优化阶段即可读取到数据

ref 指的是使用普通索引

range 对于索引进行范围检索

建立组合索引的时候，区分度最高的在最左面，如果 where a = ? and b = ? a列几乎接近于唯一值，那么只需要单建idx_a索引即可。

存在非等号和等号混合判断条件时，在创建索引时，请把等号条件的列前置。

where a > ? and b = ? 即使a的区分度很高也需要b放在索引最前面。

防止字段类型不同所造成的隐式转化，导致索引失效。

创建索引要避免宁滥勿缺，认为查询需要创建一个索引，宁缺勿滥也不要，认为索引会消耗空间，拖慢更新和新增速度。

抵制唯一索引，认为唯一索引需要在应用层通过先查后插方式解决。

varchar是可变长字符串，不预先分配存储空间，长度不要超过5000，如果存储长度大于此值，定义字段
类型为text,独立出一张表，用主键来对应，避免影响其他字段索引效率。

单行表数据超过500万或者单行表容量超过2GB,才推荐进行分库分表。

合适的字符存储长度，不但节约数据库的表空间，节约索引的存储，更重要的是提升检索速度。

    * [(十五) 安全规约]
用户个人的页面必须进行权限校验。

用户敏感数据禁止直接展示，必须脱敏，手机号隐藏中间4位。

用户输入的sql参数严格禁止使用参数绑定或者metadata字段值限定，防止SQL注入，禁止字符串拼接SQL访问数据库。

用户请求传入的参数必须进行有效的验证：否则导致1.page size 过大内存溢出 2. 恶意order by 导致数据库查询慢3.任意重定向 4.SQL注入 5. 反序列化注入 6. 正则输入源串拒绝服务ReDos

禁止向HTML页面输出未经安全过滤或未正确转义的用户数据。

表单、AJAX提交必须执行CSRF安全过滤。

CSRF跨站请求伪造是一类常见的编程漏洞，对于存在CSRF漏洞的应用网站，攻击者可以事先构造好URL,只要受害用户一访问，后台便在用户不知情的情况下对数据库进行修改。

单元测试可以重复执行，不能受外界环境的影响，在设计时就要把SUT改为注入，在测试时使用spring这样的DI框架注入一个本地实现。

    * [(十六) 异常处理]
java 类库中定义的一类RuntimeException可以通过预先检查进行规避，而不应该通过catch进行处理，比如IndexOutOfBoundsException,NullPointerException

有try块放到事务代码中，catch后，需要事务回滚，一定注意手动回滚。

不能在finally中使用return，finally块中的return返回方法后结束执行，不会再执行try中return语句。

方法的返回值可以为null,不强制返回空集合和空对象，必须添加注释说明什么情况下返回为空

    * [(十七) 其他]
在使用正则表达式时要学会利用预编译，加快正则匹配速度，定义正则的时候不要在方法体内进行定义。

volocity调用POJO类属性的时候，建议直接使用属性名取值即可，模板引擎会自动按照规约调用Pojo的getXxx(),如果是boolean基本数据类型调用 isXxx(),如果Boolean包装对象，调用getXxx()的方法

后台输出给页面的变量必须加 ! {var} 会显示在页面上。

任何数据结构构造和初始化，都应该指定大小，避免数据结构无限增长吃光内存。

对于暂时被注释掉，后续可能恢复使用的代码片段，统一使用///来说明注释掉代码的理由。

原文链接：https://www.jianshu.com/p/6a88cf7b18e8