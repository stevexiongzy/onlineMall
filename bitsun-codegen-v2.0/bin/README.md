一：**演示**
0: 先解压zip文件。
1: 进入解压目录双击startup.cmd
3：启动完成后，输入http://127.0.0.1:8080访问

二：**开发**
1: 修改config.properties文件，包括指定数据库和表名等等参数，
2: 运行startup.cmd生成工程代码（gradle工程）
3: 通过导入gradle工程方式导入IDE进行开发。
4: 重点修改关注接口方法的参数验证规则。

![示例图](README.assets/20181130090542.png)