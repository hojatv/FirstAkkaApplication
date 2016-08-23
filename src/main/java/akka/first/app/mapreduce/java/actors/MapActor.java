package akka.first.app.mapreduce.java.actors;

import akka.actor.UntypedActor;
import akka.first.app.mapreduce.messages.MapData;
import akka.first.app.mapreduce.messages.WordCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The responsibility of MapActor is to take in the English sentence as a String object, identify the words in the sentence, and not count the STOP words.
 */
public class MapActor extends UntypedActor {
    String[] STOP_WORDS = {"a", "am", "an", "and", "are", "as", "at", "be", "do", "go", "if", "in", "is", "it", "of", "on", "the", "to"};
    List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS);

    /**
     * This is the message handler for the actor
     *
     * @param message message
     * @throws Exception Exception if message type is not String
     */
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String work = (String) message;
            // map the words in the sentence and send the result to MasterActor
            getSender().tell(evaluateExpression(work));
        } else
            unhandled(message);
    }

    private MapData evaluateExpression(String line) {
        //logic to map the words in the sentences
        List<WordCount> dataList = new ArrayList<WordCount>();
        StringTokenizer parser = new StringTokenizer(line);
        while (parser.hasMoreTokens()) {
            String word = parser.nextToken().toLowerCase();
            if (!STOP_WORDS_LIST.contains(word)) {
                dataList.add(new WordCount(word, Integer.valueOf(1)));
            }
        }
        return new MapData(dataList);
    }
}