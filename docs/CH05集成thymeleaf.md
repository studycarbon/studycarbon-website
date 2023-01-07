## CH05-集成Thymeleaf

### Thymeleaf： java的模板引擎，

* 能够处理html，xml，javascript，css
* 自然模板。原型即界面
* 语法优雅一动。OGNL，SpringEL
* 遵循Web标准。支持HTML5

### Thymeleaf标准方言

* <span th:text="...">

* <span data-th-text="...">

* 一个简单的thymeleaf的示例

  需要引入th的命名空间才可以使用thymleaf的th标签

```html
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        ....
        <link rel="stylesheet" type="text/css" media="all" href="../../css/gtvg.css" th:href="@{/css/gtvg.css}"/>
    </head>
    <p th:text="#{home.welcome}">
        Welcome to our grocery store!
    </p>
</html>
```

![image-20221121180809689](./docs_Img/image-20221121180809689.png)

#### 变量表达式

语法：${...}

```html
<span th:text="${book.author.name}"></span>
```

#### 消息表达式

语法：#{...}

也称之为文本外部话，或者国际化

```html
<table>
    ...
    <th th:text="#{header.address.city}">...</th>
    <th th:text="#{header.address.country}">...</th>
    ...
</table>
```

#### 选择表达式

语法：*{...}

```html
<div th:object="${book}">
    ...
    <span th:text="*{title}">...</span>
    ...
</div>
```

#### 链接表达式

语法：@{...}

```html
<!--相对于应用程序上下文-->
<a th:href="@{../documents/report}">...</a>

<!--相对于服务器-->
<a th:href="@{~/documents/report}">...</a>

<!--相对于协议-->
<a th:href="@{//static.mycompany.com/res/initial}">...</a>

<!--link表达式绝对的-->
<a th:href="@{http://www.mycompany.com/main}">...</a>
```

#### 分段表达式

语法: th:insert 或者 th:replace

```html
<!DOCTYPE html>
<html xmls:th="http//www.thymeleaf.org">
    <body>
        <div th:fragment="copy">
            &copy;2017<a href="https://waylau.com">waylau.com</a>
        </div>
    </body>
</html>
```

```html
<body>
    ...
    <div th:insert="~{footer::copy}">
        
    </div>
    ...
</body>
```

#### 字面量

```html
<p>
    now you are looking at 
    <span th:text="working web application">tmplate file</span>
</p>
```

#### 数字

```html
<p>
    the year is <span th:text="2013">1492</span>
</p>
<p>
    in two years, it will be <span th:text="2013+2">1494</span>
</p>
```

#### 布尔

```html
<div th:if="${user.isAdmin()} == false">
    ...
</div>
```

#### null

```html
<div th:if="${variable.something} == null">...
</div>
```

#### 算术操作

+，-，*，/,%

```shell
<div th:with="isEven=$(${prodStat.count} % 2 == 0)"
```

#### 比较和等价

比较>,<,>=,<=(gt,lt,ge,le)

```html
<ul class="pagination" data-th-if="${page.totalPages le 7}">
    
</ul>
```

#### 等价 ==，!= （eq,ne)

```html
<option data-th-each="i : ${#arrays.toIntegerArray(5,10,40,100)}" data-th-value="${i}" data-th-selected="${i eq page.size}" data-th-text="${i}"></option>
```

#### 条件运算符

```html
<tr th:class="${row.even}?'even':'odd'">
...
</tr>
```

#### span 无操作

```html
<span th:text="${user.name?:_}">no user authenticated</span>
```

#### 设置属性值

```html
<form action="subscribe.html" th:attr="action=@{/subscribe}">
    <fieldset>
        <input type="text" name="email"/>
        <input type="submit" value="Subscribe!" th:attr="value=#{subscribe.submit}"/>
    </fieldset>
</
    form>
```

```html
<form action="subscribe.html" th:action="@{/subscribe}">
    
</form>
```

```html
<input type="submit" value="Subscirbe!" th:value="#{subscribe.submit}"/>
```

```html
<input type="checkbox" name="option2" checked> //html
<input type="checkbox" name="option1" checked="checked">//xhtml
<input type="checkbox" name="active" th:checked="${user.active}"> 
```

#### 迭代器

```html
<li th:each="book : ${books}" th:text="${book.title}"> En las Orillas del Sar</li>
```

状态变量：

>index,count,size,current,even/odd,first,last

```html
<table>
    <tr>
        <th>name</th>
        <th>price</th>
        <th>in stock</th>
    </tr>
    <tr th:each="prod,iterStat : ${prods}" th:class="${iterStat.odd}?'odd'">
        <td th:text="${prod.name}">onions</td>
        <td th:text="${prod.price}">2.41</td>
        <td th:text="${prod.inStock}?#{true}:#{false}">yes</td>
    </tr>
</table>
```

#### 条件语句

