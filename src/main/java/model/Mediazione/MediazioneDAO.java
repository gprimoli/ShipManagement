package model.Mediazione;

import lombok.Cleanup;
import model.Utente.Utente;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import java.sql.*;
import java.util.LinkedList;

public class MediazioneDAO {

    public static void doSave(Mediazione m) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
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
        try {
            @Cleanup Connection c = DB.getConnection();
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
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM mediazione WHERE id = ?");
            p.setInt(1, m.getId());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mediazione doRetriveById(int id) throws NoEntryException {
        Mediazione mediazione = null;
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, contratto IS NOT NULL as caricato from mediazione where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                mediazione = Mediazione.builder()
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

    public static LinkedList<Mediazione> doRetriveAll() throws NoEntryException {
        LinkedList<Mediazione> mediazioni = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, contratto IS NOT NULL as caricato from mediazione;");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                mediazioni.add(
                        Mediazione.builder()
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
        try {
            @Cleanup Connection c = DB.getConnection();
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

    public static LinkedList<Mediazione> doRetriveBy(Utente u) throws NoEntryException {
        LinkedList<Mediazione> mediazioni = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = null;
            if (u.isBroker())
                p = c.prepareStatement("Select *, contratto IS NOT NULL as caricato from mediazione where cod_fiscale_utente = ?;");
            else if (u.isArmatore())
                p = c.prepareStatement("SELECT m.id, m.nome, m.stato, m.contratto IS NOT NULL as caricato, m.cod_fiscale_utente from imbarcazione as i JOIN mediazione_imbarcazione as mi on i.cod_fiscale_utente = ? AND i.imo = mi.imo_imbarcazione JOIN mediazione as m on mi.id_mediazione = m.id;");
            else //if (u.isCliente())
                p = c.prepareStatement("SELECT m.id, m.nome, m.stato, m.contratto IS NOT NULL as caricato, m.cod_fiscale_utente from richiesta as r JOIN mediazione_richiesta as mr on r.cod_fiscale_utente = ? AND r.id = mr.id_richiesta JOIN mediazione as m on mr.id_mediazione = m.id;");

            p.setString(1, u.getCodFiscale());

            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                mediazioni.add(
                        Mediazione.builder()
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

}
