package tasks;

import cafe.InfoMessage;
import cafe.Message;
import cafe.OrderStruct;
import cafe.Slot;
import cafe.Task;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Agregator implements Task {

    private Slot entrySlot, exitSlot;
    private OrderStruct struct;

    public OrderStruct getStruct() {
        return struct;
    }

    public void setStruct(OrderStruct struct) {
        this.struct = struct;
    }

    public Agregator() {

    }

    public Agregator(Slot entrySlot, Slot exitSlot) {
        this.entrySlot = entrySlot;
        this.exitSlot = exitSlot;
    }

    public Slot getEntrySlot() {
        return entrySlot;
    }

    public void setEntrySlot(Slot entrySlot) {
        this.entrySlot = entrySlot;
    }

    public Slot getExitSlot() {
        return exitSlot;
    }

    public void setExitSlot(Slot exitSlot) {
        this.exitSlot = exitSlot;
    }

    @Override
    public void run() {
        try {

            // Parsear la estructura base a un documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document baseDocument = struct.getOrderStruct().getData();

            // Crear o buscar el elemento <drinks> en la estructura base
            Element drinksElement = (Element) baseDocument.getElementsByTagName("drinks").item(0);
            if (drinksElement == null) {
                drinksElement = baseDocument.createElement("drinks");
                baseDocument.getDocumentElement().appendChild(drinksElement);
            }

            // Procesar mensajes de entrada y agregar nodos <drink> al elemento <drinks>
            while (entrySlot.bufferSize() != 0) {
                Message inputMessage = (Message) entrySlot.next();
                if (inputMessage == null) {
                    continue;
                }

                Document document = inputMessage.getData();
                XPathFactory xPathFactory = XPathFactory.newInstance();
                XPath xpath = xPathFactory.newXPath();

                // Extraer nodos <drink>
                NodeList drinkNodes = (NodeList) xpath.evaluate("/drink", document, XPathConstants.NODESET);
                for (int i = 0; i < drinkNodes.getLength(); i++) {
                    Node drinkNode = drinkNodes.item(i);
                    Node importedNode = baseDocument.importNode(drinkNode, true);
                    drinksElement.appendChild(importedNode);
                }
            }

            // Crear un nuevo mensaje con el documento final y enviarlo al exitSlot
            Message finalMessage = new Message(new InfoMessage(6, drinksElement.getChildNodes().getLength()), baseDocument);
            exitSlot.receiveData(finalMessage);

        } catch (Exception ex) {
            System.err.println("Error en Agregator.run: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
