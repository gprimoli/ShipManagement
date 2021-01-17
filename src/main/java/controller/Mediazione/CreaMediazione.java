package controller.Mediazione;

import lombok.Cleanup;
import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Mediazione.Mediazione;
import model.Mediazione.MediazioneDAO;
import model.Utente.Utente;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

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
        Part p = req.getPart("documento");
        @Cleanup InputStream documento = null;
        boolean tmp = false;

        try {
            if (p.getSize() > 0 && p.getSize() < 4294967295.0 && p.getContentType().compareTo("application/pdf") == 0) {
                documento = p.getInputStream();
                tmp = true;
            }

            Mediazione m = Mediazione.builder()
                    .nome(nome)
                    .stato("Disponibile")
                    .codFiscaleUtente(codFiscale)
                    .contratto(documento)
                    .caricato(tmp)
                    .build();

            int id = MediazioneDAO.doSave(m);

            resp.sendRedirect("visualizza-mediazione?id=" + id);
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
