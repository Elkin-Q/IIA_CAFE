package cafe;

import org.w3c.dom.Document;

public class SolutionPort extends Port{
    
    private Slot entrySlot;

    public SolutionPort() {
        
    }

    public Slot getEntrySlot() {
        return entrySlot;
    }

    public void setEntrySlot(Slot entrySlot) {
        this.entrySlot = entrySlot;
    }
    
    public void receiveDocument(Document document){
        entrySlot.receiveData(document);
    }
    
}
