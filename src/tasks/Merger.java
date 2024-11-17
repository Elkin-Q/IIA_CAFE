package tasks;

import cafe.Slot;
import cafe.Task;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Merger implements Task {

    private ArrayList<Slot> EntrySlots;
    private Slot ExitSlot;

    public Merger() {
    }

    public ArrayList<Slot> getEntrySlots() {
        return EntrySlots;
    }

    public void setEntrySlots(ArrayList<Slot> EntrySlots) {
        this.EntrySlots = EntrySlots;
    }

    public Slot getExitSlot() {
        return ExitSlot;
    }

    public void setExitSlot(Slot ExitSlot) {
        this.ExitSlot = ExitSlot;
    }

    @Override
    public void run() {
        try {
            ArrayList<Document> documentsIn = new ArrayList<>();

            //se deberia de comprobar el id o algo para unir las que tengan sentido
            for (int i = 0; i < EntrySlots.size(); i++) {
                for (int j = 0; i < EntrySlots.get(i).bufferSize(); j++) {
                    documentsIn.add((Document) EntrySlots.get(i).next());
                }
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document mergedDocument = builder.newDocument();

            Element root = mergedDocument.createElement("cafe_order");
            Element drinks = mergedDocument.createElement("drinks");
            mergedDocument.appendChild(root);
            root.appendChild(drinks);

            for (Document document : documentsIn) {
                NodeList drinkNodes = document.getElementsByTagName("drink");

                for (int i = 0; i < drinkNodes.getLength(); i++) {
                    Node drinkNode = drinkNodes.item(i);
                    Node importedNode = mergedDocument.importNode(drinkNode, true);
                    drinks.appendChild(importedNode);
                }
            }
            
            ExitSlot.receiveData(mergedDocument);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
