package tareas;

import java.util.ArrayList;

public class Replicator {
    
    private Object elementoIN;
    private Object elementoOUT;
    //private ArrayList<Object> eIN, eOUT;

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
