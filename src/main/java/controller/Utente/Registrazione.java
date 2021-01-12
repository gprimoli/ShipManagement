package controller.Utente;

import model.CompagniaBroker.CompagniaBroker;
import model.CompagniaBroker.CompagniaBrokerDAO;
import model.Utente.Utente;
import model.Utente.UtenteDAO;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import model.Util.Mail;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(
        name = "Registrazione",
        description = "Questa servlet si occupa della registrazione di un utente",
        urlPatterns = "/registrazione"
)
@MultipartConfig
public class Registrazione extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward;
        try {
            String ruolo = req.getParameter("ruolo").toLowerCase();
            String nome = req.getParameter("nome");
            String cognome = req.getParameter("cognome");
            String dataDiNascita = req.getParameter("dataDiNascita");
            String luogoDiNascita = req.getParameter("luogoDiNascita");
            String codFiscale = req.getParameter("codFiscale");
            String telefono = req.getParameter("telefono");
            String email = req.getParameter("email");

            String password = req.getParameter("password");
            String repassword = req.getParameter("confermaPassword");

            if(password.compareTo(repassword) != 0)
                throw new InvalidParameterException();

            Utente u = Utente.builder()
                    .codFiscale(codFiscale)
                    .nome(nome)
                    .cognome(cognome)
                    .dataNascita(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataDiNascita).getTime()))
                    .luogoNascita(luogoDiNascita)
                    .email(email)
                    .telefono(telefono)
                    .attivato(false)
                    .ruolo(ruolo)
                    .build();

            String tmp = UtenteDAO.doSave(u, password);

            Mail m = new Mail();
            String path = getServletContext().getContextPath();
            String link = "Link attivazione account: http://localhost:8080" + path + "/attiva?codice=" + tmp + "&email="+ u.getEmail();
//          m.sendMail(u.getEmail(), "Benvenuto in ShipManagment", link);

            if (u.isBroker()) {
                String codFiscaleCompagnia = req.getParameter("codFiscaleCompagnia");
                String nomeCompagnia = req.getParameter("nomeCompagnia");
                String telefonoCompagnia = req.getParameter("telefonoCompagnia");
                String sedeCompagnia = req.getParameter("sedeCompagnia");
                String sitoCompagnia = req.getParameter("sitoCompagnia");
                CompagniaBroker cb = CompagniaBroker.builder()
                        .nome(nomeCompagnia)
                        .codFiscale(codFiscaleCompagnia)
                        .telefono(telefonoCompagnia)
                        .sedeLegale(sedeCompagnia)
                        .sitoWeb(sitoCompagnia)
                        .build();

                CompagniaBrokerDAO.doSave(cb, u);
            }

            forward = "/login.jsp";

        } catch (IllegalArgumentException | InvalidParameterException | ParseException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Parametri non validi");
            req.setAttribute("back", "register.jsp");
            forward = "/WEB-INF/error.jsp";
        }catch (DuplicateException e){
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Utente già registrato");
            req.setAttribute("back", "register.jsp");
            forward = "/WEB-INF/error.jsp";
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
