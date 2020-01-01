package sweeneyb.games;

import org.springframework.beans.factory.annotation.Autowired;
import sweeneyb.IncomingGameMessage;
import sweeneyb.NotificationService;

public abstract class Game {

    NotificationService notifier;
    String channel;


    public void registerNotificationChannel(NotificationService service, String room) {
        notifier = service;
        channel = room;
    }

    public abstract void handleEvent(String event);

    public abstract String serializeGameState();

    // should be a callable
    public abstract void periodicTask();

    public void emit(String message) {
        // A short attempt at a fluent API
        notifier.say(message).to(channel);
    }
}
