package cafe;

import org.w3c.dom.Document;

public class Message {
    
    private InfoMessage head;
    private Document data; //Despues sera document

    public Message() {
    }
    
    public Message(Document document) {
        this.data=document;
        head = new InfoMessage();
    }

    public Message(InfoMessage head, Document data) {
        this.head = head;
        this.data = data;
    }

    public InfoMessage getHead() {
        return head;
    }

    public void setHead(InfoMessage head) {
        this.head = head;
    }

    public Document getData() {
        return data;
    }

    public void setData(Document data) {
        this.data = data;
    }

    
    
}
