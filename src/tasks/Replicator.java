package tasks;

import cafe.Slot;
import cafe.Task;

public class Replicator implements Task {

    private Slot entrySlot, exitSlot;

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
    
    
    
    public Replicator() {

    }

    @Override
    public void run() {
        exitSlot.receiveData(entrySlot.next());
    }
    
}
