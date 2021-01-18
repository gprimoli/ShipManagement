<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.utente == null}">
    <c:redirect url="/login"/>
</c:if>
<jsp:include page="../header.jsp">
    <jsp:param name="titolo" value="Richiesta"/>
</jsp:include>

<main>
    <header class="page-header page-header-compact page-header-light border-bottom bg-white mb-4">
        <div class="container-fluid">
            <h1 class="mt-4">Richiesta</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active"><a href="index.jsp">Dashboard</a>/Richiesta</li>
            </ol>
        </div>
    </header>
    <div class="container mt-4">
        <div class="row">
            <div class="col-lg-12">
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Visualizza Richiesta</h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) == 0}">
                        <form enctype="multipart/form-data" action="modifica-richiesta" method="post"
                              onsubmit="return validateRichiesta()">
                            </c:if>
                            <input name="id" type="hidden" value="${requestScope.richiesta.id}"/>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="tipoCarico">Tipo di Carico</label>
                                    <select name="tipoCarico" id="tipoCarico" class="custom-select"
                                            <c:if test="${requestScope.imbarcazione.codFiscaleUtente.compareTo(sessionScope.utente.codFiscale) != 0}"> disabled </c:if>
                                            required>
                                        <c:forEach
                                                items="Container,Carico alla Rinfusa,Prodotti Chimici Solidi,Prodotti Chimici Liquidi,Prodotti Chimici Gassosi,Autoveicoli"
                                                var="item">
                                            <option <c:if
                                                    test="${requestScope.richiesta.tipoCarico.compareTo(item) == 0}"> selected </c:if>
                                                    value="${item}">${item}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputQuantita">Quantit&aacute;</label>
                                    <input name="quantita" class="form-control py-4" id="inputQuantita"
                                           type="number"
                                           placeholder="Inserisci Quantit&aacute;"
                                           value="${requestScope.richiesta.quantita}"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="portoPartenza">Porto di Partenza</label>
                                    <select name="portoPartenza" id="portoPartenza" onfocusout="porto()"
                                            class="custom-select"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) != 0}">
                                                disabled
                                            </c:if>
                                            required>
                                        <c:forEach items="${requestScope.porti}" var="porto">
                                            <option <c:if
                                                    test="${porto.localcode.compareTo(requestScope.richiesta.portoPartenza) == 0}"> selected </c:if>
                                                    value="${porto.localcode}">${porto.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="dataPartenza">Data di Partenza</label>
                                    <input name="dataPartenza" class="form-control py-4" id="dataPartenza"
                                           onfocusout="data()"
                                           type="date"
                                           value="${requestScope.richiesta.dataPartenza}"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="portoArrivo">Porto di Arrivo</label>
                                    <select name="portoArrivo" id="portoArrivo" class="custom-select"
                                            onfocusout="porto()"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) != 0}">
                                                disabled
                                            </c:if>
                                            required>
                                        <c:forEach items="${requestScope.porti}" var="porto">
                                            <option <c:if
                                                    test="${porto.localcode.compareTo(requestScope.richiesta.portoArrivo) == 0}"> selected </c:if>
                                                    value="${porto.localcode}">${porto.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="dataArrivo">Data di Arrivo</label>
                                    <input name="dataArrivo" class="form-control py-4" id="dataArrivo"
                                           type="date"
                                           onfocusout="data()"
                                           value="${requestScope.richiesta.dataArrivo}"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                                <div id="portoError" class="invalid-feedback" style="display: none">
                                    Non possono essere uguali il porto di arrivo e quello di partenza!
                                </div>
                                <div id="dataError" class="invalid-feedback" style="display: none">
                                    La data di partenza non pu&oacute; superare la data di arrivo!
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label class="small mb-1" for="inputDocumento">Documento</label>
                                    <input name="documento" class="form-control py-4" id="inputDocumento"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           value="${requestScope.richiesta.documento}"
                                           type="file"/>
                                    <c:if test="${!requestScope.richiesta.caricato}">
                                        <p>Documento non caricato</p>
                                    </c:if>
                                </div>
                            </div>
                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) == 0}">
                            <button type="submit" class="btn btn-primary">Modifica</button>
                    </div>
                    </form>
                    </c:if>
                    <div class="card-footer">
                        <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.richiesta.codFiscaleUtente) == 0}">
                            <button class="btn btn-danger"
                                    data-toggle="modal" data-target="#rimuovi"
                                    type="button">
                                Rimuovi
                            </button>
                        </c:if>
                        <c:if test="${requestScope.richiesta.caricato}">
                            <a href="visualizza-richiesta-documento?id=${requestScope.richiesta.id}">
                                <button class="btn btn-success">Visualizza Documento</button>
                            </a>
                        </c:if>

                        <c:if test="${sessionScope.utente.broker}">
                            <button class="btn btn-primary" data-toggle="modal"
                                    data-target="#aggiungiMediazione"
                                    type="button">
                                Aggiungi Alla mediazione
                            </button>
                        </c:if>

                        <a href="index">
                            <button class="btn btn-primary">Indietro</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="rimuovi" tabindex="-1" role="dialog" aria-hidden="true">
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
                    <form action="rimuovi-richiesta" method="post">
                        <input name="id" value="${requestScope.richiesta.id}" type="hidden"/>
                        <button type="submit" class="btn btn-primary">Conferma</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${sessionScope.utente.broker}">
        <div class="modal fade" id="aggiungiMediazione" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Aggiungi alla Mediazione</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Aggiungi alla mediazine</p>
                        <form action="aggiungi-richiesta-mediazione" method="post">
                            <select name="mediazione" class="custom-select" required>
                                <c:forEach items="${requestScope.mediazioni}" var="mediazione">
                                    <option value="${mediazione.id}">${mediazione.nome}</option>
                                </c:forEach>
                            </select>
                            <input name="id" value="${requestScope.richiesta.id}" type="hidden"/>
                            <button class="btn btn-primary">Aggiungi</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

</main>


<script>
    $(document).ready(function () {
        $("#dataArrivo").attr("min", moment().add(1, 'days').format("YYYY-MM-DD"));
        $("#dataPartenza").attr("min", moment().add(1, 'days').format("YYYY-MM-DD"));
    });


    function checkUguale(val, val1) {
        return val1 === val
    }

    function porto() {
        let porto1 = $('#portoPartenza').val()
        let porto2 = $('#portoArrivo').val()
        if (porto1 === porto2) {
            $('#portoError').show();
            return false;
        }
        $('#portoError').hide();
        return true;
    }

    function data() {
        let dataPartenza = moment($('#dataPartenza').val());
        let dataArrivo = moment($('#dataArrivo').val());
        if (moment(dataPartenza).isAfter(moment(dataArrivo))) {
            $('#dataError').show();
            return false;
        } else {
            $('#dataError').hide();
            return true;
        }
    }

    function validateRichiesta() {
        return porto() && data();
    }
</script>

<jsp:include page="../footer.jsp"/>
