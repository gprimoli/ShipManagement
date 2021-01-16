package controller.Utente;

import model.Utente.UtenteDAO;
import model.Util.Mail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/recuperopassword"
)
public class RecuperoPassword extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("email");

        String newPassword = UtenteDAO.doRecuperaPassword(mail);
        System.out.println(newPassword);

        Mail m = new Mail();
//        m.sendMail(mail, "Recupero Password ShipManagment", "Nuova password: " + newPassword);

        req.setAttribute("notifica", "Controlla la mail :)");
        req.setAttribute("tipoNotifica", "success");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

}
