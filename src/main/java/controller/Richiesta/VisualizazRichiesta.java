package controller.Richiesta;

import model.Area.AreaDAO;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Porto.PortoDAO;
import model.Richiesta.Richiesta;
import model.Richiesta.RichiestaDAO;
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
        urlPatterns = "/visualizza-richiesta"
)
public class VisualizazRichiesta extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        String forward = "/WEB-INF/richiesta.jsp";
        try {
            Richiesta r = RichiestaDAO.doRetriveById(id);

            if (u.isBroker() || r.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
                req.setAttribute("richiesta", r);
                req.setAttribute("porti", PortoDAO.doRetriveAll());
                req.setAttribute("mediazioni", MediazioneDAO.doRetriveOKBy(u));
            } else {
                LinkedList<Mediazione> propMediazini = MediazioneDAO.doRetriveBy(u);
                boolean tmp = false;
                for (Mediazione m : propMediazini) {
                    try {
                        LinkedList<Richiesta> richiesta = MediazioneDAO.doRetriveRichiesteFrom(m);
                        for (Richiesta ri : richiesta) {
                            if (ri.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {
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
                    req.setAttribute("richiesta", r);
                    req.setAttribute("porti", PortoDAO.doRetriveAll());
                } else {
                    req.setAttribute("errore", "422");
                    req.setAttribute("back", "index.jsp");
                    req.setAttribute("descrizione", "Non hai i permessi per visualizzare la richiesta!");
                    forward = "/WEB-INF/errore.jsp";
                }
            }
        } catch (NoEntryException e) {
            req.setAttribute("descrizione", "Richiesta inesistente");
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            forward = "/WEB-INF/errore.jsp";
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
