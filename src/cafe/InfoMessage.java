package cafe;

public class InfoMessage {
    
    private long id, sequenceId, lenghtSequence;

    public InfoMessage(long id, long sequenceId, long lenghtSequence) {
        this.id = id;
        this.sequenceId = sequenceId;
        this.lenghtSequence = lenghtSequence;
    }
    
    public InfoMessage(){
        
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public long getLenghtSequence() {
        return lenghtSequence;
    }

    public void setLenghtSequence(long lenghtSequence) {
        this.lenghtSequence = lenghtSequence;
    }



}
