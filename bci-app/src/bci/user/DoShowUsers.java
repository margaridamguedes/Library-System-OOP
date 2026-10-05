package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import bci.User.User;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * 4.2.4. Show all users.
 */
class DoShowUsers extends Command<LibraryManager> {

    DoShowUsers(LibraryManager receiver) {
        super(Label.SHOW_USERS, receiver);
    }

    @Override
    protected final void execute() {
        List<User> users = new ArrayList<>(_receiver.getLibrary().getUsersMap().values());
        users.sort(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER).thenComparing(User::getID));
        for (User user : users) {
            _display.popup(user.toString()); 
        }

    }


}
