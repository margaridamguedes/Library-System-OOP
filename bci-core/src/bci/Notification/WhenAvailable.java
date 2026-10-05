package bci.Notification;

import bci.Work.Work;

public class WhenAvailable extends Notification {

    public WhenAvailable(Work work){
        super(work);
    }

    @Override
    public String getMessage() {
        Work w = getWork();
        return "DISPONIBILIDADE:" + w.toString();
    }
}
