package cafe;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Slot {

    private ArrayList<Object> buffer;

    public Slot() {
        buffer = new ArrayList<>();
    }

    public ArrayList<Object> getBuffer() {
        return buffer;
    }

    public Object next() {
        Object next = buffer.getFirst();
        buffer.removeFirst();
        return next;
    }

    public void receiveData(Object data) {
        buffer.add(data);
    }
    
    public void prueba(){
        Document document = (Document) buffer.getFirst();
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes(); //Tengo mi documento separado en elementos

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Object data = element.getTextContent().trim();
                if (data!=null) {
                    System.out.println(data);
                }
            }
        }
    }

}
