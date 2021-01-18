package controller.Richiesta;

import lombok.Cleanup;
import model.CompagniaBroker.CompagniaBrokerDAO;
import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Richiesta.Richiesta;
import model.Richiesta.RichiestaDAO;
import model.Utente.Utente;
import model.Utente.UtenteDAO;
import model.Util.DuplicateException;
import model.Util.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@WebServlet(urlPatterns = "/")
@MultipartConfig
public class ModificaRichiesta extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");

        if(u == null){
            resp.sendRedirect("index");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        String tipoCarico = req.getParameter("tipoCarico");
        float quantita = Float.parseFloat(req.getParameter("quantita"));
        String portoPartenza = req.getParameter("portoPartenza");
        String dataPartenza = req.getParameter("dataPartenza");
        String portoArrivo = req.getParameter("portoArrivo");
        String dataArrivo = req.getParameter("dataArrivo");
        Part p = req.getPart("documento");
        @Cleanup InputStream documento = null;
        boolean tmp = false;
        String forward = "/WEB-INF/error.jsp";
        try {
            if (p.getSize() > 0 && p.getSize() < 4294967295.0 && p.getContentType().compareTo("application/pdf") == 0) {
                documento = p.getInputStream();
                tmp = true;
            }

            Richiesta origin = RichiestaDAO.doRetriveById(id);
            if (origin.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0) {

                if (origin.getStato().compareTo("Disponibile") == 0) {
                    Richiesta r;
                    if(tmp){
                        r = Richiesta.builder()
                                .id(origin.getId())
                                .codFiscaleUtente(u.getCodFiscale())
                                .tipoCarico(tipoCarico)
                                .quantita(quantita)
                                .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataPartenza).getTime()))
                                .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataArrivo).getTime()))
                                .portoPartenza(portoPartenza)
                                .portoArrivo(portoArrivo)
                                .stato(origin.getStato())
                                .documento(documento)
                                .caricato(tmp)
                                .build();
                    }else {
                        r = Richiesta.builder()
                                .id(origin.getId())
                                .codFiscaleUtente(u.getCodFiscale())
                                .tipoCarico(tipoCarico)
                                .quantita(quantita)
                                .dataPartenza(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataPartenza).getTime()))
                                .dataArrivo(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dataArrivo).getTime()))
                                .portoPartenza(portoPartenza)
                                .portoArrivo(portoArrivo)
                                .stato(origin.getStato())
                                .documento(origin.getDocumento())
                                .caricato(tmp)
                                .build();
                    }


                    RichiestaDAO.doUpdate(r);

                    Notifica n = Notifica.builder().oggetto("Richiesta " + r.getId() + " modificata").corpo("La richiesta " + r.getId() + " &egrave; stata modficata dall'armatore " + r.getCodFiscaleUtente()).build();

                    int idNotifica = NotificaDAO.doSave(n);
                    NotificaDAO.doSendToBroker(r, idNotifica);

                    resp.sendRedirect("visualizza-richiesta?id=" + id);
                    return;
                } else {
                    req.setAttribute("errore", "422");
                    req.setAttribute("descrizione", "La richiesta è stata già presa in carico");
                    req.setAttribute("back", "index.jsp");
                }
            }else {
                req.setAttribute("errore", "422");
                req.setAttribute("descrizione", "Questa richiesta non ti appartiene");
                req.setAttribute("back", "index.jsp");
            }
        } catch (DuplicateException | InvalidParameterException | ParseException e) {
            req.setAttribute("errore", "422");
            req.setAttribute("descrizione", "Parametri errati");
            req.setAttribute("back", "index.jsp");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
