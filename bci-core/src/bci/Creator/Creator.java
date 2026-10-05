package bci.Creator;

import java.util.Map;

import bci.Work.Work;

import java.util.HashMap;
import java.util.Collection;
import java.io.Serializable;

public class Creator implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    private String name;
    private Map <Integer, Work> works;

    public Creator(String name) {
        this.works= new HashMap<>();
        this.name = name;
    }

    public String getName(){
        return this.name;
    }   

    public Collection<Work> getWorks(){
        return works.values();
    }

    public void setName(String name){
        this.name= name;
    }

    public void addWork(Work work) {
        works.put(work.getID(), work);
    }

    public void removeWork (int workId){
        works.remove(workId);
    }
}