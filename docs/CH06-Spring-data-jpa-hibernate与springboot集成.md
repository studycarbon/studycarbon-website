## CH06 - Spring Data JPA

* jpa简介

* spring data jpa 用法介绍
* spring data jpa,hibernate与springboot集成
* 数据持久化实战

### jpa简介

#### 什么是jpa？

* jpa（java persistence api)用于管理java EE和java SE环境中的持久化，以及对象/映射关系的Java api
* 最新规范为"jsr 338:java persistence 2.1" https://jcp.org/en/jsr/detail?id=338
* 实现:eclipseLink,Hibernate，apache OpenJPA

#### jpa核心概念

* 实体表示关系数据库中的表
* 每个实体示例对应于该表中的行
* 类必须用javax.persistence.Entity注解

* 类必须有一个public或者protected无参数的构造函数

* 实体实例被当作值以分离对象方式进行传递（如通过会话bean的远程业务接口），则该分类必须实现Serializable接口

* 唯一对象标识符：简单主键（javax.persistence.Id),复合主键（javax.persistence.EmbeddedId和javax.persistence.IdClass)

* 关系

  * 一对一 @OneToOne
  * 一对多 @OneToMany
  * 多对一 @ManyToOne
  * 多对多 @ManyToMany

* EntityManager

  * 定义用于与持久性上下文交互的方法
  * 创建和删除持久实体实例，通过实体的主键查找实体
  * 允许在实体上运行查询

  * 获取EntityManager实例

    ```java
    @PersistenceUnit
    EntityManagerFactory emf;
    EntityManager em;
    @Resource
    UserTransaction utx;
    ...
    em = emf.createEntityManager();
    try {
        utx.begin();
        em.persist(someEntity);
        em.merge(AnotherEntity);
        em.remove(ThirdEntity);
        utx.commit()
    } catch(Exception e) {
        utx.rollback();
    }
    ```

  * 查找实体

    ```
    @PersistenceContext
    EntityManage em;
    public void enterOrder(int custID, CustomerOrder newOrder) {
    	Customer cust = em.find(Customer.class, custID);
    	cust.getOrders().add(newOrder);
    	newOrder.setCustomer(cust);
    }
    ```

#### Spring Data jpa简介

* 是更改spring data 家族的一部分

* 对基于jpa的数据访问层增强支持

* 更容易构建基于使用spring数据访问技术栈的应用程序

* Spring data jpa常用接口

  CrudRepository

  ```java
  public interface CrudRepository<T, ID extends Serializable> extends REpository<T,ID> {
      <S extends T> S save(S entity);
      T findOne(ID primaryKey);
      Iterable<T> findAll();
      Long count();
      void delete(T entity);
      boolean exists(ID primaryKey);
      ....
  }
  ```

  PagingAndSortingRepository:实现排序和分页的

  ```java
  public interface PaingAndSortingRepository<T,ID extends Serializable> extends CrudRepository<T,ID> {
      Iterable<T> findAll(Sort sort);
      Page<T> findAll(Pageable pageable);
  }
  ```

* Spring Data Jpa自定义接口

```java
public interface PersonRepositoy extends Repository<User, Long> {
    List<Person> findByEmailAddressAndLastName(EmailAddress emailAddress, String lastname);
    List<Person> findDistinctPeopleByLastNameOrFirstName(String lastname,String firstname);
    ....
}
```

### spring data jpa, hibernate与spring boot集成

#### 配置环境：

>mysql community server 5.7.17
>
>spring data jpa 1.11.1.Release
>
>hibernate 5.2.8.final
>
>mysql connector/j 6.0.5

#### 修改pom.xml

>```
><dependency>
><groupId>org.springframework.boot</groupId>
><artifactId>spring-boot-starter-data-jpa</artifactId>
></dependency>
>
><dependency>
><groupId>mysql</groupId>
><artifactId>mysql-connector-java</artifactId>
></dependency>
>```

#### 直接启动报错

![image-20221122174101027](E:\CodeLibraries\studycarbon-website\docs_Img\image-20221122174101027.png)

参考博客：[开发工具 - springboot整合h2数据库_祈望每天自然醒的博客-CSDN博客_springboot整合h2](https://blog.csdn.net/yeahPeng11/article/details/120257881?ops_request_misc=%7B%22request%5Fid%22%3A%22166911009716800180644098%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=166911009716800180644098&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-120257881-null-null.142^v66^control,201^v3^control_2,213^v2^t3_esquery_v3&utm_term=springboot h2数据库&spm=1018.2226.3001.4187)

#### 添加h2依赖后，启动正常.

```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
	<version>1.4.193</version>
</dependency>
```

#### 启动完成后，在浏览器中输入 [localhost:8080/h2-console](http://localhost:8080/h2-console)

![image-20221122175126722](C:\Users\TS\AppData\Roaming\Typora\typora-user-images\image-20221122175126722.png)

#### 在yml中启用控制台

```
spring:
  h2:
    console:
      enabled: true

```

#### mysql整合jpa

pom.xml配置

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost/blog?userSSL=false&serverTimezone=UTC&characterEncoding=utf-8 #设置jdbc url
    username: root                              #用户名
    password: 123456                            #密码
    driver-class-name: com.mysql.cj.jdbc.Driver # 驱动项配置

  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create-drop

```

mysql创建blog数据库

![image-20221122190442580](./docs_Img/image-20221122190442580.png)

![image-20221122190509482](./docs_Img/image-20221122190509482.png)