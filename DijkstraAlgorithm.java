import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithm {
    public static Graph calcShortWay(Graph graph, Graph source) {
        source.setDist(0);

        Set visitedNodes = new HashSet<>();
        Set unvisitedNodes = new HashSet<>();

        while (unvisitedNodes.size() != 0) {
            Graph currNode = getLowDistNode(unvisitedNodes);
            unvisitedNodes.remove(currNode);
            for(Map.Entry<GraphNode, Integer> adjacencyPair: currNode.getAdjNodes().entrySet()) {
                GraphNode adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (visitedNodes.contains(adjacentNode)) {
                    CalcMinDist(adjacentNode, edgeWeight, currNode);
                    unvisitedNodes.add(adjacentNode);
                }
            }
            visitedNodes.add(currNode);
        }
        return graph;
    }
    private static Graph getLowDistNode(Set<Graph> unvisitedNodes) {
        Graph lowDistNode = null;
        int lowDist = Integer.MAX_VALUE;
        for(Graph node: unvisitedNodes) {
            int nodeDist = node.getDist();
            if (nodeDist < lowDist) {
                lowDist = nodeDist;
                lowDistNode = node;
            }
        }
        return lowDistNode;
    }
    private static void CalcMinDist(GraphNode ENode, Integer edgeWeight, Graph sourceNode) {
//        int sourceDist = sourceNode.getDist();
//        if(sourceDist + edgeWeight < ENode.getDist()) {
//            ENode.setDist(sourceDist + edgeWeight);
//            LinkedList shortWay = new LinkedList<>(sourceNode.getShortWay());
//            shortWay.add(sourceNode);
//            ENode.setShortWay(shortWay);
//        }
    }
}

