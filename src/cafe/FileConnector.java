package cafe;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class FileConnector extends Connector {

    private Document document;

    public FileConnector() {

    }

    public void processAndSendData(Slot slot) {

    }

    public Document getDocument() {
        return document;
    }

    public void readFile(String dirFich) {

        try {
            File file = new File(dirFich); //ruta fichero xml
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(file);
            document.getDocumentElement().normalize(); //tranforma el fichero xml a un objeto document normalizado
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateFile() {

    }

    public void sendDocument() {
        port.receiveDocument(document);
    }

}
