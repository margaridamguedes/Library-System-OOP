package bci.Behaviour;

import java.io.Serializable;

import bci.User.User;
import bci.Work.Work;

public abstract class Behaviour implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 20251007001L;

    public Behaviour(){}

    public abstract String getBehaviourName();

    public abstract int requestLoanDuration(User u, Work w);

}
