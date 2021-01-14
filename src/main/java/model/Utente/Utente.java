package model.Utente;

import java.sql.Date;

import lombok.*;
import lombok.experimental.NonFinal;
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
            if (codFiscale.length() < 5)
                throw new InvalidParameterException();
            this.codFiscale = codFiscale;
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
