package bci.Rules;

import bci.User.User;
import bci.Work.Work;

public class Repeated extends Rules{
    public Repeated() {}

    @Override
    public boolean check(User u, Work w){
        return u.hasRequestedWork(w)==null;
    }
   
    @Override
    public int getRuleId() {
        return 1;
    }
}