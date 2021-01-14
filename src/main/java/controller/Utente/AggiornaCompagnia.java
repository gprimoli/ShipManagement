package controller.Utente;

import model.CompagniaBroker.CompagniaBroker;
import model.CompagniaBroker.CompagniaBrokerDAO;
import model.Utente.Utente;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/aggiornacompagnia")
public class AggiornaCompagnia extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if(u == null){
            resp.sendRedirect("index.jsp");
            return;
        }

        String codFiscaleCompagnia = req.getParameter("codFiscaleCompagnia");
        String nomeCompagnia = req.getParameter("nomeCompagnia");
        String telefonoCompagnia = req.getParameter("telefonoCompagnia");
        String sedeCompagnia = req.getParameter("sedeCompagnia");
        String sitoCompagnia = req.getParameter("sitoCompagnia");
        CompagniaBroker cb = null;
        try {
            cb = CompagniaBroker.builder()
                    .nome(nomeCompagnia)
                    .codFiscale(codFiscaleCompagnia)
                    .telefono(telefonoCompagnia)
                    .sedeLegale(sedeCompagnia)
                    .sitoWeb(sitoCompagnia)
                    .build();
            CompagniaBrokerDAO.doSave(cb);
            req.setAttribute("notifica", "Compagnia Broker aggiornata con successo!");
            req.setAttribute("tipoNotifica", "success");
            req.setAttribute("compagniaBroker", CompagniaBrokerDAO.doRetriveBy(u));
            req.getRequestDispatcher("profilo.jsp").forward(req, resp);
            return;
        } catch (InvalidParameterException e) {
            req.setAttribute("notifica", "Parametri inviati non validi!");
            req.setAttribute("tipoNotifica", "danger");
        } catch (DuplicateException e) {
            try{
                CompagniaBrokerDAO.doUpdate(cb);
            }catch (DuplicateException e1){
                req.setAttribute("notifica", "Controlla i dati inviati, se l'errore persiste chiamare l'assistenza!");
                req.setAttribute("tipoNotifica", "danger");
            }
            //eliminare la tabella di mezzo.
        }finally {
            CompagniaBrokerDAO.doSaveUtenteCompagnia(cb, u);
        }
        req.setAttribute("compagniaBroker", CompagniaBrokerDAO.doRetriveBy(u));
        req.getRequestDispatcher("profilo.jsp").forward(req, resp);
    }
}
