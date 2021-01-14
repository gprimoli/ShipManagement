package controller.Mediazione;

import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EliminaMediazione extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index.jsp");
            return;
        }
        int id = Integer.parseInt((String) req.getAttribute("id"));
        String forward = "index.jsp";
        Mediazione m = MediazioneDAO.doRetriveById(id);

        if(m.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0){
            if(m.getStato().compareTo("Default") == 0 || m.getStato().compareTo("In Attesa di Firma") == 0 || m.getStato().compareTo("Richiesta Modifica") == 0){
                MediazioneDAO.doDelete(m);
                //Notifica Quelli che hanno firmato
                req.setAttribute("notifica", "Mediazione Eliminata!");
                req.setAttribute("tipoNotifica", "danger");
            }else {
                req.setAttribute("notifica", "Non puoi eliminare una mediazione avviata!");
                req.setAttribute("tipoNotifica", "danger");
            }
        }else {
            req.setAttribute("notifica", "Non hai i permessi per visualizzare la mediazione perchè non ne fai parte!");
            req.setAttribute("tipoNotifica", "danger");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
