package cafe;

public class InfoMessage {
    
    private long id, sequenceId, lenghtSequence;

    public InfoMessage(long sequenceId, long lenghtSequence) {
        IDGeneratorSingleton IDgenerator = IDGeneratorSingleton.getInstance();
        this.id = IDgenerator.generateId();
        this.sequenceId = sequenceId;
        this.lenghtSequence = lenghtSequence;
    }
    
    public InfoMessage(){
        IDGeneratorSingleton IDgenerator = IDGeneratorSingleton.getInstance();
        this.id = IDgenerator.generateId();
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
