package tasks;

import cafe.InfoMessage;
import cafe.Message;
import cafe.Slot;
import cafe.Task;
import java.util.ArrayList;

public class Replicator implements Task {

    private Slot entrySlot;
    private ArrayList<Slot> exitSlot;
    
    public Replicator() {
        exitSlot = new ArrayList<>();
    }
    public Slot getEntrySlot() {
        return entrySlot;
    }

    public void setEntrySlot(Slot entrySlot) {
        this.entrySlot = entrySlot;
    }
    
        public void setExitSlot(ArrayList<Slot> exitSlot) {
        this.exitSlot = exitSlot;
    }
        
            public void addExitSlot(Slot e) {
        this.exitSlot.add(e);
    }
            
    @Override
    public void run() {
        int n=entrySlot.getBuffer().size();
        for (int i = 0; i < n; i++) {
            Message message = (Message) entrySlot.next();
            for (int j = 0; j < exitSlot.size(); j++) {
                Message newMessage = new Message(new InfoMessage(), message.getData());
                exitSlot.get(j).receiveData(newMessage);
            }
        }
    }
    
}
