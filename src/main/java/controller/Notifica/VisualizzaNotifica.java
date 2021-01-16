package controller.Notifica;

import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(
        urlPatterns = "/visualizza-notifica"
)

public class VisualizzaNotifica extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
            if (u == null) {
                resp.sendRedirect("index");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("index");
            return;
        }
        Notifica n = NotificaDAO.doRetriveById(id);
        LinkedList<Notifica> proprie = NotificaDAO.doRetriveBy(u);
        String forward = "/WEB-INF/error.jsp";
        if (proprie.stream().anyMatch(p -> p.getId() == n.getId())) {
            req.setAttribute("oggetto", n.getOggetto());
            req.setAttribute("corpo", n.getCorpo());
            forward = "/WEB-INF/notifica.jsp";
        } else {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Questa notifica non ti appartiene");
            req.setAttribute("back", "index.jsp");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

}
