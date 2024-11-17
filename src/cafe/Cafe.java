package cafe;

import java.util.List;
import tasks.Distributor;
import tasks.Replicator;

public class Cafe {

    public static void main(String[] args) {

        FileConnector conector = new FileConnector();
        conector.readFile("path_to_your_file.xml");

        SolutionPort port = new SolutionPort();
        conector.setPort(port);

        Slot entrada = new Slot();
        Slot salidahot = new Slot();
        Slot salidacold = new Slot();

        port.setEntrySlot(entrada);
        Distributor distributor = new Distributor();
        distributor.setEntrySlot(entrada);
        distributor.addExitSlot(salidahot);
        distributor.addExitSlot(salidacold);
        distributor.definirReglasDistribucion("hot", List.of(0));  // "hot" a slot 0
        distributor.definirReglasDistribucion("cold", List.of(1)); // "cold" a slot 1

        conector.sendDocument();
        distributor.run();

        System.out.println("comandas calientes");
        salidahot.prueba();
        System.out.println("comandas frias");
        salidacold.prueba();
    }

}
