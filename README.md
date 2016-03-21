# JavaWeb
# JavaWeb基础框架：
SpringMVC+Mybatis+Mysql+Tomcat+Java8+Bootstrap+AngularJS
# 说明：
# 一、本项目简介
本项目是一个JavaWeb基础框架，本项目为开源项目(Apache License V2.0)
# 二、使用技术
1.SpringMVC restful（latest）作为后端主体
2.Mybats（latest）作为数据库层的操作
3.Maven（latest）作为jar包管理
4.Tomcat（latest）作为应用服务器
5.Java8（latest）作为后端主体开发语言
6.Mysql5.6（latest）作为后端主体关系型数据库
7.MongoDB（latest）作为后端辅助非关系型数据库
8.Shiro（latest）作为后端权限管理框架
9.Bootstrap（latest）作为前端主要开发框架
10.AngularJS（latest）作为前端主要开发框架
# 三、如何快速部署运行本项目
1.安装jdk8或更高版本
2.安装tomcat7或更高版本
3.安装mysql5.6或更高版本
4.安装Maven较新版本
5.导入maven项目
6.进入项目目录运行：mvn clean install
7.进入项目下的target目录，找到JavaWeb.war
8.将JavaWeb.war拷贝至tomcat的webapp目录下，然后运行tomcat即可
# 四、其它说明
1、关于框架
框架初搭还有很多不完善，功能会在以后有需要的情况下陆续添加，但是满足简单的功能要求还是没问题的
2、关于业务代码编写方式
你只需要关注controller(请求分发、跳转及异常处理)->service(业务逻辑处理)->dao(数据库访问)就可以了
3、关于单元测试
从controller->service->dao都可以分层测试，也有页面可以测试API返回的json数据               
