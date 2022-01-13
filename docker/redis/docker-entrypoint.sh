#!/bin/bash

set -e #Exit immediately if a command exits with a non-zero status.

# Settings
STARTPORT=6376
TIMEOUT=5000
NODES=3
REPLICAS=0
PASSWORD=wemakeprice

# Computed vars
ENDPORT=$((STARTPORT+NODES))

cluster_start() {
    PORT=STARTPORT
    while [ $((PORT < ENDPORT)) != "0" ]; do
        PORT=$((PORT+1))
        echo "Starting $PORT"
        redis-server --port $PORT --requirepass $PASSWORD --cluster-enabled yes --cluster-config-file nodes-${PORT}.conf --cluster-node-timeout $TIMEOUT --appendonly yes --appendfilename appendonly-${PORT}.aof --dbfilename dump-${PORT}.rdb --logfile ${PORT}.log --daemonize yes
    done
}

cluster_create() {
    HOSTS=""
    PORT=STARTPORT
    while [ $((PORT < ENDPORT)) != "0" ]; do
        PORT=$((PORT+1))
        HOSTS="$HOSTS 127.0.0.1:$PORT"
    done
    redis-cli -a $PASSWORD --cluster create $HOSTS --cluster-replicas $REPLICAS --cluster-yes
}

cluster_start && cluster_create

tail -f /dev/null
