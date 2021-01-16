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

    public static void doSave(Notifica n) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO notifica(oggetto, corpo) VALUES (?,?);");
            p.setString(1, n.getOggetto());
            p.setString(2, n.getCorpo());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doSaveAll(Mediazione m, Notifica n) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO notifica(oggetto, corpo) VALUES (?,?);");
            p.setString(1, n.getOggetto());
            p.setString(2, n.getCorpo());
            p.execute();

            p = c.prepareStatement("SELECT MAX(id) from notifica where oggetto = ?");
            p.setString(1, n.getOggetto());
            @Cleanup ResultSet rs = p.executeQuery();
            int idNotifica = 0;
            while (rs.next()){
                idNotifica = rs.getInt(1);
            }

            p = c.prepareStatement("SELECT * from mediazione_richiesta as mr JOIN richiesta as r on mr.id_richiesta = r.id where mr.id_mediazione = ?");
            p.setInt(1, m.getId());
            rs = p.executeQuery();
            while (rs.next()){
                p = c.prepareStatement("Insert INTO notifica_utente(id_notifica, cod_fiscale_utente) values (?,?)");
                p.setInt(1, idNotifica);
                p.setString(2, rs.getString("cod_fiscale_utente"));
                p.execute();
            }

            p = c.prepareStatement("SELECT * from mediazione_imbarcazione as mi JOIN imbarcazione as i on mi.imo_imbarcazione = i.imo where mi.id_mediazione = ?");
            p.setInt(1, m.getId());
            rs = p.executeQuery();
            while (rs.next()){
                p = c.prepareStatement("Insert INTO notifica_utente(id_notifica, cod_fiscale_utente) values (?,?)");
                p.setInt(1, idNotifica);
                p.setString(2, rs.getString("cod_fiscale_utente"));
                p.execute();
            }

        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doSaveAll(Richiesta m, Notifica n) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO notifica(oggetto, corpo) VALUES (?,?);");
            p.setString(1, n.getOggetto());
            p.setString(2, n.getCorpo());
            p.execute();

            p = c.prepareStatement("SELECT MAX(id) from notifica where oggetto = ?");
            p.setString(1, n.getOggetto());
            @Cleanup ResultSet rs = p.executeQuery();
            int idNotifica = 0;
            while (rs.next()){
                idNotifica = rs.getInt(1);
            }

            p = c.prepareStatement("SELECT mediazione.cod_fiscale_utente from (SELECT mr.id_mediazione from (SELECT id from richiesta where richiesta.id = ?) as r, mediazione_richiesta as mr where mr.id_richiesta = r.id) as mk, mediazione where mk.id_mediazione = mediazione.id");
            p.setInt(1, m.getId());
            rs = p.executeQuery();
            while (rs.next()){
                p = c.prepareStatement("Insert INTO notifica_utente(id_notifica, cod_fiscale_utente) values (?,?)");
                p.setInt(1, idNotifica);
                p.setString(2, rs.getString("cod_fiscale_utente"));
                p.execute();
            }
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doSaveAll(Imbarcazione m, Notifica n) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO notifica(oggetto, corpo) VALUES (?,?);");
            p.setString(1, n.getOggetto());
            p.setString(2, n.getCorpo());
            p.execute();

            p = c.prepareStatement("SELECT MAX(id) from notifica where oggetto = ?");
            p.setString(1, n.getOggetto());
            @Cleanup ResultSet rs = p.executeQuery();
            int idNotifica = 0;
            while (rs.next()){
                idNotifica = rs.getInt(1);
            }

            p = c.prepareStatement("SELECT mediazione.cod_fiscale_utente from (SELECT mi.id_mediazione from (SELECT imo from imbarcazione where imbarcazione.imo = ?) as r, mediazione_imbarcazione as mi where mi.imo_imbarcazione = r.imo) as mk, mediazione where mk.id_mediazione = mediazione.id");
            p.setString(1, m.getImo());
            rs = p.executeQuery();
            while (rs.next()){
                p = c.prepareStatement("Insert INTO notifica_utente(id_notifica, cod_fiscale_utente) values (?,?)");
                p.setInt(1, idNotifica);
                p.setString(2, rs.getString("cod_fiscale_utente"));
                p.execute();
            }
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

    public static LinkedList<Notifica> doRetriveBy(Utente u) throws NoEntryException{
        LinkedList<Notifica> notifiche = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select n.id, n.corpo, n.oggetto from notifica as n JOIN notifica_utente on cod_fiscale_utente = ?");
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
