package bci.Notification;

import bci.Work.Work;

import java.io.Serializable;

public abstract class Notification implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 202507171005L;
    
    private final Work work;
    private int id;
    private String message;

    public Notification(Work work){
        this.work=work;
        this.message=toString();
    }

    public Work getWork(){
        return this.work;
    }

    public void setId(int id){
        this.id= id;
    }

    public int getId(){
        return this.id;
    }

    public void setMessage(String message){
        this.message= message;
    }

    public String getMessageContent(){
        return this.message;
    }
    
    public abstract String getMessage();
    
    @Override
    public String toString() {
        return getMessage();
    }

}