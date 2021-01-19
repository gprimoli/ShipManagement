package model.Utente;

import lombok.Cleanup;
import model.Util.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class UtenteDAOTest {
    public static Utente getUtente() {
        Utente u = null;
        try {
            u = Utente.builder()
                    .codFiscale("RMLGNR99R27F839Q")
                    .nome("Gennaro Pio")
                    .cognome("Rimoli")
                    .dataNascita(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("1999-10-27").getTime()))
                    .luogoNascita("Napoli")
                    .email("g.rimoli@studenti.unisa.it")
                    .telefono("3337800499")
                    .attivato(false)
                    .ruolo("cliente")
                    .build();
        } catch (InvalidParameterException | ParseException e) {
            e.printStackTrace();
        }
        return u;
    }

    public static Utente getUtente1() {
        Utente u = null;
        try {
            u = Utente.builder()
                    .codFiscale("RMLGNR99R27F839Q")
                    .nome("Gennaro Piu")
                    .cognome("Rimolu")
                    .dataNascita(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("1999-10-27").getTime()))
                    .luogoNascita("Napola")
                    .email("a.rml@studenti.unisa.it")
                    .telefono("3337800455")
                    .attivato(false)
                    .ruolo("cliente")
                    .build();
        } catch (InvalidParameterException | ParseException e) {
            e.printStackTrace();
        }
        return u;
    }

    public static Utente getUtente2() {
        Utente u = null;
        try {
            u = Utente.builder()
                    .codFiscale("RMLGNR99R27F839R")
                    .nome("Gennaro Piu")
                    .cognome("Rimolu")
                    .dataNascita(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("1999-10-27").getTime()))
                    .luogoNascita("Napola")
                    .email("a.rml@studenti.unisa.it")
                    .telefono("3337800455")
                    .attivato(false)
                    .ruolo("cliente")
                    .build();
        } catch (InvalidParameterException | ParseException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Test
    void doSave() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "@Gennaro1");
        Utente u1 = UtenteDAO.doRetriveByCodFiscale("RMLGNR99R27F839Q");
        assertEquals(getUtente(), u1);
        deleteAll();
    }

    @Test
    void doUpdate() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "@Gennaro1");
        UtenteDAO.doUpdate(getUtente1());
        Utente u1 = UtenteDAO.doRetriveByCodFiscale("RMLGNR99R27F839Q");
        assertEquals(getUtente1(), u1);
        deleteAll();
    }

    @Test
    void doDelete() throws DuplicateException, MediazioniInCorsoException {
        UtenteDAO.doSave(getUtente(), "@Gennaro1");
        UtenteDAO.doDelete(getUtente());

        assertFalse(getUtente1().isAttivato());

        deleteAll();
    }

    @Test
    void doRetriveAll() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "@Gennaro1");
        UtenteDAO.doSave(getUtente2(), "@Gennaro1");
        LinkedList<Utente> list = UtenteDAO.doRetriveAll();
        LinkedList<Utente> list1 = new LinkedList();
        list1.add(getUtente());
        list1.add(getUtente2());
        assertEquals(list, list1);
        deleteAll();
    }

    @Test
    void doChangePassword() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "@Gennaro1");
        UtenteDAO.doChangePassword(getUtente(), "@Gennaro2");

        Utente u = UtenteDAO.doRetriveByEmailPassword(getUtente().getEmail(), "@Gennaro2");

        assertEquals(u, getUtente());
        deleteAll();
    }

    @Test
    void doRecuperaPassword() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "@Gennaro1");
        String newPass = UtenteDAO.doRecuperaPassword(getUtente().getEmail());
        Utente u = UtenteDAO.doRetriveByEmailPassword(getUtente().getEmail(), newPass);
        assertEquals(u, getUtente());
        deleteAll();
    }

    @Test
    void doActivate() throws DuplicateException, NoEntryException {
        String activationCode = UtenteDAO.doSave(getUtente(), "@Gennaro1");
        UtenteDAO.doActivate(getUtente().getEmail(), activationCode);
        Utente u = UtenteDAO.doRetriveByEmailPassword(getUtente().getEmail(), "@Gennaro1");

        assertTrue(u.isAttivato());
        deleteAll();
    }

    @Test
    void doRetriveSearch() throws NoEntryException, DuplicateException {
        UtenteDAO.doSave(getUtente(), "@Gennaro1");
        UtenteDAO.doSave(getUtente2(), "@Gennaro1");

        LinkedList<Utente> list = UtenteDAO.doRetriveSearch("Gennaro", "");
        LinkedList<Utente> list1 = new LinkedList();
        list1.add(getUtente());
        list1.add(getUtente2());
        assertEquals(list, list1);
        deleteAll();
    }


    private void deleteAll() {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE from utente where cod_fiscale = ? OR  cod_fiscale = ?");
            p.setString(1, getUtente().getCodFiscale());
            p.setString(2, getUtente2().getCodFiscale());
            p.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
