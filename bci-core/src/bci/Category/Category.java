package bci.Category;

import java.io.Serializable;

public class Category implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 20251007001L;
    
    private final String name;

    public Category(String name){
        this.name= name;
    }

    public String getName(){
        switch(this.name){
            case "FICTION" -> {return "Ficção";}
            case "REFERENCE" -> {return "Referência";}
            case "SCITECH" -> {return "Técnica e Científica";}
        }
        return this.name;
    }
}   



