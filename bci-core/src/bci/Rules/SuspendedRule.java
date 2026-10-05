package bci.Rules;

import bci.User.User;
import bci.Work.Work;

public class SuspendedRule extends Rules{
    public SuspendedRule() {}

    @Override
    public boolean check(User u, Work w){
        String status = u.getStatus().getStatus();
        return !(status.equals("SUSPENDED"));
    }

    @Override
    public int getRuleId() {
        return 2;
    }
}