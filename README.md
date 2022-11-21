# Carbon 中文网

## CH01-概述

功能：注册博主，发博客，评论，点赞，全文检索，文件上传...

技术：前端，后端，数据库，NoSql，文件存储，大数据...

### 功能

![image-20221121160236747](./docs_Img/image-20221121160236747.png)

![image-20221121160449229](./docs_Img/image-20221121160449229.png)

![image-20221121160553262](./docs_Img/image-20221121160553262.png)

![image-20221121160652340](./docs_Img/image-20221121160652340.png)

![image-20221121160743285](./docs_Img/image-20221121160743285.png)

![image-20221121160807231](./docs_Img/image-20221121160807231.png)

![image-20221121160837452](./docs_Img/image-20221121160837452.png)

![image-20221121160952581](./docs_Img/image-20221121160952581.png)

![image-20221121161032447](./docs_Img/image-20221121161032447.png)

### 技术

![image-20221121161546668](./docs_Img/image-20221121161546668.png)

![image-20221121161728317](./docs_Img/image-20221121161728317.png)

注：

>不采用H2，直接使用 Mysql，前期暂时也不适用 MongoDB，不使用 Hibernate，直接使用 Mybatis，不使用Gradle，直接采用maven，后续图片请更改。

### Spring Boot 是什么？

> * 为所有spring开发提供一个更快，更广泛的入门体验
> * 开箱即用，不适合时候也可以快速抛弃
> * 提供一系列大型项目常用的非功能性特征
> * 零配置（不需要xml配置，遵循“约定大于配置”）

### Spring Boot 简化开发

> 抛弃了传统JavaEE项目繁琐的配置，学习过程，让企业级应用开发变得简单。

### Spring Boot 和其他框架的关系

![image-20221121163016167](./docs_Img/image-20221121163016167.png)

## CH02-开启Spring Boot的第一个项目

### 环境配置

>jdk8
>
>apache-maven-3.6.1
>
>IntelliJ IDEA 2021.2

### 创建项目

![image-20221121164313794](./docs_Img/image-20221121164313794.png)

![image-20221121164452899](./docs_Img/image-20221121164452899.png)

删除如下文件

![image-20221121164620920](./docs_Img/image-20221121164620920.png)

删除文件后，重新加载studycarbon项目

### 运行项目

![image-20221121171632172](./docs_Img/image-20221121171632172.png)

## CH03-编写一个 Hello world 项目

在studycarbon包下面建立一个controller包，里面在建立一个类名为HelloWorldController，HelloWorldController里面内容如下：

```java
package cn.studycarbon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/helloWorld")
    public String helloWorld() {
        return "Hello world!";
    }
}

```

运行项目，并在浏览器中输入:[localhost:8080/helloWorld](http://localhost:8080/helloWorld)，可以观察到，浏览器页面中返回了，helloWorld字符串

![image-20221121173202901](./docs_Img/image-20221121173202901.png)

![image-20221121173150230](./docs_Img/image-20221121173150230.png)

## CH04-开发环境配置

暂无内容~~~