package bci;


import bci.exceptions.*;
import bci.User.User;
import bci.Category.Category;
import bci.Notification.WhenAvailable;
import bci.Notification.WhenBorrowed;
import bci.Requests.Requests;
import bci.Rules.Repeated;
import bci.Rules.MaxPrice;
import bci.Rules.MaxRequest;
import bci.Rules.NoReferencial;
import bci.Rules.SuspendedRule;
import bci.Rules.Rules;
import bci.Work.Work;
import bci.Work.Book;
import bci.Work.DVD;
import bci.Behaviour.Normal;
import bci.Status.Active;
import bci.Creator.Creator;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

/** Class that represents the library as a whole. */
public class Library implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private String _filename="";
    
    /** Current date of the system. */
    private int currentDate= 1;

    /** Indicates whether the library has been modified since last save. */
    private transient boolean _libraryChanged = false;

    /** Registered users, mapped by user ID. */
    private final Map<Integer, User> _user = new HashMap<>();
    
    /** Registered works, mapped by work ID. */
    private final Map<Integer, Work> _work = new HashMap<>();

    /** Registered categories, mapped by category name. */
    private final Map<String, Category> _categories = new HashMap<>();
    
    /** Registered creators, mapped by their name. */
    private final Map<String, Creator> _creators = new HashMap<>();

    /** Next available ID for new works. */
    private int _nextWorkId = 1;

    /** Next available ID for new users. */
    private int _nextUserId= 1;


    /**
    * Advances the current date by the specified number of days.
    *
    * @param days number of days to add
    */
    public void advanceDate(int days) {
        if (days > 0){
          this.currentDate+= days;
        }

        for (User u :_user.values()){
          for (Requests r : u.getRequests()){
            if (r.isLate(this.currentDate)){
              u.setStatus(new bci.Status.Suspended());
              if(u.getBehaviour().getBehaviourName().equals("CUMPRIDOR")){
                u.setBehaviour(new bci.Behaviour.Normal());
              }
            }
          }
        }

    }
  
    /**
    * @return the current date of the system
    */
    public int getCurrentDate() {
        return this.currentDate;
    }

    public String getFilename(){
      return _filename;
    }

    public void setFilename(String filename){
      _filename= filename;
    }

    /**
    * Sets the library’s “changed” status.
    *
    * @param changed true if the library should be marked as modified
    */
    public boolean changed() {
      return _libraryChanged;
    }

    /**
    * @return true if the library has been modified since last save
    */
    public void setChanged(boolean changed) { _libraryChanged = changed; }

    /**
    * Generates the next available work ID.
    *
    * @return a unique integer work ID
    */
    public int nextWorkId() {
      return _nextWorkId++;
    }

    /**
    * Generates the next available user ID.
    *
    * @return a unique integer user ID
    */
    public int nextUserId(){
      return _nextUserId++;
    }
    
    /**
    * Retrieves an existing category by name or creates a new one if it does not exist.
    *
    * @param name category name
    * @return the corresponding Category object
    */
    public Category getOrCreateCategory(String name) {
    return _categories.computeIfAbsent(name, n -> new Category(n) {});
    }

    /**
    * Registers a new user in the library.
    *
    * @param name user’s name
    * @param email user’s email
    * @return the new user’s ID
    * @throws UseridRegistrationFailedException if registration fails (e.g., null name/email)
    */
    public int registerUser(String name, String email) throws UseridRegistrationFailedException {

      // Validação simples
      if (name.isBlank()|| email.isBlank()) {
        throw new UseridRegistrationFailedException(name, email);
      }

      int id = nextUserId();

      // Criação do utilizador com comportamento e estado iniciais
      User user = new User(id, name, email, new Normal(), new Active(), new ArrayList<>(), new ArrayList<>(), 0);
      _user.put(id, user);
      _libraryChanged = true;
      return id;
    }

    /**
    * Registers a new book in the library.
    * Each author is ensured to exist in the creator map and is linked to this book.
    *
    * @param title book title
    * @param authors list of authors
    * @param price book price
    * @param category book category name
    * @param isbn book ISBN
    * @param total_Copies total available copies
    * @return the newly registered Book object
    */
    public Book registerBook(String title, List<Creator> authors, int price, String category, String isbn, int total_Copies) {
      int id = nextWorkId();

      // Criação do livro
      Book book = new Book(id, title, authors, price, getOrCreateCategory(category), isbn, total_Copies, total_Copies, new ArrayList<>(),new ArrayList<>(), "BOOK");
      
      // Garante que cada autor existe na biblioteca e associa o livro a ele
      for (int i = 0; i < authors.size(); i++) {
        Creator author = authors.get(i);
        Creator libAuthor = _creators.computeIfAbsent(author.getName(), Creator::new);
        authors.set(i, libAuthor);  
        libAuthor.addWork(book);
    }

      _work.put(id, book);
      _libraryChanged = true;
      return book;
    }

    /**
    * Registers a new DVD in the library and links it to its director.
    *
    * @param title DVD title
    * @param director director creator
    * @param price DVD price
    * @param category DVD category name
    * @param igac IGAC registration number
    * @param total_Copies total available copies
    * @return the newly registered DVD object
    */
    public DVD registerDVD(String title, Creator director, int price, String category, String igac, int total_Copies) {
      int id = nextWorkId();

      // Garante que o realizador existe no sistema
      Creator libDirector = _creators.computeIfAbsent(director.getName(), Creator::new);

      
      // Cria o DVD
      DVD dvd = new DVD(id, title, director, price, getOrCreateCategory(category), igac, total_Copies, total_Copies, new ArrayList<>(),new ArrayList<>(), "DVD");
      _work.put(id, dvd);

      libDirector.addWork(dvd);

      _libraryChanged = true;
      return dvd;
    }

    /**
    * Retrieves a user by ID.
    *
    * @param id user ID
    * @return the corresponding User
    * @throws NoSuchUseridException if no user exists with the given ID
    */
    public User getUser(int id) throws NoSuchUseridException {
      if (!_user.containsKey(id)) {
        throw new NoSuchUseridException(id);
      }
      return _user.get(id);
    }

    /**
    * Retrieves a work by ID.
    *
    * @param id work ID
    * @return the corresponding Work
    * @throws NoSuchWorkidException if no work exists with the given ID
    */
    public Work getWork(int id) throws NoSuchWorkidException {
      if (!_work.containsKey(id)) {
        throw new NoSuchWorkidException(id);
      }
      return _work.get(id);
    }

    /**
    * Retrieves a creator by name.
    *
    * @param name creator name
    * @return the corresponding Creator
    * @throws NoSuchCreatoridException if no creator exists with the given name
    */
    public Creator getCreatorByName(String name) throws NoSuchCreatoridException {
      if (!_creators.containsKey(name)) {
        throw new NoSuchCreatoridException(name);
      }
      return _creators.get(name);
    }

    /**
     * Processes a line from the import file and delegates to the appropriate handler.
     * @param fields the split line fields.
     * @throws UnrecognizedEntryException if the entry type is unknown.
     * @throws NoSuchUseridException if a referenced user does not exist.
     */
    public void process(String[] fields) throws UnrecognizedEntryException, NoSuchUseridException, UseridRegistrationFailedException{
    switch (fields[0]) {
        case "USER" -> processUser(fields);
        case "BOOK" -> processbook(fields);
        case "DVD" -> processdvd(fields);
        default -> throw new UnrecognizedEntryException(fields[0]);
      }
    }

   /**
     * Processes a user entry from the import file.
     * @param fields entry fields (USER:name:email).
     * @return created User instance.
     * @throws UnrecognizedEntryException if input is invalid.
     */
    public User processUser(String... fields) throws UnrecognizedEntryException, UseridRegistrationFailedException {
      // Verifica se o número de campos está correto (deve ser "USER:Nome:Email")
      if (fields.length != 3) {
          throw new UnrecognizedEntryException("invalid user" + fields);
      }

      if (fields[1]==null || fields[2]==null) {
        throw new UseridRegistrationFailedException(fields[1], fields[2]);
      }
      
      String name = fields[1];
      String email = fields[2];
      int id;

      try {
          id = registerUser(name, email);
      } catch (UseridRegistrationFailedException e) {
          throw new UnrecognizedEntryException("invalid user" + fields);
      }
    
      // Extrai nome e email do vetor
      User user;
      try {
          user = getUser(id);
      } catch (NoSuchUseridException e) {
          throw new UnrecognizedEntryException("user not found: " + id);
      }
      return user;
    }

    /**
     * Processes a book entry from the import file.
     * @param fields entry fields (BOOK:title:authors:price:category:isbn:copies).
     * @return created Book instance.
     * @throws UnrecognizedEntryException if input is invalid.
     */
    public Book processbook (String... fields) throws UnrecognizedEntryException{
      if (fields.length !=7){
        throw new UnrecognizedEntryException ("invalid book" + fields);
      }
      //Extrai cada campo do array
      String title= fields[1];
      List<Creator> authors = new ArrayList<>();
    // Divide a lista de autores por vírgulas e cria um Creator para cada nome
    for (String name : fields[2].split(",")) {
        authors.add(new Creator(name.trim()));
    }
    //Converte o preço String para int
      int price= Integer.parseInt(fields[3]);
      //Categoria (string)
      String category= (fields[4]);
      //ISBN (identificador do Livro)
      String isbn= fields[5];
      //Numero total de copias
      int total_copies= Integer.parseInt(fields[6]);
      //Cria e regista o livro na biblioteca
      Book book= registerBook(title, authors, price, category, isbn, total_copies);
      return book;
    }

    /**
     * Processes a DVD entry from the import file.
     * @param fields entry fields (DVD:title:director:price:category:igac:copies).
     * @return created DVD instance.
     * @throws UnrecognizedEntryException if input is invalid.
     */

    public DVD processdvd(String... fields) throws UnrecognizedEntryException {
      // Verifica se o número de campos é exatamente 7
      if (fields.length !=7 ){
        throw new UnrecognizedEntryException("invalid dvd" + fields);
      }
      // Extrai e processa os campos
      String title = fields[1];
      Creator director = new Creator(fields[2]);
      int price = Integer.parseInt(fields[3]);
      String category = (fields[4]);
      String igac = fields[5];
      int total_copies = Integer.parseInt(fields[6]);

       // Cria e regista o DVD na biblioteca
      DVD dvd= registerDVD(title, director, price, category, igac, total_copies);
      return dvd;
      }

    /**
     * Returns a map of all users in the library.
     * @return map of user ID → User.
     */
    public Map<Integer, User> getUsersMap() {
      return _user;
    }

      /**
     * Returns a map of all works in the library.
     * @return map of work ID → Work.
     */
    public Map<Integer, Work> getWorksMap() {
      return _work;
    }

    public boolean changeWorkInventory(int workId, int amount) throws NoSuchWorkidException {
      // Obtém a obra (Book ou DVD) ou lança exceção
      Work work = getWork(workId); // lança NoSuchWorkidException se não existir

      // Exemplares totais e disponíveis
      int total = work.getTotalCopies();
      int available = work.getAvailableCopies();

      // Número de exemplares emprestados
      int borrowed = total - available; //--

      
      // Novos valores propostos
      int newTotal = total + amount;
      int newAvailable = available + amount;

      // Não se pode reduzir total abaixo de emprestados
      // Nem deixar disponíveis negativos
      if (newTotal < borrowed || newAvailable < 0) {
          return false; // inválido, mostra mensagem depois
      }

      // Atualiza total e disponíveis
      work.setTotalCopies(newTotal);
      work.setAvailableCopies(newAvailable);

      
      // Se total de exemplares for 0, remove a obra da biblioteca
      if (newTotal == 0) {
          _work.remove(workId);
      }
      
      // Atualiza total e disponíveis
      work.setTotalCopies(newTotal);
      work.setAvailableCopies(newAvailable);

    
      // Se total de exemplares for 0, remove a obra da biblioteca
      if (newTotal == 0) {
          _work.remove(workId);
      }

      for (Creator creator : _creators.values()) {
        creator.getWorks().remove(work);
      }

      // depois, remove criadores sem obras
      _creators.values().removeIf(c -> c.getWorks().isEmpty());

      setChanged(true);
      return true;
    }

    public int requestWork(int userId, int workId) 
          throws NoSuchUseridException, NoSuchWorkidException, BorrowingRulesFailedException {

      User user = getUser(userId);
      Work work = getWork(workId);

      Rules[] rules = new Rules[] {
          new Repeated(),
          new SuspendedRule(),
          new MaxRequest(),
          new NoReferencial(),
          new MaxPrice()
      };

      for (Rules rule : rules) {
          if (!rule.check(user, work)) {
              // lança a exception que o app conhece
              throw new BorrowingRulesFailedException(userId, workId, rule.getRuleId());
          }
      }

      // Regra 3: exemplares disponíveis
      if (work.getAvailableCopies() == 0) {
          throw new BorrowingRulesFailedException(userId, workId, 3);
      }
      
      int duration = user.getBehaviour().requestLoanDuration(user, work);
      int dueDate = getCurrentDate() + duration;

      // cria Loan e adiciona
      Requests request = new Requests(work, dueDate);

      // atualiza inventário e listas do user
      work.setAvailableCopies(work.getAvailableCopies() - 1);
      user.addRequests(request);

      setChanged(true);
      notifyWorkBorrowed(work);
      return dueDate;
  }

    public int returnWork(int userId, int workId)
      throws NoSuchUseridException, NoSuchWorkidException, WorkNotBorrowedByUseridException {

        User user = getUser(userId);
        Work work = getWork(workId);

        Requests request = user.hasRequestedWork(work);

        if (request== null) {
            throw new WorkNotBorrowedByUseridException(workId, userId);
        }

        int fine = user.getFine();

        if (user.isSuspended()) {
            int delay = getCurrentDate() - request.getDueDate();
            fine = fine + (delay * 5);

            user.setFine(fine);

            user.incrementLateReturns();
            user.resetOnTimeReturns();

            if (user.getLateReturns() >= 3) {
                user.setBehaviour(new bci.Behaviour.Faulty());
            }
        } else {
            user.incrementOnTimeReturns();
            user.resetLateReturns();
            if (user.getOnTimeReturns() == 3 && user.getBehaviour().getBehaviourName().equals("FALTOSO")) {
                user.setBehaviour(new bci.Behaviour.Normal());
            }
            if (user.getOnTimeReturns() >= 5) {
                user.setBehaviour(new bci.Behaviour.Good());
            }
        }

        user.getRequests().remove(request);
        work.setAvailableCopies(work.getAvailableCopies() + 1);
        
        setChanged(true);
        if (work.getAvailableCopies() == 1) {
          notifyWorkAvailable(work);
        }

        return fine;
      }

    public void payFine(int userId)
        throws NoSuchUseridException, UserIsActiveException {
        User user = getUser(userId);

        if (!user.isSuspended()) {
            throw new UserIsActiveException(userId);
        }

        user.setFine(0);
        
        boolean nothinglate= true;

        for (Requests r : user.getRequests()){
              if (r.isLate(this.currentDate)){
                nothinglate= false;
              }
          }
        if (nothinglate){
            user.setStatus(new bci.Status.Active());
        }

        setChanged(true);
    }

    public void notifyWorkBorrowed(Work work) {
      List<User> interested = work.getInterestedWhenBorrowed();
      if (interested != null) {
          for (User u : interested) {
              u.addNotification(new WhenBorrowed(work));
          }
      }
    }
    
    public void notifyWorkAvailable(Work work) {
      List<User> interested = work.getInterestedWhenAvailable();
      if (interested != null) {
          for (User u : interested) {
              u.addNotification(new WhenAvailable(work));
          }
      }
    }

    public void registerWhenAvailable(int userId, int workId) throws NoSuchUseridException, NoSuchWorkidException {
      User user = getUser(userId);
      Work work = getWork(workId);

      // Se a lista de interessados não existir, inicializa
      if (work.getInterestedWhenAvailable() == null) {
          work.setInterestedWhenAvailable(new ArrayList<>());
      }

      // Adiciona o utente à lista de interessados se ainda não estiver
      if (!work.getInterestedWhenAvailable().contains(user)) {
          work.getInterestedWhenAvailable().add(user);
      }
      setChanged(true);
    }
    
    public void registerWhenBorrowed(int userId, int workId) throws NoSuchUseridException, NoSuchWorkidException {
      User user = getUser(userId);
      Work work = getWork(workId);

      // Se a lista de interessados não existir, inicializa
      if (work.getInterestedWhenBorrowed() == null) {
          work.setInterestedWhenBorrowed(new ArrayList<>());
      }

      // Adiciona o utente à lista de interessados se ainda não estiver
      if (!work.getInterestedWhenBorrowed().contains(user)) {
          work.getInterestedWhenBorrowed().add(user);
      }
      setChanged(true);
    }

    public List<Work> performsearch(String searchTerm){
        // Lista para armazenar resultados
        List<Work> results = new ArrayList<>();

        // Itera sobre todas as obras registadas
        for (Work work : getWorksMap().values()) {
            String title = work.getTitle().toLowerCase();
            boolean matches = false;

            // Verifica se o termo aparece no título
            if (title.contains(searchTerm)) {
                matches = true;
            }

            // Verificação adicional por tipo de obra
            if (!matches) {
              if (work.matchesCreatorName(searchTerm)) {
                  matches = true;
              }
            }
            if (matches) {
                results.add(work);
            }
        }

        // Ordena os resultados por ID (ordem crescente)
        results.sort((w1, w2) -> Integer.compare(w1.getID(), w2.getID()));
        return results;
      }
    
    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users).
     *
     * @param filename name of the file to load
     * @throws UnrecognizedEntryException
     * @throws IOException
     */    
    void importFile(String filename) throws UnrecognizedEntryException, IOException, UseridRegistrationFailedException, NoSuchUseridException {
      // Tenta abrir o ficheiro para leitura linha a linha
      try (var reader = new BufferedReader(new FileReader(filename))) {
      String line;
       // Lê o ficheiro até não haver mais linhas
      while ((line = reader.readLine()) != null) {
        // Divide cada linha em campos separados por ':'
          var fields = line.split(":");
            // Processa a linha de acordo com o tipo (BOOK, DVD, USER, etc.)
          process(fields);
      }
  }
        // Marca a biblioteca como modificada

        _libraryChanged = true;

    }

}
