package tasks;

import cafe.Message;
import cafe.Slot;
import cafe.Task;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ContentEnrincher implements Task {

    private Slot EntrySlot1;
    private Slot EntrySlot2;
    private Slot ExitSlot;

    public ContentEnrincher() {
    }

    public Slot getEntrySlot1() {
        return EntrySlot1;
    }

    public void setEntrySlot1(Slot entrySlot) {
        this.EntrySlot1 = entrySlot;
    }

    public Slot getEntrySlot2() {
        return EntrySlot2;
    }

    public void setEntrySlot2(Slot entrySlot) {
        this.EntrySlot2 = entrySlot;
    }

    public Slot getExitSlot() {
        return ExitSlot;
    }

    public void setExitSlot(Slot ExitSlot) {
        this.ExitSlot = ExitSlot;
    }

    @Override
    public void run() {
        int tamaño = EntrySlot1.bufferSize();
        
        for(int i=0; i<tamaño; i++){
            Message mensaje1 = (Message) EntrySlot1.next();
            Message mensaje2 = (Message) EntrySlot2.next();
            
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList stock = null;
            
            try {
                stock = (NodeList) xpath.compile("//stock").evaluate(mensaje1.getData(), XPathConstants.NODESET);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(ContentEnrincher.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Node s = stock.item(0);
            Element elemento = (Element) s;
            String nstock = elemento.getTextContent();
            System.out.println("El estock es:" + nstock);
            
            Document doc = mensaje2.getData();
            Element newElement = doc.createElement("stock");
            newElement.appendChild(doc.createTextNode(nstock));
            Node Drink = doc.getElementsByTagName("drink").item(0);
            Drink.appendChild(newElement);
            
            Message salida = new Message(mensaje2.getHead(), doc);
            ExitSlot.receiveData(salida);
        }
    }

}
