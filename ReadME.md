# 项目介绍

WEB调试的时候，如果服务端是在大陆之外，那么在某些时刻会受到GFW的限制(随机丢包、高延迟等等)。

因此用该项目来做反向代理的同时，使用shadowsocks代理。

为方便使用，本gradle配置文件是配置了distribution，编译完方便使用。
```
➜  proxy_ss-1.0# bin/proxy_ss
usage: proxy-s
 -a,--agency-uri <arg>   shadowssocks的本地代理地址,例如: 127.0.0.1:1086
 -h,--help               show this help message and exit program
 -l,--local-port <arg>   本地提供服务的端口号
 -p,--proxy-uri <arg>    需要代理的URL. 例如: https://210.63.204.29
➜  proxy_ss-1.0# bin/proxy_ss -l 1081 -p https://210.63.204.29 -a 127.0.0.1:1086
```
注：由于个人使用，做代理的时候并没有做http header的重新赋值。因此某些场景下无法访问(后台有安全性校验，主要是HOST验证，防止跨域访问或者代理访问)，比如google和baidu等等

## 举例
```
proxy_ss -l 1081 -p https://210.63.204.29 -a 127.0.0.1:1086
```