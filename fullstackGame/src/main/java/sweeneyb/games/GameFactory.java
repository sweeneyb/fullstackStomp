package sweeneyb.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sweeneyb.NotificationService;

@Service
public class GameFactory {

    public enum GameType {
        STRING_APPENDING
         // each GameType should have a create() method to return the actual game type
    }

    @Autowired
    SimpMessagingTemplate simpTemplate;

    @Autowired
    NotificationService notificationService;

    public Game createGame(GameType type, String room) {
        Game game = new StringAppendingGame();
//        game.registerNotificationService(notificationService);
        game.registerNotificationChannel(notificationService, room);
        return game;
    }
}
