package controller.Richiesta;

import lombok.Cleanup;
import model.Richiesta.Richiesta;
import model.Richiesta.RichiestaDAO;
import model.Utente.Utente;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(
        urlPatterns = "/aggiungi-richiesta"
)
@MultipartConfig
public class CreaRichiesta extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if(u == null){
            resp.sendRedirect("index");
            return;
        }

        String forward = "/WEB-INF/error.jsp";
        String codFiscale = u.getCodFiscale();
        String tipoCarico = req.getParameter("tipoCarico");
        float quantita = Float.parseFloat(req.getParameter("quantita"));
        String portoPartenza = req.getParameter("portoPartenza");
        String dataPartenza = req.getParameter("dataPartenza");
        String portoArrivo = req.getParameter("portoArrivo");
        String dataArrivo = req.getParameter("dataArrivo");
        Part p = req.getPart("documento");
        byte[] byteArray = null;
        Blob documento = null;
        boolean tmp = false;
        try {
            if (p.getSize() > 0) {
                @Cleanup InputStream in = p.getInputStream();
                byteArray = IOUtils.toByteArray(in);
                documento = new SerialBlob(byteArray);
                tmp = true;
            }
            Richiesta r = Richiesta.builder()
                    .codFiscaleUtente(codFiscale)
                    .tipoCarico(tipoCarico)
                    .quantita(quantita)
                    .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataPartenza).getTime()))
                    .portoPartenza(portoPartenza)
                    .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataArrivo).getTime()))
                    .portoArrivo(portoArrivo)
                    .documento(documento)
                    .caricato(tmp)
                    .stato("Disponibile")
                    .build();

            RichiestaDAO.doSave(r);

            req.setAttribute("notifica", "Richiesta aggiunta con successo");
            req.setAttribute("tipoNotifica", "success");
            forward = "index";
        } catch (InvalidParameterException | SQLException | ParseException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Parametri non validi");
            req.setAttribute("back", "index.jsp");
        } catch (DuplicateException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Richiesta già presente nel database contrallare i dati");
            req.setAttribute("back", "index.jsp");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

}
