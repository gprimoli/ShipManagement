package controller.Mediazione;

import model.Mediazione.MediazioneDAO;
import model.Utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/rimuovi-dalla-mediazione-imbarcazione")
public class RimuoviImbarcazioneDallaMediazione extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        String forward = "";
        try {
            int id = Integer.parseInt(req.getParameter("imbarcazioneID"));
            int mediazioneID = Integer.parseInt(req.getParameter("mediazioneID"));

            MediazioneDAO.doDeleteImbarcazioneMediazione(mediazioneID, id);
            resp.sendRedirect("visualizza-imbarcazione?id=" + id);
            return;
        } catch (NumberFormatException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            req.setAttribute("descrizione", "Parametri errati!");
            forward = "/WEB-INF/error.jsp";
        } catch (Exception e) {
            req.setAttribute("errore", "422");
            req.setAttribute("back", "index.jsp");
            req.setAttribute("descrizione", "La richiesta non fa parte della mediazione!");
            forward = "/WEB-INF/error.jsp";
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
