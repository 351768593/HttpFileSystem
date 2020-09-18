# Http文件系统

一个走Http协议的文件储存系统的简易实现, 可以用作其它系统的子系统等

主类为`firok.hfs.FileController`

## 接口列表

### 上传

> POST http://ip/fs/upload/{filename}

在`form-data`内增加一个key-value-pair, 类型为文件, 值为文件内容

接口调用成功之后, 将会在指定目录下

### 下载

> GET http://ip/fs/download/{filename}

根据上传时候的`filename`返回对应的文件内容

## 配置文件

就一个`application.yml`, 没什么好说的, 服务器的context-path、端口号、文件保存路径都是在这改

## 注意

这个系统

* 没有做任何文件内容处理
* 没有做任何文件名映射
* 没有做任何访问权限控制

> 虽然没有做文件内容处理, 但由于`fs/download`接口返回值的HttpHeader里写死了返回内容文件编码是UTF-8, 所以在上传之前可能需要手动转换一下文件内容
