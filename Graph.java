import java.util.*;

public class Graph {
        private Set<GraphNode> nodes;
        private LinkedList<GraphNode> shortWay = new LinkedList<>();
        private Integer dist = Integer.MAX_VALUE;
        private Map<GraphNode, Integer> adjNodes = new HashMap<>();

        public void addNode(GraphNode nodeA) {
            nodes.add(nodeA);
        }
        public  Set<GraphNode> getNodes() {
            return nodes;
        }
        public void setNodes(Set<GraphNode> nodes) {
            this.nodes = nodes;
            }
        public void addNextNode(GraphNode next, int dist) {
        adjNodes.put(next, dist);
    }

    public Map<GraphNode, Integer> getAdjNodes() {
        return adjNodes;
    }

    public void setAdjNodes(Map<GraphNode, Integer> adjNodes) {
        this.adjNodes = adjNodes;
    }

    public Integer getDist() {
        return dist;
    }

    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public List<GraphNode> getShortWay() {
        return shortWay;
    }

    public void setShortWay(LinkedList<GraphNode> shortWay) {
        this.shortWay = shortWay;
    }
        }

