/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasks;

import cafe.IDGeneratorSingleton;
import cafe.InfoMessage;
import cafe.Message;
import cafe.Slot;
import cafe.Task;
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

/**
 *
 * @author elkin
 */
public class Agregator implements Task {

    private Slot entrySlot, exitSlot;

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
            // Creo un map para guardar los nodos drink por id
            Map<Long, List<Node>> drinkNodesMap = new HashMap<>();
        
            Message inputMessage;
            while ((inputMessage = (Message) entrySlot.next()) != null) {
                Document document = inputMessage.getData();
                InfoMessage infoMessage = inputMessage.getHead();
                long id = infoMessage.getId();

                // Extraigo el nodo drink del mensaje
                XPathFactory xPathFactory = XPathFactory.newInstance();
                XPath xpath = xPathFactory.newXPath();
                NodeList drinkNodes = (NodeList) xpath.evaluate("/drink", document, XPathConstants.NODESET);

                // Agrego el nodo extraido al map
                List<Node> drinkList = drinkNodesMap.computeIfAbsent(id, k -> new ArrayList<>());
                for (int i = 0; i < drinkNodes.getLength(); i++) {
                    drinkList.add(drinkNodes.item(i));
                }
            }

            // Ahora, para cada id, reconstruyo el mensaje original
        for (Map.Entry<Long, List<Node>> entry : drinkNodesMap.entrySet()) {
            long id = entry.getKey();
            List<Node> allDrinkNodes = entry.getValue();

            // Creo un nuevo documento par reconstruir el documento original
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document newDocument = builder.newDocument();

            // Creo la raiz <cafe_order>
            Element cafeOrderElement = newDocument.createElement("cafe_order");
            newDocument.appendChild(cafeOrderElement);

            // creo <drinks> 
            Element drinksElement = newDocument.createElement("drinks");
            cafeOrderElement.appendChild(drinksElement);

            // todos los nodos de drink los agrego a <drinks>
            for (Node drinkNode : allDrinkNodes) {
                Node importedNode = newDocument.importNode(drinkNode, true);
                drinksElement.appendChild(importedNode);
            }

            // Creo un nuevo mensaje con el documento reconstruido
            Message newMessage = new Message(new InfoMessage(id, allDrinkNodes.size()), newDocument);
            exitSlot.receiveData(newMessage);
        }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
