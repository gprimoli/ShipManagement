package model.Imbarcazione;

import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import lombok.*;
import lombok.experimental.NonFinal;
import model.Util.InvalidParameterException;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonFinal
public class Imbarcazione {
    String codFiscaleUtente;
    String imo;
    String nome;
    String tipologia;
    int annoCostruzione;
    String bandiera;
    float quantitaMax;
    float lunghezza;
    float ampiezza;
    float altezza;
    int posizione;
    boolean disponibile;
    @NonFinal
    Blob documento;
    @NonFinal
    boolean caricato;

    public Blob getDocumento() {
        if (caricato && documento == null) {
            caricato = true;
            documento = ImbarcazioneDAO.doRetriveDocumento(imo);
        }
        return documento;
    }

    public static class ImbarcazioneBuilder {

        public ImbarcazioneBuilder codFiscaleUtente(String codFiscaleUtente) throws InvalidParameterException {
            if (codFiscaleUtente.length() <16 || codFiscaleUtente.length() <16 || (!codFiscaleUtente.matches("^[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]$")))
                throw new InvalidParameterException();
            this.codFiscaleUtente = codFiscaleUtente;
            return this;
        }

        public ImbarcazioneBuilder imo(String imo) throws InvalidParameterException {
            if (imo.length() < 7 || imo.length() > 7 || (!imo.matches("^[0-9]+$")) )
                throw new InvalidParameterException();
            this.imo = imo;
            return this;
        }

        public ImbarcazioneBuilder nome(String nome) throws InvalidParameterException {
            if (nome.length() >50 || nome.length()<2 || (!nome.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$") ))
                throw new InvalidParameterException();
            this.nome = nome;
            return this;
        }

        public ImbarcazioneBuilder tipologia(String tipologia) throws InvalidParameterException {
            String[] ar = new String[]{"Portacontainer","Carboniera","Chimichiera","Lift-on/lift-off","Nave da carico","Nave frigorifera","Portarinfuse","Roll-on/Roll-off"};

            if (!Arrays.stream(ar).parallel().anyMatch(tipologia::contains))
                throw new InvalidParameterException();
            this.tipologia = tipologia;
            return this;
        }

        public ImbarcazioneBuilder annoCostruzione(int  annoCostruzione) throws InvalidParameterException {
            Calendar calendar = GregorianCalendar.getInstance();
            if ( annoCostruzione<999 || annoCostruzione> calendar.get( Calendar.YEAR ))
                throw new InvalidParameterException();
            this.annoCostruzione =  annoCostruzione;
            return this;
        }
        public ImbarcazioneBuilder bandiera(String bandiera) throws InvalidParameterException {
        String[] ar = new String[]{"AF","AL","DZ","AD","AO","AI","AQ","AG","AN","SA","AR","AM","AW","AU","AT","AZ","BS","BH","BD","BB","BE","BZ","BJ","BM","BY","BT","BO","BA","BW","BR","BN","BG","BF","BI","KH","CM","CA","CV","TD","CL","CN","CY","VA","CO","KM","KP","KR","CR","CI","HR","CU","DK","DM","EC","EG","IE","SV","AE","ER","EE","ET","RU","FJ","PH","FI","FR","GA","GM","GE","DE","GH","JM","JP","GI","DJ","JO","GR","GD","GL","GP","GU","GT","GN","GW","GQ","GY","GF","HT","HN","HK","IN","ID","IR","IQ","BV","CX","HM","KY","CC","CK","FK","FO","MH","MP","UM","NF","SB","TC","VI","VG","IL","IS","IT","KZ","KE","KG","KI","KW","LA","LV","LS","LB","LR","LY","LI","LT","LU","MO","MK","MG","MW","MV","MY","ML","MT","MA","MQ","MR","MU","YT","MX","MD","MC","MN","MS","MZ","MM","NA","NR","NP","NI","NE","NG","NU","NO","NC","NZ","OM","NL","PK","PW","PA","PG","PY","PE","PN","PF","PL","PT","PR","QA","GB","CZ","CF","CG","CD","DO","RE","RO","RW","EH","KN","PM","VC","WS","AS","SM","SH","LC","ST","SN","XK","SC","SL","SG","SY","SK","SI","SO","ES","LK","FM","US","ZA","GS","SD","SR","SJ","SE","CH","SZ","TJ","TH","TW","TZ","IO","TF","PS","TL","TG","TK","TO","TT","TN","TR","TM","TV","UA","UG","HU","UY","UZ","VU","VE","VN","WF","YE","ZM","ZW","RS","ME","TP","GG"};

            if (!Arrays.stream(ar).parallel().anyMatch(bandiera::contains))
                throw new InvalidParameterException();
            this.bandiera = bandiera;
            return this;
        }

        public ImbarcazioneBuilder quantitaMax(float  quantitaMax) throws InvalidParameterException {
            if ( quantitaMax>21 )
                throw new InvalidParameterException();
            this.quantitaMax =  quantitaMax;
            return this;
        }



        public ImbarcazioneBuilder lunghezza(float  lunghezza) throws InvalidParameterException {
            this.lunghezza =  lunghezza;
            return this;
        }

        public ImbarcazioneBuilder ampiezza(float  ampiezza) throws InvalidParameterException {
            this.ampiezza =  ampiezza;
            return this;
        }

        public ImbarcazioneBuilder altezza(float  altezza) throws InvalidParameterException {
            this.altezza =  altezza;
            return this;
        }

        public ImbarcazioneBuilder posizione(int  posizione) throws InvalidParameterException {
            this.posizione =  posizione;
            return this;
        }
        public ImbarcazioneBuilder disponibile(boolean  disponibile) throws InvalidParameterException {
            this.disponibile =  disponibile;
            return this;
        }

    }
}
