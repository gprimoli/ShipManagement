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
            if (codFiscale.length() != 16 || (!codFiscale.matches("^[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]$")))
                throw new InvalidParameterException();
            this.codFiscale = codFiscale;
            return this;
        }

        public CompagniaBrokerBuilder nome(String nome) throws InvalidParameterException {
            if (nome.length() > 50 || nome.length() < 2 || (!nome.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")))
                throw new InvalidParameterException();
            this.nome = nome;
            return this;
        }

        public CompagniaBrokerBuilder telefono(String telefono) throws InvalidParameterException {
            if (telefono.length() != 10 || (!telefono.matches("^[0-9]+$")))
                throw new InvalidParameterException();
            this.telefono = telefono;
            return this;
        }

        public CompagniaBrokerBuilder sedeLegale(String sedeLegale) throws InvalidParameterException {
            if (sedeLegale.length() > 50 || sedeLegale.length() < 2 || (!sedeLegale.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")))
                throw new InvalidParameterException();
            this.sedeLegale = sedeLegale;
            return this;
        }

        public CompagniaBrokerBuilder sitoWeb(String sitoWeb) throws InvalidParameterException {
            if (sedeLegale.length() > 50 || sedeLegale.length() < 2 || (!sedeLegale.matches("^(http:\\/\\/www\\.|https:\\/\\/" +
                    "www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]" +
                    "{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$")))
                throw new InvalidParameterException();
            this.sitoWeb = sitoWeb;
            return this;
        }

        public CompagniaBroker build() throws InvalidParameterException {
            if (this.codFiscale == null
                    || this.nome == null
                    || this.telefono == null
                    || this.sedeLegale == null
                    || this.sitoWeb == null)
                throw new InvalidParameterException();
            return new CompagniaBroker(codFiscale, nome, telefono, sedeLegale, sitoWeb);
        }
    }
}
