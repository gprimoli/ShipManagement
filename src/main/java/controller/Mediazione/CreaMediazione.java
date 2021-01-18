package controller.Mediazione;

import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Utente.Utente;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/aggiungi-mediazione")
public class CreaMediazione extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if(u == null){
            resp.sendRedirect("index");
            return;
        }

        String forward = "/WEB-INF/error.jsp";
        String codFiscale = u.getCodFiscale();
        String nome = req.getParameter("nome");

        try {
            Mediazione m = Mediazione.builder()
                    .nome(nome)
                    .stato("Default")
                    .codFiscaleUtente(codFiscale)
                    .contratto(null)
                    .caricato(false)
                    .build();

            int id = MediazioneDAO.doSave(m);

            resp.sendRedirect("visualizza-mediazione?id=" + id);
            return;
        } catch (InvalidParameterException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Parametri non validi");
            req.setAttribute("back", "index.jsp");
        } catch (DuplicateException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Mediazione già presente nel database contrallare i dati");
            req.setAttribute("back", "index.jsp");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

}
