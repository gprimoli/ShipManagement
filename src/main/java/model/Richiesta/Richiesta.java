package model.Richiesta;

import model.Porto.Porto;

import java.sql.Blob;
import java.sql.Date;

public class Richiesta {
    private final int id;
    private final String tipoCarico;
    private final float quantita;
    private final Date dataPartenza;
    private final Date dataArrivo;
    private final String stato;
    private final Blob documento;
    private final Porto portoPartenza;
    private final Porto portoArrivo;

    public Richiesta(int id, String tipoCarico, float quantita, Date dataPartenza, Date dataArrivo, String stato, Blob documento, Porto portoPartenza, Porto portoArrivo) {
        this.id = id;
        this.tipoCarico = tipoCarico;
        this.quantita = quantita;
        this.dataPartenza = dataPartenza;
        this.dataArrivo = dataArrivo;
        this.stato = stato;
        this.documento = documento;
        this.portoPartenza = portoPartenza;
        this.portoArrivo = portoArrivo;
    }

    public int getId() {
        return id;
    }

    public String getTipoCarico() {
        return tipoCarico;
    }

    public float getQuantita() {
        return quantita;
    }

    public Date getDataPartenza() {
        return dataPartenza;
    }

    public Date getDataArrivo() {
        return dataArrivo;
    }

    public String getStato() {
        return stato;
    }

    public Blob getDocumento() {
        return documento;
    }

    public Porto getPortoPartenza() {
        return portoPartenza;
    }

    public Porto getPortoArrivo() {
        return portoArrivo;
    }
}
