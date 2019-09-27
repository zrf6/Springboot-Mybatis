# Springboot-Mybatis

通过Maven整合Springboot和Mybatis，开发平台为IDEA

配置了

- fastjson消息内容转换（WebMvcConfig中配置，通过注解）
- 自定义日期转换（StringToDateConverter类进行转换，在WebMvcConfig中进行注册）
- Spring AOP切面ServiceImp日志（TheLogger中配置）
- 文件上传功能（Springboot不需要配置文件上传的Bean，直接注入依赖后。用@RequestParam注解即可获取前端ajax请求提交的FormData封装的form表单Input file）

数据库user表SQL如下：

```mysql
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `up_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
```

实现了基本的增删改查，包括日期转换，JSON数据转换，文件上传。浏览器图片显示还有问题。因为Maven部署Springboot项目和一般ssm项目load到tomcat不一样，是直接打war包，再通过Springboot内置的tomcat访问。路径不在webapps下，所以无法通过直接上传到项目目录下来访问图片（这样做只是比较简单）。实际项目中，浏览器要访问服务器磁盘图片最好通过配置静态资源的方式，如nginx。后续有时间会继续push代码。
