package controller.Imbarcazione;

import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Utente.Utente;
import model.Util.NoEntryException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;


@WebServlet(
        urlPatterns = "/visualizza-imbarcazione"
)
public class VisualizzaImbarcazione extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        String imo = req.getParameter("imo");
        if (u == null || imo == null) {
            resp.sendRedirect("index");
            return;
        }

        String forward = "/WEB-INF/imbarcazione.jsp";
        try {
            Imbarcazione im = ImbarcazioneDAO.doRetriveById(imo);

            if (u.isBroker() || im.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
                req.setAttribute("imbarcazione", im);
            } else {
                LinkedList<Mediazione> propMediazini = MediazioneDAO.doRetriveBy(u);
                boolean tmp = false;
                for (Mediazione m : propMediazini) {
                    try {
                        LinkedList<Imbarcazione> imbarcazioni = MediazioneDAO.doRetriveImbarcazioniFrom(m);
                        for (Imbarcazione i : imbarcazioni) {
                            if (i.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
                                tmp = true;
                                break;
                            }
                        }
                        if (tmp)
                            break;
                    } catch (NoEntryException ignored) {
                    }
                }
                if (tmp) {
                    req.setAttribute("imbarcazione", im);
                } else {
                    req.setAttribute("errore", "422");
                    req.setAttribute("back", "index.jsp");
                    forward = "/WEB-INF/errore.jsp";
                    req.setAttribute("descrizione", "Non hai i permessi per visualizzare l'imbarcazione!");
                }
            }
        } catch (NoEntryException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            forward = "/WEB-INF/errore.jsp";
            req.setAttribute("descrizione", "Imbarcazione inesistente");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
