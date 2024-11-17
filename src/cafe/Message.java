package cafe;

import org.w3c.dom.Document;

public class Message {
    
    private String titulo;
    private Document cuerpo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Document getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(Document cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Message(String titulo, Document cuerpo) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
    }
    
}
