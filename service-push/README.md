# Liyz-push

## 订阅格式
~~~json
{
  biz:"${biz}",
  op:"${op}",
  args:"${args}"
}
~~~

说明：
- biz：标识业务类型
- op：标识行为类型，**必填**。值取自为`login`、`logout`、`heatbeat`、`sub`、`call`
- args：标识传递参数。格式为`k1=v1&k2=v2&k3=v3...`

示例：
- 登录：`ws.send('{op:"login",args:"token=eyJhbGciOiJIUzUxMiJ9"}')`
- 心跳：`ws.send('{op:"heartbeat"}')`