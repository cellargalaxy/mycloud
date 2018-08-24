#!/bin/bash
chmod -R u=rwx,g=r--,o=r-- ./*
docker-compose -f mysql_master.yml up -d