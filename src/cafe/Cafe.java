package cafe;

import tasks.Replicator;
import tasks.Translator;

public class Cafe {

    public static void main(String[] args) {

        FileConnector conector = new FileConnector();
        conector.readFile("path_to_your_file.xml");
        SolutionPort port = new SolutionPort();
        conector.setPort(port);
        Slot entrada = new Slot();
        Slot salida = new Slot();
        port.setEntrySlot(entrada);
        /*
        Replicator replicator = new Replicator();
        replicator.setEntrySlot(entrada);
        replicator.setExitSlot(salida);
        */
        Translator trans = new Translator();
        trans.setEntrySlot(entrada);
        trans.setExitSlot(salida);
        trans.setTable("Tabla");
        trans.setAtribute("document");
        
        conector.sendDocument();
        trans.run();
       // replicator.run();
        salida.prueba();
    }

}
