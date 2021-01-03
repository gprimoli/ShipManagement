package model.Mediazione;

import java.sql.Blob;
import model.Imbarcazione.Imbarcazione;
import model.Richiesta.Richiesta;

import java.util.LinkedList;

public class Mediazione {
    private final int id;
    private final String nome;
    private final String stato;
    private final Blob contratto;

    private LinkedList<Richiesta> richieste;
    private LinkedList<Imbarcazione>  imbarcazioni;

    public Mediazione(int id, String nome, String stato, Blob contratto, LinkedList<Richiesta> richieste, LinkedList<Imbarcazione> imbarcazioni) {
        this.id = id;
        this.nome = nome;
        this.stato = stato;
        this.contratto = contratto;
        this.richieste = richieste;
        this.imbarcazioni = imbarcazioni;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getStato() {
        return stato;
    }

    public Blob getContratto() {
        return contratto;
    }

    public LinkedList<Richiesta> getRichieste() {
        return richieste;
    }

    public LinkedList<Imbarcazione> getImbarcazioni() {
        return imbarcazioni;
    }
}
