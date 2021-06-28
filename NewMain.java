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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewMain {
    private List<GraphNode> nodes = new ArrayList<>();
    private List<GraphWay> ways = new ArrayList<>();
    private Set<Long> refs = new HashSet<>();
    private int nodeElem = 0, wayElem = 0, relElem = 0;

    public NewMain() {}
    public static void main(String[] arg) throws ParserConfigurationException, IOException, SAXException {
        new NewMain().run();
    }
    public void run() throws ParserConfigurationException, IOException, SAXException {
        int nodeElem = 0, wayElem = 0, relElem = 0;
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document doc;
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.parse(new File("src/res/nizhnekamsk.xml"));
        analyzeXML(doc, nodeElem, wayElem, relElem);
        buildNode(doc);
        buildWay(doc);
    }

    private void analyzeXML(Document doc, int nodeElem, int wayElem, int relElem) {
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
                for (int j = 0; j < tags.getLength(); j++) {
                    Node tag = tags.item(j);
                    if (tag.getNodeType() == Node.ELEMENT_NODE) {
                        NamedNodeMap attrib = tag.getAttributes();
                        Node kAttrib = attrib.getNamedItem("k");
                        if (tag.getNodeName().equals("nd"))
                            refs.add(Long.parseLong(attrib.getNamedItem("ref").getNodeValue().replaceAll("[^0-9]", "")));
                        else if (tag.getNodeName().equals("tag") && kAttrib.getNodeValue().contains("highway"))
                            ways.add(new GraphWay(Long.parseLong(attributes.getNamedItem("id").getNodeValue())));
                    }
                }
            }
        }
        System.out.println(nodeElem + " " + wayElem + " " + relElem);
        //System.out.println(refs);
    }
    private void buildNode(Document doc) {
        NodeList children = doc.getDocumentElement().getChildNodes();
        for (int j = 0; j <= nodeElem; j++) {
            Node done = children.item(j);
            if (done.getNodeType() == Node.ELEMENT_NODE) {
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

                        if (done.getNodeName().equals("node"))
                            nodes.add(new GraphNode(id, lat, lon));
                        System.out.println(lat + " " + lon);

                    }
                }
            }
        }
    }
    private void buildWay(Document doc) {
    }
}

