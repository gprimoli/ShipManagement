package model.Imbarcazione;

import java.sql.Blob;
import model.Area.Area;
import model.Utente.Armatore;


public class Imbarcazione {
    private final String imo;
    private final String nome;
    private final String tipologia;
    private final int annoCostruzione;
    private final String bandiera;
    private final float qauntitaMax;
    private final float lughezza;
    private final float larghezza;
    private final float altezza;
    private final boolean disponibile;
    private final Blob documento;

    private final Armatore proprietario;
    private final Area posizione;

    public Imbarcazione(String imo, String nome, String tipologia, int annoCostruzione, String bandiera, float qauntitaMax, float lughezza, float larghezza, float altezza, boolean disponibile, Blob documento, Armatore proprietario, Area posizione) {
        this.imo = imo;
        this.nome = nome;
        this.tipologia = tipologia;
        this.annoCostruzione = annoCostruzione;
        this.bandiera = bandiera;
        this.qauntitaMax = qauntitaMax;
        this.lughezza = lughezza;
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.disponibile = disponibile;
        this.documento = documento;
        this.proprietario = proprietario;
        this.posizione = posizione;
    }


    public String getImo() {
        return imo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipologia() {
        return tipologia;
    }

    public int getAnnoCostruzione() {
        return annoCostruzione;
    }

    public String getBandiera() {
        return bandiera;
    }

    public float getQauntitaMax() {
        return qauntitaMax;
    }

    public float getLughezza() {
        return lughezza;
    }

    public float getLarghezza() {
        return larghezza;
    }

    public float getAltezza() {
        return altezza;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public Blob getDocumento() {
        return documento;
    }

    public Armatore getProprietario() {
        return proprietario;
    }

    public Area getPosizione() {
        return posizione;
    }
}
