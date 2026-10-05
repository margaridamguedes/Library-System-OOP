package bci.Status;

import java.io.Serializable;

public abstract class Status implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 20251007001L;

    public abstract String getStatus();
}
