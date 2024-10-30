package tareas;

public class Replicator {
    
    private Object elementoIN;
    private Object elementoOUT;

    public Object getElementoIN() {
        return elementoIN;
    }

    public void setElementoIN(Object elementoIN) {
        this.elementoIN = elementoIN;
    }

    public Object getElementoOUT() {
        return elementoOUT;
    }

    public void setElementoOUT(Object elementoOUT) {
        this.elementoOUT = elementoOUT;
    }
    
    public void ejecutar(){
        elementoOUT=elementoIN;
    }
    
    
}
