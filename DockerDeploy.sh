#! /bin/sh
./gradlew clean build -x test

docker buildx build \
 --platform linux/amd64,linux/arm64 \
 -t bogyumkim/bows-backend:latest \
 --no-cache \
 --push .