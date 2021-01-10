package model.Util;

import javax.servlet.ServletException;

public class NoEntryException extends ServletException {
    public NoEntryException() {
        super("Non sono stati trovati risultati");
    }

    public NoEntryException(String message) {
        super(message);
    }
}
