package sweeneyb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import sweeneyb.games.Game;

@Controller
public class GreetingController {

	@Autowired
	SimpMessagingTemplate simpTemplate;

	@Autowired
	GameWorkflowService gameWorkflows;


	@SubscribeMapping("/greetings/{room}")
	public void onSubscribe(@DestinationVariable("room") String room, SimpMessageHeaderAccessor accessor) {
		System.out.println(accessor.getSessionId()+ " made a new subscription to "+room);
	}

	@MessageMapping("/hello/{room}")
	public void greeting(@Payload IncomingGameMessage message, @DestinationVariable(value="room") String room) throws Exception {
		System.out.println(room);
		handleGreeting(message, room);
	}

	@GetMapping("/hello/{room}")
	public void restGreeting(@PathVariable(value = "room") String room) throws Exception {
		System.out.println("rest controller message for "+ room);
		handleGreeting(new IncomingGameMessage("foo"), room);

	}

	@PostMapping("/hello/{room}")
	public void postGreeting(@RequestBody IncomingGameMessage message, @PathVariable(value = "room") String room) throws Exception {
		System.out.println("rest controller message for "+ room);
		handleGreeting(message, room);

	}

	public void handleGreeting(IncomingGameMessage message, String room) throws Exception {
		// TODO sanitize room name

		Thread.sleep(1000); // simulated delay

		Game game = gameWorkflows.appendGameEvent(room, message.getName());
//		simpTemplate.convertAndSend("/topic/greetings/"+room, new Greeting("Game State: "+ HtmlUtils.htmlEscape(game.serializeGameState())));

	}

}
