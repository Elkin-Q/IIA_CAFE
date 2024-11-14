package cafe;

public abstract class Connector {
    
    protected SolutionPort port;

    public SolutionPort getPort() {
        return port;
    }

    public void setPort(SolutionPort port) {
        this.port = port;
    }
}
