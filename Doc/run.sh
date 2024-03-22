#!/bin/bash

#echo stop application
#source ./stop.sh
#echo start application
#source ./start.sh
# sh xxx.sh
# chmod u+x xxx.sh

logDir="/usr/local/project/pipress/logs";
jarName="ruoyi-admin.jar"

PID=$(ps -ef | grep $jarName | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo Application is already stopped
else
    echo kill $PID
    kill $PID
fi

[ ! -d "$logDir" ] && mkdir -p "$logDir"
[ ! -d "$logDir" ] && touch "nohup`date +%Y-%m-%d`.out"
nohup java -jar $jarName --server.port=8080 >> ./logs/nohup`date +%Y-%m-%d`.out 2>&1 &
echo Server Starting
#指定输出到当前目录log文件夹中 netstat -anp | grep 8080
#nohup java -Xms1024m -Xmx2048m -jar ruoyi-admin.jar --server.port=8080 1>start.out 2>&1 &