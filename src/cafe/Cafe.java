package cafe;

import java.util.ArrayList;
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

        FileConnector conectorHot = new FileConnector();
        conector.readFile("BDcafe.xml");

        FileConnector conectorCold = new FileConnector();
        conector.readFile("BDCC.xml");

        SolutionPort port = new SolutionPort();
        conector.setPort(port);

        SolutionPort portDBHot = new SolutionPort();
        conectorHot.setPort(portDBHot);

        SolutionPort portDBCold = new SolutionPort();
        conectorCold.setPort(portDBCold);

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

        port.setEntrySlot(entradaComandas);
        conector.sendDocument();

        portDBHot.setEntrySlot(salidaBDHot);
        conectorHot.sendDocument();
        conector.readFile("BDchocolate.xml");
        conectorHot.sendDocument();

        portDBCold.setEntrySlot(salidaBDCold);
        conectorCold.sendDocument();

        //tarea spliter
        Splitter splitter = new Splitter(entradaComandas, salidaSpliter);
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
        transHot.setTable("hotDrinks");
        transHot.setAtribute("name");
        transHot.run();

        //translator fria
        Translator transCold = new Translator();
        transCold.setEntrySlot(salidaRepCold1);
        transCold.setExitSlot(consultaCold);
        transCold.setTable("coldDrinks");
        transCold.setAtribute("name");
        transCold.run();

        System.out.println("Consulta caliente: ");
        consultaHot.prueba();
        System.out.println("Consulta Frio: ");
        consultaCold.prueba();
    }

}
