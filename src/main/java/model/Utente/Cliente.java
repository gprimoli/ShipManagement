package model.Utente;

import model.Notifica.Notifica;
import model.Richiesta.Richiesta;

import java.sql.Date;
import java.util.LinkedList;

public class Cliente extends Utente{
    private LinkedList<Richiesta> richieste;

    public Cliente(String codFiscale, String nome, String cognome, Date dataNascita, String luogoNascita, String email, String telefono, boolean attivato, LinkedList<Notifica> notifiche, LinkedList<Richiesta> richieste) {
        super(codFiscale, nome, cognome, dataNascita, luogoNascita, email, telefono, attivato, notifiche);
        this.richieste = richieste;
    }

    public LinkedList<Richiesta> getRichieste() {
        return richieste;
    }
}
