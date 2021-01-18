package model.Mediazione;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Arrays;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Mediazione {
    int id;
    String nome;
    String stato;
    @NonFinal
    InputStream contratto;
    String codFiscaleUtente;
    @NonFinal
    boolean caricato;

    public InputStream getDocumento() {
        if (caricato && contratto == null) {
            caricato = true;
            contratto = MediazioneDAO.doRetriveDocumento(id);
        }
        return contratto;
    }

    public static class MediazioneBuilder {

        public MediazioneBuilder id(int id) throws InvalidParameterException {
            if (id < 0)
                throw new InvalidParameterException();
            this.id = id;
            return this;
        }

//        public MediazioneBuilder nome(String nome) throws InvalidParameterException {
//            if (nome.length() > 100 || nome.length() < 2 || (!nome.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")))
//                throw new InvalidParameterException();
//            this.nome = nome;
//            return this;
//        }
//
//        public MediazioneBuilder stato(String stato) throws InvalidParameterException {
//            String[] ar = new String[]{"Default", "In corso", "Richiesta Modifica", "Richiesta Terminazione",
//                    "In Attesa di Firma", "Terminata"};
//
//            if (Arrays.stream(ar).noneMatch(s -> s.compareTo(stato) == 0))
//                throw new InvalidParameterException();
//            this.stato = stato;
//            return this;
//        }
//
//        public MediazioneBuilder codFiscaleUtente(String codFiscaleUtente) throws InvalidParameterException {
//            if (codFiscaleUtente.length() != 16 || (!codFiscaleUtente.matches("^[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]$")))
//                throw new InvalidParameterException();
//            this.codFiscaleUtente = codFiscaleUtente;
//            return this;
//        }


        public MediazioneBuilder clone(Mediazione m){
            this.id = m.id;
            this.nome = m.nome;
            this.stato = m.stato;
            this.contratto = m.contratto;
            this.caricato = m.caricato;
            this.codFiscaleUtente = m.codFiscaleUtente;
            return this;
        }


        public Mediazione build() throws InvalidParameterException {
            if (this.nome == null
                    || this.stato == null
                    || this.codFiscaleUtente == null
            )
                throw new InvalidParameterException();
            return new Mediazione(id, nome, stato, contratto, codFiscaleUtente, caricato);
        }
    }
}
