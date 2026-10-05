package bci.Notification;

import bci.Work.Work;

public class WhenBorrowed extends Notification {
    public WhenBorrowed(Work work){
        super(work);
    }

    @Override
    public String getMessage() {
        Work w = getWork();
        return "REQUISIÇÃO:" + w.toString();
    }
}
