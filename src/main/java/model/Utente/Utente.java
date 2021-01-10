package model.Utente;

import java.sql.Date;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Utente {
    String codFiscale;
    String nome;
    String cognome;
    Date dataNascita;
    String luogoNascita;
    String email;
    String telefono;
    Ruolo ruolo;
    boolean attivato;

    public boolean isArmatore() {
        return ruolo == Ruolo.Armatore;
    }

    public boolean isCliente() {
        return ruolo == Ruolo.Cliente;
    }

    public boolean isBroker() {
        return ruolo == Ruolo.Broker;
    }

    public static class UtenteBuilder {
        //Controlli costruttore
        public UtenteBuilder codFiscale(String codFiscale) throws InvalidParameterException {
            if (codFiscale.length() < 5)
                throw new InvalidParameterException();
            this.codFiscale = codFiscale;
            return this;
        }
    }
}
