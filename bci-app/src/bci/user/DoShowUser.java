package bci.app.user;

import bci.User.User;
import bci.LibraryManager;
import bci.exceptions.NoSuchUseridException;
import bci.app.exceptions.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

    DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
        addIntegerField("Userid", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        int id= integerField("Userid");
        try{
            User user= _receiver.getUser(id);
            _display.addLine(user.toString());

        } catch (NoSuchUseridException e) {
            throw new NoSuchUserException(id);
        }
    }

}
