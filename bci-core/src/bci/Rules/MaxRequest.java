package bci.Rules;

import bci.User.User;
import bci.Work.Work;

public class MaxRequest extends Rules{
    public MaxRequest() {}

    @Override
    public boolean check(User u, Work w){
        int currentLoans = u.getRequestsCount();
        String behaviour = u.getBehaviour().getBehaviourName();

        int limit;
        switch (behaviour) {
            case "GOOD":
                limit = 5;
                break;
            case "FAULTY":
                limit = 1;
                break;
            default:
                limit = 3;
        }

        return currentLoans < limit;
    }

    @Override
    public int getRuleId() {
        return 5;
    }
}