package bci.exceptions;

public class NoSuchUseridException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private final int id;

    public NoSuchUseridException(int id) {
        this.id = id;
    }

    public int getId() {return this.id;}

}
