package cafe;

import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OrderStruct {

    private Message orderStruct;

    public OrderStruct() {
    }

    public OrderStruct(Message orderStruct) {
        this.orderStruct = orderStruct;
    }

    public Message getOrderStruct() {
        return orderStruct;
    }

    public void setOrderStruct(Message orderStruct) {
        this.orderStruct = orderStruct;
    }

    public void saveStruct(Message message) throws ParserConfigurationException {

        Document document = message.getData();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document newDoc = builder.newDocument();
        Element root = newDoc.createElement("cafe_order");
        newDoc.appendChild(root);

        Node orderIdNode = document.getElementsByTagName("order_id").item(0);
        if (orderIdNode != null) {
            Node importedOrderId = newDoc.importNode(orderIdNode, true);
            root.appendChild(importedOrderId);
        }
        orderStruct = new Message(newDoc);
    }

    public void printDocument(Document document) {
        try {
            // Crear un transformador
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Crear un StringWriter para capturar la salida
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));

            // Imprimir el documento
            System.out.println(writer.toString());
        } catch (Exception e) {
            System.out.println("Error al imprimir el documento: " + e.getMessage());
        }

    }
}
