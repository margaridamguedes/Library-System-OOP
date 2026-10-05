package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.UserIsActiveException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import bci.exceptions.NoSuchUseridException;
import bci.exceptions.NoSuchWorkidException;
import bci.exceptions.WorkNotBorrowedByUseridException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = Form.requestInteger(bci.app.user.Prompt.userId());
        int workId = Form.requestInteger(bci.app.work.Prompt.workId());

        try {
            int fine=_receiver.returnWork(userId, workId);
            if (fine> 0) {
                _display.popup(Message.showFine(userId, fine));

                boolean payFine = Form.confirm(Prompt.finePaymentChoice());
                // Chama a função de pagar multa apenas se quiser pagar
                if (payFine) {
                    _receiver.payFine(userId);
                }
            }

        } catch (NoSuchUseridException e) {
            throw new NoSuchUserException(userId);
        } catch (NoSuchWorkidException e) {
            throw new NoSuchWorkException(workId);
        } catch (WorkNotBorrowedByUseridException e) {
            throw new WorkNotBorrowedByUserException(workId, userId);
        } catch (bci.exceptions.UserIsActiveException e) {
            throw new UserIsActiveException(userId);
        }
    }
}