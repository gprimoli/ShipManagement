package model.Richiesta;

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

class RichiestaDAOTest {
    private static Richiesta getRichiesta() {
        Richiesta r = null;
        try {
            r = Richiesta.builder()
                    .codFiscaleUtente("RMLGNR99R27F839Q")
                    .tipoCarico("Container")
                    .quantita(5)
                    .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-01").getTime()))
                    .portoPartenza("A")
                    .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-31").getTime()))
                    .portoArrivo("B")
                    .stato("Disponibile")
                    .caricato(false)
                    .build();
        } catch (InvalidParameterException | ParseException e) {
            e.printStackTrace();
        }
        return r;
    }

    private static Richiesta getRichiesta1() {
        Richiesta r = null;
        try {
            r = Richiesta.builder()
                    .codFiscaleUtente("RMLGNR99R27F839A")
                    .tipoCarico("Carico alla Rinfusa")
                    .quantita(5)
                    .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-01").getTime()))
                    .portoPartenza("A")
                    .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-31").getTime()))
                    .portoArrivo("A")
                    .stato("In Lavorazione")
                    .caricato(false)
                    .build();
        } catch (InvalidParameterException | ParseException e) {
            e.printStackTrace();
        }
        return r;
    }

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

    @Test
    void doSave() throws DuplicateException {
        int id = RichiestaDAO.doSave(getRichiesta());
        Richiesta r = RichiestaDAO.doRetriveById(id);

        assertEquals(r.getCodFiscaleUtente(), getRichiesta().getCodFiscaleUtente());
        assertEquals(r.getTipoCarico(), getRichiesta().getTipoCarico());
        assertEquals(r.getQuantita(), getRichiesta().getQuantita());
        assertEquals(r.getDataPartenza(), getRichiesta().getDataPartenza());
        assertEquals(r.getPortoPartenza(), getRichiesta().getPortoPartenza());
        assertEquals(r.getDataArrivo(), getRichiesta().getDataArrivo());
        assertEquals(r.getPortoArrivo(), getRichiesta().getPortoArrivo());
        assertEquals(r.getStato(), getRichiesta().getStato());
        assertEquals(r.getDocumento(), getRichiesta().getDocumento());
        deleteAll();
    }

    @Test
    void doUpdate() throws DuplicateException, InvalidParameterException, ParseException {
        int id = RichiestaDAO.doSave(getRichiesta());
        Richiesta r = Richiesta.builder()
                .id(id)
                .codFiscaleUtente("RMLGNR99R27F839Q")
                .tipoCarico("Carico alla Rinfusa")
                .quantita(5)
                .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-01").getTime()))
                .portoPartenza("A")
                .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-31").getTime()))
                .portoArrivo("A")
                .stato("Disponibile")
                .caricato(false)
                .build();
        RichiestaDAO.doUpdate(r);
        Richiesta r1 = RichiestaDAO.doRetriveById(id);

        assertEquals(r.getCodFiscaleUtente(), r1.getCodFiscaleUtente());
        assertEquals(r.getTipoCarico(), r1.getTipoCarico());
        assertEquals(r.getQuantita(), r1.getQuantita());
        assertEquals(r.getDataPartenza(), r1.getDataPartenza());
        assertEquals(r.getPortoPartenza(), r1.getPortoPartenza());
        assertEquals(r.getDataArrivo(), r1.getDataArrivo());
        assertEquals(r.getPortoArrivo(), r1.getPortoArrivo());
        assertEquals(r.getStato(), r1.getStato());
        assertEquals(r.getDocumento(), r1.getDocumento());
        deleteAll();

    }

    @Test
    void doDelete() throws DuplicateException, NoEntryException, ParseException, InvalidParameterException {
        int id = RichiestaDAO.doSave(getRichiesta());

        Richiesta r = Richiesta.builder()
                .id(id)
                .codFiscaleUtente("RMLGNR99R27F839Q")
                .tipoCarico("Carico alla Rinfusa")
                .quantita(5)
                .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-01").getTime()))
                .portoPartenza("A")
                .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-31").getTime()))
                .portoArrivo("A")
                .stato("Disponibile")
                .caricato(false)
                .build();

        RichiestaDAO.doDelete(r);
        assertEquals(new LinkedList<Richiesta>(), RichiestaDAO.doRetriveAll());
        deleteAll();
    }

    @Test
    void doRetriveBy() throws DuplicateException, NoEntryException, InvalidParameterException, ParseException {
        Richiesta r = Richiesta.builder()
                .id(RichiestaDAO.doSave(getRichiesta()))
                .codFiscaleUtente("RMLGNR99R27F839Q")
                .tipoCarico("Container")
                .quantita(5)
                .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-01").getTime()))
                .portoPartenza("A")
                .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-31").getTime()))
                .portoArrivo("B")
                .stato("Disponibile")
                .caricato(false)
                .build();
        LinkedList<Richiesta> list = new LinkedList<>();
        list.add(r);
        assertEquals(list, RichiestaDAO.doRetriveBy(getUtente()));
        deleteAll();
    }

    @Test
    void doRetriveAllDisponibili() throws NoEntryException, DuplicateException, InvalidParameterException, ParseException {
        int id = RichiestaDAO.doSave(getRichiesta());

        Richiesta r = Richiesta.builder()
                .id(id)
                .codFiscaleUtente("RMLGNR99R27F839Q")
                .tipoCarico("Container")
                .quantita(5)
                .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-01").getTime()))
                .portoPartenza("A")
                .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-31").getTime()))
                .portoArrivo("B")
                .stato("Disponibile")
                .caricato(false)
                .build();
        LinkedList<Richiesta> list = new LinkedList<>();
        list.add(r);
        assertEquals(list, RichiestaDAO.doRetriveBy(getUtente()));
        deleteAll();
    }

    private void deleteAll() {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE from richiesta where cod_fiscale_utente = ? OR  cod_fiscale_utente = ? ");
            p.setString(1, getRichiesta().getCodFiscaleUtente());
            p.setString(2, getRichiesta1().getCodFiscaleUtente());
            p.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
