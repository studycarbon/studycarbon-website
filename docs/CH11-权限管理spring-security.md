## CH11-权限管理Spring Security

提供安全服务 

基于角色的角色管理，什么是角色？用户能做什么往往和角色相关联，限定能做什么不能做什么.

RBAC:基于角色的角色管理(role-based-access-control )

隐式访问控制：

```html

if(user.hasRole("project manager")) {

 //显示按钮

} else {

 //不显示按钮

}

```

显示访问控制:

```

if(user.isPermitted("projectReport:view:12345")) {

 //显示报表按钮

} else {

 //不显示报表按钮

}

```

解决方案: spring security



Spring security简介：



>spring security简介



spring security和spring boot集成



>spring security 4.2.2.RELEASE
>
>thymeleaf spring security 3.0.2.release
>
>pom.xml添加如下内容：

>```yaml
><dependency>
> <groupId>org.springframework.boot</groupId>
> <artifactId>spring-boot-starter-security</artifactId>
></dependency>
>
><dependency>
>  <groupId>org.thymeleaf.extras</groupId>
>  <artifactId>thymeleaf-extras-springsecurity4</artifactId>
>  <version>3.0.2.RELEASE</version>
></dependency>
>```
