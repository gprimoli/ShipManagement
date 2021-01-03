package model.Utente;

import model.CompagniaBroker.CompagniaBroker;
import model.Mediazione.Mediazione;
import model.Notifica.Notifica;

import java.sql.Date;
import java.util.LinkedList;

public class Armatore extends Utente{
    private CompagniaBroker compagniaBroker;
    private LinkedList<Mediazione> mediazioni;

    public Armatore(String codFiscale, String nome, String cognome, Date dataNascita, String luogoNascita, String email, String telefono, boolean attivato, LinkedList<Notifica> notifiche, CompagniaBroker compagniaBroker, LinkedList<Mediazione> mediazioni) {
        super(codFiscale, nome, cognome, dataNascita, luogoNascita, email, telefono, attivato, notifiche);
        this.compagniaBroker = compagniaBroker;
        this.mediazioni = mediazioni;
    }

    public LinkedList<Mediazione> getMediazioni() {
        return mediazioni;
    }

    public CompagniaBroker getCompagniaBroker() {
        return compagniaBroker;
    }
}
