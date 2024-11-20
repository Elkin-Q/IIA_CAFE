package tasks;

import cafe.InfoMessage;
import cafe.Slot;
import cafe.Task;

public class Splitter implements Task {

    private Slot entrySlot, exitSlot;
    
    public Splitter() {
    }

    public Splitter(Slot entrySlot, Slot exitSlot) {
        this.entrySlot = entrySlot;
        this.exitSlot = exitSlot;
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

    @Override
    public void run() {
        
        entrySlot.next();
    }
    
}
