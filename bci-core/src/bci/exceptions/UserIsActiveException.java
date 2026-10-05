package bci.exceptions;

public class UserIsActiveException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private final int userId;

    public UserIsActiveException(int userId) {
        this.userId = userId;
    }

    public int getUserId() { return userId; }
}