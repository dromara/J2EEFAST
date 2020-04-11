# FASTOS

#### 介绍
FastOS 是一个 Java EE 企业级快速开发平台，基于经典技术组合（Spring Boot、Spring MVC、Apache Shiro、MyBatis-Plus、freemarker、Bootstrap、AdminLTE）采用经典开发模式，让初学者能够更快的入门并投入到团队开发中去,尤其适合新手朋友练手。 在线代码生成功能，包括核心模块如：组织机构、角色用户、菜单及按钮授权、数据权限、系统参数等。采用松耦合设计；界面无刷新，一键换肤；众多账号安全设置，密码策略；在线定时任务配置；支持多数据源；支持读写分离、分库分表.

#### 软件架构 

1.  _核心框架：Spring Boot 2.2.5.RELEASE_ 
2.   _安全框架：Apache Shiro 1.4.2_ 
3.   _模板引擎：Freemarker_ 
4.   _前端：AdminLTE 2.3.8, Bootstrap 3.3.7, Bootstrap-Table 1.11.0, JQuery 3.3.1_ 
5.   _持久层框架：MyBatis-Plus 3.3.1_ 
6.   _定时任务: Quartz_ 
7.   _数据库连接池：Druid 1.10.1_ 
9.   _数据库: Mysql5.7_
10.  _分布式缓存数据库: Redis 4.0.9_
11.   _工具类：Hutool 4.5.8_ 

#### 演示地址

1.  演示地址： http://www.j2eefast.com/fast <br>账号 ：admin 密码：admin
2.  功能还在陆续更新中......

#### 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
9.  登录日志：系统登录日志记录查询包含登录异常。
10. 在线用户：当前系统中活跃用户状态监控。
11. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
12. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
13. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
14. 在线构建器：拖动表单元素生成相应的HTML代码。
15. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
16. 产品许可: 对项目进行许可证书控制，防止程序拷贝运行。
17. 公告通知:  针对项目升级重要情况出通知公告直接生成静态页面，可以实现延迟推送，设置时效性,实时预览功能

**common 模块中crypto包下有关于银行加密算法 DES 3DES 国密，怎么算MAC 银行密码加密、都是通过项目实践的。 对这块感兴趣的可以学习下** 
 
**产品许可生成数字证书 保护个人与企业的软件作品权益，降低盗版造成的损失**

 **项目配置文件yml对于敏感字段如数据库密码，证书密码，等重要敏感信息加密。防止敏感信息直接暴露！#注意 数据加密安全性其实是相对的,什么加密如果私钥或者key泄露都是无用,这个世界上没有一种加密方法是绝对安全的。这种加密方法只能做到：防君子不防小人！！**
```
写法需要加密的参数 ENC{} DES{} 包裹 或者 SM4{} 包裹 系统会自动识别，其他正常参数不加即可
例如:
spring:
    datasource:
        #默认(主)数据库配置
        default:
            driverClassName: com.mysql.cj.jdbc.Driver
            url: SM4(YPS0KwUcR6ZETrk1CkHPQydzrGO0WEGKb23G4SYdxRHfCqpDI+CrZfEhdxrjkLrH8TlmyqsC50mP/q4ZJzyJfauQIZ3AbsgQ3k/XzDwsrvSI+58c1UuMKaZW3zdIPj1wg+dUmfldaW4i3CQOfHGXwXL+hpVTIjpUBPZFewkPcnk=)
```
#### 安装教程

1.  需要准备环境 Mysql5.7以上、 JDK 1.8、 Maven 3.3 、Redis4.X 以上 、开发工具 eclipse 或者 IEDA
2.  下载源码 git clone https://gitee.com/zhouhuanOGP/J2EEFAST.git
3.  编译代码
    找到根目录下 pom.xml，执行 mvn clean install 命令编译一键打包。
    一般来说不会有什么问题，如果还是编译不成功，可以按照优先级逐个编译试一试。
4.  导入数据库
    db目录里initDb.sql 有建库建表语句按步骤执行即可
5.  将代码导入开发工具fast-admin启动模块- 执行 FastApplication 类即可
注意:
    fast-admin模块 资源目录 application-Test.yml 中修改连接数据库 链接地址 如果你是按照initDb.sql 建库，test.sql导入初始数据 则账号密码用户名都不需要修改

