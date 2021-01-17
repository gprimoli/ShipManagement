package model.CompagniaBroker;

import lombok.Cleanup;
import model.Imbarcazione.Imbarcazione;
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

public class CompagniaBrokerDAO {

    public static void doSave(CompagniaBroker compagnia) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO compagnia_broker(cod_fiscale, nome, telefono, sede_legale, sito_web) VALUES (?,?,?,?,?);");
            p.setString(1, compagnia.getCodFiscale());
            p.setString(2, compagnia.getNome());
            p.setString(3, compagnia.getTelefono());
            p.setString(4, compagnia.getSedeLegale());
            p.setString(5, compagnia.getSitoWeb());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doSaveUtenteCompagnia(CompagniaBroker cb, Utente u) {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO compagnia_broker_utente(cod_fiscale_compagnia, cod_fiscale_utente) VALUES (?,?);");
            p.setString(1, cb.getCodFiscale());
            p.setString(2, u.getCodFiscale());

            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(CompagniaBroker compagnia) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE compagnia_broker SET nome = ?, telefono = ?, sede_legale = ?, sito_web = ? WHERE cod_fiscale = ?");
            p.setString(1, compagnia.getNome());
            p.setString(2, compagnia.getTelefono());
            p.setString(3, compagnia.getSedeLegale());
            p.setString(4, compagnia.getSitoWeb());
            p.setString(5, compagnia.getCodFiscale());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(CompagniaBroker compagnia) {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM compagnia_broker WHERE cod_fiscale = ?");
            p.setString(1, compagnia.getCodFiscale());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompagniaBroker doRetriveBy(String codFiscale) throws NoEntryException {
        CompagniaBroker compagniaBroker = null;
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from compagnia_broker where cod_fiscale = ?;");
            p.setString(1, codFiscale);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                compagniaBroker = CompagniaBroker.builder()
                        .codFiscale(r.getString("cod_fiscale"))
                        .nome(r.getString("nome"))
                        .telefono(r.getString("telefono"))
                        .sedeLegale(r.getString("sede_legale"))
                        .sitoWeb(r.getString("sito_web"))
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
        return compagniaBroker;
    }

    public static LinkedList<CompagniaBroker> doRetriveAll() throws NoEntryException {
        LinkedList<CompagniaBroker> compagnieBroker = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select *, contratto IS NOT NULL as caricato from mediazione;");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                compagnieBroker.add(
                        CompagniaBroker.builder()
                                .codFiscale(r.getString("cod_fiscale"))
                                .nome(r.getString("nome"))
                                .telefono(r.getString("telefono"))
                                .sedeLegale(r.getString("sede_legale"))
                                .sitoWeb(r.getString("sito_web"))
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
        return compagnieBroker;
    }

    public static CompagniaBroker doRetriveBy(Utente u) throws NoEntryException {
        CompagniaBroker compagniaBroker = null;
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select c.cod_fiscale, c.nome, c.telefono, c.sede_legale, c.sito_web from compagnia_broker as c JOIN compagnia_broker_utente as cb on cb.cod_fiscale_utente = ? AND c.cod_fiscale = cb.cod_fiscale_compagnia;");
            p.setString(1, u.getCodFiscale());
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                compagniaBroker = CompagniaBroker.builder()
                        .codFiscale(r.getString("cod_fiscale"))
                        .nome(r.getString("nome"))
                        .telefono(r.getString("telefono"))
                        .sedeLegale(r.getString("sede_legale"))
                        .sitoWeb(r.getString("sito_web"))
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
        return compagniaBroker;
    }

}
