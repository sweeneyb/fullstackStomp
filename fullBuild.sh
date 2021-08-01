#!/bin/bash

set -e

cd frontend
npm run build
mkdir -p ../fullstackGame/src/main/resources/static
cp -r dist/* ../fullstackGame/src/main/resources/static
cd ../fullstackGame
./gradlew build
cd ..

# do docker build here
