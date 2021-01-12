package model.Richiesta;

import lombok.Cleanup;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import java.sql.*;
import java.util.LinkedList;

public class RichiestaDAO {
    public static void doSave(Richiesta r, String codiceFiscale, String pPartenza, String pArrivo) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO richiesta(cod_fiscale_utente, tipo_carico, quantita, data_partenza, porto_partenza, data_arrivo, porto_arrivo, documento) VALUES (?,?,?,?,?,?,?,?);");
            p.setString(1, codiceFiscale);
            p.setString(2, r.getTipoCarico());
            p.setFloat(3, r.getQuantita());
            p.setDate(4, r.getDataPartenza());
            p.setString(5, pPartenza);
            p.setDate(6, r.getDataArrivo());
            p.setString(7, pArrivo);
            p.setBlob(8, r.getDocumento());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Richiesta r, String pPartenza, String pArrivo) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE richiesta SET tipo_carico = ?, quantita = ?, data_partenza = ?, porto_partenza = ?, data_arrivo = ?, porto_arrivo = ?, documento = ? WHERE id = ?");
            p.setString(1, r.getTipoCarico());
            p.setFloat(2, r.getQuantita());
            p.setDate(3, r.getDataPartenza());
            p.setString(4, pPartenza);
            p.setDate(5, r.getDataArrivo());
            p.setString(6, pArrivo);
            p.setBlob(7, r.getDocumento());
            p.setInt(8, r.getId());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Richiesta r) {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM richiesta WHERE id = ?");
            p.setInt(1, r.getId());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Richiesta doRetriveById(int id) {
        Richiesta tmp = null;
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT *, documento IS NOT NULL as caricato FROM richiesta where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            r.next();
            tmp = new Richiesta.RichiestaBuilder()
                    .id(r.getInt("id"))
                    .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                    .tipoCarico(r.getString("tipo_carico"))
                    .quantita(r.getFloat("quantita"))
                    .dataPartenza(r.getDate("data_partenza"))
                    .portoPartenza(r.getString("porto_partenza"))
                    .dataArrivo(r.getDate("data_arrivo"))
                    .portoArrivo(r.getString("porto_arrivo"))
                    .stato(r.getString("stato"))
                    .caricato(r.getBoolean("caricato"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("database compromesso");
            e.printStackTrace();
        }
        return tmp;
    }

    public LinkedList<Richiesta> doRetriveAll() throws NoEntryException {
        LinkedList<Richiesta> richieste = new LinkedList<>();
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, documento IS NOT NULL as caricato from richiesta");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                richieste.add(
                        new Richiesta.RichiestaBuilder()
                                .id(r.getInt("id"))
                                .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                                .tipoCarico(r.getString("tipo_carico"))
                                .quantita(r.getFloat("quantita"))
                                .dataPartenza(r.getDate("data_partenza"))
                                .portoPartenza(r.getString("porto_partenza"))
                                .dataArrivo(r.getDate("data_arrivo"))
                                .portoArrivo(r.getString("porto_arrivo"))
                                .stato(r.getString("stato"))
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
        return richieste;
    }


    //fine base

    public static Blob doRetriveDocumento(int id) {
        Blob d;
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT documento FROM richiesta where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            r.next();
            d = r.getBlob("documento");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return d;
    }
}