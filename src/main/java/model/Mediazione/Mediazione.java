package model.Mediazione;
import java.sql.Blob;
import java.util.Arrays;

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

          public MediazioneBuilder nome(String nome) throws InvalidParameterException {
               if(nome.length()>100 || nome.length()<2 || (!nome.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")) )
                    throw new InvalidParameterException();
               this.nome = nome;
               return this;
          }

          public MediazioneBuilder stato(String stato) throws InvalidParameterException {
               String[] ar = new String[]{"Default","In corso","Richiesta Modifica","Richiesta Terminazione",
               "In Attesa di Firma","Terminata"};

               if (!Arrays.stream(ar).parallel().anyMatch(stato::contains))
                    throw new InvalidParameterException();
               this.stato = stato;
               return this;
          }
     }
}
