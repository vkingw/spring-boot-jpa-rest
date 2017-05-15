# spring-boot-jpa-rest
Spring Boot Accessing JPA Data with REST

这是一个演示的例子

请在终端中执行

```
./gradlew bootRun 
```

服务即可运行，访问http://localhost:8080/api即可。

如需打包war，请执行

```
./mvnw package    
```

所有请求请在header中加入 

```
X-Auth-Token : XXX
```

xxx可以是任意字符，这里只校验是否有X-Auth-Token，不校验其内容。

如有问题请联系 wangxiao@wafersystems.com

