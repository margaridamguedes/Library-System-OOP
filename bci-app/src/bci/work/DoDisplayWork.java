package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.NoSuchWorkidException;
import bci.Work.Work;

/**

* 4.3.1. Display work.
  */
  class DoDisplayWork extends Command<LibraryManager> {

  DoDisplayWork(LibraryManager receiver) {
  super(Label.SHOW_WORK, receiver);
  addIntegerField("workId", Prompt.workId());
  }

  @Override
  protected final void execute() throws CommandException {
  int id = integerField("workId");

   try {
       Work work = _receiver.getLibrary().getWork(id);
       _display.popup(work.toString());
   } catch (NoSuchWorkidException e) {
       throw new NoSuchWorkException(id);
   }

  }
}
