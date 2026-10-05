package bci.User;

import java.util.List;
import java.util.ArrayList;

import bci.Behaviour.Behaviour;
import bci.Notification.Notification;
import bci.Requests.Requests;
import bci.Status.Status;
import bci.Work.Work;
import java.io.Serializable;

public class User implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171004L;
    
    private final int id;
    private String name;
    private String email;
    private Behaviour behaviour;
    private Status status;
    private List<Requests> requests;
    private List<Notification> notifications;
    private int nextNotificationId = 1;
    private int fine;
    private int onTimeReturns=0;
    private int lateReturns=0;
    
    public User (int id, String name, String email,Behaviour behaviour,  Status status,  List<Requests> requests, List<Notification> notifications, int fine){
        this.id= id++;
        this.name= name;
        this.email= email;
        this.status= status;
        this.behaviour= behaviour;
        this.requests= requests;
        this.notifications=notifications;
        this.fine=fine;
    }

    public int getID(){
        return this.id;
    }
   
    public String getName(){
        return this.name;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public Behaviour getBehaviour() {
        return this.behaviour;
    }
    
    public Status getStatus(){
        return this.status;
    }

    public List<Requests> getRequests(){
        if (this.requests == null) {
            this.requests = new ArrayList<>();
        }
        return this.requests;
    }

    public List<Notification> getNotifications(){
        return this.notifications;
    }
    public int getNextNotificationId() {
        return this.nextNotificationId++;
    }
    public int getRequestsCount() {
        return (this.requests == null) ? 0 : this.requests.size();
    }
    
    public void setName(String name){
        this.name= name;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setStatus(Status status){
        this.status=status;
    }

    public void setBehaviour(Behaviour behaviour){
        this.behaviour= behaviour;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public int getFine() {
        return this.fine;
    }

    public boolean isSuspended(){
        return this.status != null && this.status.getStatus().equals("SUSPENDED");
    }

    @Override
    public String toString(){
        if (isSuspended()){
            return String.format("%d - %s - %s - %s - SUSPENSO - EUR %d",
                this.id, this.name, this.email, this.behaviour.getBehaviourName(), this.fine);
        } else {
            return String.format("%d - %s - %s - %s - ACTIVO",
                this.id, this.name, this.email, this.behaviour.getBehaviourName());
        }
    }

    public void addNotification(Notification notification){
        notification.setId(getNextNotificationId());
        this.notifications.add(notification); 
    }

    public void cleanNotifications(){
        this.notifications.clear();
        this.nextNotificationId = 1;
    }
    public Requests hasRequestedWork(Work w) {
        for (Requests r : this.getRequests()) {
            if (r.getWork().equals(w)) {
                return r;
            }
        }
        return null;
    }
    public void addRequests(Requests r) {
        if (this.requests == null) this.requests = new ArrayList<>();
        this.requests.add(r);
    }

    public void incrementOnTimeReturns(){
        this.onTimeReturns++;
    }

    public void incrementLateReturns(){
        this.lateReturns++;
    }

    public void resetLateReturns (){
        this.lateReturns=0;
    }

    public void resetOnTimeReturns (){
        this.onTimeReturns=0;
    }

    public int getOnTimeReturns(){
        return this.onTimeReturns;
    }

    public int getLateReturns(){
        return this.lateReturns;
    }

}