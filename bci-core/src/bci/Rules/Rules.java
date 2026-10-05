package bci.Rules;

import bci.User.User;
import bci.Work.Work;
import java.io.Serializable;

public abstract class Rules implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 20251007002L;
    
    public Rules(){}

    public abstract boolean check(User u, Work w);

    public abstract int getRuleId();
    
}
