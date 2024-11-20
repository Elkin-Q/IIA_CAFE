package tasks;

import cafe.Slot;
import cafe.Task;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ContentEnrincher implements Task {

    private Slot EntrySlot1;
    private Slot EntrySlot2;
    private Slot ExitSlot;

    private XPathExpression exp, exp2,exp3;
     
    public void setXPath(XPathExpression exp,XPathExpression exp2,XPathExpression exp3){
        this.exp=exp;
        this.exp=exp2;
        this.exp=exp3;
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
    public ContentEnrincher() {
    }

    @Override
    public void run() {
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            int tamaño = EntrySlot1.bufferSize();
            for (int i = 0; i < tamaño; i++) {
                Document aux = builder.newDocument();

                Element rootElement = aux.createElement("mensaje");
                aux.appendChild(rootElement);

                NodeList nl = (NodeList) exp.evaluate(EntrySlot1.getBuffer(), XPathConstants.NODESET);
                NodeList nl2 = (NodeList) exp2.evaluate(EntrySlot1.getBuffer(), XPathConstants.NODESET);
                NodeList nl3 = (NodeList) exp3.evaluate(EntrySlot2.getBuffer(), XPathConstants.NODESET);

                Node cabecera = nl.item(0);
                Node drink = nl2.item(0);
                Node stock = nl3.item(0);

                aux.adoptNode(cabecera);
                rootElement.appendChild(cabecera);

                aux.adoptNode(drink);
                rootElement.appendChild(drink);

                aux.adoptNode(stock);
                drink.appendChild(stock);

                ExitSlot.receiveData(aux);
                EntrySlot1.next();
                EntrySlot2.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
