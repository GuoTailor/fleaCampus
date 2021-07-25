# 找有app socket通信文档

## 协议格式

服务器统一返回格式

```json
{
  "code": 0,
  "msg": "成功或失败",
  "data": [
    "数据"
  ]
}
```

### 关于WebSocket

**注意**

- websocket url路径：ws://47.107.178.147:82/room?bearer=token
- <a style="color:red;"> 目前WebSocket基本已稳定</a>，可以点击[这里](http://47.107.178.147:82/index.html) 查看测试页面(可能加载有点慢)
- 现在基本每个推送都有重试机制，需要在服务端推送后响应以下格式，如果没有响应将最多重试3次。加上第一次就是最多发送4次
```json
{
  "order": "/ok",
  "body": {},
  "req": 1
}
```


**通讯格式**

请求格式

```json
{
  "order": "/echo",
  "body": {},
  "req": 1
}
```

协议解释：

| 字段    | 类型    | 说明    |
| :---: | :----:    | :---- |   
| order | String    | 请求的路径，类似于url路径，用于标识请求的接口，注意这个是放在socket里面传输的，不是websocket的url路径 |
| body  | Object    | 请求的数据，可以参考[这个试列](http://47.107.178.147/fcdoc/#api-Socket-echo) |
| req   | int       | 区分请求用，当有多个请求同时到服务器时，服务器将用该位标识<br> 回应的是那一条请求. 该位建议跟随请求次数自增，当增加到2^31-1时复位为0 |

响应格式

```json
{
  "body": {
    "code": 0,
    "msg": "成功或失败",
    "data": [
      "数据"
    ],
    "order": 0
  },
  "req": 1
}
```

响应格式中的body字段和统一响应格式（http响应格式）一致,但增加了一个"order",用于标识推送内容，具体见下表

| 字段标识   | order  | 说明    |
| :------: | :----: | :------ | 
| userMsg   | -3    | 私聊消息通知
| groupMag  | -2    | 群消息通知
| errorNotify | -1  | 错误通知
| requestReq | 0    | 正常响应

