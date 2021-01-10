package model.Notifica;
import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Notifica {
     int id;
     String oggetto;
     String corpo;

     public static class NotificaBuilder {
          public NotificaBuilder id(int id) throws InvalidParameterException {
               if(id < 0)
                    throw new InvalidParameterException();
               this.id = id;
               return this;
          }
     }
}
