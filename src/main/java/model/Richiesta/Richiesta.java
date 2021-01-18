package model.Richiesta;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import lombok.*;
import lombok.experimental.NonFinal;
import model.CompagniaBroker.CompagniaBroker;
import model.Porto.PortoDAO;
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
    InputStream documento;
    @NonFinal
    boolean caricato;

    public InputStream getDocumento() {
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
//
//        public RichiestaBuilder codFiscaleUtente(String codFiscaleUtente) throws InvalidParameterException {
//            if (codFiscaleUtente.length() != 16 || (!codFiscaleUtente.matches("^[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]$")))
//                throw new InvalidParameterException();
//            this.codFiscaleUtente = codFiscaleUtente;
//            return this;
//        }
//
//
//        public RichiestaBuilder tipoCarico(String tipoCarico) throws InvalidParameterException {
//            String[] ar = new String[]{"Container", "Carico alla Rinfusa", "Prodotti Chimici Solidi", "Prodotti Chimici Liquidi", "Prodotti Chimici Gassosi", "Autoveicoli"};
//            if (Arrays.stream(ar).noneMatch(t -> t.compareTo(tipoCarico) == 0))
//                throw new InvalidParameterException();
//            this.tipoCarico = tipoCarico;
//            return this;
//        }
//
//        public RichiestaBuilder quantita(float quantita) throws InvalidParameterException {
//            if (quantita < 1)
//                throw new InvalidParameterException();
//            this.quantita = quantita;
//            return this;
//        }
//
//        public RichiestaBuilder dataPartenza(Date dataPartenza) throws InvalidParameterException {
//            Calendar compareDate = Calendar.getInstance();
//            if (dataArrivo.compareTo(compareDate.getTime()) > 0)
//                throw new InvalidParameterException();
//            this.dataPartenza = dataPartenza;
//            return this;
//        }
//
//        public RichiestaBuilder dataArrivo(Date dataArrivo) throws InvalidParameterException {
//            Calendar compareDate = Calendar.getInstance();
        //Data oggi controllo
//            if (dataArrivo.compareTo(compareDate.getTime()) < 0)
//                throw new InvalidParameterException();
//            this.dataArrivo = dataArrivo;
//            return this;
//        }
//
//
        public RichiestaBuilder portoPartenza(String portoPartenza) throws InvalidParameterException {
            if (PortoDAO.doCheckPorto(portoPartenza))
                throw new InvalidParameterException();
            this.portoPartenza = portoPartenza;
            return this;
        }

        public RichiestaBuilder portoArrivo(String portoArrivo) throws InvalidParameterException {
            if (PortoDAO.doCheckPorto(portoArrivo))
                throw new InvalidParameterException();
            this.portoArrivo = portoArrivo;
            return this;
        }
//
//        public RichiestaBuilder stato(String stato) throws InvalidParameterException {
//            String[] ar = new String[]{"Disponibile", "In lavorazione", "Terminata"};
//            if (Arrays.stream(ar).noneMatch(s -> s.compareTo(stato) == 0))
//                throw new InvalidParameterException();
//            this.stato = stato;
//            return this;
//        }

        public Richiesta build() throws InvalidParameterException{
            if (this.codFiscaleUtente == null
                    || this.tipoCarico == null
                    || this.portoPartenza == null
                    || this.dataPartenza.after(this.dataArrivo)
                    || this.portoArrivo == null
                    || this.stato == null
            )
                throw new InvalidParameterException();
            return new Richiesta(id, codFiscaleUtente,tipoCarico, quantita, dataPartenza, portoPartenza,dataArrivo, portoArrivo, stato, documento, caricato);
        }
    }
}
