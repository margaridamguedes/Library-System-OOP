package bci.app.user;

import bci.LibraryManager;
import bci.Notification.Notification;
import bci.app.exceptions.NoSuchUserException;
import bci.User.User;
import bci.exceptions.NoSuchUseridException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = Form.requestInteger(Prompt.userId());
        
        try {
            User user = _receiver.getUser(userId);
            
            // Mostra notificações pela ordem em que foram recebidas
            for (Notification n : user.getNotifications()) {
                _display.addLine(n.getMessageContent());
            }

            // Se não havia notificações, não mostra nada especial — apenas não imprime linhas.
            // Agora limpa-as para que não voltem a ser mostradas
            user.cleanNotifications();

            _display.display();

        } catch (NoSuchUseridException e) {
            throw new NoSuchUserException(userId);
        }
    }
}
