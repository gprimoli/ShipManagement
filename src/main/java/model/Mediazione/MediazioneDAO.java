package model.Mediazione;

import lombok.Cleanup;
import model.Imbarcazione.Imbarcazione;
import model.Richiesta.Richiesta;
import model.Utente.Utente;
import model.Util.DB;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;

public class MediazioneDAO {

    public static int doSave(Mediazione m) throws DuplicateException {
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("INSERT INTO mediazione(nome, stato, contratto, cod_fiscale_utente) VALUES (?,?,?,?);");
            p.setString(1, m.getNome());
            p.setString(2, m.getStato());
            p.setBlob(3, m.getContratto());
            p.setString(4, m.getCodFiscaleUtente());
            p.execute();

            p = c.prepareStatement("SELECT MAX(id) from mediazione where cod_fiscale_utente = ?;");
            p.setString(1, m.getCodFiscaleUtente());
            @Cleanup ResultSet rs = p.executeQuery();
            rs.next();
            return rs.getInt(1);
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

            p = c.prepareStatement("DELETE FROM mediazione_imbarcazione as mi WHERE mi.id_mediazione = ?");
            p.setInt(1, m.getId());
            p.execute();

            p = c.prepareStatement("DELETE FROM mediazione_richiesta as mr WHERE mr.id_mediazione = ?");
            p.setInt(1, m.getId());
            p.execute();

            p = c.prepareStatement("DELETE FROM firma as f WHERE f.id_mediazione = ?");
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

    public static InputStream doRetriveDocumento(int id) {
        InputStream d;
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT contratto FROM mediazione where id = ?;");
            p.setInt(1, id);
            @Cleanup ResultSet r = p.executeQuery();
            r.next();

            d = r.getBinaryStream("documento");
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

    public static LinkedList<Richiesta> doRetriveRichiesteFrom(Mediazione m) throws NoEntryException {
        LinkedList<Richiesta> richieste = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("Select r.id, r.cod_fiscale_utente, r.tipo_carico, r.quantita, r.data_arrivo, r.porto_arrivo, r.data_partenza, r.porto_partenza, r.stato, r.documento IS NOT NULL as caricato from  mediazione as m JOIN mediazione_richiesta as mr on m.id = ? AND m.id = mr.id_mediazione JOIN richiesta as r on mr.id_richiesta = r.id");
            p.setInt(1, m.getId());
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                richieste.add(
                        Richiesta.builder()
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

    public static LinkedList<Imbarcazione> doRetriveImbarcazioniFrom(Mediazione m) throws NoEntryException {
        LinkedList<Imbarcazione> imbarcazioni = new LinkedList<>();
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT i.cod_fiscale_utente, i.imo, i.nome, i.tipologia, i.anno_costruzione, i.bandiera, i.quantita_max, i.lunghezza_fuori_tutto, i.altezza, i.ampiezza, i.posizione, i.disponibile, i.documento IS NOT NULL as caricato FROM mediazione as m JOIN mediazione_imbarcazione as mi ON m.id = ? AND m.id = mi.id_mediazione JOIN imbarcazione as i on mi.imo_imbarcazione = i.imo;");
            p.setInt(1, m.getId());
            @Cleanup ResultSet r = p.executeQuery();
            while (r.next()) {
                imbarcazioni.add(
                        Imbarcazione.builder()
                                .codFiscaleUtente(r.getString("cod_fiscale_utente"))
                                .imo(r.getString("imo"))
                                .nome(r.getString("nome"))
                                .tipologia(r.getString("tipologia"))
                                .annoCostruzione(r.getInt("anno_costruzione"))
                                .bandiera(r.getString("bandiera"))
                                .quantitaMax(r.getFloat("quantita_max"))
                                .lunghezza(r.getFloat("lunghezza_fuori_tutto"))
                                .ampiezza(r.getFloat("ampiezza"))
                                .altezza(r.getFloat("altezza"))
                                .posizione(r.getInt("posizione"))
                                .disponibile(r.getBoolean("disponibile"))
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
        return imbarcazioni;
    }

    public static boolean doCheck(Imbarcazione i) {
        boolean tmp = false;
        try {
            @Cleanup Connection c = DB.getConnection();
            @Cleanup PreparedStatement p = c.prepareStatement("SELECT * from (SELECT * from (SELECT * from imbarcazione where imo = ?) as i, mediazione_imbarcazione as mi where mi.imo_imbarcazione = i.imo) as x, mediazione as m where x.id_mediazione = m.id AND m.stato = 'In Corso'");
            p.setString(1, i.getImo());
            @Cleanup ResultSet r = p.executeQuery();
            if(r.next())
                tmp = true;
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("S1000") == 0)
                return true;
        }
        return tmp;
    }

}
