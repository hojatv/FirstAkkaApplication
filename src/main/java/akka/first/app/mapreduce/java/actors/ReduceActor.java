package akka.first.app.mapreduce.java.actors;

import akka.actor.UntypedActor;
import akka.first.app.mapreduce.messages.MapData;
import akka.first.app.mapreduce.messages.ReduceData;
import akka.first.app.mapreduce.messages.WordCount;

import java.util.HashMap;
import java.util.List;

/**
 * The Reduce actor will go through the list of words and reduce for duplicate words, and accordingly increase the number of instances counted for such words.
 */
public class ReduceActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MapData) {
            MapData mapData = (MapData) message;
            // reduce the incoming data and forward the result to Master
            getSender().tell(reduce(mapData.getDataList()));
        } else
            unhandled(message);
    }

    private ReduceData reduce(List<WordCount> dataList) {
        HashMap<String, Integer> reducedMap = new HashMap<String,
                Integer>();
        for (WordCount wordCount : dataList) {
            if (reducedMap.containsKey(wordCount.getWord())) {
                Integer value = (Integer)
                        reducedMap.get(wordCount.getWord());
                value++;
                reducedMap.put(wordCount.getWord(), value);
            } else {
                reducedMap.put(wordCount.getWord(),
                        Integer.valueOf(1));
            }
        }
        return new ReduceData(reducedMap);
    }
}