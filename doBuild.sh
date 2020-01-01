cp fullstackGame/build/libs/*.jar infra/docker/app.jar
cd infra/docker
docker build . -t sweeneyb/stomp
