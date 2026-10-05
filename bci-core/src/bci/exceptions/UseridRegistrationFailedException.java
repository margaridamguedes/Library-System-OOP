package bci.exceptions;

public class UseridRegistrationFailedException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private String name;
    private String email;

    public UseridRegistrationFailedException(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public String getname() { return name; }
    public String getemail() { return email; }
}
