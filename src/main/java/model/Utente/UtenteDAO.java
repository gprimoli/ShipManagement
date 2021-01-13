package model.Utente;

import lombok.Cleanup;
import model.Util.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.*;
import java.util.LinkedList;

public class UtenteDAO {
    public static String doSave(Utente u, String password) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO utente(cod_fiscale, password, nome, cognome, data_nascita, luogo_nascita, email, telefono, ruolo, attivato) VALUES (?,UNHEX(SHA1(?)),?,?,?,?,?,?,?,?);");
            String tmp = RandomStringUtils.random(30, true, true);

            p.setString(1, u.getCodFiscale());
            p.setString(2, password);
            p.setString(3, u.getNome());
            p.setString(4, u.getCognome());
            p.setDate(5, u.getDataNascita());
            p.setString(6, u.getLuogoNascita());
            p.setString(7, u.getEmail());
            p.setString(8, u.getTelefono());
            p.setString(9, u.getRuolo());
            p.setString(10, tmp);
            p.execute();

            return tmp;
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23000") == 0)
                throw new DuplicateException();
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Utente u) throws DuplicateException {
        try (Connection c = DB.getConnection()) {
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

    public static void doDelete(Utente u) throws MediazioniInCorsoException {
        try (Connection c = DB.getConnection()) {
            if (!checkMediazioni(u.getCodFiscale()))
                throw new MediazioniInCorsoException();
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE utente SET attivato = '-1' WHERE cod_fiscale = ?");
            p.setString(1, u.getCodFiscale());
            p.execute();
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                throw new RuntimeException(e);
        }
    }

    private static boolean checkMediazioni(String codFiscale) {
        boolean stato = false;
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT m.stato = 'Terminta' as stato from (SELECT * FROM utente as utente where utente.cod_fiscale = ?) as u JOIN (SELECT * from richiesta where richiesta.cod_fiscale_utente = ?) as r JOIN (SELECT * from imbarcazione where imbarcazione.cod_fiscale_utente = ?) as i JOIN mediazione as m JOIN mediazione_imbarcazione as mi JOIN mediazione_richiesta as mr WHERE m.id = mi.id_mediazione AND i.imo = mi.imo_imbarcazione AND r.id = mr.id_richiesta AND m.id = mr.id_mediazione AND m.cod_fiscale_utente = u.cod_fiscale;");
            p.setString(1, codFiscale);
            p.setString(2, codFiscale);
            p.setString(3, codFiscale);
            @Cleanup ResultSet r = p.executeQuery();
            if(r.getFetchSize() == 0)
                stato = true;
            while (r.next())
                stato = r.getBoolean("stato");
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                stato = true;
        }
        return stato;
    }

    public static Utente doRetriveByCodFiscale(String codFiscale) throws NoEntryException {
        Utente u = null;
        try (Connection c = DB.getConnection()) {
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
                    .attivato(r.getString("attivato").compareTo("0") == 0)
                    .ruolo(r.getString("ruolo"))
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
        try (Connection c = DB.getConnection()) {
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
                                .attivato(r.getString("attivato").compareTo("0") == 0)
                                .ruolo(r.getString("ruolo"))
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

    public static void doChangePassword(Utente u, String newPassword) {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE utente SET password = UNHEX(SHA1(?)) WHERE cod_fiscale = ?");
            p.setString(1, newPassword);
            p.setString(2, u.getCodFiscale());
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doRecuperaPassword(String email) {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE utente SET password = UNHEX(SHA1(?)) WHERE email = ?");
            String tmp = RandomStringUtils.random(20, true, true);

            p.setString(1, tmp);
            p.setString(2, email);
            p.execute();

            return tmp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doActivate(String email, String validation) {
        try (Connection c = DB.getConnection()) {
            @Cleanup PreparedStatement p = c.prepareStatement("UPDATE utente SET attivato = '0' WHERE email = ? AND attivato = ?");
            p.setString(1, email);
            p.setString(2, validation);
            p.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Utente doRetriveByEmailPassword(String email, String password) throws NoEntryException {
        Utente u = null;
        try (Connection c = DB.getConnection()) {
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
                    .attivato(r.getString("attivato").compareTo("0") == 0)
                    .ruolo(r.getString("ruolo"))
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
        try (Connection c = DB.getConnection()) {
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
                                .attivato(r.getString("attivato").compareTo("0") == 0)
                                .ruolo(r.getString("ruolo"))
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
