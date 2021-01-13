package controller.Utente;

import model.Utente.UtenteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/attiva"
)
public class AttivazioneAccount extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codice = req.getParameter("codice");
        String mail = req.getParameter("email");
        if (codice.compareTo("-1") != 0) {
            UtenteDAO.doActivate(mail, codice);
            req.setAttribute("tipoNotifica", "success");
            req.setAttribute("notifica", "Account attivato con successo! Adesso puoi eseguire l'accesso");
        }else {
            req.setAttribute("tipoNotifica", "danger");
            req.setAttribute("notifica", "Account Disattivato! Contattare l'amministrazione");
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
