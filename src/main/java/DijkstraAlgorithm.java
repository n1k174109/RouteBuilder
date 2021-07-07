import java.util.*;

public class DijkstraAlgorithm {
    public void calcShortWay(Edge edge, Graph graph, GraphNode startNode, GraphNode endNode) {
        edge.setDist(0);

        Set<GraphNode> visitedNodes = new HashSet<>();
        Set<GraphNode> unvisitedNodes = new HashSet<>();
        unvisitedNodes.add(startNode);

        while (unvisitedNodes.size() != 0) {
            GraphNode currNode = getLowDistNode(unvisitedNodes, edge);
            unvisitedNodes.remove(currNode);
            for (Map.Entry<GraphNode, List<Edge>> mapRelation : graph.getRelation().entrySet()) {
                GraphNode nextNode = mapRelation.getKey();
                Integer edgeWeight = edge.getDist();

                if (!visitedNodes.contains(nextNode)) {

                    calcMinDist(currNode, nextNode, edge, edgeWeight, graph);
                    unvisitedNodes.add(nextNode);
                }
            }

            visitedNodes.add(currNode);
            if (!edge.getDist().equals(0)) {
                System.out.print("] -" + edge.getDist() + "m-> [" + currNode.getLAT() + "," + currNode.getLON());
            }
            else System.out.print(currNode.getLAT() + ", " + currNode.getLON());
            if (currNode.equals(endNode)) break;
        }
    }

    private GraphNode getLowDistNode(Set<GraphNode> unvisitedNodes, Edge edge) {
        GraphNode lowDistNode = null;
//        int lowDist = Integer.MAX_VALUE;
        for (GraphNode node : unvisitedNodes) {
            int edgeDist = edge.getDist();

//            if (edgeDist < lowDist) {
            int lowDist = edgeDist;
            lowDistNode = node;

        }
        return lowDistNode;
    }

    private void calcMinDist(GraphNode currNode, GraphNode nextNode, Edge edge, Integer edgeWeight, Graph graph) {
        Integer sourceDist = edge.getDist();
        if (sourceDist + edgeWeight < edge.getDist()) {
            edge.setDist(sourceDist + edgeWeight);
            List<GraphNode> shortWay = new ArrayList<>(graph.getShortWay());
            shortWay.add(currNode);
            graph.setShortWay(shortWay);
        }
    }
}
