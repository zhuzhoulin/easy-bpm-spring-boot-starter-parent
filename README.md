
# easy-bpm-spring-boot-starter-parent
lowCode formDesign &amp; processDesign &amp; springboot

#### 介绍
bpm 

风中思絮低代码开发平台主要包含流程引擎功能，流程设计器、表单设计器、动态路由、我的待办/已办/草稿/申请 等。页面兼容移动端且满足各种中国式流程，力争成为开源流程引擎的标杆。

[在线体验地址](http://120.77.218.141:9992/bpm-web/index.html)

[文档地址](http://120.77.218.141:9992/low-code-docs/)

[前端项目路径](https://gitee.com/zhuzl002/bpm-web-ui)
#### 开源目的

源于开源，回馈开源 。目前国内缺乏一个完整的开源流程引擎，因此想弥补一下这个空白，为后浪降低壁垒-。-
#### 软件架构

前端： vue + elementui
后端：

springboot 2.2.X

mysql 5.8
redis

发布部署： jenkins 2.50.0
MAVEN私有仓库： nexus 3.X

#### 安装教程

1.  安装 mysql
2.  yml 中配置 一下脚本 自动创建表，或者手动运行 db/init/data-bpm.sql  db/init/schema-bpm.sql 脚本
	easy:
	 bpm:
      api:
       init-data:
        auto-init-data: true
        continue-on-error: true
        sql-script-encoding: utf-8


#### 项目结构
	
 easy-bpm-spring-boot-starter-parent 父工程
  easy-bpm-common                    工具包
  easy-bpm-auth                      组织架构工程
  easy-bpm-api                       核心项目工程(如 只需通过service访问，则可以只启动改项目，需复制easy-bpm-web中 application.yml)  
  easy-bpm-web                       controller工程  
  generator                          代码生成器工程，子项目，如需 需要单独启动！
#### 使用说明

1. 运行 com.pig.easy.bpm.web.EasyBpmWebApplication。
2. 如只需 api 脚本，则可只运行 easy-bpm-api 。	
3. 如果有使用代码生成器，则需要单独启动	generator 工程。

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

#### 问题反馈及讨论
邮箱： 786289666@qq.com

QQ讨论群： 957664677

#### License
[AGPL3.0](https://www.gnu.org/licenses/agpl-3.0.txt) license.
Copyright (c) 2020-present pig

#### 打赏
<img src="https://gitee.com/zhuzl002/bpm-web/raw/master/src/assets/images/20200820181716.jpg" width="450" height="350" alt="note"/>
<img src="https://gitee.com/zhuzl002/bpm-web/raw/master/src/assets/images/20200820181724.jpg" width="450" height="350" alt="note"/>
