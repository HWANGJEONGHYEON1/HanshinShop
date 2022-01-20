#!/bin/bash

set -e #Exit immediately if a command exits with a non-zero status.

cluster_start() {
    PORT=$START_PORT
    while [ $((PORT <= $END_PORT)) != "0" ]; do
        echo "Starting $PORT"
        redis-server --port $PORT --requirepass $PASSWORD --cluster-enabled yes --cluster-config-file nodes-${PORT}.conf --cluster-node-timeout $TIMEOUT --appendonly yes --appendfilename appendonly-${PORT}.aof --dbfilename dump-${PORT}.rdb --logfile ${PORT}.log --daemonize yes
        PORT=$((PORT+1))
    done
}

cluster_create() {
    HOSTS=""
    PORT=$START_PORT
    while [ $((PORT <= $END_PORT)) != "0" ]; do
        HOSTS="$HOSTS 127.0.0.1:$PORT"
        redis-cli -p $PORT -a $PASSWORD cluster reset
        PORT=$((PORT+1))
    done
    redis-cli -a $PASSWORD --cluster create $HOSTS --cluster-replicas $REPLICAS --cluster-yes
}

cluster_start && cluster_create

tail -f /dev/null