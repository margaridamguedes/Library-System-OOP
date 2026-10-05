package bci.exceptions;


public class BorrowingRulesFailedException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private final int idUser;
    private final int idWork;
    private final int idRule;

    public BorrowingRulesFailedException(int idUser, int idWork, int idRule) {
        this.idUser= idUser;
        this.idWork=idWork;
        this.idRule=idRule;
    }
    
    public int getIdUser() {return this.idUser;}
    public int getIdWork() {return this.idWork;}
    public int getIdRule() {return this.idRule;}

}
