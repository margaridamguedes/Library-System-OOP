package bci.Rules;

import bci.User.User;
import bci.Work.Work;

public class NoReferencial extends Rules{
    public NoReferencial() {}

    @Override
    public boolean check(User u, Work w){
        return!(w.getType().equals("Referencial"));
    }

    @Override
    public int getRuleId() {
        return 3;
    }
}
