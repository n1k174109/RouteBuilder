import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithm {
    public static Graph calcShortWay(Graph graph, GraphNode source, GraphNode end) {

        source.setDist(0);

        Set visitedNodes = new HashSet<>();
        Set unvisitedNodes = new HashSet<>();
        unvisitedNodes.add(source);

        while (unvisitedNodes.size() != 0) {
            GraphNode currNode = getLowDistNode(unvisitedNodes);
            unvisitedNodes.remove(currNode);
            for( Map.Entry<GraphNode, Integer> adjacencyPair: currNode.getAdjNodes().entrySet()) {
                GraphNode adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();

                if (!visitedNodes.contains(adjacentNode)) {
                    CalcMinDist(adjacentNode, edgeWeight, currNode);
                    unvisitedNodes.add(adjacentNode);
                }
            }

            visitedNodes.add(currNode);
            if (!currNode.getDist().equals(0)) { System.out.print(" -" + currNode.getDist() + "-> " + currNode.getLAT() + "," + currNode.getLON()); }
            else System.out.print(currNode.getLAT() + "," + currNode.getLON());
            if(currNode.equals(end)) break;
        }
        return graph;
    }
    private static GraphNode getLowDistNode(Set<GraphNode> unvisitedNodes) {
        GraphNode lowDistNode = null;
        int lowDist = Integer.MAX_VALUE;
        for(GraphNode node: unvisitedNodes) {
            int nodeDist = node.getDist();
            if (nodeDist < lowDist) {
                lowDist = nodeDist;
                lowDistNode = node;
            }
        }
        return lowDistNode;
    }
    private static void CalcMinDist(GraphNode ENode, Integer edgeWeight, GraphNode sourceNode) {
        Integer sourceDist = sourceNode.getDist();
        if(sourceDist + edgeWeight < ENode.getDist()) {
            ENode.setDist(sourceDist + edgeWeight);
            LinkedList shortWay = new LinkedList<>(sourceNode.getShortWay());
            shortWay.add(sourceNode);
            ENode.setShortWay(shortWay);
        }
    }
}
