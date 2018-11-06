#!/bin/bash
echo 'remember modify configuration'
echo 'MYCLOUD_DB_HOST'
echo 'MYCLOUD_DB_PASSWORD'
echo 'MYCLOUD_SECRET'
echo 'MYCLOUD_USERNAME'
echo 'MYCLOUD_PASSWORD'
echo 'press any key to continue or ctrl+c to stop'
read
chmod -R u=rwx,g=r--,o=r-- ./*
docker-compose -f mycloud.yml up -d