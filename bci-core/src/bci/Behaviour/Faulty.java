package bci.Behaviour;

import bci.User.User;
import bci.Work.Work;

public class Faulty extends Behaviour{

    public Faulty(){}
    
    @Override
    public String getBehaviourName(){
        return "FALTOSO";
    }    

    public int requestLoanDuration(User u, Work w){
        return 2;
    }
}
