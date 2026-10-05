package bci.Work;

import bci.Category.Category;
import bci.User.User;
import java.util.List;
import java.util.stream.Collectors;

import bci.Creator.Creator;

public class Book extends Work{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    private String isbn;
    private List<Creator> authors;

    public Book(int id, String title, List<Creator> authors, int price, Category category, String isbn, int total_copies, int availableCopies, List<User> interestedWhenAvailable, List<User> interestedWhenBorrowed, String type) {
        super(id, title, price, category, total_copies, availableCopies, interestedWhenAvailable, interestedWhenBorrowed, "Livro");

        this.isbn = isbn;
        this.authors= authors;
    }
    
    public List<String> getAuthorNames() {
        return authors.stream()
                .map(Creator::getName)
                .collect(Collectors.toList());
    }

    public List<Creator> getCreator() {
        return authors;
    }
    
    public String getIsbn() {
        return isbn;
    }
    @Override
    public boolean matchesCreatorName(String searchTerm) {
        for (Creator author : authors) {
            if (author.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString(){
        String authorNames = authors.stream()
        .map(Creator::getName)
        .collect(Collectors.joining("; "));

        return super.toString() + " - " + authorNames + " - " + this.isbn;
    }
}
