package model.Mediazione;

import lombok.Cleanup;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import java.sql.*;
import java.util.LinkedList;

public class MediazioneDAO {

    public static void doSave(Mediazione m) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO mediazione(nome, stato, contratto, cod_fiscale_utente) VALUES (?,?,?,?);");
            p.setString(1, m.getNome());
            p.setString(2, m.getStato());
            p.setBlob(3, m.getContratto());
            p.setString(4, m.getCodFiscaleUtente());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Mediazione m) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE mediazione SET nome = ?, stato = ?, contratto = ?, cod_fiscale_utente = ? WHERE id = ?");
            p.setString(1, m.getNome());
            p.setString(2, m.getStato());
            p.setBlob(3, m.getContratto());
            p.setString(4, m.getCodFiscaleUtente());
            p.setInt(5, m.getId());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Mediazione m) {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM mediazione WHERE id = ?");
            p.setInt(1, m.getId());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Mediazione doRetriveById(int id) throws NoEntryException {
        Mediazione mediazione = null;
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, contratto IS NOT NULL as caricato from mediazione where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                mediazione = new Mediazione.MediazioneBuilder()
                        .id(r.getInt("id"))
                        .nome(r.getString("nome"))
                        .stato(r.getString("stato"))
                        .caricato(r.getBoolean("caricato"))
                        .codFiscaleUtente(r.getString("cod_fiscale_utente"))
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
        return mediazione;
    }

    public LinkedList<Mediazione> doRetriveAll() throws NoEntryException {
        LinkedList<Mediazione> mediazioni = new LinkedList<>();
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, contratto IS NOT NULL as caricato from mediazione;");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                mediazioni.add(
                        new Mediazione.MediazioneBuilder()
                                .id(r.getInt("id"))
                                .nome(r.getString("nome"))
                                .stato(r.getString("stato"))
                                .caricato(r.getBoolean("caricato"))
                                .codFiscaleUtente(r.getString("cod_fiscale_utente"))
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
        return mediazioni;
    }


    //fine base

    public static Blob doRetriveDocumento(int id) {
        Blob d;
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT contratto FROM mediazione where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            r.next();
            d = r.getBlob("contratto");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return d;
    }
}
