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
               if(localcode.length() < 10)
                    throw new InvalidParameterException();
               this.localcode = localcode;
               return this;
          }
     }
}
