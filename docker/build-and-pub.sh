#!/usr/bin/env bash

cd ..
./gradlew shadowJar
cd ./docker

timestamp=$(date '+%Y%m%d%H%M%S')
image_name="josafaverissimo/boogie-woogie-pay"

docker buildx build --platform linux/amd64 \
  -t $image_name:$timestamp \
  -t $image_name:latest \
  --push ../app/
