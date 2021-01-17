package model.Util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import model.Utente.Utente;

import java.util.Date;

public class Test1Prova extends TestCase {
//inizializzo la variabile per un UtenteX
    @Override
    protected void setUp() throws Exception {
        Date dateNascita=new Date("12/04/2000");
        Utente.builder().codFiscale("RMLGNR99R27F839Q").nome("Io").cognome("LOL").dataNascita((java.sql.Date) dateNascita).email("XoXoDoc@gmail.com").luogoNascita("Salerno").attivato(true).ruolo("Cliente").telefono("1234567890").build();
    }

    public void testLogin(){



    }

    public void testLoginException(){



    }
    public static Test suite(){
//restituisce un nuova testsuite che contine tutti i metodi di test dei Test1 tramite la reflection
        return new TestSuite(Test1Prova.class);
    }
//main chiama il test runner di JUnit e farà il run del metodo suite() ed eseguirà tutti i casi di test di quella suite

    public static void main(String[] args) throws InvalidParameterException {

    }
}
