package model.Richiesta;

import java.sql.Blob;
import java.sql.Date;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Richiesta {
    int id;
    String codFiscaleUtente;
    String tipoCarico;
    float quantita;
    Date dataPartenza;
    String portoPartenza;
    Date dataArrivo;
    String portoArrivo;
    String stato;
    @NonFinal
    Blob documento;
    @NonFinal
    boolean caricato;

    public Blob getDocumento() {
        if (caricato && documento == null) {
            caricato = true;
            documento = RichiestaDAO.doRetriveDocumento(id);
        }
        return documento;
    }

    public static class RichiestaBuilder {
        public RichiestaBuilder id(int id) throws InvalidParameterException {
            if (id < 0)
                throw new InvalidParameterException();
            this.id = id;
            return this;
        }
    }
}
