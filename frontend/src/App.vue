<template>
    <div>
        <div id="main-content" class="container">
            <div class="row">
                <div class="col-md-6">
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="connect">Join Room:</label>
                            <input type="text" id="roomName" class="form-control" v-model="room_name" placeholder="join room"/>
                            <button id="connect" class="btn btn-default" type="submit" :disabled="connected == true" @click.prevent="connect">Connect</button>
                            <button id="disconnect" class="btn btn-default" type="submit" :disabled="connected == false" @click.prevent="disconnect">Disconnect
                            </button>
                        </div>
                    </form>
                </div>
                <div class="col-md-6">
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="name">What is your name?</label>
                            <input type="text" id="name" class="form-control" v-model="send_message" placeholder="Your name here...">
                        </div>
                        <button id="send" class="btn btn-default" type="submit" @click.prevent="send">Send</button>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table id="conversation" class="table table-striped">
                        <thead>
                            <tr>
                                <th>Greetings</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="item in received_messages" :key="item">
                                <td>{{ item }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: 'app',
  components: {
    // HelloWorld
  }, 
  data() {
    return {
      received_messages: [],
      send_message: null,
      connected: false,
      room_name: ""
    };
  },
  created() {
    console.log("app created")
  },
  // https://stackoverflow.com/questions/46818674/spring-stomp-websockets-with-vue-js?rq=1
  methods: {
    send() {
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = { name: this.send_message };
        this.stompClient.send("/app/hello/"+this.room_name, JSON.stringify(msg), {});
      }
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/topic/greetings/"+this.room_name, tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
          });
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    }
  },
  mounted() {
    // this.connect();
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
