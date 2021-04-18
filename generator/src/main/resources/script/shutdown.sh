#!/bin/sh

# 关闭服务

pid=$(ps -ef | grep generator.jar | grep -v grep | awk '{print $2}')
if [ -n "$pid" ]; then
  echo "stop generator.jar, pid:" "$pid"
  kill -9 "$pid"
fi
