package model.Util;

import javax.servlet.ServletException;

public class MediazioniInCorsoException extends ServletException {
    public MediazioniInCorsoException() {
        super("Sono presenti mediazioni in corso");
    }

    public MediazioniInCorsoException(String message) {
        super(message);
    }
}
