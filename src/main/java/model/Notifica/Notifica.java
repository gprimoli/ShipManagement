package model.Notifica;

public class Notifica {
    private final int id;
    private final String oggetto;
    private final String corpo;

    public Notifica(int id, String oggetto, String corpo) {
        this.id = id;
        this.oggetto = oggetto;
        this.corpo = corpo;
    }

    public int getId() {
        return id;
    }

    public String getOggetto() {
        return oggetto;
    }

    public String getCorpo() {
        return corpo;
    }
}
