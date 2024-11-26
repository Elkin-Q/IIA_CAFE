package tasks;

import cafe.Slot;
import cafe.Task;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Merger implements Task {

    private ArrayList<Slot> EntrySlots;
    private Slot ExitSlot;

    public Merger() {
    }

    public ArrayList<Slot> getEntrySlots() {
        return EntrySlots;
    }

    public void setEntrySlots(ArrayList<Slot> EntrySlots) {
        this.EntrySlots = EntrySlots;
    }

    public Slot getExitSlot() {
        return ExitSlot;
    }

    public void setExitSlot(Slot ExitSlot) {
        this.ExitSlot = ExitSlot;
    }

    @Override
    public void run() {

        for (Slot entrySlot : EntrySlots) {
            for (int i = 0; i < entrySlot.bufferSize(); i++) {
                ExitSlot.receiveData(entrySlot.next());
            }
        }
    }

}
