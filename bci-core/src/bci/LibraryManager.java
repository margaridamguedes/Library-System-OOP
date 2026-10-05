package bci;

import bci.exceptions.*;


import java.io.*;
import bci.User.User;
import bci.Creator.Creator;
import bci.Work.Work;

import java.util.List;

/**
 * The façade class.
 */
public class LibraryManager implements Serializable{

  @java.io.Serial
  private static final long serialVersionUID = 202507171003L;
    /** The object doing all the actual work. */
    private Library _library = new Library(/*_defaultRules*/);
    
    public LibraryManager() {
        _library = new Library();
    }

    public LibraryManager(Library library) {
        _library = library;
    }

    public void advanceDate(int days) {
      _library.advanceDate(days);
    }

    public int getCurrentDate() {
      return _library.getCurrentDate();
    }

    public boolean changed() {
      return _library.changed();
    }

    public void save() throws MissingFileAssociationException, IOException {
    if (_library.getFilename().isBlank())
        throw new MissingFileAssociationException();
    try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_library.getFilename())))) {
        oos.writeObject(_library);
        _library.setChanged(false);
      }
    }

    public void saveAs(String filename) throws MissingFileAssociationException, IOException {
        _library.setFilename(filename);
        save();
    }

    public void load(String filename) throws UnavailableFileException {
    if (filename == null || filename.equals(""))
        throw new UnavailableFileException(filename);
    try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
        _library = (Library) ois.readObject();
        _library.setFilename(filename);
        _library.setChanged(false);
    } catch (IOException | ClassNotFoundException e) {
        throw new UnavailableFileException(filename);
    }
  }

    public Library getLibrary() {
        return _library;
    }
  
    public int registerUser(String name, String email) throws UseridRegistrationFailedException {
        return _library.registerUser(name, email);
    }

    public void registerBook(String title, List<Creator> authors, int price, String category, String isbn, int total_copies) {
      _library.registerBook(title, authors, price, category, isbn, total_copies);
    }

    public void registerDVD(String title, Creator director, int price, String category, String igac, int total_copies) {
      _library.registerDVD(title, director, price, category, igac, total_copies);
    }
    
    public User getUser(int id) throws NoSuchUseridException {
      return _library.getUser(id);
    }

    public boolean changeWorkInventory(int workId, int amount) throws NoSuchWorkidException {
      return _library.changeWorkInventory(workId, amount);
    }

    public int requestWork(int userId, int workId) throws NoSuchUseridException, NoSuchWorkidException, BorrowingRulesFailedException {
      return _library.requestWork(userId, workId);
    }
    
    public void registerWhenAvailable(int userId, int workId) throws NoSuchUseridException, NoSuchWorkidException {
      _library.registerWhenAvailable(userId, workId);
      _library.setChanged(true);
    }

    public void registerWhenBorrowed(int userId, int workId) throws NoSuchUseridException, NoSuchWorkidException {
      _library.registerWhenBorrowed(userId, workId);
      _library.setChanged(true);
    }

    public int returnWork(int userId, int workId) throws NoSuchUseridException, NoSuchWorkidException, WorkNotBorrowedByUseridException{
      int fine = _library.returnWork(userId, workId);
      _library.setChanged(true);
      return fine;
    }

    public void payFine(int userId) throws NoSuchUseridException, UserIsActiveException {
      _library.payFine(userId);
      _library.setChanged(true);
    }

    public List<Work> performsearch(String searchTerm){
      return _library.performsearch(searchTerm);
    }
    /**
     * Read text input file and initializes the current library (which should be empty)
     * with the domain entities representeed in the import file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if some error happens during the processing of the
     * import file.
     */
    public void importFile(String filename) throws ImportFileException {
      try {
        if (filename != null && !filename.isEmpty())
          _library.importFile(filename);
          _library.setChanged(true);
      } catch (IOException | UnrecognizedEntryException | UseridRegistrationFailedException | NoSuchUseridException e) /* FIXME maybe other exceptions */  {
        throw new ImportFileException(filename, e);
      }
    }

}
