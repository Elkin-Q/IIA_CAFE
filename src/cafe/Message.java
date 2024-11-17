package cafe;

import org.w3c.dom.Document;

public class Message {
    
    private String tipo;
    private String cuerpo; //Despues sera document

    public String getTitulo() {
        return tipo;
    }

    public void setTitulo(String tipo) {
        this.tipo = tipo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Message(String tipo, String cuerpo) {
        this.tipo = tipo;
        this.cuerpo = cuerpo;
    }
    
}
