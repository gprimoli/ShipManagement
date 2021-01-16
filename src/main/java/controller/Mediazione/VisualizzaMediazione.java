package controller.Mediazione;

import model.CompagniaBroker.CompagniaBrokerDAO;
import model.Imbarcazione.Imbarcazione;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Richiesta.Richiesta;
import model.Utente.Utente;
import model.Utente.UtenteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(urlPatterns = "/visualizza-mediazione")
public class VisualizzaMediazione extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String forward;
        Mediazione m = MediazioneDAO.doRetriveById(id);
        Utente b = UtenteDAO.doRetriveByCodFiscale(m.getCodFiscaleUtente());
        LinkedList<Imbarcazione> i = MediazioneDAO.doRetriveImbarcazioniFrom(m);
        LinkedList<Richiesta> r = MediazioneDAO.doRetriveRichiesteFrom(m);

        if(m.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0 ||
                i.stream().anyMatch(item -> item.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) ||
                r.stream().anyMatch(item -> item.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0)
        ){

            req.setAttribute("mediazione", m);
            req.setAttribute("utente", b);
            req.setAttribute("inbarcazioni", i);
            req.setAttribute("richieste", r);
            forward = "/WEB-INF/mediazione.jsp";
        }else {
            req.setAttribute("notifica", "Non hai i permessi per visualizzare la mediazione perchè non ne fai parte!");
            req.setAttribute("tipoNotifica", "danger");
            forward = "index";
        }
        req.getRequestDispatcher(forward).forward(req, resp);

    }
}
