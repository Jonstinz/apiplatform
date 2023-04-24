# apiplatform
**API开放平台** 

本平台可以让调用者注册并登录账号，通过申请API调用次数，使用各种API。同时管理员可以通过统一的管理界面，可以方便地添加、删除和更新接口，并对接口进行版本管理，并且提供API调用情况和数据的可视化界面。
![主页](https://user-images.githubusercontent.com/90243245/233917532-b8b1f25e-ce17-4caf-95e5-aa273e5100da.png)
![数据可视化](https://user-images.githubusercontent.com/90243245/233917863-c54e0bbb-653c-478b-8409-8491f8b60262.png)
![API调用](https://user-images.githubusercontent.com/90243245/233918108-004d7229-dffb-42b4-b41d-2dd721dca141.png)  
**技术栈** 

前端：
+ Ant Design Pro V6.0  
+ Ant Design & Procomponents组件库
+ React 18
+ Umi 4  

后端：
+ Spring Boot
+ Spring Cloud Gateway
+ Spring Boot Starter
+ Dubbo
+ Nacos
+ Redis
+ Swagger + Knife4j 
+ MyBatisPlus
+ Mysql

# 项目亮点
![系统架构](https://user-images.githubusercontent.com/90243245/233950284-076b09b5-70fb-4ce7-976b-f2b831ee1535.jpg)
+ 整个项目后端划分为WEB系统、公共模块、客户端SDK、API网关、接口服务器。
+ 后端使用Swagger + Knife4j自动生成OpenAPI规范接口文档，前端只需在此基础上使用插件自动生成接口请求代码，从而降低前后端协助成本。
+ 基于Spring Boot Starter 开发了客户端SDK，一行代码即可调用接口。
+ 签名认证算法，用户在注册账号时同时生成唯一的AK/SK用于鉴权，保障调用的安全性。
+ 使用Dubbo RPC框架实现子系统间的高性能接口调用
+ 使用Spring Cloud Gateway作为API网关，实现动态路由转发、访问控制、流量染色、签名校验、调用统计、日志等业务逻辑。
