package model.Util;

import javax.servlet.ServletException;

public class DuplicateException extends ServletException {

    public DuplicateException() {
        super("L'elemento che stai cercando di inserire è un duplicato");
    }

    public DuplicateException(String message) {
        super(message);
    }
}
