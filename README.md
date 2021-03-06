领奖工具后台管理系统
---------------------by XC

组织登录页

BE

- 创建
   
  数据表一
  - 组织名称 ( 明文 )
  - 密码 ( 非对称加密 )
    - 用公钥将密码进行加密后再存入数据表
  - Session ID ( 组织名称 + 时间戳 + 随机字符串? + sha256 加密 )

FE

- 设置 CSP 防止脚本注入
- 对输入组织名称与密码进行校验
- 将组织名称与密码进行转义
- 用公钥将密码进行加密
- 发送数据给服务端

BE

- 接收客户端的数据
- 根据组织名称查询数据表中对应的一行数据并返回
- 用私钥解密客户端发来的密码和数据库中查询到的密码并进行对比
- 响应数据
  - 对比后若密码不一致则返回错误信息
  - 对比后若密码一致则将 Session ID 作为 Cookie 内容返回

FE

- 接受响应数据
  - 若出现错误信息则提示输入密码错误
  - 若登录成功则路由跳转至组织活动展示页

组织活动展示页

FE

- 判断 Cookie 是否存在登录态的字段，若否则路由跳转至组织登录页

BE

- 获取 Cookie 中的 Session ID 字段，并根据此字段返回数据库中
   
  数据表二
   
  的所有活动相关信息
  - 在创建 数据表二 时，依照不同的 Session ID 查询不同组织下的活动相关信息
- 返回组织已创建活动相关信息 ( 活动名称、开始时间、二维码 URL )

FE

- 展示组织已创建活动相关信息
- 结束活动
  - 请求删除活动接口
    - api/deleteActivity?activity=xxx
- 信息反馈 ( 点击进入活动信息反馈页 )
- 二维码保存到本地
- 继续编辑 ( 进入领奖活动创建页 )
- 点击添加按钮进入领奖活动创建页

BE

- 当客户端用户点击结束活动按钮时，对 数据表二、数据表三 进行相应的 Delete 操作

领奖活动创建页

FE

- 判断 Cookie 是否存在登录态的字段，若否则路由跳转至组织登录页
- 对输入活动名称进行转义
- 依照领奖类型的不同进行对应的操作
  - 指定发放型
    - 上传名单 : 将 Excel 格式的数据转为 JSON 对象
      - 该 JSON 对象中只应该有姓名、学院、学号、电话四个属性
    - 获取输入的奖品名称
    - 获取输入的推送消息
  - 非指定发放型
    - 获取输入的奖品名称
- 点击
   
  生成领奖二维码
  - 在这之前若存在指定发放型
    - 取出前文 JSON 对象中所有给学生推送消息时所需的相关信息 ( 学号 or 电话 )
    - 调用后台向学生 ( 微信推送消息 or 短信推送消息 ) 的接口
      - api/sendMessage?type=wechat.sms
    - 从后台返回推送情况的相关数据
    - 将 领奖类型、姓名、学院、学号、电话、奖品类型、推送情况、奖品情况 整合为 聚合数据一
    - 发送 POST 请求，将聚合数据一传输到服务端
      - api/sendDataOne?activity=xxx&type=pointed
  - 生成二维码图片的 URL
    - 该图片内容应包括 : 活动名称、领奖类型、二维码
    - 只存在两种二维码，扫码后请求相应接口
      - 指定类型：api/type/activity/session_id
      - 非指定类型：api/reward/type/activity/session_id
  - 将 活动名称、二维码 URL、活动创建时间 整合为
     
    聚合数据二
    - api/sendDataTwo
  - 发送 聚合数据二 到服务端

BE

- 当指定类型存在时
  - 接收 ( 学号 or 密码 ) 信息
  - 实现 ( 微信推送消息 or 短信推送消息 ) 接口
  - 返回推送情况的响应数据
- 接收 聚合数据一 并作为 数据表三 存入数据库
- 接收 聚合数据二 并作为 数据表二 存入数据库
- 判断前两步的操作结果
  - 若出现错误，则向浏览器端返回 failed 字段
  - 若操作成功，则向浏览器端返回 success 字段

FE

- 接收服务端响应信息
  - success
    - 将 二维码 URL 、推送失败信息 整合然后存入 Redux
    - 路由跳转至活动创建完成页
  - failed
    - 显示生成领奖二维码失败界面

活动创建完成页

FE

- 判断 Cookie 是否存在登录态的字段，若否则路由跳转至组织登录页
- 从 Redux 取出数据
- 显示二维码图片
- 显示推送失败信息的界面 ( 如果有的话 )
- 实现保存图片到本地功能
- 返回组织活动展览页按钮

活动信息反馈页

FE

- 判断 Cookie 是否存在登录态的字段，若否则路由跳转至组织登录页
- 对学生领奖信息实现分页显示功能
- 将学生领奖信息导出为 Excel 文件

BE

- 基于数据表三 
  实现学生领奖信息的分页查询接口
  - api/getDataThree?activity=xxx&page=1

线下图片扫码

BE

- 用户通过微信扫码
- 调取微信获取用户信息 API
- 判断扫码后生成的 URL
  -     api/type/activity/session_id
    - 依照 type 、activity 、session_id 字段查询 数据表三 中是否存在该学生信息
    - 返回结果 : yes or no
  -     api/reward/type/activity/session_id
    - 依照
       
          reward
       
      、
       
          type
       
      、
          activity
       
      、
          session_id
       
      字段查询
       
      数据表三中
       
      是否已录入该学生信息
      - 若已录入，返回结果 no
      - 若为录入，则将该学生信息存入 数据表三 中，返回结果 yes

FE

- 根据后台返回的响应数据判断
  - yes ：路由跳转至领奖成功页
  - no ：路由跳转至领奖失败页

MongoDB

    // 数据表一
    var table_1 = {
        orgnazitions: [
            {
                org_name: '',
                password: '',
                session_id: ''
            },
            // ...
        ]
    }
    
    // 数据表二
    var table_2 = {
        orgnazitions: [
            {
                session_id: '',
                activities: [
                    {
                        name: '',
                        create_time: '',
                        qrcode_url: ''
                    },
                    {
                        name: '',
                        create_time: '',
                        qrcode_url: ''
                    },
                    // ...
                ]
            },
            // ...
        ]
    }
    
    // 数据表三
    var table_3 = {
        orgnazitions: [
            {
                session_id: '',
                activities: [
                    {
                        name: '',
                        pointed: [
                            {
                                // ...
                            },
                            {
                                // ...
                            },
                            // ...
                        ],
                        un_pointed: [
                            {
                                // ...
                            },
                            {
                                // ...
                            },
                            // ...
                        ]
                    },
                    // ...
                ]
            },
            // ...
        ]
    }

<details class="details-reset details-overlay details-overlay-dark" style="box-sizing: border-box; display: block;"><summary data-hotkey="l" aria-label="Jump to line" aria-haspopup="dialog" style="box-sizing: border-box; display: list-item; cursor: pointer; list-style: none;"></summary></details>





   
