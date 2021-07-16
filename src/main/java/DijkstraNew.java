import java.util.*;

public class DijkstraNew {
    private final Map<Long, PathEntry> nodeId2PathEntry = new HashMap<>();
    private final PriorityQueue<PathEntry> entryPriorityQueue = new PriorityQueue<>();
    private Graph graph;

    public DijkstraNew(Graph graph) {
        this.graph = graph;
    }

    public void calcShortWay(GraphNode startNode, GraphNode endNode) {
        double maxWeight = Double.MAX_VALUE;
        PathEntry currEntry = new PathEntry(0, 0, startNode.getId(), null);
        entryPriorityQueue.add(currEntry);
        nodeId2PathEntry.put(startNode.getId(), currEntry);
        while (!entryPriorityQueue.isEmpty()) {
            currEntry = entryPriorityQueue.poll();
            if (currEntry.getWeight() > maxWeight )
                break;

            List<Edge> edges = graph.getRelations().get(currEntry.getLastNodeId());
            for (Edge edge : edges) {
                long nextNodeId = edge.getNextNode(currEntry.getLastNodeId());
                double weight = currEntry.getWeight() + getWeight(edge);
                PathEntry entry = nodeId2PathEntry.get(nextNodeId);
                if (entry == null) {
                    entry = new PathEntry(edge.getId(), weight, nextNodeId, currEntry);
                    entryPriorityQueue.add(entry);
                    nodeId2PathEntry.put(nextNodeId, entry);
                } else if (entry.getWeight() > weight) {
                    entryPriorityQueue.remove(entry);
                    entry.setWeight(weight);
                    entry.setPrevEntry(currEntry);
                    entry.setEdgeId(edge.getId());
                    entryPriorityQueue.add(entry);
                } else {
                    continue;
                }
                if (nextNodeId == endNode.getId() && maxWeight > weight)
                    maxWeight = weight;

            }
        }
    }

    private double getWeight(Edge edge) {
        return edge.getDist();
    }
//    private GraphNode getMinNode(Set<GraphNode> unvisited) {
//        GraphNode minNode = null;
//        int minDist = Integer.MAX_VALUE/2;
//        for (GraphNode node: unvisited) {
//            for (Map.Entry<GraphNode, Integer> valueNodes : node2Weight.entrySet()) {
//                if (valueNodes.getKey().equals(node) && valueNodes.getValue() < minDist)
//                    minNode = node;
//                    minDist = valueNodes.getValue();
//            }
//        }
//        return minNode;
//    }

        public void addDistance (Collection < GraphNode > nodesFromMap) {
            List<GraphNode> nodes = new ArrayList<>(nodesFromMap);
//        Map<GraphNode, Integer> valueNodes = new HashMap<>();
            for (int i = 0; i < nodes.size() - 1; i++) {
                double distEdge = 0;
                GraphNode currNode = nodes.get(i);
                GraphNode nextNode = nodes.get(i + 1);
                distEdge += ((int) calcDistNodes(currNode.getLat(), currNode.getLon(), nextNode.getLat(), nextNode.getLon()));
//                distEdge += edge.getDist();
                node2Weight.put(currNode, (int) distEdge);
            }

        }

        public GraphNode nearestValueNode(double lat, double lon) {
            double minDist = Double.MAX_VALUE;
            GraphNode nearestNode = null;
            for (GraphNode currMainNode : graph.getMainNodes().values()) {
                double dist = 0;
                if (lat == currMainNode.getLat() && lon == currMainNode.getLon())
                    return currMainNode;

                dist = calcDistNodes(currMainNode.getLat(), currMainNode.getLon(), lat, lon);
                if (minDist > dist)
                    minDist = dist;
                nearestNode = currMainNode;
            }
            return nearestNode;
        }

        public double calcDistNodes ( double lat1, double lon1, double lat2, double lon2){
            final double radEarth = 6371.009;
            double dLAT = Math.abs(lat2 - lat1) * (Math.PI / 180);
            double dLON = Math.abs(lon2 - lon1) * (Math.PI / 180);
            double dist = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLAT / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLON / 2), 2)));
            dist = radEarth * dist * 1000;
            return dist;
        }
    }
}
