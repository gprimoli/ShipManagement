<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="titolo" value="Login"/>
</jsp:include>
<c:if test="${sessionScope.utente == null}">
    <c:redirect url="index.jsp"/>
</c:if>
<main>
    <header class="page-header page-header-compact page-header-light border-bottom bg-white mb-4">
        <div class="container-fluid">
            <h1 class="mt-4">Profilo</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active"><a href="index.jsp">Dashboard</a>/Profilo</li>
            </ol>
        </div>
    </header>
    <!-- Main page content-->
    <div class="container mt-4">
        <c:if test="${requestScope.notifica != null}">
        <div class="alert alert-${requestScope.tipoNotifica}" role="alert">
                ${requestScope.notifica}
        </div>
        </c:if>
        <!-- Account page navigation-->
        <%--            <nav class="nav nav-borders">--%>
        <%--                <a class="nav-link active ml-0" href="account-profile.html">Profile</a>--%>
        <%--                <a class="nav-link" href="account-billing.html">Billing</a>--%>
        <%--                <a class="nav-link" href="account-security.html">Security</a>--%>
        <%--                <a class="nav-link" href="account-notifications.html">Notifications</a>--%>
        <%--            </nav>--%>
        <div class="row">
            <div class="col-xl-8">
                <!-- Account details card-->
                <div class="card mb-4">
                    <div class="card-header">Dettagli Account</div>
                    <div class="card-body">
                        <form action="aggiornautente" method="post">
                            <!-- Form Group (username)-->
                            <div class="form-group">
                                <label class="small mb-1" for="inputEmailAddress">Email</label>
                                <input name="email" class="form-control py-4" id="inputEmailAddress"
                                       type="email"
                                       aria-describedby="emailHelp" placeholder="Inserisci Email"
                                       value="${sessionScope.utente.email}"
                                       required/>
                            </div>
                            <!-- Form Row-->
                            <div class="form-row">
                                <!-- Form Group (first name)-->
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputFirstName">Nome</label>
                                    <input name="nome" class="form-control py-4" id="inputFirstName"
                                           type="text"
                                           placeholder="Inserisci Nome" minlength="2" maxlength="50"
                                           pattern="^[A-Za-z\s]*$"
                                           title="Il campo non pu&oacute; contenere numeri"
                                           value="${sessionScope.utente.nome}"
                                           required/>
                                </div>
                                <!-- Form Group (last name)-->
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputLastName">Cognome</label>
                                    <input name="cognome" class="form-control py-4" id="inputLastName"
                                           type="text" placeholder="Inserisci Cognome" minlength="2"
                                           maxlength="50" pattern="^[A-Za-z\s]*$"
                                           title="Il campo non pu&oacute; contenere numeri"
                                           value="${sessionScope.utente.cognome}"
                                           required/>
                                </div>
                            </div>
                            <!-- Form Row        -->
                            <div class="form-row">
                                <!-- Form Group (organization name)-->
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputDataNascita">Data di Nascita</label>
                                    <input name="dataDiNascita" class="form-control py-4"
                                           id="inputDataNascita"
                                           type="date" placeholder="Inserisci Data di Nascita"
                                           onfocusout="checkData()"
                                           value="${sessionScope.utente.dataNascita.toString()}"
                                           required/>
                                    <div id="dataError" class="invalid-feedback" style="display: none">
                                        Devi avere 18 anni!
                                    </div>
                                </div>
                                <!-- Form Group (location)-->
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputLuogoNascita">Luogo di
                                        Nascita</label>
                                    <input name="luogoDiNascita" class="form-control py-4"
                                           id="inputLuogoNascita" type="text"
                                           placeholder="Inserisci Lugoo di Nascita" minlength="2"
                                           maxlength="50" pattern="^[A-Za-z\s]*$"
                                           title="Il campo non pu&oacute; contenere numeri"
                                           value="${sessionScope.utente.luogoNascita}"
                                           required/>
                                </div>
                            </div>
                            <!-- Save changes button-->
                            <button class="btn btn-primary" type="submit">Aggiorna</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-xl-4">
                <div class="card mb-4">
                    <div class="card-header">Disattiva Account</div>
                    <div class="card-body">
                        <p>La disattivazione del tuo account &eacute; permanente, per poterlo riattivare bisogna
                            contattare l'assistenza e provvedere al riconoscimento tramite documento.
                            Se sei sicuro di voler disattivare il tuo account seleziona il pulsante qui sotto.
                            (*Non puoi eliminare l'account se hai mediazioni in sospeso)</p>
                        <button class="btn btn-outline-danger" data-toggle="modal" data-target="#modal" type="button">Ho
                            compreso, disattiva il mio
                            account
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">

            <c:choose>
            <c:when test="${sessionScope.utente.ruolo.compareTo('broker') == 0}">
            <div class="col-xl-6">
                </c:when>
                <c:otherwise>
                <div class="col-xl-12">
                    </c:otherwise>
                    </c:choose>
                    <div class="card mb-4">
                        <div class="card-header">Cambia Password</div>
                        <div class="card-body">
                            <form action="aggiornapassword" method="post" onsubmit="return validateForm()">
                                <!-- Form Group (new password)-->
                                <div class="form-group">
                                    <label class="small mb-1" for="inputPassword">Password (minimo 8
                                        caratteri)</label>
                                    <input name="password" class="form-control py-4" id="inputPassword"
                                           type="password" placeholder="Inserisci Password"
                                           pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$"
                                           onfocusout="checkPassword()"
                                           required/>
                                </div>
                                <!-- Form Group (confirm password)-->
                                <div class="form-group">
                                    <label class="small mb-1" for="inputConfermaPassword">Conferma Password</label>
                                    <input name="confermapassword" class="form-control py-4" id="inputConfermaPassword"
                                           type="password" placeholder="Inserisci Password"
                                           pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$"
                                           onfocusout="checkPassword()"
                                           required/>
                                </div>
                                <div id="passError" class="invalid-feedback" style="display: none">
                                    Le password devono coincidere!
                                </div>
                                <p>La password deve contenere:
                                <ul>
                                    <li>1 lettera maiuscola</li>
                                    <li>1 lettera minuscola</li>
                                    <li>1 carattere speciale</li>
                                    <li>1 numero</li>
                                </ul>
                                </p>
                                <button class="btn btn-primary" type="submit">Aggiorna Password</button>
                            </form>
                        </div>
                    </div>
                </div>
                <c:if test="${sessionScope.utente.ruolo.compareTo('broker') == 0}">
                    <div class="col-xl-6">
                        <div class="card mb-4">
                            <div class="card-header">Dati Compagnia Broker</div>
                            <div class="card-body">
                                <form action="aggiornacompagnia" method="post">
                                    <div class="form-group">
                                        <label class="small mb-1" for="inputCodFiscaleCompagnia">Codice Fiscale
                                            della Compagnia a cui Appartieni</label>
                                        <input name="codFiscaleCompagnia" class="form-control py-4"
                                               id="inputCodFiscaleCompagnia"
                                               type="text"
                                               aria-describedby="codicefiscaleCompagniaHelp"
                                               value="${requestScope.compagniaBroker.codFiscale}"
                                               placeholder="Inserisci Codice Fiscale Compagnia" maxlength="16"
                                               pattern="^[A-Za-z\s]*$"/>
                                    </div>
                                    <div class="form-row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="small mb-1" for="inputNomeCompagnia">Nome
                                                    Compangia</label>
                                                <input name="nomeCompagnia" class="form-control py-4"
                                                       id="inputNomeCompagnia"
                                                       type="text"
                                                       aria-describedby="nomeCompagniaHelp"
                                                       value="${requestScope.compagniaBroker.nome}"
                                                       placeholder="Inserisci Nome Compagnia" maxlength="50"
                                                       pattern="^[A-Za-z\s]*$"/>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="small mb-1" for="inputNumTelefonoCompagnia">Telefono
                                                    Compangia</label>
                                                <input name="telefonoCompagnia" class="form-control py-4"
                                                       id="inputNumTelefonoCompagnia"
                                                       type="text"
                                                       aria-describedby="telefonoCompagniaHelp"
                                                       value="${requestScope.compagniaBroker.telefono}"
                                                       placeholder="Inserisci Telefono Compagnia" maxlength="30"
                                                       pattern="^[0-9]*$"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="small mb-1" for="inputSedeCompagnia">Sede Legale
                                                    Compangia</label>
                                                <input name="sedeCompagnia" class="form-control py-4"
                                                       id="inputSedeCompagnia"
                                                       type="text"
                                                       aria-describedby="sedeCompagniaHelp"
                                                       value="${requestScope.compagniaBroker.sedeLegale}"
                                                       placeholder="Inserisci Sede Legale Compagnia"
                                                       pattern="^[A-Za-z\s]*$"/>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="small mb-1" for="inputSitoCompagnia">Sito web
                                                    Compangia</label>
                                                <input name="sitoCompagnia" class="form-control py-4"
                                                       id="inputSitoCompagnia"
                                                       type="url"
                                                       aria-describedby="sitoCompagniaHelp"
                                                       value="${requestScope.compagniaBroker.sitoWeb}"
                                                       placeholder="Inserisci Sito Web Compangia"/>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary" type="submit">Aggiorna Compagnia Broker</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

        <div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Conferma Operazione</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Sicuro di voler continuare?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                        <form action="disattivaaccount" method="post">
                            <button type="submit" class="btn btn-primary">Conferma</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
</main>
<script>
    function checkData() {
        if (moment($('#inputDataNascita').val()).isAfter(moment().subtract(18, 'years'))) {
            $('#dataError').show();
            return false;
        } else {
            $('#dataError').hide()
            return true;
        }
    }

    function checkPassword() {
        let pass = $('#inputPassword').val();
        let conf = $('#inputConfermaPassword').val();
        if (pass !== conf) {
            $('#passError').show();
            return false;
        } else {
            $('#passError').hide()
            return true;
        }
    }

    function validateForm() {
        return checkData() && checkPassword();
    }
</script>
<jsp:include page="footer.jsp"/>
