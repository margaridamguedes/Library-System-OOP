package bci.Rules;

import bci.User.User;
import bci.Work.Work;

public class MaxPrice extends Rules{
    public MaxPrice() {}

    @Override
    public boolean check(User u, Work w){
        if (!(u.getBehaviour().getBehaviourName().equals("GOOD"))){
            if (w.getPrice() > 25){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int getRuleId() {
        return 6;
    }
}