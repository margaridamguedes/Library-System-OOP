package bci.Work;

import bci.Category.Category;
import bci.User.User;
import java.util.List;
import bci.Creator.Creator;
import java.lang.String;


public class DVD extends Work{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    private String igac;
    private Creator director;

    public DVD(int id, String title, Creator director, int price, Category category, String igac, int total_copies, int availableCopies, List<User> interestedWhenAvailable, List<User> interestedWhenBorrowed,  String type) {
        super(id, title, price, category, total_copies, availableCopies, interestedWhenAvailable, interestedWhenBorrowed, "DVD");
        this.director= director;
        this.igac = igac;
    }

    public Creator getCreator() {
        return director;
    }
    @Override
    public boolean matchesCreatorName(String searchTerm) {
        return director.getName().toLowerCase().equals(searchTerm.toLowerCase());
    }

    public String getIgac() {
        return igac;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + director.getName() + " - " + this.igac;
    }

}
