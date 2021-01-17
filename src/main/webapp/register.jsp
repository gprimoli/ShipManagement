<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp">
    <jsp:param name="titolo" value="Registrazione"/>
</jsp:include>
<c:if test="${sessionScope.utente != null}">
    <c:redirect url="index.jsp"/>
</c:if>
<main>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-7">
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Crea un Account</h3>
                    </div>
                    <div class="card-body">
                        <form onsubmit="return validateForm()" action="registrazione" method="post">
                            <div class="form-row mb-2">
                                <div class="btn-group btn-group-toggle col-md-12" data-toggle="buttons">
                                    <label class="btn btn-primary active mx-1">
                                        <input type="radio" value="cliente" name="ruolo"
                                               id="option1" onclick="$('form').trigger('reset');
                                                   $('#plusForm').hide();
                                                   $('#baseForm').show();
                                                   $('#tipoRuolo').text('Cliente');
                                                   $('#plusForm input').each((index, item) => item.removeAttribute('required'));">Cliente</label>
                                    <label class="btn btn-success mx-1">
                                        <input type="radio" value="armatore" name="ruolo"
                                               id="option2"
                                               onclick="$('form').trigger('reset'); $('#plusForm').hide(); $('#baseForm').show();$('#tipoRuolo').text('Armatore');$('#plusForm input').each((index, item) => item.removeAttribute('required'));">Armatore</label>
                                    <label class="btn btn-warning mx-1">
                                        <input type="radio" value="broker" name="ruolo" id="option3"
                                               onclick="$('form').trigger('reset'); $('#baseForm').show();$('#tipoRuolo').text('Broker'); $('#plusForm').show();$('#plusForm input').each((index, item) => item.setAttribute('required', ''));">
                                        Broker</label>
                                </div>
                            </div>
                            <div id="baseForm" style="display: none">
                                <p>Tipo di account scelto: <span id="tipoRuolo"></span></p>
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="small mb-1" for="inputFirstName">Nome</label>
                                            <input name="nome" class="form-control py-4" id="inputFirstName"
                                                   type="text"
                                                   placeholder="Inserisci Nome" minlength="2" maxlength="50"
                                                   pattern="^[A-Za-z\s]*$"
                                                   title="Il campo non pu&oacute; contenere numeri"
                                                   value="asd"
                                                   required/>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="small mb-1" for="inputLastName">Cognome</label>
                                            <input name="cognome" class="form-control py-4" id="inputLastName"
                                                   type="text" placeholder="Inserisci Cognome" minlength="2"
                                                   maxlength="50" pattern="^[A-Za-z\s]*$"
                                                   title="Il campo non pu&oacute; contenere numeri"
                                                   value="asd"
                                                   required/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="small mb-1" for="inputDataNascita">Data di Nascita</label>
                                            <input name="dataDiNascita" class="form-control py-4"
                                                   id="inputDataNascita"
                                                   type="date" placeholder="Inserisci Data di Nascita"
                                                   onfocusout="checkData()"
                                                   required/>
                                            <div id="dataError" class="invalid-feedback" style="display: none">
                                                Devi avere 18 anni!
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="small mb-1" for="inputLuogoNascita">Luogo di
                                                Nascita</label>
                                            <input name="luogoDiNascita" class="form-control py-4"
                                                   id="inputLuogoNascita" type="text"
                                                   placeholder="Inserisci Lugoo di Nascita" minlength="2"
                                                   maxlength="50" pattern="^[A-Za-z]*$"
                                                   title="Il campo non pu&oacute; contenere numeri"
                                                   value="Napoli"
                                                   required/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="small mb-1" for="inputCodFiscale">Codice Fiscale</label>
                                    <input name="codFiscale" class="form-control py-4" id="inputCodFiscale"
                                           type="text"
                                           aria-describedby="codicefiscaleHelp"
                                           placeholder="Inserisci Codice Fiscale" minlength="16" maxlength="16"
                                           pattern="^[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]$"
                                           title="Il campo non contenere un codice fiscale corretto"
                                           value="RMLGNR99R27F839Q"
                                           required/>
                                </div>
                                <div class="form-group">
                                    <label class="small mb-1" for="inputNumTelefono">Telefono</label>
                                    <input name="telefono" class="form-control py-4" id="inputNumTelefono"
                                           type="text"
                                           aria-describedby="teledonoHelp"
                                           placeholder="Inserisci numero di telefono" maxlength="10"
                                           pattern="^[0-9]+$" title="Il campo non pu&oacute; contenere lettere"
                                           value="3337800499"
                                           required/>
                                </div>
                                <div class="form-group">
                                    <label class="small mb-1" for="inputEmailAddress">Email</label>
                                    <input name="email" class="form-control py-4" id="inputEmailAddress"
                                           type="email"
                                           aria-describedby="emailHelp" placeholder="Inserisci Email"
                                           value="g.rimoli@studenti.unisa.it"
                                           required/>
                                </div>
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="small mb-1" for="inputPassword">Password (minimo 8
                                                caratteri)</label>
                                            <input name="password" class="form-control py-4" id="inputPassword"
                                                   type="password" placeholder="Inserisci Password"
                                                   pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$"
                                                   onfocusout="checkPassword()"
                                                   value="@Gennaro1"
                                                   required/>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="small mb-1" for="inputConfirmPassword">Conferma
                                                Password</label>
                                            <input name="confermaPassword" class="form-control py-4"
                                                   id="inputConfirmPassword" type="password"
                                                   placeholder="Conferma Password"
                                                   pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$"
                                                   onfocusout="checkPassword()"
                                                   value="@Gennaro1"
                                                   required/>
                                        </div>
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
                                </div>
                                <div id="plusForm" style="display: none">
                                    <div class="form-group">
                                        <label class="small mb-1" for="inputCodFiscaleCompagnia">Codice Fiscale
                                            della Compagnia a cui Appartieni</label>
                                        <input name="codFiscaleCompagnia" class="form-control py-4"
                                               id="inputCodFiscaleCompagnia"
                                               type="text"
                                               maxlength="16"
                                               aria-describedby="codicefiscaleCompagniaHelp"
                                               placeholder="Inserisci Codice Fiscale Compagnia" pattern="^[A-Z]{6}[A-Z0-9]{2}[A-Z][A-Z0-9]{2}[A-Z][A-Z0-9]{3}[A-Z]$"/>
                                    </div>
                                    <div class="form-row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="small mb-1" for="inputNomeCompagnia">Nome
                                                    Compangnia</label>
                                                <input name="nomeCompagnia" class="form-control py-4"
                                                       id="inputNomeCompagnia"
                                                       type="text"
                                                       aria-describedby="nomeCompagniaHelp"
                                                       placeholder="Inserisci Nome Compagnia" pattern="^[A-Za-z\s]*$"/>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="small mb-1" for="inputNumTelefonoCompagnia">Telefono
                                                    Compangnia</label>
                                                <input name="telefonoCompagnia" class="form-control py-4"
                                                       id="inputNumTelefonoCompagnia"
                                                       type="text"
                                                       aria-describedby="telefonoCompagniaHelp"
                                                       placeholder="Inserisci Telefono Compagnia" pattern="^[0-9]*$"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="small mb-1" for="inputSedeCompagnia">Sede Legale
                                                    Compangnia</label>
                                                <input name="sedeCompagnia" class="form-control py-4"
                                                       id="inputSedeCompagnia"
                                                       type="text"
                                                       aria-describedby="sedeCompagniaHelp"
                                                       placeholder="Inserisci Sede Legale Compagnia" pattern="^[A-Za-z\s]*$"/>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="small mb-1" for="inputSitoCompagnia">Sito web
                                                    Compangnia</label>
                                                <input name="sitoCompagnia" class="form-control py-4"
                                                       id="inputSitoCompagnia"
                                                       type="url"
                                                       aria-describedby="sitoCompagniaHelp"
                                                       placeholder="Inserisci Sito Web Compangnia"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button class="btn btn-primary btn-block" type="submit">Registra</button>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                        <div class="small"><a href="login.jsp">Hai gi&aacute un account? Effettua il Login</a></div>
                    </div>
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
        let conf = $('#inputConfirmPassword').val();
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
