package model.Imbarcazione;

import lombok.Cleanup;
import model.Mediazione.Mediazione;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import java.sql.*;
import java.util.LinkedList;

public class ImbarcazioneDAO {

    public static void doSave(Imbarcazione i) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO imbarcazione(cod_fiscale_utente, imo, nome, tipologia, anno_costruzione, bandiera, quantita_max, lunghezza_fuori_tutto, ampiezza, altezza, posizione, disponibile, documento) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
            p.setString(1, i.getCodFiscaleUtente());
            p.setString(2, i.getImo());
            p.setString(3, i.getNome());
            p.setString(4, i.getTipologia());
            p.setInt(5, i.getAnnoCostruzione());
            p.setString(6, i.getBandiera());
            p.setFloat(7, i.getQauntitaMax());
            p.setFloat(8, i.getLughezza());
            p.setFloat(9, i.getAmpiezza());
            p.setFloat(10, i.getAltezza());
            p.setInt(11, i.getPosizione());
            p.setBoolean(12, i.isDisponibile());
            p.setBlob(13, i.getDocumento());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Imbarcazione i) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE imbarcazione SET cod_fiscale_utente = ?, nome = ?, tipologia = ?, anno_costruzione = ?, bandiera = ?, quantita_max = ?, lunghezza_fuori_tutto = ?, ampiezza = ?, altezza = ?, posizione = ?, disponibile = ?, documento = ? WHERE imo = ?");
            p.setString(1, i.getCodFiscaleUtente());
            p.setString(2, i.getNome());
            p.setString(3, i.getTipologia());
            p.setInt(4, i.getAnnoCostruzione());
            p.setString(5, i.getBandiera());
            p.setFloat(6, i.getQauntitaMax());
            p.setFloat(7, i.getLughezza());
            p.setFloat(8, i.getAmpiezza());
            p.setFloat(9, i.getAltezza());
            p.setInt(10, i.getPosizione());
            p.setBoolean(11, i.isDisponibile());
            p.setBlob(12, i.getDocumento());
            p.setString(13, i.getImo());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Imbarcazione i) {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM imbarcazione WHERE imo = ?");
            p.setString(1, i.getImo());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Imbarcazione doRetriveById(String imo) throws NoEntryException {
        Imbarcazione imbarcazione = null;
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, documento IS NOT NULL as caricato from imbarcazione where imo = ?;");
            p.setString(1, imo);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                imbarcazione = new Imbarcazione.ImbarcazioneBuilder()
                        .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                        .imo(r.getString("imo"))
                        .nome(r.getString("nome"))
                        .tipologia(r.getString("tipologia"))
                        .annoCostruzione(r.getInt("anno_costruzione"))
                        .bandiera(r.getString("bandiera"))
                        .qauntitaMax(r.getFloat("quantita_max"))
                        .lughezza(r.getFloat("lunghezza_fuori_tutto"))
                        .ampiezza(r.getFloat("ampiezza"))
                        .altezza(r.getFloat("altezza"))
                        .posizione(r.getInt("posizione"))
                        .disponibile(r.getBoolean("disponibile"))
                        .caricato(r.getBoolean("caricato"))
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

    public LinkedList<Imbarcazione> doRetriveAll() throws NoEntryException {
        LinkedList<Imbarcazione> imbarcazioni = new LinkedList<>();
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, contratto IS NOT NULL as caricato from mediazione;");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                imbarcazioni.add(
                        new Imbarcazione.ImbarcazioneBuilder()
                                .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                                .imo(r.getString("imo"))
                                .nome(r.getString("nome"))
                                .tipologia(r.getString("tipologia"))
                                .annoCostruzione(r.getInt("anno_costruzione"))
                                .bandiera(r.getString("bandiera"))
                                .qauntitaMax(r.getFloat("quantita_max"))
                                .lughezza(r.getFloat("lunghezza_fuori_tutto"))
                                .ampiezza(r.getFloat("ampiezza"))
                                .altezza(r.getFloat("altezza"))
                                .posizione(r.getInt("posizione"))
                                .disponibile(r.getBoolean("disponibile"))
                                .caricato(r.getBoolean("caricato"))
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

    public static Blob doRetriveDocumento(String imo) {
        Blob d;
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT documento FROM imbarcazione where imo = ?;");
            p.setString(1, imo);
            @Cleanup ResultSet r = p.executeQuery();
            r.next();
            d = r.getBlob("documento");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return d;
    }
}
