package model.Notifica;

import lombok.Cleanup;
import model.Imbarcazione.Imbarcazione;
import model.Mediazione.Mediazione;
import model.Richiesta.Richiesta;
import model.Utente.Utente;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class NotificaDAO {


    public static int doSave(Notifica n) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO notifica(oggetto, corpo) VALUES (?,?);");
            p.setString(1, n.getOggetto());
            p.setString(2, n.getCorpo());
            p.execute();

            p = c.prepareStatement("SELECT MAX(id) from notifica where oggetto = ? AND corpo = ?");
            p.setString(1, n.getOggetto());
            p.setString(2, n.getCorpo());
            @Cleanup ResultSet rs = p.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }


    public static void doSendToBroker(Imbarcazione i, int idNotifica) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT m.cod_fiscale_utente from mediazione as m, mediazione_imbarcazione as mi WHERE m.id = mi.id_mediazione AND mi.id_imbarcazione = ?");
            p.setInt(1, i.getId());
            @Cleanup ResultSet rs = p.executeQuery();
            while (rs.next()) {
                p = c.prepareStatement("INSERT into notifica_utente(id_notifica, cod_fiscale_utente) VALUES (?,?)");
                p.setInt(1, idNotifica);
                p.setString(2, rs.getString(1));
                p.execute();
            }
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doSendToPropietario(Imbarcazione i, int idNotifica) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT into notifica_utente(id_notifica, cod_fiscale_utente) VALUES (?,?)");
            p.setInt(1, idNotifica);
            p.setString(2, i.getCodFiscaleUtente());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doSendToBroker(Richiesta r, int idNotifica) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT r.cod_fiscale_utente from richiesta as r, mediazione_richiesta as mr WHERE r.id = mr.id_mediazione AND mr.id_richiesta = ?");
            p.setInt(1, r.getId());
            @Cleanup ResultSet rs = p.executeQuery();
            while (rs.next()) {
                p = c.prepareStatement("INSERT into notifica_utente(id_notifica, cod_fiscale_utente) VALUES (?,?)");
                p.setInt(1, idNotifica);
                p.setString(2, rs.getString(1));
                p.execute();
            }

        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doSendToPropietario(Richiesta r, int idNotifica) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT into notifica_utente(id_notifica, cod_fiscale_utente) VALUES (?,?)");
            p.setInt(1, idNotifica);
            p.setString(2, r.getCodFiscaleUtente());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doSendToBroker(Mediazione m, int idNotifica) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT into notifica_utente(id_notifica, cod_fiscale_utente) VALUES (?,?)");
            p.setInt(1, idNotifica);
            p.setString(2, m.getCodFiscaleUtente());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Notifica n) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE notifica SET oggetto = ?, corpo = ? WHERE id = ?");
            p.setString(1, n.getOggetto());
            p.setString(2, n.getCorpo());
            p.setInt(3, n.getId());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Notifica n) {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM notifica WHERE id = ?");
            p.setInt(1, n.getId());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Notifica doRetriveById(int id) throws NoEntryException {
        Notifica notifica = null;
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from notifica where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                notifica = Notifica.builder()
                        .id(r.getInt("id"))
                        .oggetto(r.getString("oggetto"))
                        .corpo(r.getString("corpo"))
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
        return notifica;
    }

    public static LinkedList<Notifica> doRetriveAll() throws NoEntryException {
        LinkedList<Notifica> notifiche = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from notifica");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                notifiche.add(
                        Notifica.builder()
                                .id(r.getInt("id"))
                                .oggetto(r.getString("oggetto"))
                                .corpo(r.getString("corpo"))
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
        return notifiche;
    }

    public static LinkedList<Notifica> doRetriveBy(Utente u) throws NoEntryException {
        LinkedList<Notifica> notifiche = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select n.id, n.corpo, n.oggetto from (select * from notifica_utente where cod_fiscale_utente = ?) as nu, notifica n where nu.id_notifica = n.id;");
            p.setString(1, u.getCodFiscale());
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                notifiche.add(
                        Notifica.builder()
                                .id(r.getInt("id"))
                                .oggetto(r.getString("oggetto"))
                                .corpo(r.getString("corpo"))
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
        return notifiche;
    }

}
