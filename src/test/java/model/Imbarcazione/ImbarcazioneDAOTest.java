package model.Imbarcazione;

import lombok.Cleanup;
import model.Utente.Utente;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ImbarcazioneDAOTest {
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

    private static Imbarcazione getImbarcazione() {
        Imbarcazione i = null;
        try {
            i = Imbarcazione.builder()
                    .nome("Pinta")
                    .imo("ABC")
                    .codFiscaleUtente("RMLGNR99R27F839Q")
                    .quantitaMax(500)
                    .bandiera("IT")
                    .annoCostruzione(2021)
                    .lunghezza(150)
                    .ampiezza(50)
                    .altezza(10)
                    .documento(null)
                    .caricato(false)
                    .tipologia("Portacontainer")
                    .build();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static Imbarcazione getImbarcazione1() {
        Imbarcazione i = null;
        try {
            i = Imbarcazione.builder()
                    .nome("Pinta")
                    .imo("ABC")
                    .codFiscaleUtente("RMLGNR99R27F839A")
                    .quantitaMax(500)
                    .bandiera("IT")
                    .annoCostruzione(2021)
                    .lunghezza(150)
                    .ampiezza(50)
                    .altezza(10)
                    .documento(null)
                    .caricato(false)
                    .tipologia("Portacontainer")
                    .build();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        return i;
    }

    @Test
    void doSave() throws DuplicateException, NoEntryException, InvalidParameterException {
        int id = ImbarcazioneDAO.doSave(getImbarcazione());
        Imbarcazione i = ImbarcazioneDAO.doRetriveById(id);
        Imbarcazione im = Imbarcazione.builder()
                .id(id)
                .nome("Pinta")
                .imo("ABC")
                .codFiscaleUtente("RMLGNR99R27F839Q")
                .quantitaMax(500)
                .bandiera("IT")
                .annoCostruzione(2021)
                .lunghezza(150)
                .ampiezza(50)
                .altezza(10)
                .documento(null)
                .caricato(false)
                .tipologia("Portacontainer")
                .posizione(i.getPosizione())
                .disponibile(true)
                .build();

        assertEquals(i, im);
        deleteAll();
    }

    @Test
    void doCheckIMO() throws DuplicateException {
        ImbarcazioneDAO.doSave(getImbarcazione());
        assertTrue(ImbarcazioneDAO.doCheckIMO(getImbarcazione()));
        deleteAll();
    }

    @Test
    void doUpdate() throws InvalidParameterException, DuplicateException, NoEntryException {
        int id = ImbarcazioneDAO.doSave(getImbarcazione());
        Imbarcazione im = Imbarcazione.builder()
                .id(id)
                .nome("Pinta")
                .imo("ABC")
                .codFiscaleUtente("RMLGNR99R27F839Q")
                .quantitaMax(500)
                .bandiera("IT")
                .annoCostruzione(2021)
                .lunghezza(150)
                .ampiezza(50)
                .altezza(10)
                .documento(null)
                .caricato(false)
                .tipologia("Portacontainer")
                .posizione(2)
                .disponibile(true)
                .build();

        ImbarcazioneDAO.doUpdate(im);
        Imbarcazione i = ImbarcazioneDAO.doRetriveById(id);
        assertEquals(i, im);
        deleteAll();
    }

    @Test
    void doDisponibileIndisponibile() throws DuplicateException, NoEntryException {
        int id = ImbarcazioneDAO.doSave(getImbarcazione());
        ImbarcazioneDAO.doDisponibileIndisponibile(ImbarcazioneDAO.doRetriveById(id));
        assertFalse(ImbarcazioneDAO.doRetriveById(id).isDisponibile());
        deleteAll();
    }

    @Test
    void doRetriveAll() throws DuplicateException, NoEntryException {
        int id = ImbarcazioneDAO.doSave(getImbarcazione());
        int id2 = ImbarcazioneDAO.doSave(getImbarcazione1());
        Imbarcazione i = ImbarcazioneDAO.doRetriveById(id);
        Imbarcazione i2 = ImbarcazioneDAO.doRetriveById(id2);
        LinkedList<Imbarcazione> list = new LinkedList<>();
        list.add(i);
        list.add(i2);

        assertEquals(list, ImbarcazioneDAO.doRetriveAll());
        deleteAll();
    }

    @Test
    void doRetriveBy() throws DuplicateException, NoEntryException {
        int id = ImbarcazioneDAO.doSave(getImbarcazione());
        LinkedList<Imbarcazione> u = new LinkedList<>();
        u.add(ImbarcazioneDAO.doRetriveById(id));
        assertEquals(u, ImbarcazioneDAO.doRetriveBy(getUtente()));
        deleteAll();
    }

    @Test
    void doRetriveAllDisponibili() throws DuplicateException, NoEntryException {
        int id = ImbarcazioneDAO.doSave(getImbarcazione());
        LinkedList<Imbarcazione> u = new LinkedList<>();
        u.add(ImbarcazioneDAO.doRetriveById(id));
        assertEquals(u, ImbarcazioneDAO.doRetriveAll());
        deleteAll();
    }

    private void deleteAll() {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE from imbarcazione where cod_fiscale_utente = ? OR  cod_fiscale_utente = ?");
            p.setString(1, getImbarcazione().getCodFiscaleUtente());
            p.setString(2, getImbarcazione1().getCodFiscaleUtente());
            p.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
