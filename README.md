# spring-boot-jpa-rest
Spring Boot Accessing JPA Data with REST

这是一个Spring boot演示的例子

src/resources/application.yml中配置redis参数等

请在终端中执行

```
./gradlew bootRun 
```

访问 http://localhost:9000/swagger-ui.html 查看自定义Controller API

通过tokens POST 获取token，然后在请求中带上

```
X-Auth-Token : token
```

访问http://localhost:8080/api即可。




如需打包war，请执行

```
./mvnw package    
```

所有请求请在header中加入 

```
X-Auth-Token : XXX
```

如有问题请联系 wangxiao@wafersystems.com

