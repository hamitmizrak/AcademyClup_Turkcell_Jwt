#!/usr/bin/env bash
set -euo pipefail

# chmod +x scripts/redis-up.sh

NAME="${REDIS_NAME:-redis}"
HOST_PORT="${REDIS_PORT:-6379}"
IMAGE="${REDIS_IMAGE:-redis:7-alpine}"

echo "[redis-up] NAME=$NAME HOST_PORT=$HOST_PORT IMAGE=$IMAGE"


# Yoksa oluştur ve başlat
echo "[redis-up] $NAME oluşturuluyor ve başlatılıyor..."
# docker run -d --name redis -p 6379:6379 redis:7-alpine
docker run -d --name "$NAME" -p "${HOST_PORT}:6379" "$IMAGE"
echo "[redis-up] OK"
