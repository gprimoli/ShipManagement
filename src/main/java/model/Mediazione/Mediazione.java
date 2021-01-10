package model.Mediazione;
import java.sql.Blob;
import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Mediazione {
     int id;
     String nome;
     String stato;
     @NonFinal
     Blob contratto;
     String codFiscaleUtente;
     @NonFinal
     boolean caricato;

     public Blob getDocumento() {
          if (caricato && contratto == null) {
               caricato = true;
               contratto = MediazioneDAO.doRetriveDocumento(id);
          }
          return contratto;
     }

     public static class MediazioneBuilder {
          public MediazioneBuilder id(int id) throws InvalidParameterException {
               if(id < 0)
                    throw new InvalidParameterException();
               this.id = id;
               return this;
          }
     }
}
