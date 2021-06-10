# MysqlWorkbench_demo

## [](https://github.com/HelloWorld-Ian/MysqlWorkbench_demo/blob/master/README.md#%E9%A1%B9%E7%9B%AE%E7%AE%80%E4%BB%8B)项目简介

数据库课设作业，以web形式实现MySQL数据库软件可视化操作的demo，可对数据库进行建表操作，同时对表进行增删查改  

## [](https://github.com/HelloWorld-Ian/MysqlWorkbench_demo/blob/master/README.md#%E9%85%8D%E7%BD%AE%E7%8E%AF%E5%A2%83)配置环境

1.前端页面文件夹database_view，采用以vue3为基础的vue-cli框架，使用element组件库对页面进行简单设计  
2.后端文件夹datavbase-view文件夹，采用JavaWeb技术栈  

## [](https://github.com/HelloWorld-Ian/MysqlWorkbench_demo/blob/master/README.md#%E9%A2%84%E5%AE%9A%E4%B9%89%E6%95%B0%E6%8D%AE%E5%BA%93)预定义数据库

CREATE TABLE  `table_name`  (  `id`  int(11) NOT NULL AUTO_INCREMENT,  `name`  varchar(255) NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci  
  
CREATE TABLE  `columns`  (  `id`  int(11) NOT NULL AUTO_INCREMENT,  `table_name`  varchar(255) NOT NULL,  `name`  varchar(255) NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
