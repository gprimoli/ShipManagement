package model.Util;

public class InvalidParameterException extends Exception{
    public InvalidParameterException() {
        super("Parametri non validi");
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
