package cafe;

import java.io.StringWriter;
import java.util.ArrayList;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    
    public int bufferSize(){
        return buffer.size();
    }

    public void receiveData(Object data) {
        buffer.add(data);
    }

    public void prueba() {

        if (buffer.isEmpty()) {
            System.out.println("El buffer está vacío.");
        } else {

            for (Object firstObject : buffer) {
                if (firstObject instanceof Message) {
                    Message message = (Message) firstObject;
                    Document document = message.getData();
                    Element root = document.getDocumentElement();
                    printDocument(document);

                } else if (firstObject instanceof Element) {
                    
                    Element elemento = (Element) firstObject;
                    System.out.println("Elemento recibido: " + elemento.getTagName() + " | Valor: " + elemento.getTextContent());
                } else {
                    System.out.println("El buffer no contiene un Document o Element. Buffer está vacío o contiene un tipo incorrecto.");
                }
            }
        }
    }
    
    public void printDocument(Document document) {
    try {
        // Crear un transformador
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Crear un StringWriter para capturar la salida
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));

        // Imprimir el documento
        System.out.println(writer.toString());
    } catch (Exception e) {
        System.out.println("Error al imprimir el documento: " + e.getMessage());
    }
}

}
