package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.1.2. Display the current date.
 */
class DoDisplayDate extends Command<LibraryManager> {

    DoDisplayDate(LibraryManager receiver) {
        super(Label.DISPLAY_DATE, receiver);	
    }

    @Override
    protected final void execute() throws CommandException {
        int currentDate = _receiver.getLibrary().getCurrentDate();
        _display.addLine("Data actual: " + currentDate);
    }

}
