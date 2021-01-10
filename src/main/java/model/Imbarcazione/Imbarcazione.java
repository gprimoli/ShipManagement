package model.Imbarcazione;

import java.sql.Blob;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Imbarcazione {
    String codFiscaleUtente;
    String imo;
    String nome;
    String tipologia;
    int annoCostruzione;
    String bandiera;
    float qauntitaMax;
    float lughezza;
    float ampiezza;
    float altezza;
    int posizione;
    boolean disponibile;
    @NonFinal
    Blob documento;
    @NonFinal
    boolean caricato;

    public Blob getDocumento() {
        if (caricato && documento == null) {
            caricato = true;
            documento = ImbarcazioneDAO.doRetriveDocumento(imo);
        }
        return documento;
    }

    public static class ImbarcazioneBuilder {
        public ImbarcazioneBuilder codFiscaleUtente(String codFiscaleUtente) throws InvalidParameterException {
            if (codFiscaleUtente.length() < 10)
                throw new InvalidParameterException();
            this.codFiscaleUtente = codFiscaleUtente;
            return this;
        }
    }
}
