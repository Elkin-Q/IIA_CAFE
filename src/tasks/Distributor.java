package tasks;

import cafe.InfoMessage;
import cafe.Message;
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
         this.reglasDistribucion = new HashMap<>();
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
        int n = entrySlot.getBuffer().size(); // Número de mensajes en el slot de entrada
        for (int i = 0; i < n; i++) {
            Message message = (Message) entrySlot.next();
            Document data = message.getData();

            // Procesar el Document
            Element drinkElement = data.getDocumentElement(); // Obtener el nodo raíz
            String type = drinkElement.getElementsByTagName("type").item(0).getTextContent().trim(); // Obtener el tipo de bebida

            if (reglasDistribucion.containsKey(type)) {
                List<Integer> slotsAsignados = reglasDistribucion.get(type);

                for (Integer slotIndex : slotsAsignados) {
                    // crear un nuevo mensaje
                    Message newMessage = new Message(new InfoMessage(), data);
                    // enviar el mensaje al slot correspondiente
                    exitSlot.get(slotIndex).receiveData(newMessage);
                    System.out.println("Mensaje enviado a slot " + slotIndex);
                }
            } else {
                System.out.println("No se encontró regla de distribución para el tipo: " + type);
            }
        }

    }
}
