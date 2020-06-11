package sweeneyb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sweeneyb.games.Game;
import sweeneyb.games.GameFactory;

import java.util.HashMap;

@Service
public class GameWorkflowService {

    static Logger logger = LoggerFactory.getLogger(GameWorkflowService.class);
    HashMap<String, Game> gameStates = new HashMap<>();

    @Autowired
    GameFactory factory;

    public Game getGameState(String roomName) {
        return gameStates.getOrDefault(roomName, factory.createGame(GameFactory.GameType.STRING_APPENDING, roomName));
    }

    public Game appendGameEvent(String roomName, String gameEvent) {
        Game gameState = getGameState(roomName);
        gameState.handleEvent(gameEvent);
        gameStates.put(roomName, gameState);
        return gameState;

    }

    @Scheduled(fixedRate = 10000)
    public void periodicVisitor() {

        gameStates.entrySet().stream().forEach( e -> {
//            logger.info("running periodic tasks for room "+ e.getKey());
            e.getValue().periodicTask();
        });
    }

}
