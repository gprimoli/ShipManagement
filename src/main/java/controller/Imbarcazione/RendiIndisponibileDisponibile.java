package controller.Imbarcazione;

import lombok.SneakyThrows;
import model.Imbarcazione.Imbarcazione;
import model.Imbarcazione.ImbarcazioneDAO;
import model.Mediazione.MediazioneDAO;
import model.Notifica.Notifica;
import model.Notifica.NotificaDAO;
import model.Utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/disponibile-indisponibile"
)
public class RendiIndisponibileDisponibile extends HttpServlet {

    @SneakyThrows
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Utente u = (Utente) s.getAttribute("utente");
        if (u == null) {
            resp.sendRedirect("index");
            return;
        }
        String imo = req.getParameter("imo");
        String forward = "index";
        Imbarcazione i = ImbarcazioneDAO.doRetriveById(imo);

        if(i.getCodFiscaleUtente().compareTo(u.getCodFiscale()) == 0){
            if(i.isDisponibile()){

                Notifica n = Notifica.builder().corpo("Imbarcazione " + i.getImo() + " eliminata").oggetto("L'imbarcazione " + i.getNome() + " &egrave; stata resa indisponibile dall'armatore " + i.getCodFiscaleUtente()).build();

                NotificaDAO.doSaveAll(i, n);

                ImbarcazioneDAO.doDisponibileIndisponibile(i);

                req.setAttribute("notifica", "Imbarcazione resa indisponibile!");
                req.setAttribute("tipoNotifica", "danger");
            }else{
                if(!MediazioneDAO.doCheck(i)){
                    ImbarcazioneDAO.doDisponibileIndisponibile(i);
                    req.setAttribute("notifica", "Imbarcazione resa disponibile!");
                    req.setAttribute("tipoNotifica", "success");
                } else{
                    req.setAttribute("notifica", "L'imbarcazione fa parte di una mediazione non può essere disponibile.!");
                    req.setAttribute("tipoNotifica", "warnign");
                }
            }
        }else {
            req.setAttribute("notifica", "Non hai i permessi per rimuovere la richiesta perchè non ne fai parte!");
            req.setAttribute("tipoNotifica", "danger");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }
}
