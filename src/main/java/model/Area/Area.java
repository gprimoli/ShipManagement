package model.Area;

import model.CompagniaBroker.CompagniaBroker;

import java.sql.Date;

public class Area {
    private final String codice;
    private final String nome;

    public Area(String codice, String nome) {
        this.codice = codice;
        this.nome = nome;
    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }
}
