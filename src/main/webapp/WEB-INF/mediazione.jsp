<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.utente == null}">
    <c:redirect url="/login"/>
</c:if>
<jsp:include page="../header.jsp">
    <jsp:param name="titolo" value="Mediazione"/>
</jsp:include>

<script>
    $(document).ready(function () {
        $("#richiesteCoinvolte").DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.22/i18n/Italian.json"
            }
        });
        $("#imbarcazioniCoinvolte").DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.22/i18n/Italian.json"
            }
        });
    });
</script>

<main>
    <header class="page-header page-header-compact page-header-light border-bottom bg-white mb-4">
        <div class="container-fluid">
            <h1 class="mt-4">Mediazione</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active"><a href="index.jsp">Dashboard</a>/Mediazione</li>
            </ol>
        </div>
    </header>
    <div class="container mt-4">
        <c:if test="${requestScope.notifica != null}">
            <div class="alert alert-${requestScope.tipoNotifica}" role="alert">
                    ${requestScope.notifica}
            </div>
        </c:if>
        <div class="row">
            <div class="col-xl-12">
                <div class="card mb-4">
                    <div class="card-header">Visualizza Mediazione</div>
                    <div class="card-body">
                        <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.mediazione.codFiscaleUtente) == 0}">
                        <form action="modifica-mediazione" method="post" enctype="multipart/form-data">
                            </c:if>
                            <input name="id" type="hidden" value="${requestScope.mediazione.id}"/>
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputNome">Nome</label>
                                    <input name="nome" class="form-control py-4" id="inputNome"
                                           type="text"
                                           placeholder="Inserisci Nome" minlength="2" maxlength="50"
                                           pattern="^[A-Za-z1-9\s]*$"
                                           title="Il campo non pu&oacute; contenere caratteri speciali"
                                           value="${requestScope.mediazione.nome}"
                                            <c:if test="${!sessionScope.utente.broker}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputStato">Stato</label>
                                    <input name="nome" class="form-control py-4" id="inputStato"
                                           type="text"
                                           value="${requestScope.mediazione.stato}" readonly/>
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputContratto">Contratto</label>
                                    <input name="documento" class="form-control py-4" id="inputContratto"
                                           type="file"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.mediazione.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           value="${requestScope.mediazione.contratto}"/>
                                </div>
                            </div>
                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.mediazione.codFiscaleUtente) == 0}">
                            <button class="btn btn-primary" type="submit">Modifica</button>
                        </form>
                        <c:if test="${requestScope.mediazione.stato.compareTo('Default') == 0 || requestScope.mediazione.stato.compareTo('Richiesta Modifica') == 0}">
                            <form action="finalizza-mediazione" method="post">
                                <input name="id" value="${requestScope.mediazione.id}" type="hidden"/>
                                <button class="btn btn-primary">Finalizza Mediazione</button>
                            </form>
                        </c:if>
                        </c:if>
                        <div class="card-footer">
                            <c:if test="${requestScope.mediazione.stato.compareTo('Default') == 0 || requestScope.mediazione.stato.compareTo('In attesa di Firma') == 0 || requestScope.mediazione.stato.compareTo('Richiesta Modifica') == 0}">
                                <button class="btn btn-outline-danger" data-toggle="modal"
                                        data-target="#eliminazione"
                                        type="button">Rimuovi
                                </button>
                            </c:if>

                            <c:if test="${requestScope.mediazione.caricato}">
                                <a href="visualizza-mediazione-contratto?id=${requestScope.mediazione.id}">
                                    <button class="btn btn-success">Visualizza Contratto</button>
                                </a>
                            </c:if>

                            <c:forEach items="${requestScope.firme}" var="firma">
                                <c:if test="${sessionScope.utente.codFiscale.compareTo(firma) == 0}">
                                    <c:set var="tmp" value="${true}"/>
                                </c:if>
                            </c:forEach>

                            <c:if test="${!tmp && requestScope.mediazione.stato.compareTo('In Attesa di Firma') == 0 && !sessionScope.utente.broker}">
                                <form action="firma">
                                    <input name="id" value="${requestScope.mediazione.id}" type="hidden"/>
                                    <button class="btn btn-success" type="submit">Firma Mediazione</button>
                                </form>
                                <button class="btn btn-danger" data-toggle="modal"
                                        data-target="#nofirma" type="button">
                                    Rifiuta Firma
                                </button>
                            </c:if>
                            <a href="index">
                                <button class="btn btn-primary">Indietro</button>
                            </a>
                        </div>
                        <p class="h5 mt-2 mb-1">Dati Broker:</p>
                        <div class="form-row">
                            <!-- Form Group (first name)-->
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="inputNomeBroker">Nome</label>
                                <input name="cognome" class="form-control py-4" id="inputNomeBroker"
                                       type="text"
                                       value="${requestScope.utente.nome}"
                                       readonly/>
                            </div>
                            <!-- Form Group (last name)-->
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="inputCognomeBroker">Cognome</label>
                                <input name="cognome" class="form-control py-4" id="inputCognomeBroker"
                                       type="text"
                                       value="${requestScope.utente.cognome}"
                                       readonly/>
                            </div>
                            <div class="form-group col-md-12">
                                <label class="small mb-1" for="inputTelefonoBroker">Telefono</label>
                                <input name="cognome" class="form-control py-4" id="inputTelefonoBroker"
                                       type="text"
                                       value="${requestScope.utente.telefono}"
                                       readonly/>
                            </div>
                            <div class="form-group col-md-12">
                                <label class="small mb-1" for="inputEmailBroker">Email</label>
                                <input name="cognome" class="form-control py-4" id="inputEmailBroker"
                                       type="text"
                                       value="${requestScope.utente.email}"
                                       readonly/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xl-6">
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-ship mr-1"></i>
                        Imbarcazioni Coinvolte
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${requestScope.inbarcazioni.size() != 0}">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="imbarcazioniCoinvolte" width="100%"
                                           cellspacing="0">
                                        <thead>
                                        <tr>
                                            <th>Imo</th>
                                            <th>Nome</th>
                                            <th>Tipologia</th>
                                            <th>Azioni</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.inbarcazioni}" var="imbarcazione"
                                                   varStatus="indice">
                                            <tr>
                                                <td>${imbarcazione.imo}</td>
                                                <td>${imbarcazione.nome}</td>
                                                <td>${imbarcazione.tipologia}</td>
                                                <td>
                                                    <button class="btn btn-danger" data-toggle="modal"
                                                            data-target="#rimuoviImbarcazione" type="button"
                                                            onclick="$('#imbarcazioneIMO').attr('value', '${imbarcazione.id}')">
                                                        Rimuovi dalla Mediazione
                                                    </button>
                                                    <a href="visualizza-imbarcazione?id=${imbarcazione.id}">
                                                        <button class="btn btn-primary">Visualizza</button>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="h6 text-center">Non ci sono risultati</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <div class="col-xl-6">
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-ship mr-1"></i>
                        Richieste Coinvolte
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${requestScope.richieste.size() != 0}">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="richiesteCoinvolte" width="100%"
                                           cellspacing="0">
                                        <thead>
                                        <tr>
                                            <th>Tipo Carico</th>
                                            <th>Quantit&aacute;</th>
                                            <th>Data Partenza</th>
                                            <th>Data Arrivo</th>
                                            <th>Azioni</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.richieste}" var="richiesta"
                                                   varStatus="indice">
                                            <tr>
                                                <td>${richiesta.tipoCarico}</td>
                                                <td>${richiesta.quantita}</td>
                                                <td>${richiesta.dataPartenza}</td>
                                                <td>${richiesta.dataArrivo}</td>
                                                <td>
                                                    <button class="btn btn-danger" data-toggle="modal"
                                                            data-target="#rimuoviRichiesta" type="button"
                                                            onclick="$('#richiestaID').attr('value', '${richiesta.id}')">
                                                        Rimuovi dalla Mediazione
                                                    </button>
                                                    <a href="visualizza-richiesta?id=${richiesta.id}">
                                                        <button class="btn btn-primary">Visualizza</button>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="h6 text-center">Non ci sono risultati</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.mediazione.codFiscaleUtente) == 0}">

        <div class="modal fade" id="eliminazione" tabindex="-1" role="dialog" aria-hidden="true">
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
                        <form action="rimuovi-mediazione" method="post">
                            <input name="id" value="${requestScope.mediazione.id}" type="hidden"/>
                            <button type="submit" class="btn btn-primary">Conferma</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="rimuoviRichiesta" tabindex="-1" role="dialog" aria-hidden="true">
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
                        <form action="rimuvoi-dalla-mediazione-richiesta" method="post">
                            <input id="richiestaID" name="richiestaID" value="" type="hidden">
                            <input name="mediazioneID" value="${requestScope.mediazione.id}" type="hidden">
                            <button type="submit" class="btn btn-primary">Conferma</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="rimuoviImbarcazione" tabindex="-1" role="dialog" aria-hidden="true">
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
                        <form action="rimuovi-dalla-mediazione-imbarcazione" method="post">
                            <input id="imbarcazioneIMO" name="imbarcazioneID" value="" type="hidden">
                            <input id="mediazioneID" name="mediazioneID" value="${requestScope.mediazione.id}"
                                   type="hidden">
                            <button type="submit" class="btn btn-primary">Conferma</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <div class="modal fade" id="nofirma" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Rifiuta Firma</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="rifiuta-firma" method="post">
                        <input name="motivazione" value="" type="text" rows="5"/>
                        <input name="mediazioineID" value="${requestScope.mediazione.id}" type="hidden"/>
                        <button class="btn btn-danger">Rifiuta</button>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                </div>
            </div>
        </div>
    </div>


</main>

<jsp:include page="../footer.jsp"/>
