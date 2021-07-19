import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class NewMain {
    private Set<Long> neededNodes = new HashSet<>();
    private Set<Long> neededWays = new HashSet<>();
    private ArrayList<String> Road = new ArrayList<>();
    private List<Edge> path;


    private int nodeElem = 0;
    private Graph graph;

    public NewMain() {}
    public static void main(String[] arg) throws ParserConfigurationException, IOException, SAXException {
        new NewMain().run();
    }
    public void run() throws ParserConfigurationException, IOException, SAXException {
        BufferedReader read;
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document doc;
        graph = new Graph();
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.parse(new File("src/res/nizhnekamsk.xml"));
        initializeRoad(Road);
        analyzeXML(doc);
        buildNodesWays(doc);
        graph.prepareGraph();
        DijkstraNew dn = new DijkstraNew(graph);

        read = new BufferedReader(new InputStreamReader(System.in));
        double lat, lon, lat2, lon2;
        lat = Double.parseDouble(read.readLine());
        lon = Double.parseDouble(read.readLine());
        lat2 = Double.parseDouble(read.readLine());
        lon2 = Double.parseDouble(read.readLine());

        GraphNode startNode = dn.nearestValueNode(lat, lon);
        GraphNode endNode = dn.nearestValueNode(lat2, lon2);

        path = dn.calcShortWay(startNode, endNode);
        printPath(path, startNode.getId());
    }

    private void analyzeXML(Document doc) {
        NodeList docChildren = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < docChildren.getLength(); i++) {
            Node docChild = docChildren.item(i);

            if (docChild.getNodeType() != Node.ELEMENT_NODE)
                continue;

            if (docChild.getNodeName().equals("node")) {
                nodeElem = i;
            } else if (docChild.getNodeName().equals("way")) {
                NodeList wayChildren = docChild.getChildNodes();
                boolean isRoad = false;
                for (int j = wayChildren.getLength() - 1; j >= 0; j--) {
                    Node wayChild = wayChildren.item(j);

                    if (wayChild.getNodeType() != Node.ELEMENT_NODE)
                        continue;

                    NamedNodeMap attrib = wayChild.getAttributes();
                    if (wayChild.getNodeName().equals("tag")) {
                        Node kAttrib = attrib.getNamedItem("k");
                        Node vAttrib = attrib.getNamedItem("v");
                        if (kAttrib.getNodeValue().equals("highway") && Road.contains(vAttrib.getNodeValue())) {
                            isRoad = true;
                            neededWays.add(Long.parseLong(docChild.getAttributes().getNamedItem("id").getNodeValue()));
                        }
                    } else if (isRoad) {
                        neededNodes.add(Long.parseLong(attrib.getNamedItem("ref").getNodeValue()));
                    }
                }
            }
        }
//        System.out.println(neededWays);
    }
    private void buildNodesWays(Document doc) {
        NodeList docChildren = doc.getDocumentElement().getChildNodes();
        for (int j = 0; j < docChildren.getLength(); j++) {
            Node docChild = docChildren.item(j);
            List<Long> nodesList = new ArrayList<>();

            if (docChild.getNodeType() != Node.ELEMENT_NODE) continue;

            if (docChild.getNodeName().equals("node")) {

                NamedNodeMap attributes = docChild.getAttributes();
                long nodeId = Long.parseLong(attributes.getNamedItem("id").getNodeValue());
                double lat = Double.parseDouble(attributes.getNamedItem("lat").getNodeValue());
                double lon = Double.parseDouble(attributes.getNamedItem("lon").getNodeValue());


                if (neededNodes.contains(nodeId)) {
                    graph.addNode(nodeId, new GraphNode(nodeId, lat, lon));
//                    System.out.println(nodeId + " " + lat + " " + lon);
                }
            }
            else if (docChild.getNodeName().equals("way")) {
                NamedNodeMap attributes = docChild.getAttributes();
                NodeList nodeChildren = docChild.getChildNodes();
                long wayId = Long.parseLong(attributes.getNamedItem("id").getNodeValue());
                for (int i = 0; i < nodeChildren.getLength(); i++) {
                    Node nodeChild = nodeChildren.item(i);
                    if (nodeChild.getNodeType() != Node.ELEMENT_NODE) continue;
                    NamedNodeMap attrib = nodeChild.getAttributes();

                    if (!nodeChild.getNodeName().equals("nd")) continue;


                    long ref = Long.parseLong(attrib.getNamedItem("ref").getNodeValue());
                    if (neededWays.contains(wayId)) {
                        nodesList.add(ref);
                    }
                }

                graph.addWay(new GraphWay(wayId, nodesList));
            }
        }
//        System.out.println(waysList);
    }
    private void printPath(List<Edge> path, long startNodeId) {
        System.out.print("LINESTRING(");
        double dist = 0;
        long lastNode  = startNodeId;
        for (int j = 0; j < path.size(); j++) {
            Edge currEdge = path.get(j);
            dist += currEdge.getDist();
            List<Long> nodes = new ArrayList<>(currEdge.getNodesList());
            if (lastNode != currEdge.getFirstNode())
                Collections.reverse(nodes);

            if (j == 0) {
                System.out.print(graph.getMapNodes().get(nodes.get(0)).getLon() + " " + graph.getMapNodes().get(nodes.get(0)).getLat());
            }
            for (int i = 1; i < nodes.size(); i++) {
                System.out.print("," + graph.getMapNodes().get(nodes.get(i)).getLon() + " " + graph.getMapNodes().get(nodes.get(i)).getLat());
            }
            lastNode = nodes.get(nodes.size() - 1);
        }
        System.out.print(")");
        System.out.println("\nDistance:" + Math.round(dist));
        System.out.println("Time:" + Math.round((dist)/60));
    }

    private static void initializeRoad(ArrayList<String> Road) {
        Road.add("motorway");
        Road.add("trunk");
        Road.add("primary");
        Road.add("secondary");
        Road.add("tertiary");
        Road.add("unclassified");
        Road.add("residential");
        Road.add("service");
    }
}
