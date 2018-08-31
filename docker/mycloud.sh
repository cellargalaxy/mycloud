#!/bin/bash
echo 'remember modify configuration'
echo 'MYCLOUD_MYSQL_IP_PORT'
echo 'MYCLOUD_MYSQL_PASSWORD'
echo 'MYCLOUD_REDIS_IP'
echo 'MYCLOUD_REDIS_PASSWORD'
echo 'MYCLOUD_SECRET'
echo 'press any key to continue or ctrl+c to stop'
read
chmod -R u=rwx,g=r--,o=r-- ./*
docker-compose -f mycloud.yml up -d