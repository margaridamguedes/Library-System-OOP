package bci.app.user;

import bci.LibraryManager;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import bci.app.exceptions.UserRegistrationFailedException;

import bci.exceptions.UseridRegistrationFailedException;


/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

    DoRegisterUser(LibraryManager receiver) {
        super(Label.REGISTER_USER, receiver);
        addStringField(("UserName"), Prompt.userName());
        addStringField(("UserEmail"), Prompt.userEMail());
    }


    @Override
    protected final void execute() throws CommandException {
        try { 
            int id= _receiver.registerUser(
            stringField("UserName"),
            stringField("UserEmail")
            );

            _display.addLine(Message.registrationSuccessful(id));
            _display.display();
        } catch (UseridRegistrationFailedException e) {
            throw new UserRegistrationFailedException(stringField("UserName"), stringField("UserEmail"));
        }
    }


}
