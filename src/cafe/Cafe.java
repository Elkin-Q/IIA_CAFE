package cafe;

import java.util.List;
import tasks.Distributor;
import tasks.Merger;
import tasks.Replicator;
import tasks.Splitter;
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
        conector.sendDocument();
        
       /* Splitter splitter = new Splitter();
        splitter.setEntrySlot(entrada);
        splitter.setExitSlot(salida);
        splitter.run();*/
       
       Translator tranlator = new Translator();
       tranlator.setAtribute("name");
       tranlator.setEntrySlot(entrada);
       tranlator.setExitSlot(salida);
       tranlator.setTable("tabla");
       tranlator.run();
      
        
        
        salida.prueba();
        
        /*Slot salidacold = new Slot();

        port.setEntrySlot(entrada);
        Distributor distributor = new Distributor();
        distributor.setEntrySlot(entrada);
        distributor.addExitSlot(salidahot);
        distributor.addExitSlot(salidacold);
        distributor.definirReglasDistribucion("hot", List.of(0));  // "hot" a slot 0
        distributor.definirReglasDistribucion("cold", List.of(1)); // "cold" a slot 1
*/
        /*
        Replicator replicator = new Replicator();
        replicator.setEntrySlot(entrada);
        replicator.setExitSlot(salida);
         */
 /*Translator trans = new Translator();
        trans.setEntrySlot(entrada);
        //trans.setExitSlot(salida);
        trans.setTable("Tabla");
        trans.setAtribute("document");
        
        conector.sendDocument();
        distributor.run();

        System.out.println("comandas calientes");
        salidahot.prueba();
        System.out.println("comandas frias");
        salidacold.prueba();
         */
    }

}
