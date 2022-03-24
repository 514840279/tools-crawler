# xxx爬虫文档
## 调研

## 数据库设计

## 爬虫逻辑

## 项目架构

## 常用命令


#### 创建爬虫
cd spiders
feapder create -s xxx_spider

#### 根据json创建表 
feapder create -t gongshang_list
根据提示数据json，确定添加时间，添加主键


#### 创建数据库实体类
首先确保表创建OK
cd items
feapder create -i gongshang_list


