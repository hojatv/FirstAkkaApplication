package akka.first.app.mapreduce.messages;
import java.util.List;

/**
 * MapData is the message that is passed from the Map actor to the Reduce actor.
 */
public final class MapData {
    private final List<WordCount> dataList;
    public List<WordCount> getDataList() {
        return dataList;
    }
    public MapData(List<WordCount> dataList) {
        this.dataList = dataList;
    }
}