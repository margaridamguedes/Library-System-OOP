package bci.app.work;

import bci.LibraryManager;
import bci.exceptions.NoSuchWorkidException; // <- necessário
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int workId = Form.requestInteger(Prompt.workId());
        int amount = Form.requestInteger(Prompt.amountToUpdate());

        try {
            boolean changed = _receiver.changeWorkInventory(workId, amount);

            if (!changed)
                _display.popup(Message.notEnoughInventory(workId, amount));

        } catch (NoSuchWorkidException e) {
            _display.popup(Message.noSuchWork(workId));
        }
    }
}
