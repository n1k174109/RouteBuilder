import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithm {
    public static Graph calcShortWay(Graph graph, Edge edge, GraphNode source, GraphNode end) {

        edge.setDist(0);

        Set visitedNodes = new HashSet<>();
        Set unvisitedNodes = new HashSet<>();
        unvisitedNodes.add(source);

        while (unvisitedNodes.size() != 0) {
            GraphNode currNode = getLowDistNode(unvisitedNodes);
            unvisitedNodes.remove(currNode);
            for (Map.Entry<GraphNode, Integer> relatedPair: currNode.getRelatedNodes().entrySet()) {
                GraphNode relatedNode = relatedPair.getKey();
                Integer edgeWeight = relatedPair.getValue();

                if (!visitedNodes.contains(relatedNode)) {
                    CalcMinDist(relatedNode, edgeWeight, currNode, graph);
                    unvisitedNodes.add(relatedNode);
                }
            }

            visitedNodes.add(currNode);
//            if (!currNode.getDist().equals(0)) {
//                System.out.print("] -" + currNode.getDist() + "m-> [" + currNode.getLAT() + "," + currNode.getLON());
//            }
//            else System.out.print(currNode.getLAT() + ", " + currNode.getLON());
//            if(currNode.equals(end)) break;
        }
        return graph;
    }
    private static GraphNode getLowDistNode(Set<GraphNode> unvisitedNodes) {
        GraphNode lowDistNode = null;
        Edge edge = new Edge();
        int lowDist = Integer.MAX_VALUE;
        for(GraphNode node: unvisitedNodes) {
            int edgeDist = edge.getDist();
            if (edgeDist < lowDist) {
                lowDist = edgeDist;
                lowDistNode = node;
            }
        }
        return lowDistNode;
    }
    private static void CalcMinDist(Edge analysisNode, Integer edgeWeight, Edge sourceNode, Graph graph) {
        Integer sourceDist = sourceNode.getDist();
        if(sourceDist + edgeWeight < analysisNode.getDist()) {
            analysisNode.setDist(sourceDist + edgeWeight);
            LinkedList shortWay = new LinkedList<>(graph.getShortWay());
            shortWay.add(sourceNode);
            graph.setShortWay(shortWay);
        }
    }
}
