package tasks;

import cafe.Slot;
import cafe.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Distributor implements Task {

    private Slot entrySlot;
    private ArrayList<Slot> exitSlot;
    private Map<String, List<Integer>> reglasDistribucion;

    public Distributor() {
        exitSlot = new ArrayList<>();
        this.reglasDistribucion = new HashMap<>();
    }

    public void definirReglasDistribucion(String tagName, List<Integer> slotsAsignados) {
        reglasDistribucion.put(tagName, slotsAsignados);
    }

    public Distributor(Slot entrySlot, ArrayList<Slot> exitSlot) {
        this.entrySlot = entrySlot;
        this.exitSlot = exitSlot;
    }

    public Slot getEntrySlot() {
        return entrySlot;
    }

    public void setEntrySlot(Slot entrySlot) {
        this.entrySlot = entrySlot;
    }

    public void addExitSlot(Slot newExitSlot) {
        exitSlot.add(newExitSlot);
    }

    @Override
    public void run() {
        Document documento = (Document) entrySlot.next();
        NodeList nodos = documento.getElementsByTagName("drink");

        for (int i = 0; i < nodos.getLength(); i++) {
            if (nodos.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodos.item(i);
                String tagName = elemento.getTagName();

                // Verificar el valor de 'type' dentro del 'drink'
                String type = elemento.getElementsByTagName("type").item(0).getTextContent().trim();

                if (reglasDistribucion.containsKey(type)) {
                    List<Integer> slotsAsignados = reglasDistribucion.get(type);
                    for (Integer slotIndex : slotsAsignados) {
                        if (slotIndex < exitSlot.size()) {
                            exitSlot.get(slotIndex).receiveData(elemento);
                        }
                    }
                } else {
                    System.out.println("No se encontró regla de distribución para el tipo: " + type);
                }
            }
        }
    }

}
