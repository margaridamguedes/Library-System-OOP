package bci.exceptions;

public class NoSuchCreatoridException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private final String name;

    public NoSuchCreatoridException(String name) {
        this.name = name;
    }

    public String getName() {return this.name;}


}
