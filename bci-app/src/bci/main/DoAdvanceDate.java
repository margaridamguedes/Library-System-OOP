package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.1.3. Advance the current date.
 */
class DoAdvanceDate extends Command<LibraryManager> {

    DoAdvanceDate(LibraryManager receiver) {
        super(Label.ADVANCE_DATE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        _receiver.advanceDate(Form.requestInteger(Prompt.daysToAdvance()));
    }

}
