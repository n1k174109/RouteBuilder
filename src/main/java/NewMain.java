import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class NewMain {
    private List<GraphNode> nodes = new ArrayList<>();
    private List<GraphWay> ways = new ArrayList<>();
    private Set<Long> refs = new HashSet<>();
    private int nodeElem = 0, wayElem = 0, relElem = 0;
//    private Map<GraphWay, Integer> ways= new HashMap<>();



    public void setElements(int nodeElem, int wayElem, int relElem) {
        this.nodeElem = nodeElem;
        this.wayElem = wayElem;
        this.relElem = relElem;
    }
    public int getNodeElem() {
        return nodeElem;
    }
    public int getWayElem() {
        return wayElem;
    }
    public int getRelElem() {
        return relElem;
    }

    public NewMain() {}
    public static void main(String[] arg) throws ParserConfigurationException, IOException, SAXException {
        new NewMain().run();
    }
    public void run() throws ParserConfigurationException, IOException, SAXException {
        int nodeElem = 0, wayElem = 0, relElem = 0;
        ArrayList<String> notRoad = new ArrayList<>();
        notRoad.add("footway");
        notRoad.add("pedestrian");
        notRoad.add("track");
        notRoad.add("bus_guideway");
        notRoad.add("escape");
        notRoad.add("raceway");
        notRoad.add("bridleway");
        notRoad.add("steps");
        notRoad.add("corridor");
        notRoad.add("path");
        notRoad.add("crossing");
        notRoad.add("give_way");
        notRoad.add("milestone");
        notRoad.add("platform");
        notRoad.add("crossing");
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document doc;
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.parse(new File("src/res/nizhnekamsk.xml"));
        analyzeXML(doc, nodeElem, wayElem, relElem, notRoad);
        buildNode(doc);
        Graph graph = new Graph();
        for (GraphNode node:
             nodes) {
            graph.addNode(node);
        }

        graph = DijkstraAlgorithm.calcShortWay(graph, nodes.get(0));
        System.out.println(graph.getNodes());

        //buildWay(doc);
    }

    private void analyzeXML(Document doc, int nodeElem, int wayElem, int relElem, ArrayList<String> notRoad) {
        NodeList children = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node done = children.item(i);
            if (done.getNodeName().equals("node")) {
                nodeElem = i;
            } else if (done.getNodeName().equals("way")) {
                wayElem = i;
            } else if (done.getNodeName().equals("relation")) {
                relElem = i;
            }
            if (done.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attributes = done.getAttributes();
                NodeList tags = done.getChildNodes();
                boolean t = false;
                for (int j = tags.getLength() - 1; j >= 0; j--) {
                    Node tag = tags.item(j);
                    if (tag.getNodeType() == Node.ELEMENT_NODE) {
                        NamedNodeMap attrib = tag.getAttributes();
                        Node kAttrib = attrib.getNamedItem("k");
                        Node vAttrib = attrib.getNamedItem("v");
                        if(t && tag.getNodeName().equals("nd")) refs.add(Long.parseLong(attrib.getNamedItem("ref").getNodeValue()));
                        if (tag.getNodeName().equals("tag") && kAttrib.getNodeValue().equals("highway") && !notRoad.contains(vAttrib.getNodeValue())) {
                            t = true;
                            ways.add(new GraphWay(Long.parseLong(attributes.getNamedItem("id").getNodeValue())));
                        }
                    }
                }
            }
        }

        setElements(nodeElem, wayElem, relElem);
//        System.out.println(refs);
    }
    private void buildNode(Document doc) {
        NodeList children = doc.getDocumentElement().getChildNodes();
        for (int j = 0; j < nodeElem; j++) {
            Node done = children.item(j);
            if (done.getNodeType() == Node.ELEMENT_NODE && done.getNodeName().equals("node")) {
                // атрибуты узла
                NamedNodeMap attributes = done.getAttributes();
                NodeList tags = done.getChildNodes();
                for (int i = 0; i < tags.getLength(); i++) {
                    Node tag = tags.item(i);
                    if (tag.getNodeType() == Node.ELEMENT_NODE ) {
                        NamedNodeMap at = tag.getAttributes();
                        long id = 0;
                        double lat = 0, lon = 0;

                        try {
                            id = Long.parseLong(attributes.getNamedItem("id").getNodeValue().replaceAll("[^0-9]", ""));
                            lat = Double.parseDouble(attributes.getNamedItem("lat").getNodeValue().replaceAll("[^0-9]", ""));
                            lon = Double.parseDouble(attributes.getNamedItem("lon").getNodeValue().replaceAll("[^0-9]", ""));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if (refs.contains(id)) {
                            nodes.add(new GraphNode(id, lat, lon));
//                            System.out.println("id:" + id + "lat:" + lat + "lon:" + lon);
                        }
                    }
                }
            }
        }
        //System.out.println(nodes);
    }
    private void buildWay(Document doc) {
    }
}
