#!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/action/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo ">$(date) build 파일명: $JAR_NAME" >> /home/ec2-user/action/deploy.log

echo "> $(date)build 파일 복사" >> /home/ec2-user/action/deploy.log
DEPLOY_PATH=/home/ec2-user/action/
cp $BUILD_JAR $DEPLOY_PATH #한 단계 위 폴더로 복사

echo ">$(date) 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/action/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

echo "$(date)> 현재 구동중인 애플리케이션 pid: $CURRENT_PID" >> /home/ec2-user/action/deploy.log

if [ -z $CURRENT_PID ]
then
  echo ">$(date) 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/action/deploy.log
else
  echo "$(date)> 현재 구동중인 process를 종료합니다 kill -9 $CURRENT_PID" >> /home/ec2-user/action/deploy.log

  kill -9 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo ">$(date) DEPLOY_JAR 배포"    >> /home/ec2-user/action/deploy.log
nohup java -jar $DEPLOY_JAR > /home/ec2-user/deploy.log 2>/home/ec2-user/action/deploy_err.log &