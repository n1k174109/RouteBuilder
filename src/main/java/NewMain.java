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
//    private List<GraphWay> ways = new ArrayList<>();
    private List<Long> refs = new ArrayList<>();
    private Map<Long, List<Long>> ways = new HashMap<>();
    private int nodeElem = 0;

    public void setNodeElem(int nodeElem) {
        this.nodeElem = nodeElem;
    }
    public int getNodeElem() {
        return nodeElem;
    }

    public NewMain() {}
    public static void main(String[] arg) throws ParserConfigurationException, IOException, SAXException {
        new NewMain().run();
    }
    public void run() throws ParserConfigurationException, IOException, SAXException {
        int nodeElem = 0;
        GraphWay graphWay = new GraphWay(ways);
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
        BufferedReader read;
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document doc;
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.parse(new File("src/res/nizhnekamsk.xml"));
        analyzeXML(doc, nodeElem, notRoad, ways);
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
        for (int i = nodes.size() - 1; i > 0; i--) {
            graph.addNode(nodes.get(i));
            nodes.get(i).addNextNode(nodes.iterator().next(), (int)calcDistNodes(nodes.get(i).getLAT(), nodes.get(i).getLON(),nodes.get(i - 1).getLAT(),nodes.get(i - 1).getLON()));
            if(nodes.get(i).getLAT() == lat && nodes.get(i).getLON() == lon) {
                nodeA = nodes.get(i);

            } else if(nodes.get(i).getLAT() == lat2 && nodes.get(i).getLON() == lon2)
                nodeZ = nodes.get(i);
        }

        graph = DijkstraAlgorithm.calcShortWay(graph, nodeA, nodeZ);
    }

    private void analyzeXML(Document doc, int nodeElem, ArrayList<String> notRoad, Map<Long, List<Long>> ways) {
        NodeList children = doc.getDocumentElement().getChildNodes();
        long wayID = 0;
        for (int i = 0; i < children.getLength(); i++) {

            Node done = children.item(i);

            if (done.getNodeName().equals("node")) {
                nodeElem = i;
            }

            if (done.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attributes = done.getAttributes();
                NodeList tags = done.getChildNodes();
                boolean check = false;
                for (int j = tags.getLength() - 1; j >= 0; j--) {

                    Node tag = tags.item(j);

                    if (tag.getNodeType() == Node.ELEMENT_NODE) {
                        NamedNodeMap attrib = tag.getAttributes();
                        Node kAttrib = attrib.getNamedItem("k");
                        Node vAttrib = attrib.getNamedItem("v");

                        if(check && tag.getNodeName().equals("nd")) refs.add(Long.parseLong(attrib.getNamedItem("ref").getNodeValue()));

                        if (tag.getNodeName().equals("tag") && kAttrib.getNodeValue().equals("highway") && !notRoad.contains(vAttrib.getNodeValue())) {
                            check = true;
                            wayID = Long.parseLong(attributes.getNamedItem("id").getNodeValue());
                        }

                        if(j == 1) ways.put(wayID, refs);
                    }
                }
            }
        }
//        System.out.println(ways);
        setNodeElem(nodeElem);
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
                        id = Long.parseLong(attributes.getNamedItem("id").getNodeValue());
                        lat = Double.parseDouble(attributes.getNamedItem("lat").getNodeValue());
                        lon = Double.parseDouble(attributes.getNamedItem("lon").getNodeValue());

                        if (refs.contains(id)) {
                            nodes.add(new GraphNode(id, lat, lon));
                            //System.out.println(id + " " + lat + " " + lon);
                        }
                    }
                }
            }
        }
    }

    private double calcDistNodes(double lat1, double lon1, double lat2, double lon2) {
        final double radEarth = 6371.009;
        double dLAT = Math.abs(lat2 - lat1);
        double dLON = Math.abs(lon2 - lon1);
        double dO = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLAT/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLON/2),2)));
        double Dist = radEarth * dO;
        return Dist;
    }
}
