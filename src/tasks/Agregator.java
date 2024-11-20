/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tasks;

import cafe.Slot;
import cafe.Task;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author elkin
 */
public class Agregator implements Task {

    private Slot entrySlot, exitSlot;
    private XPathExpression exp, exp2;

    public void setXPath(XPathExpression exp,XPathExpression exp2){
        this.exp=exp;
        this.exp=exp2;
    }
    
    public Slot getEntrySlot() {
        return entrySlot;
    }

    public void setEntrySlot(Slot entrySlot) {
        this.entrySlot = entrySlot;
    }

    public Slot getExitSlot() {
        return exitSlot;
    }

    public void setExitSlot(Slot exitSlot) {
        this.exitSlot = exitSlot;
    }

    public Agregator() {

    }
    
    

    @Override
    public void run() {
        try {

            NodeList nl = (NodeList) exp.evaluate(entrySlot.getBuffer().getFirst(), XPathConstants.NODESET);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            int tamaño = entrySlot.bufferSize();

            Document aux = builder.newDocument();

            //se crea un elemento raiz cafe_order en el nuevo documento y se agrega al documento
            Element rootElement = aux.createElement("cafe_order");
            aux.appendChild(rootElement);

            //se crea un elemento order_id y se le asigna un valor obtenido del primer nodo de la lista
            Element id = aux.createElement("order_id");
            id.appendChild(aux.createTextNode(nl.item(0).getChildNodes().item(0).getTextContent()));
            rootElement.appendChild(id);

            //Se crea un elemento drinks que contendra las bebidas que se agregen más tarde
            Element drs = aux.createElement("drinks");
            rootElement.appendChild(drs);

            for (int i = 0; i < tamaño; i++) {

                NodeList nl2 = (NodeList) exp2.evaluate(entrySlot.getBuffer().getFirst(), XPathConstants.NODESET);

                Node drink = nl2.item(0);

                aux.adoptNode(drink);
                drs.appendChild(drink);

                entrySlot.next();

            }
            exitSlot.receiveData(aux);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
