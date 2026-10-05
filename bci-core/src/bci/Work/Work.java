package bci.Work;

import java.util.List;

import bci.Category.Category;
import bci.User.User;
import java.io.Serializable;

public abstract class Work implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    private final int id;
    private String title;
    private int price;
    private Category category;
    private int totalCopies;
    private int availableCopies;
    private List<User> interestedWhenAvailable;
    private List<User> interestedWhenBorrowed;
    private final String type;


    public Work(int id, String title, int price, Category category, int totalCopies, int availableCopies, List<User> interestedWhenAvailable, List<User> interestedWhenBorrowed, String type){
        this.id= id++;
        this.title= title;
        this.price= price;
        this.category= category;
        this.totalCopies= totalCopies;
        this.availableCopies= availableCopies;
        this.interestedWhenAvailable= interestedWhenAvailable;
        this.interestedWhenBorrowed= interestedWhenBorrowed;
        this.type= type;
    }

    public int getID(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }
    public int getPrice(){
        return this.price;
    }
    public Category getCategory(){
        return this.category;
    }
    public int getTotalCopies(){
        return this.totalCopies;
    }
    public int getAvailableCopies(){
        return this.availableCopies;
    }
    public List<User> getInterestedWhenAvailable(){
        return this.interestedWhenAvailable;
    }
    public List<User> getInterestedWhenBorrowed(){
        return this.interestedWhenBorrowed;
    }
    public String getType(){
        return this.type;
    }
    public void setTitle(String Title){
        this.title= Title;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public void setCategory(Category category){
        this.category= category;
    }
    public void setCopies(int totalCopies){
        this.totalCopies=totalCopies;
    }
    public void setAvailableCopies(int availableCopies){
        this.availableCopies= availableCopies;
    }
    public void setTotalCopies(int copies) {
        this.totalCopies = copies;
    }
    public boolean matchesCreatorName(String searchTerm) {
        return false; 
    }

    public void setInterestedWhenAvailable(List<User> users) {
        this.interestedWhenAvailable = users;
    }
    public void setInterestedWhenBorrowed(List<User> users) {
        this.interestedWhenBorrowed = users;
    }



    @Override
    public String toString(){

        return String.format("%d - %d de %d - %s - %s - %d - %s",
            getID(), getAvailableCopies(), getTotalCopies(), getType(), getTitle(), getPrice(), getCategory().getName());
    }
    

} 


    