```html
<a href="comments.html" th:href="@{/product/comments(prodId=$(prod.id))}"></a>
<th:if="${not #list.isEmpty(prod.comments)}">view</th:if>
```

```html
<a href="comments.html" th:href="@{/product/comments(prodId=$(prod.id))}"></a>
<th:unless="${not #list.isEmpty(prod.comments)}">view</th:if>
```

```html
<div th:switch="${user.role}">
    <p th:case="admin">
        user is an admin
    </p>
    <p th:case="#{roles.manager}">
        user is an manager
    </p>
    <p th:case="*">
        user is other
    </p>
</div>
```

#### 模板布局

定义片段

```html
<!DOCTYPE html>
<html xmlns:th="http://www/thymeleaf.org">
    <body>
        <div th:fragment="copy">
            &copy;2017<a href="https://waylau.com">waylau.com</a>
        </div>
    </body>
</html>
```

```html
<div id="copy-section">
    &copy;2017<a href="https://waylau.com">waylau.com</a>
</div>
```



引用片段

```html
<body>
    ...
    <div th:insert="~{footer :: copy}">
        
    </div>
    ...
</body>
```

```html
<body>
    ...
    <div th:insert="~{footer :: #copy-section">
        
    </div>
    ...
</body>
```

th:insert,th:replace,th:include区别：

```html
<body>
    <div th:insert="footer::copy">
    </div>
    <div th:replace="footer::copy">
    </div>
    <div th:include="footer::copy">
    </div> 
</body>
```

#### 属性优先级

当在同一个标签中写入多个th:*属性时，会发生什么？

```html
<ul>
    <li th:each="item : ${items}" th:text="${item.description}"> Item description here....</li>
</ul>
```

| order | feature      | attributes                           |
| ----- | ------------ | ------------------------------------ |
| 1     | 片段引用     | th:insert/th:replace                 |
| 2     | 遍历         | th:each                              |
| 3     | 条件语句     | th:if/th:unless/th:swich/th:case     |
| 4     | 本地变量定义 | th:object/th:with                    |
| 5     | 通常属性设置 | th:attr/th:attrprepend/th:attrappend |
| 6     | 特殊属性设置 | th:value/th:href/th:src ...          |
| 7     | 文本         | th:text/th:utext                     |
| 8     | 片段声明     | th:fragment                          |
| 9     | 片段移除     | th:remove                            |

#### 注释

* thymeleaf解析器级别注释快

删除<!--/*-->和<!--*/-->之间内容

```html
<!--/*-->
...
<!--*/-->
```

* 原型注释块

当模板静态打开时，原型注释快所注释代码将被注释，模板执行时，这些注释代码，将被显示出来。

```html
<!--/**/
...
/*/-->
```

#### 内联

[[...]]或[(...)]分别对应于th:text和th:utext

```html
<p>
    the msg is "[(${msg})]"
</p>

<p>
    the msg is "<b>great!</b>"
</p>

```

```html
<p>
    the msg is "[(${msg})]"
</p>

<p>
    the msg is "&lt;b&gt;great!&lt;/b&gt;"
</p>
```

禁用内联

```html
<p th:inline="none">
    a double array looks like this:[[1,2,3],[4,5]]!</p>
</p>
```

javascript内联

```html
<script>
    ...
    var username=/*[[${session.user.name}]]*/ "Gertrud kiwifruit"
    ...
</script>
```

css内联

```html
classname='main elems'
align='center'
<style>
	.[[${classname}]] {
		text-align:[[${align}]]
	}
</style>
```

#### 表达式基本对象

基本对象

>#ctx:上下文对象。是org.thymeleaf,context.IContext或者org.thymeleaf.context.IWebContext实现
>
>#locale
>
>param
>
>session
>
>application

web上下文对象

>#request
>
>#session
>
>#servletCtx

### thymeleaf和Spring Boot集成

pom.xml添加如下内容

>```xml
><dependency>
><groupId>org.springframework.boot</groupId>
><artifactId>spring-boot-starter-thymeleaf</artifactId>
></dependency>
>```

application.yml（需要新建这个文件，同时删除application.properties文件)并添加如下内容：

```
spring:
  thymeleaf:
    cache: false 
    prefix: classpath:/templates/
    encoding: UTF-8 #编码
    suffix: .html #模板后缀
    mode: HTML #模板
```

配置说明：

 **cache**这一行是将页面的缓存关闭，不然我们改变页面之后可能不能及时看到更改的内容，默认是true。

 **prefix**是配置thymeleaf模板所在的位置。

 **encoding** 是配置thymeleaf文档的编码，后面的就不说了

注：更详细情况请参考：[SpringBoot系列（六）集成thymeleaf详解版 - 全栈学习笔记 - 博客园 (cnblogs.com)](https://www.cnblogs.com/swzx-1213/p/12726432.html)

#### thymeleaf实战

> ThymeleafUserController
>
> ThymeleafUserDao
>
> ThymeleafUser
>
> template/ThymeleafUse