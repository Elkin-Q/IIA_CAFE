package tasks;

import cafe.Message;
import cafe.Slot;
import cafe.Task;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Translator implements Task {

    private Slot entrySlot, exitSlot;
    private String table, atribute;

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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAtribute() {
        return atribute;
    }

    public void setAtribute(String atribute) {
        this.atribute = atribute;
    }

    public Translator() {
    }

    @Override
    public void run() {

        Message message = (Message) entrySlot.next();
        Document input = message.getData();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document output = builder.newDocument();

            Element sqlElement = output.createElement("sql");
            output.appendChild(sqlElement);

            NodeList atributeNodes = input.getElementsByTagName(atribute);

            for (int i = 0; i < atributeNodes.getLength(); i++) {
                Node node = atributeNodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String atributeValue = node.getTextContent();
                    Element qElement = output.createElement("query");
                    qElement.setTextContent("SELECT * FROM "+ table +" WHERE "+ atribute +" = '" + atributeValue + "'");
                    sqlElement.appendChild(qElement);
                }
            }
            
            message.setData(output);
            exitSlot.receiveData(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
