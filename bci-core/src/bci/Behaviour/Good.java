package bci.Behaviour;

import bci.User.User;
import bci.Work.Work;

public class Good extends Behaviour{

    public Good(){ }

    @Override
    public String getBehaviourName(){
        return "CUMPRIDOR";
    }    

    public int requestLoanDuration (User u, Work w){
        if (w.getTotalCopies() == 1) {
            return 8;
        } else if (w.getTotalCopies()>1 && w.getTotalCopies()<=5) {
            return 15;
        } else {
            return 30;
        }
    }
}
