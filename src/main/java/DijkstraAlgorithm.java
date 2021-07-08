import java.util.*;

public class DijkstraAlgorithm {
    private double distNode;

    public double getDistNode() {
        return distNode;
    }

    public void setDistNode(double distNode) {
        this.distNode = distNode;
    }

    public void calcShortWay(Edge edge, Graph graph, GraphNode startNode, GraphNode endNode) {
        //edge.setDist(0);
        setDistNode(0);

        Set<GraphNode> visitedNodes = new HashSet<>();
        Set<GraphNode> unvisitedNodes = new HashSet<>();
        unvisitedNodes.add(startNode);

        while (unvisitedNodes.size() != 0) {
            GraphNode currNode = unvisitedNodes.iterator().next();
            unvisitedNodes.remove(currNode);
            for (Map.Entry<GraphNode, List<Edge>> mapRelation : graph.getRelation().entrySet()) {
                GraphNode nextNode = mapRelation.getKey();
                List edges = new ArrayList<>(mapRelation.getValue());
                Integer edgeWeight = 0;
                for (int i = 0; i < edges.size(); i++) {
                    if (mapRelation.getValue().get(i).getNodesList().contains(currNode.getID()))
                        edgeWeight = mapRelation.getValue().get(i).getDist();


                    if (!visitedNodes.contains(nextNode)) {
                        calcMinDist(currNode, edgeWeight, graph);
                        unvisitedNodes.add(nextNode);
                    }
                }
            }

            visitedNodes.add(currNode);
//            if (!edge.getDist().equals(0)) {
//                System.out.print("] -" + edge.getDist() + "m-> [" + currNode.getLAT() + "," + currNode.getLON());
//            }
//            else
                System.out.print("--> " + currNode.getLAT() + ", " + currNode.getLON() + "[" + edge.getDist() + "] ");
            if (currNode.equals(endNode)) break;
        }
    }

//    private GraphNode getLowDistNode(Set<GraphNode> unvisitedNodes, Edge edge) {
//        GraphNode lowDistNode = null;
////        int lowDist = Integer.MAX_VALUE;
//        for (GraphNode node : unvisitedNodes) {
//            int edgeDist = edge.getDist();
//
////            if (edgeDist < lowDist) {
//            int lowDist = edgeDist;
//            lowDistNode = node;
//
//        }
//        return lowDistNode;
//    }

    private void calcMinDist(GraphNode currNode, Integer edgeWeight, Graph graph) {
        Integer sourceDist = (int)getDistNode();
        if (sourceDist + edgeWeight < getDistNode()) {
            setDistNode(sourceDist + edgeWeight);
            List<GraphNode> shortWay = new ArrayList<>(graph.getShortWay());
            shortWay.add(currNode);
            graph.setShortWay(shortWay);
        }
    }
}
