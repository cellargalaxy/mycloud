#!/bin/bash
echo 'remember modify configuration'
echo 'MYSQL_ROOT_PASSWORD'
echo 'ports'
echo 'press any key to continue or ctrl+c to stop'
read
chmod -R u=rwx,g=r--,o=r-- ./*
docker-compose -f mysql_master.yml up -d