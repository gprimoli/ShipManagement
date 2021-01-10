package model.Area;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;


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
    }
}
