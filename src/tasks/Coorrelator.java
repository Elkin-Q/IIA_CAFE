package tasks;

import cafe.Slot;
import cafe.Task;
import java.util.ArrayList;


public class Coorrelator implements Task {
    
    private ArrayList<Slot> entrySlots, exitSlots;

    public Coorrelator() {
        this.entrySlots = new ArrayList<>();
        this.exitSlots = new ArrayList<>();
    }

    @Override
    public void run() {
        
        
    }
    
}
