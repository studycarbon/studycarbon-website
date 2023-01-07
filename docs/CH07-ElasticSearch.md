### CH07-ElasticSearch

#### 全文搜索

如：在百度搜索框中搜索

 数据结构

>结构化：指定固定格式或者有限长度数据，如数据库，元数据等
>
>非结构化数据：邮件，word文件

非机构化数据搜索

>顺序扫描法：操作系统中比较常见，如搜索某个文件
>
>全文搜索：将非结构化数据的提取一部分信息转化为结构化数据，最后搜索结构化搜索。将非结构化数据转化为结构化数据就是建立索引的过程。

概念：全文搜索是一种将文件中所有文本与搜索向匹配的文字资料检索方法。

#### 全文搜索原理

建立文本库 -> 建立索引（提取规律：偏旁或者拼音）-> 执行搜索 -> 过滤结果

#### 全文搜索实现技术

基于java的开源实现

>lucene
>
>ElasticSearch
>
>solr



#### ElasticSerch 简介：

高度可扩展的开源全文搜索和分析引擎

快速地，近实时地对大数据进行存储，搜索和分析

用来支撑有复杂的数据搜索需求的企业级应用

#### ElasticSearch特点

分布式，高可用，多类型，多api，面向文档，近实时，基于lucene,基于apache协议

#### ElasticSearch核心概念

近实时：一般延迟在1秒左右

集群：一个或者多个，名称默认为ElasticSearch

节点：集群中的单台服务器，可自定义节点名称

索引：类似于字典中的目录

类型：对产品进行分类

文档：索引的基本单位，具体的产品的信息，对应实体

分片：数据比较大时候，需要进行分片

副本：对原数据进行拷贝，增加吞吐量和搜索量

#### ElasticSearch和spring boot集成

配置环境

>  Elasticsearch 2.4.4
>
>  spring data Elasticsearch 2.1.3.RELEASE 
>
>  jna 4.3.0 访问原生的一些东西，es需要这个依赖

pom.xml添加如下依赖

>```yml
><dependency>
><groupId>org.springframework.boot</groupId>
><artifactId>spring-boot-starter-data-elasticsearch</artifactId>
></dependency>
>
><dependency>
><groupId>net.java.dev.jna</groupId>
><artifactId>jna</artifactId>
><version>4.3.0</version>
></dependency>
>```

#### Elasticsearch实战

参考博客：[Spring Boot 集成 Elasticsearch_攻城狮·正的博客-CSDN博客_springboot @query kql](https://blog.csdn.net/ghdqfhw/article/details/113687869)

修改application.yml

>```
>elasticsearch:
>username: # 用户名
>password: #密码
>connection-timeout: 1s  # 连接超时时间
>socket-timeout: 30s #
>uris: ${ES_HOST:localhost}:${ES_PORT:9200} # 端口位置
>```

后台编码

文档：EsBlog

资源库：EsBlogRepository

资源库测试用例 EsBlogRepositoryTest

控制器：BlogController

启动es:

>点击elasticsearch.bat脚本即可

> https://blog.csdn.net/w184167377/article/details/122095865
>
> 启动ElasticSearch报错:error updating geoip database
>
> ingest.geoip.downloader.enabled: false