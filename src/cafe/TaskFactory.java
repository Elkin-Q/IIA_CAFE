package cafe;

import tasks.Coorrelator;
import tasks.Agregator;
import tasks.Distributor;
import tasks.Translator;
import tasks.ContentEnrincher;
import tasks.Replicator;
import tasks.Splitter;
import tasks.Merger;


public class TaskFactory {

    public TaskFactory() {
        
    }
    
    public Task createTask(String tipo){
        
        Task task= null;
        
        switch(tipo){
            
            case "ContentEnrincher":
                task = new ContentEnrincher();
                break;
                
            case "Coorrelator":
                task = new Coorrelator();
                break;
                
            case "Distributor":
                task = new Distributor();
                break;
                
            case "Replicator":
                task= new Replicator();
                break;
                
            case "Merger":
                task= new Merger();
                break;
                
            case "Splitter":
                task = new Splitter();
                break;
                
            case "Agregator":
                task= new Agregator();
                break;
                
            case "Translator":
                task=new Translator();
                break;
        }
        return task;
    }
}
