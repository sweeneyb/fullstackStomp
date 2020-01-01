## Demo Fullstack application

### Challenge
Deploy an application that does requires no client-side install and does some process on the backend.  Make it run on a bare cloud instance start/stoppable with a single command.

### App description
This app posts notifications to a page segregated by a logical name... a "room."  Messages are appended to previous messages.  If there are no updates after about 20 seconds, the state is cleared.

### Application stop/start
The application has been installed as a systemd service named "cloudservice"
#### Updates
The application was packages as a docker image.  So the update process is 2 commands:
* sudo docker pull sweeneyb/stomp
* sudo systemctl restart cloudservice

## Discussion
### What I wanted to show & learn
I wanted to show a basic framework that was capable of 2 way, asynchronous communication from the client to the server.  I've only worked with client-initiated requests, so I wanted to try something with websockets.  I also wanted to show how components written in different langauges (java and javascript) can interact.  Finally, I wanted to the backend to be easy to manage.  Docker + a systemd service seemed like the easiest management after using ansible to get everything ready.

### Frontend
The frontend is written in Vue, but is very basic.  Most of it came from https://stackoverflow.com/questions/46818674/spring-stomp-websockets-with-vue-js?rq=1, with some modification to show I can get around.  Frontend is definitely my weakest area, so I spent just enough time getting things running. In the future, I'd like to use the Vue router to add new games and the room subscriptions to run multiple instances of games.

### Backend
The backend is java 8+, but would I think I would have preferred Kotlin.  I pulled most of the websocket interaction code from https://spring.io/guides/gs/messaging-stomp-websocket/, but added a few things.  There's a REST endpoint that will add a message that isn't coming through the websocket.  There's also some abstraction of game creation to demonstrate how multiple game types could be supported.  The "games" are event based, which should support a lot of different gameplay flows.  The games are also abstracted behind a service, which could easily be moved to a database or redis.  Future work is to actually implement game rules and let the frontend create instances of those game flows.

### Infrastructure
This project isn't big enough for CI/CD, though I have a flow at this point.  Build the UI, build the jar, build the docker image.  Docker was the obvious packaging, as it makes it easy to run anywhere.  Because I had a cloud instance, there's just enough ansible to get docker installed & a systemd unit file installed.  But really, this would be more easily deployed via cloud-init (https://cloud.google.com/container-optimized-os/docs/how-to/create-configure-instance) or Fargate.  
