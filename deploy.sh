#!/bin/sh

export JAVA_HOME=/usr/java/jdk1.8.0_171-amd64
export PATH=$JAVA_HOME/bin:$PATH

HOME=root/app/spider
PID_PATH_NAME=/tmp/spider.pid

PATH_TO_JAR=${HOME}/spider-manage-1.0-SNAPSHOT.jar
SERVICE_NAME=spider


function start_service() {
    if [ ! -f ${PID_PATH_NAME} ]; then
        CUR=$(pwd)
        cd ${HOME}
        nohup java -jar ${PATH_TO_JAR} >/dev/null 2>&1 &
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

