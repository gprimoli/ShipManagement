package model.Utente;

import lombok.Cleanup;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import java.sql.*;
import java.util.LinkedList;

public class UtenteDAO {
    public static void doSave(Utente u, String password, String ruolo) throws DuplicateException {
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO utente(cod_fiscale, password, nome, cognome, data_nascita, luogo_nascita, email, telefono, ruolo) VALUES (?,UNHEX(SHA1(?)),?,?,?,?,?,?,?);");
            p.setString(1, u.getCodFiscale());
            p.setString(2, password);
            p.setString(3, u.getNome());
            p.setString(4, u.getCognome());
            p.setDate(5, u.getDataNascita());
            p.setString(6, u.getLuogoNascita());
            p.setString(7, u.getEmail());
            p.setString(8, u.getTelefono());
            p.setString(9, ruolo);
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Utente u) throws DuplicateException {
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE utente SET nome = ?, cognome = ?, data_nascita = ?, luogo_nascita = ?, email = ?, telefono = ? WHERE cod_fiscale = ?");
            p.setString(1, u.getNome());
            p.setString(2, u.getCognome());
            p.setDate(3, u.getDataNascita());
            p.setString(4, u.getLuogoNascita());
            p.setString(5, u.getEmail());
            p.setString(6, u.getTelefono());
            p.setString(7, u.getCodFiscale());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Utente u) {
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("DELETE FROM utente WHERE cod_fiscale = ?");
            p.setString(1, u.getCodFiscale());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Utente doRetriveByCodFiscale(String codFiscale) throws NoEntryException {
        Utente u = null;
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT * FROM utente WHERE cod_fiscale = ?;");
            p.setString(1, codFiscale);
            @Cleanup ResultSet r = p.executeQuery();
            r.next();
            u = new Utente.UtenteBuilder()
                    .codFiscale(r.getString("cod_fiscale"))
                    .nome(r.getString("nome"))
                    .cognome(r.getString("cognome"))
                    .dataNascita(r.getDate("data_nascita"))
                    .luogoNascita(r.getString("luogo_nascita"))
                    .email(r.getString("email"))
                    .telefono(r.getString("telefono"))
                    .attivato(r.getBoolean("attivato"))
                    .ruolo(Ruolo.valueOf(r.getString("ruolo")))
                    .build();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                throw new NoEntryException();
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("database compromesso");
            e.printStackTrace();
        }
        return u;
    }

    public static LinkedList<Utente> doRetriveAll() throws NoEntryException {
        LinkedList<Utente> utenti = new LinkedList<>();
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from utente");
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                utenti.add(
                        new Utente.UtenteBuilder()
                                .codFiscale(r.getString("cod_fiscale"))
                                .nome(r.getString("nome"))
                                .cognome(r.getString("cognome"))
                                .dataNascita(r.getDate("data_nascita"))
                                .luogoNascita(r.getString("luogo_nascita"))
                                .email(r.getString("email"))
                                .telefono(r.getString("telefono"))
                                .attivato(r.getBoolean("attivato"))
                                .ruolo(Ruolo.valueOf(r.getString("ruolo")))
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
        return utenti;
    }


    //fine base

    public static void doChangePassword(Utente u, String newPassword) throws DuplicateException {
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE utente SET password = UNHEX(SHA1(?)) WHERE cod_fiscale = ?");
            p.setString(1, newPassword);
            p.setString(2, u.getCodFiscale());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doActivate(Utente u) throws DuplicateException {
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE utente SET attivato = true WHERE cod_fiscale = ?");
            p.setString(1, u.getCodFiscale());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static Utente doRetriveByEmailPassword(String email, String password) throws NoEntryException {
        Utente u = null;
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = UNHEX(SHA1(?))");
            p.setString(1, email);
            p.setString(2, password);
            @Cleanup ResultSet r = p.executeQuery();
            r.next();
            u = new Utente.UtenteBuilder()
                    .codFiscale(r.getString("cod_fiscale"))
                    .nome(r.getString("nome"))
                    .cognome(r.getString("cognome"))
                    .dataNascita(r.getDate("data_nascita"))
                    .luogoNascita(r.getString("luogo_nascita"))
                    .email(r.getString("email"))
                    .telefono(r.getString("telefono"))
                    .attivato(r.getBoolean("attivato"))
                    .ruolo(Ruolo.valueOf(r.getString("ruolo")))
                    .build();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                throw new NoEntryException();
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("database compromesso");
            e.printStackTrace();
        }
        return u;
    }

    public static LinkedList<Utente> doRetriveSearch(String name, String cognome) throws NoEntryException {
        LinkedList<Utente> utenti = new LinkedList<>();
        try (@Cleanup Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("Select * from utente WHERE MATCH(nome,cognome) AGAINST (?)");
            p.setString(1, name + " " + cognome);
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                utenti.add(
                        new Utente.UtenteBuilder()
                                .codFiscale(r.getString("cod_fiscale"))
                                .nome(r.getString("nome"))
                                .cognome(r.getString("cognome"))
                                .dataNascita(r.getDate("data_nascita"))
                                .luogoNascita(r.getString("luogo_nascita"))
                                .email(r.getString("email"))
                                .telefono(r.getString("telefono"))
                                .attivato(r.getBoolean("attivato"))
                                .ruolo(Ruolo.valueOf(r.getString("ruolo")))
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
        return utenti;
    }

}
