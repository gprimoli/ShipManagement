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
                    .codFiscale("SLCNDR80A05F912A")
                    .nome("Albe")
                    .cognome("Rosso")
                    .dataNascita(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("1996-01-17").getTime()))
                    .luogoNascita("Pozzuoli")
                    .email("albertoRossi@gmail.com")
                    .telefono("33316590328")
                    .attivato(false)
                    .ruolo("armatore")
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
                    .codFiscale("SLCNDR80A05F912A")
                    .nome("Albe")
                    .cognome("Rosso")
                    .dataNascita(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("1996-01-17").getTime()))
                    .luogoNascita("Pozzuoli")
                    .email("albertoRossi@gmail.com")
                    .telefono("33316590328")
                    .attivato(false)
                    .ruolo("armatore")
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
                    .codFiscale("SLCNDR80A05F912A")
                    .nome("Albe")
                    .cognome("Rosso")
                    .dataNascita(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("1996-01-17").getTime()))
                    .luogoNascita("Pozzuoli")
                    .email("albertoRossi@gmail.com")
                    .telefono("33316590328")
                    .attivato(false)
                    .ruolo("armatore")
                    .build();
        } catch (InvalidParameterException | ParseException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Test
    void doSave() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "Armatore1!");
        Utente u1 = UtenteDAO.doRetriveByCodFiscale("SLCNDR80A05F912A");
        assertEquals(getUtente(), u1);
        deleteAll();
    }

    @Test
    void doUpdate() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "Armatore1!");
        UtenteDAO.doUpdate(getUtente1());
        Utente u1 = UtenteDAO.doRetriveByCodFiscale("SLCNDR80A05F912A");
        assertEquals(getUtente1(), u1);
        deleteAll();
    }

    @Test
    void doDelete() throws DuplicateException, MediazioniInCorsoException {
        UtenteDAO.doSave(getUtente(), "Armatore1!");
        UtenteDAO.doDelete(getUtente());

        assertFalse(getUtente1().isAttivato());

        deleteAll();
    }

    @Test
    void doRetriveAll() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "Armatore1!");
        UtenteDAO.doSave(getUtente2(), "Armatore1!");
        LinkedList<Utente> list = UtenteDAO.doRetriveAll();
        LinkedList<Utente> list1 = new LinkedList();
        list1.add(getUtente());
        list1.add(getUtente2());
        assertEquals(list, list1);
        deleteAll();
    }

    @Test
    void doChangePassword() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "Username1!");
        UtenteDAO.doChangePassword(getUtente(), "Armatore1!");

        Utente u = UtenteDAO.doRetriveByEmailPassword(getUtente().getEmail(), "Armatore1!");

        assertEquals(u, getUtente());
        deleteAll();
    }

    @Test
    void doRecuperaPassword() throws DuplicateException, NoEntryException {
        UtenteDAO.doSave(getUtente(), "Armatore1!");
        String newPass = UtenteDAO.doRecuperaPassword(getUtente().getEmail());
        Utente u = UtenteDAO.doRetriveByEmailPassword(getUtente().getEmail(), newPass);
        assertEquals(u, getUtente());
        deleteAll();
    }

    @Test
    void doActivate() throws DuplicateException, NoEntryException {
        String activationCode = UtenteDAO.doSave(getUtente(), "Armatore1!");
        UtenteDAO.doActivate(getUtente().getEmail(), activationCode);
        Utente u = UtenteDAO.doRetriveByEmailPassword(getUtente().getEmail(), "@Gennaro1");

        assertTrue(u.isAttivato());
        deleteAll();
    }

    @Test
    void doRetriveSearch() throws NoEntryException, DuplicateException {
        UtenteDAO.doSave(getUtente(), "Armatore1!");
        UtenteDAO.doSave(getUtente2(), "Armatore1!");

        LinkedList<Utente> list = UtenteDAO.doRetriveSearch("Albe", "Rosso");
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
