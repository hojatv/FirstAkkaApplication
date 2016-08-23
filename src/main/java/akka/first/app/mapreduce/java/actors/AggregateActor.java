package akka.first.app.mapreduce.java.actors;

import akka.actor.UntypedActor;
import akka.first.app.mapreduce.messages.ReduceData;
import akka.first.app.mapreduce.messages.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Aggregate actor receives the reduced data list from the Master actor and aggregates it into one big list.
 */
public class AggregateActor extends UntypedActor {
    /**
     * a state variable that holds the final reduced map across multiple sentences
     */
    private Map<String, Integer> finalReducedMap =
            new HashMap<String, Integer>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ReduceData) {
            /*ReduceData messages are received from the Master actor*/
            ReduceData reduceData = (ReduceData) message;
            aggregateInMemoryReduce(reduceData.
                    getReduceDataList());
        } else if (message instanceof Result) {
            /*A Result message is sent from the Master actor, and as a response to this message, we send back the results of the finalReducedMap variable*/
            getSender().tell(finalReducedMap.toString());
        } else
            unhandled(message);
    }

    private void aggregateInMemoryReduce(Map<String,
            Integer> reducedList) {
        Integer count = null;
        for (String key : reducedList.keySet()) {
            if (finalReducedMap.containsKey(key)) {
                count = reducedList.get(key) +
                        finalReducedMap.get(key);
                finalReducedMap.put(key, count);
            } else {
                finalReducedMap.put(key, reducedList.get(key));
            }
        }
    }
}