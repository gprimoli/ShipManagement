package controller.Utente;

import model.Utente.Utente;
import model.Utente.UtenteDAO;
import model.Util.NoEntryException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name = "AttivazioneAccount",
        description = "Questa servlet si occupa del Login di un utente",
        urlPatterns = "/attiva"
)
public class AttivazioneAccount extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codice = req.getParameter("codice");
        String mail = req.getParameter("email");

        UtenteDAO.doActivate(mail, codice);

        req.setAttribute("notifica", "Account attivato con successo! Adesso puoi eseguire l'accesso");
        req.setAttribute("tipoNotifica", "success");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
