#!/bin/sh

export JAVA_HOME=/usr/java/jdk1.8.0_171-amd64
export PATH=$JAVA_HOME/bin:$PATH

HOME=/root/app/spiderManager
PID_PATH_NAME=/tmp/spiderManager.pid

PATH_TO_JAR=${HOME}/spider-manage-1.0-SNAPSHOT.jar
SERVICE_NAME=spiderManage


function start_service() {
    if [ ! -f ${PID_PATH_NAME} ]; then
        CUR=$(pwd)
        cd ${HOME}
        nohup java -classpath ${PATH_TO_JAR} com.chinamcloud.spider.run.WeChatSpiderManager >/dev/null 2>&1 &
                    echo $! > ${PID_PATH_NAME}
        echo "${SERVICE_NAME} started ..."
        cd ${CUR}
    else
        echo "${SERVICE_NAME} is already running ..."
    fi
}

function stop_service() {
    if [ -f ${PID_PATH_NAME} ]; then
        PID=$(cat ${PID_PATH_NAME});
        echo "${SERVICE_NAME} stoping ..."
        kill ${PID};
        echo "${SERVICE_NAME} stopped ..."
        rm ${PID_PATH_NAME}
    else
        echo "{$SERVICE_NAME} is not running ..."
    fi
}

case $1 in
    start)
        echo "Starting ${SERVICE_NAME} ..."
        start_service
    ;;
    stop)
        stop_service
    ;;
    restart)
        stop_service
        start_service
    ;;
esac

