package bci.Requests;

import java.io.Serializable;
import bci.Work.Work;

public class Requests implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 202507211200L;

    private Work work;       // referência à obra
    private int dueDate;
    private int returnDate;

    public Requests(Work work, int dueDate) {
        this.work = work;
        this.dueDate = dueDate;
        this.returnDate = -1; // -1 = ainda não devolvida
    }

    public Work getWork() {
        return work;
    }

    public int getDueDate() {
        return this.dueDate;
    }

    public int getReturnDate() {
        return this.returnDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }

    public void markReturned(int returnDate) {
        this.returnDate = returnDate;
    }

    /** 
     * Retorna true se o empréstimo está atrasado na data dada.
     */
    public boolean isLate(int currentDate) {
        return returnDate == -1 && currentDate > dueDate;
    }

    /**
     * Retorna true se a obra ainda não foi devolvida.
     */
    public boolean isActive() {
        return returnDate == -1;
    }

}
