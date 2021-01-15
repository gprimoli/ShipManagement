package model.Area;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;

import static javax.print.attribute.standard.MediaSizeName.A;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Area {
    int id;
    String nome;

    public static class AreaBuilder {
        public AreaBuilder id(int id) throws InvalidParameterException {
            if (id < 0)
                throw new InvalidParameterException();
            this.id = id;
            return this;
        }

        public AreaBuilder nome(String nome) throws InvalidParameterException {
            if (nome.length() > 10 || nome.length() < 2 || (!nome.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")))
                throw new InvalidParameterException();
            this.nome = nome;
            return this;
        }

        public Area build() throws InvalidParameterException {
            if (id < 0 || nome == null)
                throw new InvalidParameterException();
            return new Area(id, nome);
        }
    }
}
