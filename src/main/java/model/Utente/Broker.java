package model.Utente;

import model.Imbarcazione.Imbarcazione;
import model.Notifica.Notifica;

import java.sql.Date;
import java.util.LinkedList;

public class Broker extends Utente{
    private LinkedList<Imbarcazione> imbarcazioni;

    public Broker(String codFiscale, String nome, String cognome, Date dataNascita, String luogoNascita, String email, String telefono, boolean attivato, LinkedList<Notifica> notifiche, LinkedList<Imbarcazione> imbarcazioni) {
        super(codFiscale, nome, cognome, dataNascita, luogoNascita, email, telefono, attivato, notifiche);
        this.imbarcazioni = imbarcazioni;
    }

    public LinkedList<Imbarcazione> getImbarcazioni() {
        return imbarcazioni;
    }
}
