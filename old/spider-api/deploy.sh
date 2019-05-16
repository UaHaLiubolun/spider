#!/bin/sh

xport JAVA_HOME=/usr/java/jdk1.8.0_171-amd64
export JAVA_JRE=/home/bcz/programs/jdk1.8.0_144/jre/
export PATH=$JAVA_HOME/bin:$PATH

HOME=/root/app/spiderApi
PID_PATH_NAME=/tmp/spiderApi.pid

PATH_TO_JAR=${HOME}/spider-api-1.0-SNAPSHOT.jar
SERVICE_NAME=spider-api
PORT=80

function check_port_use() {
    PID=`/usr/sbin/lsof -i :${PORT} | grep -v "PID" | awk '{print $2}'`
    if [ "${PID}" != "" ];
    then
       echo "1"
    else
       echo "0"
    fi
}

function wait_service_shutdown() {
    echo 'waiting service shutdown...'
    while [ $(check_port_use) -eq 1 ]
    do
        sleep 1
    done
}

function wait_service_running() {
    echo 'waiting service running...'
    while [ $(check_port_use) -eq 0 ]
    do
        sleep 1
    done
}


function start_service() {
    if [ ! -f ${PID_PATH_NAME} ]; then
        CUR=$(pwd)
        cd ${HOME}
        nohup java -jar ${PATH_TO_JAR} >/dev/null 2>&1 &
                    echo $! > ${PID_PATH_NAME}
        wait_service_running
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
        wait_service_shutdown
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