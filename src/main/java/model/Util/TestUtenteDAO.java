package model.Util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import model.Utente.Utente;
import model.Utente.UtenteDAO;

import java.util.Date;

public class TestUtenteDAO extends TestCase {

    UtenteDAO dao;
    String email;
    String password;
    @Override
    protected void setUp() throws Exception {

        email="albertorossi@gmail.com";
        password="Armatore1&";

      // Utente x= Utente.builder().codFiscale("RMLGNR99R27F839Q").nome("Albe").cognome("Rosso").dataNascita((java.sql.Date) dateNascita).email("XoXoDoc@gmail.com").luogoNascita("Salerno").attivato(true).ruolo("Cliente").telefono("1234567890").build();
    }

    public void testLogin() throws NoEntryException {
        String cod="SLCNDR80A05F912A";
        Utente x=UtenteDAO.doRetriveByEmailPassword(email,password);
        assertEquals(x.getCodFiscale(),cod);
    }

    public void testLoginException() throws NoEntryException {

        try {
            String cod = "SLCADR80A05F912A";
            Utente x = UtenteDAO.doRetriveByEmailPassword(email, password);
            //se non da ecc da fail , se ti aspetti eccez ma non viene lanciata
            assertFalse(x.getCodFiscale().equals(cod));
           // fail("failed login");
        }catch (NoEntryException ex){}

    }

    public static Test suite(){
//restituisce un nuova testsuite che contiene tutti i metodi di test dei Test1 tramite la reflection
        return new TestSuite(TestUtenteDAO.class);
    }

}
