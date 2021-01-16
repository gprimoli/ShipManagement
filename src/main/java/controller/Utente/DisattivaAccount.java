package controller.Utente;

import model.Utente.Utente;
import model.Utente.UtenteDAO;
import model.Util.MediazioniInCorsoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/disattivaaccount"
)
public class DisattivaAccount extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "profilo.jsp";
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u != null) {
            try{
                UtenteDAO.doDelete(u);
                req.setAttribute("notifica", "Account disattivato con successo");
                req.setAttribute("tipoNotifica", "success");
                forward = "login.jsp";
                s.invalidate();
            }catch (MediazioniInCorsoException e){
                req.setAttribute("notifica", "Impossibile disattivare l'account mediazioni in corso");
                req.setAttribute("tipoNotifica", "danger");
            }
        } else {
            req.setAttribute("notifica", "Impossibile disattivare l'account");
            req.setAttribute("tipoNotifica", "danger");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
