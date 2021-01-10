package model.Porto;

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

public class PortoDAO {
    public static void doSave(Porto porto, int id_area) throws DuplicateException {
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO porto(localcode, nome, id_area) VALUES (?,?,?);");
            p.setString(1, porto.getLocalcode());
            p.setString(2, porto.getNome());
            p.setInt(3, id_area);
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Porto porto, int id_area) throws DuplicateException {
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE porto SET nome = ?, id_area = ? WHERE localcode = ?");
            p.setString(1, porto.getNome());
            p.setInt(2, id_area);
            p.setString(3, porto.getLocalcode());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Porto porto) {
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM porto WHERE localcode = ?");
            p.setString(1, porto.getLocalcode());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Porto doRetriveByLocalCode(String localCode) throws NoEntryException {
        Porto porto = null;
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from porto where localcode = ?;");
            p.setString(1, localCode);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                porto = new Porto.PortoBuilder()
                        .localcode(r.getString("localcode"))
                        .nome(r.getString("nome"))
                        .idArea(r.getInt("id_area"))
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
        return porto;
    }

    public LinkedList<Porto> doRetriveAll() throws NoEntryException {
        LinkedList<Porto> porti = new LinkedList<>();
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from porto");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                porti.add(
                        new Porto.PortoBuilder()
                                .localcode(r.getString("localcode"))
                                .nome(r.getString("nome"))
                                .idArea(r.getInt("id_area"))
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
        return porti;
    }
}
