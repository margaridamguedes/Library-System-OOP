package bci.exceptions;

public class NoSuchWorkidException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private final int id;

    public NoSuchWorkidException(int id) {
        this.id = id;
    }

    public int getId() {return this.id;}

}
