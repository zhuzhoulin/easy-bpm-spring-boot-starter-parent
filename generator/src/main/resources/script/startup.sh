#!/bin/sh

# 先关闭服务
sh shutdown.sh
# --server.port：启动端口
nohup java -jar -Xms128m -Xmx128m generator.jar --server.port=9999 >/dev/null 2>&1 &
