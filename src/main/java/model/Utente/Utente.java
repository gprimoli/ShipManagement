package model.Utente;

import model.Notifica.Notifica;

import java.sql.Date;
import java.util.LinkedList;

public abstract class Utente {
    private final String codFiscale;
    private final String nome;
    private final String cognome;
    private final Date dataNascita;
    private final String luogoNascita;
    private final String email;
    private final String telefono;
    private final boolean attivato;

    private final LinkedList<Notifica> notifiche;

    public Utente(String codFiscale, String nome, String cognome, Date dataNascita, String luogoNascita, String email, String telefono, boolean attivato, LinkedList<Notifica> notifiche) {
        this.codFiscale = codFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.luogoNascita = luogoNascita;
        this.email = email;
        this.telefono = telefono;
        this.attivato = attivato;
        this.notifiche = notifiche;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean isAttivato() {
        return attivato;
    }

    public LinkedList<Notifica> getNotifiche() {
        return notifiche;
    }
}
