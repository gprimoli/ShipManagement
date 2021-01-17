package controller.Mediazione;

import model.Area.AreaDAO;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Porto.Porto;
import model.Porto.PortoDAO;
import model.Richiesta.RichiestaDAO;
import model.Utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = "/ricerca-mediazioni")
public class RicercaMediazioni  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        String forward = "/WEB-INF/ricerca.jsp";
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }

        if (u.isBroker()){
            req.setAttribute("imbarcazioni", ImbarcazioneDAO.doRetriveAllDisponibili());
            req.setAttribute("richieste", RichiestaDAO.doRetriveAllDisponibili());
            req.setAttribute("aree", AreaDAO.doRetriveAll());
            req.setAttribute("porti", PortoDAO.doRetriveAll());
        }else {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            forward = "/WEB-INF/errore.jsp";
            req.setAttribute("descrizione", "Non hai i permessi visualizzare le ricerche");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
