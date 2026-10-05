package bci.exceptions;

public class WorkNotBorrowedByUseridException extends Exception {
    private final int idUser;
    private final int idWork;

    public WorkNotBorrowedByUseridException(int idWork, int idUser) {
        this.idUser = idUser;
        this.idWork = idWork;
    }

    public int getIdUser() { return idUser; }
    public int getIdWork() { return idWork; }
}
