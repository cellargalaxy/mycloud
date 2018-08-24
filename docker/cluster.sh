#!/bin/bash
chmod -R u=rwx,g=r--,o=r-- ./*
docker-compose -f cluster.yml up -d