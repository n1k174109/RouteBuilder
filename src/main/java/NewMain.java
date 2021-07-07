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
    private List<GraphWay> ways = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Set<GraphNode> mainNodes = new HashSet<>();

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
        buildNodesWays(doc);
        Graph graph = new Graph();
        read = new BufferedReader(new InputStreamReader(System.in));
        double lat, lon, lat2, lon2;
        lat = Double.parseDouble(read.readLine());
        lon = Double.parseDouble(read.readLine());
        lat2 = Double.parseDouble(read.readLine());
        lon2 = Double.parseDouble(read.readLine());
        GraphNode nodeStart = null;
        GraphNode nodeEnd = null;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getLAT() == lat && nodes.get(i).getLON() == lon) {
                nodeStart = nodes.get(i);
            }
            if (nodes.get(i).getLAT() == lat2 && nodes.get(i).getLON() == lon2) {
                nodeEnd = nodes.get(i);
            }
        }

//        for (Map.Entry wayKeyValue : waysList.entrySet()) {
//            List<Long> valueForNodes = new ArrayList<Long>(waysList.get(wayKeyValue.getKey()));
//            for (int i = 0; i < nodes.size(); i++) {
//                GraphNode currNode = nodes.get(i);
//
//                    if (valueForNodes.contains(currNode.getID())) {
//                        if (i < nodes.size() - 1) {
//                            GraphNode nextNode = nodes.get(i + 1);
//                            currNode.addNextNode(nextNode, (int) calcDistNodes(currNode.getLAT(), currNode.getLON(), nextNode.getLAT(), nextNode.getLON()));
//                        }
//                        if (!graph.getNodes().contains(currNode))
//                            graph.addNode(currNode);
//
//                    }
//                }
//            }

        Edge edge = new Edge();
        GraphWay graphway = new GraphWay();
//        List<Long> mNodes = new ArrayList<>();
//        mNodes.addAll(mainNodes);
        for (int i = 0; i < nodes.size(); i++) {

//            for (int j = 0; j < mNodes.size(); j++) {
                if (mainNodes.contains(nodes.get(i).getID())) {

                }

        }
        graphway.sliceWays(mainNodes, ways, graph, edges);
        for (GraphNode node: nodes) {
            graph.addNode(node);
        }


        DijkstraAlgorithm da = new DijkstraAlgorithm();
        da.calcShortWay(edge, graph, nodeStart, nodeEnd);

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
        Set<Long> findCrossNode = new HashSet();
        List<Long> refList = new ArrayList<>();
        for (int j = docChildren.getLength(); j > 0; j--) {
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
                    if (!findCrossNode.add(refList.get(j)))
                        mainNodes.add(new GraphNode(refList.get(j), lat, lon));
//                    System.out.println(nodeId + " " + lat + " " + lon);
                }

            } else if (docChild.getNodeName().equals("way")) {
                NamedNodeMap attributes = docChild.getAttributes();
                Set<Long> findMainNodes = new HashSet<>();
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
//                        if (!findCrossNode.add(ref))
//                            mainNodes.add((ref));
                        ways.add(new GraphWay(wayId, nodesList));
                    }
                }
            }
        }
//        System.out.println(waysList);
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
