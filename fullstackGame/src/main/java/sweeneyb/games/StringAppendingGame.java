package sweeneyb.games;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StringAppendingGame extends Game {

    public List<String> events = new ArrayList<>();
    Date lastUpdate = new Date();

    public void handleEvent(String event) {
        events.add(event);
        lastUpdate = new Date();
        emit(events.stream().collect(Collectors.joining(" ")));
    }

    public String serializeGameState() {
        return events.stream().collect(Collectors.joining(" "));
    }

    public void periodicTask() {
        Date now = new Date();
        if(now.getTime() - lastUpdate.getTime() > 20*1000) {
            events.clear();
            emit("game state cleared due to timeout");
        }
    }

}
