package bci.app.work;

import bci.LibraryManager;
import bci.Work.Work;
import bci.Work.Book;
import bci.Work.DVD;
import bci.Creator.Creator;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import java.util.List;
import java.util.ArrayList;

/**
 * 4.3.5. Perform search according to miscellaneous criteria.
 */
class DoPerformSearch extends Command<LibraryManager> {

    DoPerformSearch(LibraryManager receiver) {
        super(Label.PERFORM_SEARCH, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        // Pede o termo de pesquisa ao utilizador
        String searchTerm = Form.requestString(Prompt.searchTerm()).toLowerCase();
        List<Work> results = _receiver.performsearch(searchTerm);

        // Mostra os resultados encontrados
        for (Work work : results) {
            _display.addLine(work.toString());
        }

        // Se não houver resultados, não mostra nada (como diz o enunciado)
        _display.display();
    }
}
