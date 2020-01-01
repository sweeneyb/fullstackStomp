package sweeneyb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class NotificationService {

    @Autowired
    SimpMessagingTemplate simpTemplate;

    public Message say(String what) {
        return new Message(what);
    }


    // Builders are easier in Kotlin
    public class Message {

        String message;
        Message(String message) {
            this.message = message;
        }

        public void to(String channel) {
            NotificationService.this.simpTemplate.convertAndSend("/topic/greetings/"+channel, new GameStateMessage("Game State: "+ HtmlUtils.htmlEscape(message)));
        }
    }
}
