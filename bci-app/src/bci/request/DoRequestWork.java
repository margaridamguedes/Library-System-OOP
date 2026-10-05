package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.BorrowingRuleFailedException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.BorrowingRulesFailedException;
import bci.exceptions.NoSuchUseridException;
import bci.exceptions.NoSuchWorkidException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = Form.requestInteger(bci.app.user.Prompt.userId());
        int workId = Form.requestInteger(bci.app.work.Prompt.workId());

        try {
            // chama o core requestWork que lança BorrowingRulesFailedException
            int returnDay = _receiver.requestWork(userId, workId);

            // mostra a data de devolução se sucesso
            _display.popup(Message.workReturnDay(workId, returnDay));

        } catch (NoSuchUseridException e) {
            throw new NoSuchUserException(userId);

        } catch (NoSuchWorkidException e) {
            throw new NoSuchWorkException(workId);

        } catch (BorrowingRulesFailedException e) {
            // regra 3 → falta de exemplares
            if (e.getIdRule() == 3) {
                if (Form.confirm(Prompt.returnNotificationPreference())) {
                    try {
                        _receiver.registerWhenAvailable(e.getIdUser(), e.getIdWork());
                    } catch (NoSuchUseridException x) {
                        throw new NoSuchUserException(e.getIdUser());
                    } catch (NoSuchWorkidException x) {
                        throw new NoSuchWorkException(e.getIdWork());
                    }
                }
            } else {
                // para todas as outras regras → lança exceção do app
                throw new BorrowingRuleFailedException(e.getIdUser(), e.getIdWork(), e.getIdRule());
            }
        }
    }
}
