package cafe;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class FileConnector extends Connector {

    private Message message;

    public FileConnector() {

    }

    public Message getMessage() {
        return message;
    }

    public void readFile(String dirFich) {

        Document document;
        
        try {
            File file = new File(dirFich); //ruta fichero xml
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(file);
            document.getDocumentElement().normalize(); //tranforma el fichero xml a un objeto message normalizado
            message = new Message(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateFile(Document doc, String nombreArchivo) {
        try {
            
            File carpeta = new File("soluciones");
            
            File archivoXML = new File(carpeta, nombreArchivo + ".xml");

            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(archivoXML);

            
            transformer.transform(source, result);

            System.out.println("Archivo XML generado correctamente: " + archivoXML.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendDocument() {
        port.receiveDocument(message);
    }

}
