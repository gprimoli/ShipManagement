package controller.Richiesta;

import lombok.SneakyThrows;
import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Richiesta.Richiesta;
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
        urlPatterns = "/rimuovi-richiesta"
)
public class EliminaRichiesta extends HttpServlet {

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String forward = "index";
        Richiesta r = RichiestaDAO.doRetriveById(id);

        if(r.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0){
            if(r.getStato().compareTo("Disponibile") == 0){

                Notifica n = Notifica.builder().oggetto("Richiesta " + r.getId() + " eliminata").corpo("La richiesta " + r.getId() + " &egrave; stata eliminata dal clinete " + r.getCodFiscaleUtente()).build();

                int notificaID = NotificaDAO.doSave(n);

                NotificaDAO.doSendToBroker(r, notificaID);

                RichiestaDAO.doDelete(r);
                req.setAttribute("notifica", "Richiesta Eliminata!");
                req.setAttribute("tipoNotifica", "danger");
            }else {
                req.setAttribute("errore", "422");
                req.setAttribute("back", "index.jsp");
                forward = "/WEB-INF/errore.jsp";
                req.setAttribute("descrizione", "Non puoi eliminare una richiesta avviata!");
            }
        }else {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            forward = "/WEB-INF/errore.jsp";
            req.setAttribute("descrizione", "Non hai i permessi per rimuovere la richiesta perchè non ne fai parte!");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
