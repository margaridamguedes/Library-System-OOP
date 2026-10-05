package bci.Behaviour;

import bci.User.User;
import bci.Work.Work;


public class Normal extends Behaviour{

    public Normal(){}

    @Override
    public String getBehaviourName(){
        return "NORMAL";
    }

    public int requestLoanDuration(User u, Work w){
        if (w.getTotalCopies() == 1) {
            return 3;
        } else if (w.getTotalCopies()>1 && w.getTotalCopies()<=5) {
            return 8;
        } else {
            return 15;
        }
    }
}
