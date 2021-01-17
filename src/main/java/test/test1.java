package test;

import model.Utente.Utente;
import model.Util.InvalidParameterException;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
/*
public class test1 {
    private Utente x;

    @Test
    public void testLegalConstruction() throws InvalidParameterException {
        Date dateNascita=new Date("12/04/2000");
        x = Utente.builder().codFiscale("RMLGNR99R27F839Q").nome("Io").cognome("LOL").dataNascita((java.sql.Date) dateNascita).email("XoXoDoc@gmail.com").luogoNascita("Salerno").attivato(true).ruolo("Cliente").telefono("1234567890").build();
        assertEquals("wrong cod", "RMLGNR99R27F839Q", x.getCodFiscale());
        assertEquals("wrong nome", "Io", x.getNome());
    }

        @Test
        public void testIlegalConstruction () throws InvalidParameterException {
            Date dateNascita=new Date("12/04/2000");
            x = Utente.builder().codFiscale("RMLGNR99R27F839QAAAAAA").nome("Io").cognome("LOL").dataNascita((java.sql.Date) dateNascita).email("XoXoDoc@gmail.com").luogoNascita("Salerno").attivato(true).ruolo("Cliente").telefono("1234567890").build();
            assertEquals("wrong num", "RMLGNR99R27F839F", x.getCodFiscale());
            assertEquals("wrong den", "Io", x.getNome());
        }

} */