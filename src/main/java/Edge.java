import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Edge {
    private Integer dist = Integer.MAX_VALUE;
    private Map<Long, List<Long>> edgeList;

    public Integer getDist() {
        return dist;
    }
    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public void sliceWays(GraphWay way, GraphNode node) {
        for (Map.Entry<Long, List<Long>> edge : edgeList.entrySet()) {
            ArrayList<Long> nodes = new ArrayList<>(way.getNodesLinks());
            for (Long nodeItem: nodes) {
                if (nodes.contains(node.getID()) && node.getIsCross()){}
//                    edgeList.put(way.getId(), nodes.subList());
            }



        }
    }

    public void setEdgeList(Map<Long, List<Long>> edgeList) {
        this.edgeList = edgeList;
    }

    public Map<Long, List<Long>> getEdgeList() {
        return edgeList;
    }
}
