package model.Richiesta;

import java.sql.Blob;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import lombok.*;
import lombok.experimental.NonFinal;
import model.CompagniaBroker.CompagniaBroker;
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

        public RichiestaBuilder codFiscaleUtente(String codFiscaleUtente) throws InvalidParameterException {
            if (codFiscaleUtente.length() < 16 || codFiscaleUtente.length() > 16 || (!codFiscaleUtente.matches("^[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]$")))
                throw new InvalidParameterException();
            this.codFiscaleUtente = codFiscaleUtente;
            return this;
        }


        public RichiestaBuilder tipoCarico(String tipoCarico) throws InvalidParameterException {
            String[] ar = new String[]{"container","carico alla rinfusa","prodotti chimici solidi","prodotti chimici liquidi","prodotti chimici gassosi","prodotti alimentari","autoveicoli"};

            if (!Arrays.stream(ar).parallel().anyMatch(tipoCarico::contains))
                throw new InvalidParameterException();
            this.tipoCarico = tipoCarico;
            return this;
        }

        public RichiestaBuilder quantita(float quantita) throws InvalidParameterException {
            if (quantita<1)
                throw new InvalidParameterException();
            this.quantita = quantita;
            return this;
        }
        public RichiestaBuilder dataPartenza(Date dataPartenza) throws InvalidParameterException {
            Calendar compareDate = Calendar.getInstance();
            if (dataArrivo.compareTo(compareDate.getTime()) > 0)
                throw new InvalidParameterException();
            this.dataPartenza = dataPartenza;
            return this;
        }

        public RichiestaBuilder dataArrivo(Date dataArrivo) throws InvalidParameterException {
            Calendar compareDate = Calendar.getInstance();
            if (dataArrivo.compareTo(compareDate.getTime()) < 0)
                throw new InvalidParameterException();
            this.dataArrivo = dataArrivo;
            return this;
        }


        public RichiestaBuilder portoPartenza(String portoPartenza) throws InvalidParameterException {

           if (portoPartenza.length()<2 && portoPartenza.length()>100)
            throw new InvalidParameterException();
            this.portoPartenza = portoPartenza;
            return this;
        }

        public RichiestaBuilder portoArrivo(String portoArrivo) throws InvalidParameterException {

            if (portoArrivo.length()<2 && portoArrivo.length()>100)
                throw new InvalidParameterException();
            this.portoArrivo = portoArrivo;
            return this;
        }

        public RichiestaBuilder stato(String stato) throws InvalidParameterException {
            String[] ar = new String[]{"Disponibile","In lavorazione","Terminata"};

            if (!Arrays.stream(ar).parallel().anyMatch(stato::contains))
                throw new InvalidParameterException();
            this.stato = stato;
            return this;
        }


    }
}
