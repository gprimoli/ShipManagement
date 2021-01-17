package controller.Mediazione;

import lombok.Cleanup;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Richiesta.Richiesta;
import model.Richiesta.RichiestaDAO;
import model.Utente.Utente;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ModificaMediazione extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if (u == null) {
            resp.sendRedirect("index");
            return;
        }

        int id = Integer.parseInt(req.getParameter("nome"));
        String nome = req.getParameter("nome");
        Part p = req.getPart("documento");
        @Cleanup InputStream documento = null;
        boolean tmp = false;
        String forward = "/WEB-INF/error.jsp";
        try {
            if (p.getSize() > 0 && p.getSize() < 4294967295.0 && p.getContentType().compareTo("application/pdf") == 0) {
                documento = p.getInputStream();
                tmp = true;
            }

            Mediazione origin = MediazioneDAO.doRetriveById(id);


            if (origin.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
                if (origin.getStato().compareTo("Default") == 0 || origin.getStato().compareTo("Richiesta Modifica") == 0) {
                    Mediazione m = Mediazione.builder()
                            .id(origin.getId())
                            .nome(nome)
                            .contratto(documento)
                            .codFiscaleUtente(u.getCodFiscale())
                            .caricato(tmp)
                            .build();

                    MediazioneDAO.doUpdate(m);

                    Notifica n = Notifica.builder().oggetto("Mediazione " + m.getId() + " modificata").corpo("La mediazione " + m.getId() + " &egrave; stata modficata dall'armatore " + m.getCodFiscaleUtente()).build();

                    NotificaDAO.doSaveAll(m, n);

                    resp.sendRedirect("visualizza-mediazione?id=" + id);
                    return;
                } else {
                    req.setAttribute("errore", "422");
                    req.setAttribute("descrizione", "La mediazione è già stata avviata");
                    req.setAttribute("back", "index.jsp");
                }
            } else {
                req.setAttribute("errore", "422");
                req.setAttribute("descrizione", "Non possiedi questa mediazione");
                req.setAttribute("back", "index.jsp");
            }
        } catch (DuplicateException | InvalidParameterException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Parametri errati");
            req.setAttribute("back", "index.jsp");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
