#!/bin/bash
chmod -R u=rwx,g=r--,o=r-- ./*
docker-compose -f all.yml up -d