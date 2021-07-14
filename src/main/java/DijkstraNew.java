import java.util.*;

public class DijkstraNew {
//    private double distNode;
    private Map<GraphNode, List<GraphNode>> shortWay = new HashMap<>();
    private Map<GraphNode, Integer> valueNodes = new HashMap<>();

    public Map<GraphNode, Integer> getValueNodes() {
        return valueNodes;
    }

    public void addValueNode(GraphNode node, Integer value) {
        valueNodes.put(node, value);
    }

    public Map<GraphNode, List<GraphNode>> getShortWay() {
        return shortWay;
    }

    public void setShortWay(GraphNode node, List<GraphNode> path) {
        shortWay.put(node, path);
    }

    public void calcShortWay(GraphNode startNode, GraphNode endNode, Graph graph) {
        Set<GraphNode> visited = new HashSet<>();
        Set<GraphNode> unvisited = new HashSet<>();

        unvisited.add(startNode);

        while (!unvisited.isEmpty()) {
            GraphNode currNode = getMinNode(unvisited);
            unvisited.remove(currNode);
            for (Map.Entry<GraphNode, List<Edge>> mapRelation : graph.getRelation().entrySet()) {
                GraphNode nextNode = mapRelation.getKey();
                Integer weightEdge = 0;
                List<Edge> edges = new ArrayList<>(mapRelation.getValue());
                for (int i = 0; i < edges.size(); i++) {
                    if (edges.get(i).getNodesList().contains(currNode.getID()))
                        weightEdge = edges.get(i).getDist();

                    if (!visited.contains(nextNode)) {
                        int nextNodeDist = 0;
                        int currNodeDist = 0;
                        for (Map.Entry<GraphNode, Integer> valueNodes : getValueNodes().entrySet()) {
                            if (valueNodes.getKey().equals(nextNode))
                                nextNodeDist = valueNodes.getValue();
                            if (valueNodes.getKey().equals(currNode))
                                currNodeDist = valueNodes.getValue();
                        }
                            if (weightEdge + currNodeDist < nextNodeDist) {
                                nextNodeDist = weightEdge + currNodeDist;
                                List shortestPath = new ArrayList<>(getShortWay().values());
                                shortestPath.add(currNode);
                                setShortWay(nextNode, shortestPath);
                            }

//                        calcMinDist(weightEdge, currNode, nextNode);
                        unvisited.add(nextNode);
                    }
                }
            }

            visited.add(currNode);
            if (currNode.equals(endNode)) break;;
        }
    }
    private GraphNode getMinNode(Set<GraphNode> unvisited) {
        GraphNode minNode = null;
        int minDist = Integer.MAX_VALUE/2;
        for (GraphNode node: unvisited) {
            for (Map.Entry<GraphNode, Integer> valueNodes : valueNodes.entrySet()) {
                if (valueNodes.getKey().equals(node) && valueNodes.getValue() < minDist)
                    minNode = node;
                    minDist = valueNodes.getValue();
            }
        }
        return minNode;
    }

    public void addDistance(Collection<GraphNode> nodesFromMap) {
        List<GraphNode> nodes = new ArrayList<>(nodesFromMap);
//        Map<GraphNode, Integer> valueNodes = new HashMap<>();
        for (int i = 0; i < nodes.size() - 1; i++) {
            double distEdge = 0;
            GraphNode currNode = nodes.get(i);
            GraphNode nextNode = nodes.get(i + 1);
            distEdge += ((int) calcDistNodes(currNode.getLAT(), currNode.getLON(), nextNode.getLAT(), nextNode.getLON()));
//                distEdge += edge.getDist();
            valueNodes.put(currNode, (int) distEdge);
        }

    }
    private double calcDistNodes(double lat1, double lon1, double lat2, double lon2) {
        final double radEarth = 6371.009;
        double dLAT = Math.abs(lat2 - lat1) * (Math.PI/180);
        double dLON = Math.abs(lon2 - lon1) * (Math.PI/180);
        double dist = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLAT/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLON/2),2)));
        dist = radEarth * dist * 1000;
        return dist;
    }
}
