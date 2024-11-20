package cafe;

public class IDGeneratorSingleton {
    
    private static IDGeneratorSingleton instance;
    private int idCounter = 0;

    private IDGeneratorSingleton() {}

    public static synchronized IDGeneratorSingleton getInstance() {
        if (instance == null) {
            instance = new IDGeneratorSingleton();
        }
        return instance;
    }

    public synchronized int generateId() {
        return ++idCounter;
    }
}
