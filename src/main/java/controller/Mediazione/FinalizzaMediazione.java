package controller.Mediazione;

import model.Imbarcazione.Imbarcazione;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Notifica.Notifica;
import model.Richiesta.Richiesta;
import model.Utente.Utente;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.NoEntryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

public class FinalizzaMediazione extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        String forward = "";
        try {
            int idMediazione = Integer.parseInt(req.getParameter("id"));
            Mediazione origin = MediazioneDAO.doRetriveById(idMediazione);
            if (origin.isCaricato()) {
                Mediazione m = Mediazione.builder()
                        .clone(origin)
                        .stato("In Attesa di Firma")
                        .build();
                LinkedList<Imbarcazione> i = MediazioneDAO.doRetriveImbarcazioniFrom(m);
                LinkedList<Richiesta> r = MediazioneDAO.doRetriveRichiesteFrom(m);
                if (i.size() > 0 && r.size() > 0) {
                    MediazioneDAO.doUpdate(m);

                    Notifica n = Notifica.builder().oggetto("In attesa di Firma Mediazione " + m.getId()).corpo("").build();

                    resp.sendRedirect("index");
                    return;
                } else {
                    req.setAttribute("errore", "422");
                    req.setAttribute("back", "index.jsp");
                    req.setAttribute("descrizione", "Mancano le richieste o le imbarcazione!");
                    forward = "/WEB-INF/error.jsp";
                }
            } else {
                throw new InvalidParameterException();
            }
            return;
        } catch (NumberFormatException | InvalidParameterException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            req.setAttribute("descrizione", "Parametri errati!");
            forward = "/WEB-INF/error.jsp";
        } catch (NoEntryException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            req.setAttribute("descrizione", "Nessuna mediazione con l'id passato!");
            forward = "/WEB-INF/error.jsp";
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
