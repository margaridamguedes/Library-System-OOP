package bci.app.main;

import bci.LibraryManager;
import bci.exceptions.MissingFileAssociationException;
import java.io.IOException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form; 

/**
 * §4.1.1 Open and load files.
 */
class DoSaveFile extends Command<LibraryManager> {

    DoSaveFile(LibraryManager receiver) {
        super(Label.SAVE_FILE, receiver, r -> r.getLibrary() != null);
    }

    @Override
    protected final void execute() {
		try {
			_receiver.save();
		} catch (MissingFileAssociationException _) {
			try {
				_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
			} catch (MissingFileAssociationException | IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }

}
