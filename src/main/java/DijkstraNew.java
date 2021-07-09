import com.sun.source.doctree.SeeTree;

import java.util.*;

public class DijkstraNew {
    private double distNode;
    private Map<GraphNode, Integer> valueNodes = new HashMap<>();

    public Map<GraphNode, Integer> getValueNodes() {
        return valueNodes;
    }

    public void addValueNode(GraphNode node, Integer value) {
        valueNodes.put(node, value);
    }
    public double getDistNode() {
        return distNode;
    }

    public void setDistNode(double distNode) {
        this.distNode = distNode;
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
                                List shortestPath = new ArrayList<>(graph.getShortWay().values());
                                shortestPath.add(currNode);
                                graph.setShortWay(nextNode, shortestPath);
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
            for (Map.Entry<GraphNode, Integer> valueNodes : getValueNodes().entrySet()) {
                if (valueNodes.getKey().equals(node) && valueNodes.getValue() < minDist)
                    minNode = node;
                    minDist = valueNodes.getValue();
            }
        }
        return minNode;
    }
}
