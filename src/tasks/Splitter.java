package tasks;

import cafe.IDGeneratorSingleton;
import cafe.InfoMessage;
import cafe.Message;
import cafe.OrderStruct;
import cafe.Slot;
import cafe.Task;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Splitter implements Task {

    private Slot entrySlot, exitSlot;
    private OrderStruct struct;

    public Splitter() {
        
    }

    public OrderStruct getStruct() {
        return struct;
    }

    public void setStruct(OrderStruct struct) {
        this.struct = struct;
    }

    public Splitter(Slot entrySlot, Slot exitSlot) {
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
            Message input_message = (Message) entrySlot.next();
            struct.saveStruct(input_message);
            Document document = input_message.getData();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            NodeList drinkNodes = (NodeList) xpath.evaluate("/cafe_order/drinks/drink", document, XPathConstants.NODESET);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            long id = IDGeneratorSingleton.getInstance().generateId();
            int secuencylong = drinkNodes.getLength();

            for (int i = 0; i < drinkNodes.getLength(); i++) {
                
                Document newDocument = builder.newDocument();
                
                Node importedNode = newDocument.importNode(drinkNodes.item(i), true);
                newDocument.appendChild(importedNode);
                
                Message newMessage = new Message(new InfoMessage(id, secuencylong), newDocument);
                exitSlot.receiveData(newMessage);
                
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
