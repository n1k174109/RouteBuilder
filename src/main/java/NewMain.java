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
    private List<GraphNode> nodes = new ArrayList<>();
    private Set<Long> neededNodes = new HashSet<>();
    private Set<Long> neededWays = new HashSet<>();
    private ArrayList<String> Road = new ArrayList<>();
    private Map<Long, List<Long>> waysList = new HashMap<>();

    private int nodeElem = 0;

    public NewMain() {}
    public static void main(String[] arg) throws ParserConfigurationException, IOException, SAXException {
        new NewMain().run();
    }
    public void run() throws ParserConfigurationException, IOException, SAXException {
        BufferedReader read;
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document doc;
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.parse(new File("src/res/nizhnekamsk.xml"));
        initializeRoad(Road);
        analyzeXML(doc);
        buildNode(doc);
        Graph graph = new Graph();
        read = new BufferedReader(new InputStreamReader(System.in));
        double lat, lon, lat2, lon2;
        lat = Double.parseDouble(read.readLine());
        lon = Double.parseDouble(read.readLine());
        lat2 = Double.parseDouble(read.readLine());
        lon2 = Double.parseDouble(read.readLine());
        GraphNode nodeA = null;
        GraphNode nodeZ = null;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getLAT() == lat && nodes.get(i).getLON() == lon) {
                nodeA = nodes.get(i);
            }
            if (nodes.get(i).getLAT() == lat2 && nodes.get(i).getLON() == lon2) {
                nodeZ = nodes.get(i);
            }
        }

//            for (Map.Entry entry : waysList.entrySet()) {
//                if (nodes.iterator().equals(entry.getValue()))
//                    GraphNode currNode = nodes.get(i);
//
//                if (entry.getValue().equals(currNode.getID())) {
//                    graph.addNode(currNode);
//
//                    GraphNode nextNode = nodes.get(i + 1);
//                    currNode.addNextNode(nextNode, (int) calcDistNodes(currNode.getLAT(), currNode.getLON(), nextNode.getLAT(), nextNode.getLON()));
//
//                    if (lat == currNode.getLAT() && lon == currNode.getLON()) nodeA = currNode;
//                    if (lat2 == currNode.getLAT() && lon2 == currNode.getLON()) nodeZ = currNode;
//



//        if (graph != null) System.out.println(graph.getNodes());
        graph = DijkstraAlgorithm.calcShortWay(graph, nodeA, nodeZ);
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
    private void buildNode(Document doc) {
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
                    nodes.add(new GraphNode(nodeId, lat, lon));
                    System.out.println(nodeId + " " + lat + " " + lon);
                }

            } else if (docChild.getNodeName().equals("way")) {
                NamedNodeMap attributes = docChild.getAttributes();

                NodeList nodeChildren = docChild.getChildNodes();
                for (int i = 0; i < nodeChildren.getLength(); i++) {
                    Node nodeChild = nodeChildren.item(i);
                    if (nodeChild.getNodeType() != Node.ELEMENT_NODE) continue;
                    NamedNodeMap attrib = nodeChild.getAttributes();
                    if (!nodeChild.getNodeName().equals("nd")) continue;
                    long wayId = Long.parseLong(attributes.getNamedItem("id").getNodeValue());
                    long ref = Long.parseLong(attrib.getNamedItem("ref").getNodeValue());
                    if (neededWays.contains(wayId) && neededNodes.contains(ref)) {
                        nodesList.add(ref);
                        waysList.put(wayId, nodesList);
                    }
                }

            }
        }
//        System.out.println(waysList);
    }

    private double calcDistNodes(double lat1, double lon1, double lat2, double lon2) {
        final double radEarth = 6371.009;
        double dLAT = Math.abs(lat2 - lat1);
        double dLON = Math.abs(lon2 - lon1);
        double dist = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLAT/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLON/2),2)));
        dist = radEarth * dist;
        return dist;
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
