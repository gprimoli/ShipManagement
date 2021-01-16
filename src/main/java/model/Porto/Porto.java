package model.Porto;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Porto {
    String localcode;
    String nome;
    int idArea;

    public static class PortoBuilder {
        public PortoBuilder localcode(String localcode) throws InvalidParameterException {
            if (localcode.length() > 6 || (!localcode.matches("^[A-Za-z0-9]+$")))
                throw new InvalidParameterException();
            this.localcode = localcode;
            return this;
        }
//
//        public PortoBuilder nome(String nome) throws InvalidParameterException {
//            if (nome.length() > 50 || nome.length() < 2 || (!nome.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")))
//                throw new InvalidParameterException();
//            this.nome = nome;
//            return this;
//        }
//
//        public PortoBuilder idArea(int idArea) throws InvalidParameterException {
//            if (idArea < 0)
//                throw new InvalidParameterException();
//            this.idArea = idArea;
//            return this;
//        }

        public Porto build() throws InvalidParameterException {
            if (this.localcode == null || this.nome == null)
                throw new InvalidParameterException();
            return new Porto(localcode, nome, idArea);
        }
    }
}
