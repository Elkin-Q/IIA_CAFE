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

    public void prueba() {

        if (buffer.isEmpty()) {
            System.out.println("El buffer está vacío.");
        } else {

            for (Object firstObject : buffer) {
                if (firstObject instanceof Document) {
                    Document document = (Document) firstObject;
                    Element root = document.getDocumentElement();

                    System.out.println("Comanda recibida: ");

                    NodeList orderIdNodes = root.getElementsByTagName("order_id");
                    if (orderIdNodes.getLength() > 0) {
                        String orderId = orderIdNodes.item(0).getTextContent();
                        System.out.println("Order ID: " + orderId);
                    }

                    NodeList drinksNodes = root.getElementsByTagName("drinks");
                    for (int i = 0; i < drinksNodes.getLength(); i++) {
                        Element drinksElement = (Element) drinksNodes.item(i);
                        System.out.println("Bebidas: ");

                        NodeList drinkNodes = drinksElement.getElementsByTagName("drink");
                        for (int j = 0; j < drinkNodes.getLength(); j++) {
                            Element drinkElement = (Element) drinkNodes.item(j);
                            String drinkName = drinkElement.getElementsByTagName("name").item(0).getTextContent();
                            String drinkType = drinkElement.getElementsByTagName("type").item(0).getTextContent();

                            System.out.println("    Bebida: " + drinkName + " | Tipo: " + drinkType);
                        }
                    }
                } else if (firstObject instanceof Element) {
                    
                    Element elemento = (Element) firstObject;
                    System.out.println("Elemento recibido: " + elemento.getTagName() + " | Valor: " + elemento.getTextContent());
                } else {
                    System.out.println("El buffer no contiene un Document o Element. Buffer está vacío o contiene un tipo incorrecto.");
                }
            }
        }
    }

}
