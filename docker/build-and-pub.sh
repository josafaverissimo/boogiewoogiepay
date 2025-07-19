#!/usr/bin/env bash

cd ..
./gradlew shadowJar
cd ./docker

timestamp=$(date '+%Y%m%d%H%M%S')
api_image_name="josafaverissimo/boogie-woogie-pay-api"
worker_image_name="josafaverissimo/boogie-woogie-pay-worker"

docker buildx build --platform linux/amd64 \
  -t $api_image_name:$timestamp \
  -t $api_image_name:latest \
  --push ../app/

docker buildx build --platform linux/amd64 \
  -t $worker_image_name:$timestamp \
  -t $worker_image_name:latest \
  --push ../subs/worker/
