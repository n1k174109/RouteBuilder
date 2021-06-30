import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphWay {
//    private long id;
    private Map<Long, List<Long>> ways;

    public GraphWay(Map<Long, List<Long>> ways) {
        this.ways = ways;
    }
//    public long getID() {
//        return id;
//    }

    public Map<Long, List<Long>> getWays()              { return ways; }
    public void setWays(Map<Long, List<Long>> ways) { this.ways = ways; }
}
