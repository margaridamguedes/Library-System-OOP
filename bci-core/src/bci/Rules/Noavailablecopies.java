package bci.Rules;

import bci.User.User;
import bci.Work.Work;

public class Noavailablecopies extends Rules{
    public Noavailablecopies() {}

    @Override
    public boolean check(User u, Work w){
        return w.getAvailableCopies()>0;
    }
   
    @Override
    public int getRuleId() {
        return 4;
    }
}