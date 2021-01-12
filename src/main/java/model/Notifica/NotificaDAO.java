package model.Notifica;

import lombok.Cleanup;
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
        try (Connection c = DB.getConnection()) {
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

    public static void doUpdate(Notifica n) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
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
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM notifica WHERE id = ?");
            p.setInt(1, n.getId());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Notifica doRetriveById(int id) throws NoEntryException {
        Notifica notifica = null;
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from notifica where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                notifica = new Notifica.NotificaBuilder()
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

    public LinkedList<Notifica> doRetriveAll() throws NoEntryException {
        LinkedList<Notifica> notifiche = new LinkedList<>();
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from notifica");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                notifiche.add(
                        new Notifica.NotificaBuilder()
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
