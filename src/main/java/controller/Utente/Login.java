package controller.Utente;

import model.Utente.Utente;
import model.Utente.UtenteDAO;
import model.Util.NoEntryException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet(
        name = "Login",
        description = "Questa servlet si occupa del Login di un utente",
        urlPatterns = "/login"
)
public class Login extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        String forward;
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (email.length() == 0 || password.length() == 0)
                throw new NoEntryException();

            Utente u = UtenteDAO.doRetriveByEmailPassword(email, password);

            if (!u.isAttivato()) {
                req.setAttribute("notifica", "Account non attivato");
                req.setAttribute("tipoNotifica", "danger");
                forward = "login.jsp";
            } else {
                s.setAttribute("utente", u);
                forward = "index.jsp";
            }
        } catch (NoEntryException e) {
            req.setAttribute("notifica", "Hai inserito credenziali non valide");
            req.setAttribute("tipoNotifica", "danger");
            req.setAttribute("back", "login.jsp");
            forward = "login.jsp";
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
