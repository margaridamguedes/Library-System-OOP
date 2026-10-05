package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import bci.Work.Work;
import bci.Creator.Creator;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import bci.app.exceptions.NoSuchCreatorException;
import bci.exceptions.NoSuchCreatoridException;

/**

* 4.3.3. Display all works by a specific creator.
  */
  class DoDisplayWorksByCreator extends Command<LibraryManager> {
  
  DoDisplayWorksByCreator(LibraryManager receiver) {
    super(Label.SHOW_WORKS_BY_CREATOR, receiver);
    addStringField("creatorName", Prompt.creatorId());
  }
  @Override
  protected final void execute() throws NoSuchCreatorException{
    String creatorName = stringField("creatorName");
    try{   
        Creator creator = _receiver.getLibrary().getCreatorByName(creatorName);
        List<Work> works = new ArrayList<>(creator.getWorks());
        works.sort(Comparator.comparing(Work :: getTitle, String.CASE_INSENSITIVE_ORDER));

        for (Work work : works) {
            _display.popup(work.toString());
        }
    }
    catch (NoSuchCreatoridException e) {
        throw new NoSuchCreatorException(creatorName);
    }
  }
}
