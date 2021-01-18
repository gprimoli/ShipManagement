package model.Imbarcazione;

import lombok.Cleanup;
import model.Utente.Utente;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.Random;

public class ImbarcazioneDAO {

    public static int doSave(Imbarcazione i) throws DuplicateException {
        try {
            Random r = new Random();
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO imbarcazione(cod_fiscale_utente, imo, nome, tipologia, anno_costruzione, bandiera, quantita_max, lunghezza_fuori_tutto, ampiezza, altezza, posizione, documento) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
            p.setString(1, i.getCodFiscaleUtente());
            p.setString(2, i.getImo());
            p.setString(3, i.getNome());
            p.setString(4, i.getTipologia());
            p.setInt(5, i.getAnnoCostruzione());
            p.setString(6, i.getBandiera());
            p.setFloat(7, i.getQuantitaMax());
            p.setFloat(8, i.getLunghezza());
            p.setFloat(9, i.getAmpiezza());
            p.setFloat(10, i.getAltezza());
            p.setInt(11, r.nextInt(8) + 1); //API marin traffic
            p.setBinaryStream(12, i.getDocumento());
            p.execute();

            p = c.prepareStatement("SELECT MAX(id) from imbarcazione where cod_fiscale_utente = ?;");
            p.setString(1, i.getCodFiscaleUtente());
            @Cleanup ResultSet rs = p.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static boolean doCheckIMO(Imbarcazione i) {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT * from imbarcazione where imo = ?;");
            p.setString(1, i.getImo());
            @Cleanup ResultSet r = p.executeQuery();
            return r.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Imbarcazione i) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE imbarcazione SET cod_fiscale_utente = ?, nome = ?, tipologia = ?, anno_costruzione = ?, bandiera = ?, quantita_max = ?, lunghezza_fuori_tutto = ?, ampiezza = ?, altezza = ?, posizione = ?, documento = ? WHERE id = ?");
            p.setString(1, i.getCodFiscaleUtente());
            p.setString(2, i.getNome());
            p.setString(3, i.getTipologia());
            p.setInt(4, i.getAnnoCostruzione());
            p.setString(5, i.getBandiera());
            p.setFloat(6, i.getQuantitaMax());
            p.setFloat(7, i.getLunghezza());
            p.setFloat(8, i.getAmpiezza());
            p.setFloat(9, i.getAltezza());
            p.setInt(10, i.getPosizione());
            p.setBlob(11, i.getDocumento());
            p.setInt(12, i.getId());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doDisponibileIndisponibile(Imbarcazione i) {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE imbarcazione SET disponibile = ? WHERE id = ?");
            p.setBoolean(1, !i.isDisponibile());
            p.setInt(2, i.getId());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Imbarcazione doRetriveById(int id) throws NoEntryException {
        Imbarcazione imbarcazione = null;
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, documento IS NOT NULL as caricato from imbarcazione where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                imbarcazione = Imbarcazione.builder()
                        .id(r.getInt("id"))
                        .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                        .imo(r.getString("imo"))
                        .nome(r.getString("nome"))
                        .tipologia(r.getString("tipologia"))
                        .annoCostruzione(r.getInt("anno_costruzione"))
                        .bandiera(r.getString("bandiera"))
                        .quantitaMax(r.getFloat("quantita_max"))
                        .lunghezza(r.getFloat("lunghezza_fuori_tutto"))
                        .ampiezza(r.getFloat("ampiezza"))
                        .altezza(r.getFloat("altezza"))
                        .posizione(r.getInt("posizione"))
                        .disponibile(r.getBoolean("disponibile"))
                        .caricato(r.getBoolean("caricato"))
                        .trasferito(r.getBoolean("trasferito"))
                        .build();
            }
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                throw new NoEntryException();
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("database compromesso");
            e.printStackTrace();
        }
        return imbarcazione;
    }

    public static LinkedList<Imbarcazione> doRetriveAll() throws NoEntryException {
        LinkedList<Imbarcazione> imbarcazioni = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, documento IS NOT NULL as caricato from imbarcazione;");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                imbarcazioni.add(
                        Imbarcazione.builder()
                                .id(r.getInt("id"))
                                .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                                .imo(r.getString("imo"))
                                .nome(r.getString("nome"))
                                .tipologia(r.getString("tipologia"))
                                .annoCostruzione(r.getInt("anno_costruzione"))
                                .bandiera(r.getString("bandiera"))
                                .quantitaMax(r.getFloat("quantita_max"))
                                .lunghezza(r.getFloat("lunghezza_fuori_tutto"))
                                .ampiezza(r.getFloat("ampiezza"))
                                .altezza(r.getFloat("altezza"))
                                .posizione(r.getInt("posizione"))
                                .disponibile(r.getBoolean("disponibile"))
                                .caricato(r.getBoolean("caricato"))
                                .trasferito(r.getBoolean("trasferito"))
                                .build()
                );
            }
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                throw new NoEntryException();
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("database compromesso");
            e.printStackTrace();
        }
        return imbarcazioni;
    }


    //fine base

    public static InputStream doRetriveDocumento(int id) {
        InputStream d;
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT documento FROM imbarcazione where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            r.next();
            d = r.getBinaryStream("documento");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return d;
    }

    public static LinkedList<Imbarcazione> doRetriveBy(Utente u) throws NoEntryException {
        LinkedList<Imbarcazione> imbarcazioni = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, documento IS NOT NULL as caricato from imbarcazione where cod_fiscale_utente = ? && trasferito = false;");
            p.setString(1, u.getCodFiscale());
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                imbarcazioni.add(
                        Imbarcazione.builder()
                                .id(r.getInt("id"))
                                .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                                .imo(r.getString("imo"))
                                .nome(r.getString("nome"))
                                .tipologia(r.getString("tipologia"))
                                .annoCostruzione(r.getInt("anno_costruzione"))
                                .bandiera(r.getString("bandiera"))
                                .quantitaMax(r.getFloat("quantita_max"))
                                .lunghezza(r.getFloat("lunghezza_fuori_tutto"))
                                .ampiezza(r.getFloat("ampiezza"))
                                .altezza(r.getFloat("altezza"))
                                .posizione(r.getInt("posizione"))
                                .disponibile(r.getBoolean("disponibile"))
                                .caricato(r.getBoolean("caricato"))
                                .trasferito(r.getBoolean("trasferito"))
                                .build()
                );
            }
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                throw new NoEntryException();
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("database compromesso");
            e.printStackTrace();
        }
        return imbarcazioni;
    }

    public static LinkedList<Imbarcazione> doRetriveAllDisponibili() throws NoEntryException {
        LinkedList<Imbarcazione> imbarcazioni = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, documento IS NOT NULL as caricato from imbarcazione where disponibile = true");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                imbarcazioni.add(
                        Imbarcazione.builder()
                                .id(r.getInt("id"))
                                .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                                .imo(r.getString("imo"))
                                .nome(r.getString("nome"))
                                .tipologia(r.getString("tipologia"))
                                .annoCostruzione(r.getInt("anno_costruzione"))
                                .bandiera(r.getString("bandiera"))
                                .quantitaMax(r.getFloat("quantita_max"))
                                .lunghezza(r.getFloat("lunghezza_fuori_tutto"))
                                .ampiezza(r.getFloat("ampiezza"))
                                .altezza(r.getFloat("altezza"))
                                .posizione(r.getInt("posizione"))
                                .disponibile(r.getBoolean("disponibile"))
                                .caricato(r.getBoolean("caricato"))
                                .trasferito(r.getBoolean("trasferito"))
                                .build()
                );
            }
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                throw new NoEntryException();
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("database compromesso");
            e.printStackTrace();
        }
        return imbarcazioni;
    }

}
