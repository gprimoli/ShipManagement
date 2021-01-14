package model.CompagniaBroker;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class CompagniaBroker {
    String codFiscale;
    String nome;
    String telefono;
    String sedeLegale;
    String sitoWeb;

    public static class CompagniaBrokerBuilder {
        public CompagniaBrokerBuilder codFiscale(String codFiscale) throws InvalidParameterException {
            if (codFiscale.length() < 1)
                throw new InvalidParameterException();
            this.codFiscale = codFiscale;
            return this;
        }
    }
}
