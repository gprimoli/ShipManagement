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

          public NotificaBuilder oggetto(String oggetto) throws InvalidParameterException {
               if(oggetto.length() > 50 || oggetto.length()<1 )
                    throw new InvalidParameterException();
               this.oggetto = oggetto;
               return this;
          }

          public NotificaBuilder corpo(String corpo) throws InvalidParameterException {
               if(corpo.length() > 5000 || corpo.length()<1 )
                    throw new InvalidParameterException();
               this.corpo = corpo;
               return this;
          }
     }
}