6. ****[搭建文档](https://gitee.com/zhouhuanOGP/J2EEFAST/wikis)**** 
#### 参与贡献

1.  本项目设计思路借鉴了当前gitee中 开源项目中后台管理框架众多优秀项目的设计思路

####  版本更新
1.  `2020-04-11 v2.0.4 ` 
    * 新增百度ueditor富文本编辑器更换之前编辑器
    * 增强ueditor 新增在线删除功能,修改ueditor 完全适配fastos,页面引入直接使用
    * 增加上传图片添加水印
    * 修改公告展示页面，改用iframe引入确保页面所见即所得
    * 修复授权角色选中值丢失情况
    * 修复表格初始化选中，获取选中行值丢失问题
    * 修复TAB栏菜单刷新/TAB切换刷新问题
    * 修复定时任务功能cron工具 不能显示最近执行时间，查询列表新增下次执行时间字段
    * 新增3套登陆页面风格,可以再后台配置默认风格
    * 整合Spring注解Redis缓存,解决Redis故障或者不可用情况任然可以执行方法获取参数
    * 兼容不配置Redis缓存数据库项目也可以运行
    * 解决初次登陆显示问题
    * 登陆页面主页面增加页面压缩减低易读性，提高安全性。此功能可以全局开启
    * 完善国际化字段
    * 去掉Dao包,简化代码
    * 调整包名统一规范
    * 优化代码，修复若干细节
2.  `2020-03-28 v2.0.3 ` 
    * fastjson升级1.2.67 阻止漏洞
    * 修复tab菜单关闭当前BUG
    * 新增yml字段加密写法
    * 新增yml文件敏感信息加密处理
    * 新增信息加密测试类
    * 感谢@wangyu 提出问题，修改公司,地区 顶级节点报错问题
    * 修复树表格 展开/折叠 BUG
    * 升级Swagger到2.9.2 新增swagger-bootstrap-ui 优化接口展示
    * 新增公告发布功能.信息延迟推送，可设置时效性。
    * 修复修改头像点击多次弹出多个对话框情况
    * 修改定时任务可以选择cron执行表达式，方便添加任务
    * 统一处理前端传入日期格式转换
    * 修复若干细节
3.  `2020-03-20 v2.0.2 `
    * 新增产品许可功能
    * 系统启动验证证书是否合法
    * 修改用户、权限页面滑动按钮获取表ID数据丢失导致页面异常情况
    * 配置文件新增项目参品名称、license证书、公钥存放位置字段
    * 新增测试数据SQL
    * fastJS.js 新增页面模板引擎,优化部分代码
    * 删除多余类与无用的引用
    * 针对Apache Shiro Padding Oracle导致远程代码执行漏洞,Apache Shiro 1.4.1 升级 1.4.2 安全版本
    * 感谢@凌晨雨薇 提出的问题, 修复登陆页面免登录记住我BUG,但是作者建议此功能正式项目不用此功能.有远程执行风险
    * 测试模式定时任务不执行问题
    * 修复删除操作日志报错问题
    * 其他细节优化
4.  `2020-03-01 v2.0.1 `
    * FASTOS 正式发布

#### 关于系统
* FASTOS (快速开发开源系统)名字由来:包含作者对于软件开发的美好幻想，希望软件开发变的快速而简单，每个人都能分享自己的技术从而达到快速又强大的软件系统,让我们有更多的时间去陪伴家人!你可能在本系统中看到众多优秀开源项目的影子，因为她本身集成了众多优秀的开源项目精华功能，在这种环境中成长的，但是她目前还有很多缺点，希望大神们嘴下留情。如果你也喜欢开源、喜欢本项目,作者欢迎您的加入,FASTOS会因为您的加入而变的更加完善与丰富!
* 如果本项目对你有帮助,[请点击Star收藏](https://gitee.com/zhouhuanOGP/J2EEFAST),**本项目会长期维护**,若你在使用中有任何问题或建议,欢迎在[码云issue提交问题](https://gitee.com/zhouhuanOGP/J2EEFAST/issues)作者会第一时间处理，让我们一起完善FASTOS
* 关于文档：因目前项目初期,暂还没时间整理,后期会整理.
* 关于更新：项目每周都会有更新,演示网站会在周五~周日,不定期暂停访问,带来不便尽情谅解!
* QQ群：因目前项目初期暂不提供，所有问题请在码云issue提问,后期会开放官方群,请随时关注!
#### 免责声明：
* **本项目代码全部开源，无需任何费用。如果有人向你贩卖本系统都是骗子!**
* 不得将 FASTOS 于危害国家安全、荣誉和利益的行为，不能以任何形式用于非法为目的的行为,否则后果自负
* FASTOS 前身主要用于银行项目,本身很注重安全因素,可以从项目登陆可以看出.但是您也需了解使用本软件的风险，是软件皆有漏洞，任何人都无法保证100%没有漏洞，所以由软件漏洞造成的损失不予赔偿，也不承担任何因使用本软件而产生相关法律责任。请软件上线使用前进行足够的安全检测，以避免此问题发生。
* 新版本会增加项目启动证书认证,可以使用你的机器码在演示地址中生成证书后启动。若你是大神则略过!


>  **码云Gitee(主): [https://gitee.com/zhouhuanOGP/J2EEFAST](https://gitee.com/zhouhuanOGP/J2EEFAST)** 
> 
>  **Github(辅): [https://github.com/zhouhuan751312/J2EEFAST](https://github.com/zhouhuan751312/J2EEFAST)** 

#### 演示图
![输入图片说明](https://images.gitee.com/uploads/images/2020/0411/013701_bdeebf4a_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0411/013817_82639c6c_1816537.png "屏幕截图.png")
![输入图片说明](http://www.j2eefast.com/fast/profile/fileUeditor/upload/image/20200411/1586537580720033839.jpg "在这里输入图片标题")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0328/234931_48e39435_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/142557_2b61c8bb_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/143045_4257591d_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/143126_14c6028e_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/143208_4fc272f0_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/143225_79a0cc62_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/143804_b6608568_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0328/235226_d2af8f89_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/143840_e4b06bc2_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/143916_9cfb2619_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/143945_f7f210f4_1816537.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0320/144019_b2834f9c_1816537.png "屏幕截图.png")