<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="titolo" value="Password Dimenticata"/>
</jsp:include>
<c:if test="${sessionScope.utente != null}">
    <c:redirect url="index.jsp"/>
</c:if>
<main>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-5">
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Recupero Password</h3>
                    </div>
                    <div class="card-body">
                        <div class="small mb-3 text-muted">Inserisci il tuo indirizzo mail per ricevere una nuova
                            password.
                        </div>
                        <form method="post" action="recuperopassword">
                            <div class="form-group">
                                <label class="small mb-1" for="inputEmailAddress">Email</label>
                                <input name="email" class="form-control py-4" id="inputEmailAddress" type="email"
                                       aria-describedby="emailHelp" placeholder="Insirisci Email"
                                       value="g.rimoli@studenti.unisa.it" required/>
                            </div>
                            <div class="form-group d-flex align-items-center justify-content-between mt-4 mb-0">
                                <a class="small" href="login.jsp">Ritorna alla pagina di Login</a>
                                <button class="btn btn-primary">Invia Nuova Password</button>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                        <div class="small"><a href="register.jsp">Hai bisogno di un nuovo account? Registrati!</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>

