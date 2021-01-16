package controller.Utente;

import model.Imbarcazione.ImbarcazioneDAO;
import model.Mediazione.MediazioneDAO;
import model.Notifica.NotificaDAO;
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

@WebServlet(
        urlPatterns = "/index"
)
public class index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u != null) {
            req.setAttribute("notifiche", NotificaDAO.doRetriveBy(u));
            req.setAttribute("mediazioni", MediazioneDAO.doRetriveBy(u));
            if (u.isCliente()){
                req.setAttribute("richieste", RichiestaDAO.doRetriveBy(u));
                req.setAttribute("porti", PortoDAO.doRetriveAll());
            }else if (u.isArmatore())
                req.setAttribute("imbarcazioni", ImbarcazioneDAO.doRetriveBy(u));
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
