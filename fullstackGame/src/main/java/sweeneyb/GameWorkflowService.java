package sweeneyb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sweeneyb.games.Game;
import sweeneyb.games.GameFactory;
import sweeneyb.games.StringAppendingGame;

import java.util.HashMap;

@Service
public class GameWorkflowService {

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
//            System.out.println("running periodic tasks for room "+ e.getKey());
            e.getValue().periodicTask();
        });
    }

}
