package model.Utente;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;

import lombok.*;
import lombok.experimental.NonFinal;
import model.CompagniaBroker.CompagniaBroker;
import model.Util.InvalidParameterException;

@Value
@Builder
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Utente {
    String codFiscale;
    String nome;
    String cognome;
    Date dataNascita;
    String luogoNascita;
    String email;
    String telefono;
    String ruolo;
    boolean attivato;

    public boolean isArmatore() {
        return ruolo.compareTo("armatore") == 0;
    }

    public boolean isCliente() {
        return ruolo.compareTo("cliente") == 0;
    }

    public boolean isBroker() {
        return ruolo.compareTo("broker") == 0;
    }

    public static class UtenteBuilder {
        //Controlli costruttore
        public UtenteBuilder codFiscale(String codFiscale) throws InvalidParameterException {
            if (codFiscale.length() < 16 || codFiscale.length() > 16 || (!codFiscale.matches("^[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]$")))
                throw new InvalidParameterException();
            this.codFiscale = codFiscale;
            return this;
        }

        public UtenteBuilder nome(String nome) throws InvalidParameterException {
            if (nome.length() >50 || nome.length() <2 || (!nome.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")))
                throw new InvalidParameterException();
            this.nome = nome;
            return this;
        }

        public UtenteBuilder cognome(String cognome) throws InvalidParameterException {
            if (cognome.length() >50 || cognome.length() <2 || (!cognome.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")))
                throw new InvalidParameterException();
            this.cognome = cognome;
            return this;
        }

        public UtenteBuilder dataNascita(Date dataNascita) throws InvalidParameterException {
            Calendar compareDate = Calendar.getInstance();
            if (dataNascita.compareTo(compareDate.getTime()) >= 0)
                throw new InvalidParameterException();
            this.dataNascita = dataNascita;
            return this;
        }

        public UtenteBuilder luogoNascita(String luogoNascita) throws InvalidParameterException {
            if (luogoNascita.length() >50 || luogoNascita.length() <2 || (!luogoNascita.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")))
                throw new InvalidParameterException();
            this.luogoNascita = luogoNascita;
            return this;
        }

        public UtenteBuilder email(String email) throws InvalidParameterException {
            if (!email.matches("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,4}$"))
                throw new InvalidParameterException();
            this.email = email;
            return this;
        }


        public UtenteBuilder telefono(String telefono) throws InvalidParameterException {
            if (telefono.length() <10 || telefono.length() >10 ||(!telefono.matches("^[0-9]+$")))
                throw new InvalidParameterException();
            this.telefono = telefono;
            return this;
        }

        public UtenteBuilder ruolo(String ruolo) throws InvalidParameterException {
            String[] ar = new String[]{"Cliente","Armatore","Broker"};
            if (!Arrays.stream(ar).parallel().anyMatch(ruolo::contains))
            this.ruolo = ruolo;
            return this;
        }

        public UtenteBuilder clone(Utente u) throws InvalidParameterException {
            this.codFiscale = u.getCodFiscale();
            this.nome = u.getNome();
            this.cognome = u.getCognome();
            this.dataNascita = u.getDataNascita();
            this.luogoNascita = u.getLuogoNascita();
            this.email = u.getEmail();
            this.telefono = u.getTelefono();
            this.ruolo = u.getRuolo();
            this.attivato = u.isAttivato();
            return this;
        }
    }
}
