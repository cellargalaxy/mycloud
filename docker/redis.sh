#!/bin/bash
chmod -R u=rwx,g=r--,o=r-- ./*
docker-compose -f redis.yml up -d