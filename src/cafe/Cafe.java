package cafe;

import java.util.ArrayList;
import java.util.List;
import tasks.*;

public class Cafe {

    public static void main(String[] args) {
        
        String comanda = "order6"; //nombre de la comanda con la que se desea trabajar

        FileConnector conector = new FileConnector();
        conector.readFile(comanda);
        
        DBConnector conectorDBHot = new DBConnector();
        conectorDBHot.connect();
        
        DBConnector conectorDBCold = new DBConnector();
        conectorDBCold.connect();

        FileConnector exitConector = new FileConnector();

        SolutionPort port = new SolutionPort();
        conector.setPort(port);

        SolutionPort portDBHot = new SolutionPort();
        conectorDBHot.setPort(portDBHot);
        
        SolutionPort portDBCold = new SolutionPort();
        conectorDBCold.setPort(portDBCold);
        
        OrderStruct struct = new OrderStruct();
        

        //declaracion de slots
        Slot entradaComandas = new Slot();
        Slot salidaSpliter = new Slot();
        Slot salidaDis1 = new Slot();
        Slot salidaDis2 = new Slot();
        ArrayList<Slot> salidasDis = new ArrayList<>();
        salidasDis.add(salidaDis1);
        salidasDis.add(salidaDis2);
        ArrayList<Slot> salidasRepHot = new ArrayList<>();
        Slot salidaRepHot1 = new Slot();
        Slot salidaRepHot2 = new Slot();
        salidasRepHot.add(salidaRepHot1);
        salidasRepHot.add(salidaRepHot2);
        ArrayList<Slot> salidasRepCold = new ArrayList<>();
        Slot salidaRepCold1 = new Slot();
        Slot salidaRepCold2 = new Slot();
        salidasRepCold.add(salidaRepCold1);
        salidasRepCold.add(salidaRepCold2);
        Slot consultaHot = new Slot();
        Slot consultaCold = new Slot();

        Slot salidaBDHot = new Slot();
        Slot salidaBDCold = new Slot();
        ArrayList<Slot> entradasCoorHot = new ArrayList<>();
        entradasCoorHot.add(salidaBDHot);
        entradasCoorHot.add(salidaRepHot2);
        ArrayList<Slot> entradasCoorCold = new ArrayList<>();
        entradasCoorCold.add(salidaBDCold);
        entradasCoorCold.add(salidaRepCold2);

        Slot salidaCoorHot1 = new Slot();
        Slot salidaCoorHot2 = new Slot();
        Slot salidaCoorCold1 = new Slot();
        Slot salidaCoorCold2 = new Slot();
        ArrayList<Slot> salidasCoorHot = new ArrayList<>();
        salidasCoorHot.add(salidaCoorHot1);
        salidasCoorHot.add(salidaCoorHot2);
        ArrayList<Slot> salidasCoorCold = new ArrayList<>();
        salidasCoorCold.add(salidaCoorCold1);
        salidasCoorCold.add(salidaCoorCold2);
        Slot salidaContentHot = new Slot();
        Slot salidaContentCold = new Slot();
        ArrayList<Slot> entradasMerger = new ArrayList<>();
        entradasMerger.add(salidaContentHot);
        entradasMerger.add(salidaContentCold);
        Slot salidaMerger = new Slot();
        Slot salidaAgregator = new Slot();

        port.setEntrySlot(entradaComandas);
        conector.sendDocument();

        portDBHot.setEntrySlot(salidaBDHot);
        portDBCold.setEntrySlot(salidaBDCold);

        //tarea spliter
        Splitter splitter = new Splitter(entradaComandas, salidaSpliter);
        splitter.setStruct(struct);
        splitter.run();

        //tarea distributor
        Distributor distributor = new Distributor(salidaSpliter, salidasDis);
        distributor.definirReglasDistribucion("hot", List.of(0));  // "hot" a slot 0
        distributor.definirReglasDistribucion("cold", List.of(1)); // "cold" a slot 1
        distributor.run();

        System.out.println("Bebidas calientes");
        salidaDis1.prueba();
        System.out.println("Bebidas frias");
        salidaDis2.prueba();

        //*************** tareas replicator **************************
        //replicator caliente
        Replicator repHot = new Replicator();
        repHot.setEntrySlot(salidaDis1);
        repHot.setExitSlot(salidasRepHot);
        repHot.run();

        //replicator fria
        Replicator repCold = new Replicator();
        repCold.setEntrySlot(salidaDis2);
        repCold.setExitSlot(salidasRepCold);
        repCold.run();

        //****************** tareas Translator *****************************
        //tranlator caliente
        Translator transHot = new Translator();
        transHot.setEntrySlot(salidaRepHot1);
        transHot.setExitSlot(consultaHot);
        transHot.setTable("HotDrinks");
        transHot.setAtribute("name");
        transHot.run();

        //translator fria
        Translator transCold = new Translator();
        transCold.setEntrySlot(salidaRepCold1);
        transCold.setExitSlot(consultaCold);
        transCold.setTable("ColdDrinks");
        transCold.setAtribute("name");
        transCold.run();

        System.out.println("Consulta caliente: ");
        consultaHot.prueba();
        System.out.println("Consulta Frio: ");
        consultaCold.prueba();
        
        //**************** Realizar consultas a la BD ************************
        int tamHot = consultaHot.getBuffer().size();
        Message messageH;
        for (int i = 0; i < tamHot; i++) {
            messageH = (Message) consultaHot.next();
            conectorDBHot.consult(messageH.getData());
        }
        
        int tamCold = consultaCold.getBuffer().size();
        Message messageC;
        for (int i = 0; i < tamCold; i++) {
            messageC = (Message) consultaCold.next();
            conectorDBCold.consult(messageC.getData());
        }
        

        System.out.println("\nSALIDAS DE LA BD Caliente");
        salidaBDHot.prueba();
        System.out.println("\nSALIDAS DE LA BD Frio");
        salidaBDCold.prueba();
       
        //******************* tareas coorrelator *****************************
        //coorrelator calientes
        Coorrelator coorrelatorHot = new Coorrelator();
        coorrelatorHot.setEntrySlots(entradasCoorHot);
        coorrelatorHot.setExitSlots(salidasCoorHot);
        coorrelatorHot.run();

        //coorrelator frias
        Coorrelator coorrelatorCold = new Coorrelator();
        coorrelatorCold.setEntrySlots(entradasCoorCold);
        coorrelatorCold.setExitSlots(salidasCoorCold);
        coorrelatorCold.run();

        System.out.println("Salidas del coorrelator");
        salidaCoorHot1.prueba();
        salidaCoorHot2.prueba();

        System.out.println("Salidas del coorrelator frio");
        salidaCoorCold1.prueba();
        salidaCoorCold2.prueba();

        //******************** tareas Content enrinchment *********************
        //Content enrinchment hot
        ContentEnrincher contentHot = new ContentEnrincher();
        contentHot.setEntrySlot1(salidaCoorHot1);
        contentHot.setEntrySlot2(salidaCoorHot2);
        contentHot.setExitSlot(salidaContentHot);
        contentHot.run();
        System.out.println("Salida Enricher");
        salidaContentHot.prueba();

        //Content enrinchment Cold
        ContentEnrincher contentCold = new ContentEnrincher();
        contentCold.setEntrySlot1(salidaCoorCold1);
        contentCold.setEntrySlot2(salidaCoorCold2);
        contentCold.setExitSlot(salidaContentCold);
        contentCold.run();
        System.out.println("Salida Enricher Cold");
        salidaContentCold.prueba();

        //******************** tareas Merger *********************
        Merger merger = new Merger();
        merger.setEntrySlots(entradasMerger);
        merger.setExitSlot(salidaMerger);
        merger.run();

        System.out.println("Salida Merger");
        salidaMerger.prueba();

        //******************** tareas Agregator *********************
        Agregator agregator = new Agregator();
        agregator.setEntrySlot(salidaMerger);
        agregator.setExitSlot(salidaAgregator);
        agregator.setStruct(struct);
        agregator.run();

        System.out.println("Salida Agregator");
        salidaAgregator.prueba();

        Message exitMessage =  (Message) salidaAgregator.next();
        exitConector.generateFile(exitMessage.getData(), "solucion_" + comanda);
        
        conectorDBHot.disconnect();
        conectorDBCold.disconnect();
    }

}
