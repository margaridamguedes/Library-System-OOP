package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import bci.Work.Work;
import java.util.Map;

/**

* 4.3.2. Display all works.
  */
  class DoDisplayWorks extends Command<LibraryManager> {

  DoDisplayWorks(LibraryManager receiver) {
  super(Label.SHOW_WORKS, receiver);
  }

  @Override
  protected final void execute() {
        Map<Integer, Work> works = _receiver.getLibrary().getWorksMap();

    for (Work work : works.values()) {
    _display.popup(work.toString());
    }

}
  }
