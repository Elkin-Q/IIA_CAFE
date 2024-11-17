package tasks;

import cafe.Slot;
import cafe.Task;
import java.util.ArrayList;

public class Distributor implements Task {

    private Slot entrySlot;
    private ArrayList<Slot> exitSlot;
    
    public Distributor() {
        exitSlot=new ArrayList<>();
    }

    public Distributor(Slot entrySlot, ArrayList<Slot> exitSlot) {
        this.entrySlot = entrySlot;
        this.exitSlot = exitSlot;
    }
    
    public void addExitSlot(Slot newExitSlot){
        exitSlot.add(newExitSlot);
    }

    @Override
    public void run() {
        
    }
    
}
