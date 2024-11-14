package cafe;

import tasks.Replicator;

public class Cafe {

    public static void main(String[] args) {

        FileConnector conector = new FileConnector();
        conector.readFile("path_to_your_file.xml");
        SolutionPort port = new SolutionPort();
        conector.setPort(port);
        Slot entrada = new Slot();
        Slot salida = new Slot();
        port.setEntrySlot(entrada);
        Replicator replicator = new Replicator();
        replicator.setEntrySlot(entrada);
        replicator.setExitSlot(salida);
        conector.sendDocument();
        replicator.run();
        salida.prueba();
    }

}
