package tasks;

import cafe.Message;
import cafe.Slot;
import cafe.Task;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Coorrelator implements Task {

    private ArrayList<Slot> entrySlots, exitSlots;

    public Coorrelator() {
        this.entrySlots = new ArrayList<>();
        this.exitSlots = new ArrayList<>();
    }

    public void setEntrySlots(ArrayList<Slot> entrySlots) {
        this.entrySlots = entrySlots;
    }

    public void setExitSlots(ArrayList<Slot> exitSlots) {
        this.exitSlots = exitSlots;
    }

    @Override
    public void run() {

        int tam = entrySlots.get(0).bufferSize();
        for (int i = 0; i < tam; i++) {

            Message messagebd = (Message) entrySlots.get(0).next();
            Message messageRep = (Message) entrySlots.get(1).next();

            //Cogemos el nombre del input de la base de datos
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList drinkBD = null;

            try {
                drinkBD = (NodeList) xpath.compile("//name").evaluate(messagebd.getData(), XPathConstants.NODESET);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Coorrelator.class.getName()).log(Level.SEVERE, null, ex);
            }

            Node D = drinkBD.item(0);

            Element d = (Element) D;

            String nameBD = d.getTextContent();

            //Cogemos el nombre del input del replicator
            XPath xpath2 = XPathFactory.newInstance().newXPath();
            NodeList drinkRep = null;

            try {
                drinkRep = (NodeList) xpath2.compile("//name").evaluate(messageRep.getData(), XPathConstants.NODESET);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Coorrelator.class.getName()).log(Level.SEVERE, null, ex);
            }

            Node Dr = drinkRep.item(0);
            Element dr = (Element) Dr;
            String nameRep = dr.getTextContent();

            if (nameBD.equalsIgnoreCase(nameRep)) {
                exitSlots.get(0).receiveData(messagebd);
                exitSlots.get(1).receiveData(messageRep);
            }

        }

    }

}
