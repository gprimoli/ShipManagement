<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.utente == null}">
    <c:redirect url="./login.jsp"/>
</c:if>

<jsp:include page="header.jsp">
    <jsp:param name="titolo" value="DashBoard"/>
</jsp:include>

<script>
    $(document).ready(function () {
        $("#richieste").DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.22/i18n/Italian.json"
            }
        });
        $("#imbarcazioni").DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.22/i18n/Italian.json"
            }
        });
        $("#mediazioni").DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.22/i18n/Italian.json"
            }
        });
        $("#notifiche").DataTable({
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.22/i18n/Italian.json"
            }
        });
    });
</script>
<div id="layoutSidenav_content mt-0">
    <main>
        <div class="container-fluid">
            <h1 class="mt-4">Dashboard</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Dashboard</li>
            </ol>
            <div class="row">
                <div class="col-xl-4 mb-2 mx-auto">
                    <p>Azioni Disponibili:
                        <c:choose>
                            <c:when test="${sessionScope.utente.cliente}">
                                <button class="btn btn-primary" data-toggle="modal"
                                        data-target="#aggiungiRichiesta" type="button">Aggiungi Richiesta
                                </button>
                            </c:when>
                            <c:when test="${sessionScope.utente.armatore}">
                                <button class="btn btn-primary" data-toggle="modal"
                                        data-target="#aggiungiImbarcazione" type="button">Aggiungi
                                    Imbarcazione
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-primary" data-toggle="modal"
                                        data-target="#aggiungiMediazione" type="button">Aggiungi Mediazione
                                </button>
                                <a href="ricerca-mediazioni">
                                    <button class="btn btn-warning">Ricerca</button>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>

            <div class="row">
                <div class="col-xl-6">
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-envelope-open-text mr-1"></i>
                            Notifiche
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${requestScope.notifiche.size() != 0}">
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="notifiche" width="100%" cellspacing="0">
                                            <thead>
                                            <tr>
                                                <th>N.</th>
                                                <th>Oggetto</th>
                                                <th>Azioni</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${requestScope.notifiche}" var="notifica"
                                                       varStatus="indice">
                                                <tr>
                                                    <td>${indice.count}</td>
                                                    <td>${notifica.oggetto}</td>
                                                    <td><a href="visualizza-notifica?id=${notifica.id}">
                                                        <button class="btn btn-primary">Visualizza</button>
                                                    </a></td>
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
                            <i class="fas fa-hands-helping mr-1"></i>
                            Mediazioni
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <c:choose>
                                    <c:when test="${requestScope.mediazioni.size() != 0}">
                                        <table class="table table-bordered" id="mediazioni" width="100%"
                                               cellspacing="0">
                                            <thead>
                                            <tr>
                                                <th>N.</th>
                                                <th>Nome</th>
                                                <th>Stato</th>
                                                <th>Azioni</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${requestScope.mediazioni}" var="mediazione"
                                                       varStatus="indice">
                                                <tr>
                                                    <td>${indice.count}</td>
                                                    <td>${mediazione.nome}</td>
                                                    <td>${mediazione.stato}</td>
                                                    <td><a href="visualizza-mediazione?id=${mediazione.id}">
                                                        <button class="btn btn-primary">Visualizza</button>
                                                    </a></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
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
            <div class="row">
                <c:choose>
                    <c:when test="${sessionScope.utente.armatore}">
                        <div class="col-xl-12">
                            <div class="card mb-4">
                                <div class="card-header">
                                    <i class="fas fa-ship mr-1"></i>
                                    Imbarcazioni
                                </div>
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${requestScope.imbarcazioni.size() != 0}">
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="imbarcazioni" width="100%"
                                                       cellspacing="0">
                                                    <thead>
                                                    <tr>
                                                        <th>N.</th>
                                                        <th>Imo</th>
                                                        <th>Nome</th>
                                                        <th>Tipologia</th>
                                                        <th>Disponibile</th>
                                                        <th>Azioni</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${requestScope.imbarcazioni}" var="imbarcazione"
                                                               varStatus="indice">
                                                        <tr>
                                                            <td>${indice.count}</td>
                                                            <td>${imbarcazione.imo}</td>
                                                            <td>${imbarcazione.nome}</td>
                                                            <td>${imbarcazione.tipologia}</td>
                                                            <td>${imbarcazione.disponibile ? 'Disponibile' : 'Non disponibile'}</td>
                                                            <td>
                                                                <a href="visualizza-imbarcazione?id=${imbarcazione.id}">
                                                                    <button class="btn btn-primary">Visualizza</button>
                                                                </a></td>
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
                    </c:when>
                    <c:when test="${sessionScope.utente.cliente}">
                        <div class="col-xl-12">
                            <div class="card mb-4">
                                <div class="card-header">
                                    <i class="fas fa-dolly mr-1"></i>
                                    Richieste
                                </div>
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${requestScope.richieste.size() != 0}">
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="richieste" width="100%"
                                                       cellspacing="0">
                                                    <thead>
                                                    <tr>
                                                        <th>N.</th>
                                                        <th>Tipo di carico</th>
                                                        <th>Quantit&aacute;</th>
                                                        <th>Data di Partenza</th>
                                                        <th>Data di Arrivo</th>
                                                        <th>Stato</th>
                                                        <th>Azioni</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${requestScope.richieste}" var="richiesta"
                                                               varStatus="indice">
                                                        <tr>
                                                            <td>${indice.count}</td>
                                                            <td>${richiesta.tipoCarico}</td>
                                                            <td>${richiesta.quantita}</td>
                                                            <td>${richiesta.dataPartenza}</td>
                                                            <td>${richiesta.dataArrivo}</td>
                                                            <td>${richiesta.stato}</td>
                                                            <td><a href="visualizza-richiesta?id=${richiesta.id}">
                                                                <button class="btn btn-primary">Visualizza</button>
                                                            </a></td>
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
                    </c:when>
                </c:choose>
            </div>
        </div>
    </main>

    <c:if test="${sessionScope.utente.armatore}">
        <div class="modal fade" id="aggiungiImbarcazione" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Aggiungi Imbarcazione</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form enctype="multipart/form-data" action="aggiungi-imbarcazione" method="post">
                        <div class="modal-body">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputImo">Imo</label>
                                    <input name="imo" class="form-control py-4" id="inputImo"
                                           type="text" maxlength="7"
                                           placeholder="Inserisci Imo"
                                           value="ASD"
                                           required/>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputNomeImbarcazione">Nome</label>
                                    <input name="nome" class="form-control py-4" id="inputNomeImbarcazione"
                                           type="text"
                                           value="ASD"
                                           required/>
                                </div>

                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="tipologia">Tipologia</label>
                                    <select name="tipologia" id="tipologia" class="custom-select" required>
                                        <option selected value="Portacontainer">Portacontainer</option>
                                        <option value="Carboniera">Carboniera</option>
                                        <option value="Chimichiera">Chimichiera</option>
                                        <option value="Lift-on/Lift-off">Lift-on/Lift-off</option>
                                        <option value="Nave da Carico">Nave da Carico</option>
                                        <option value="Nave Frigorifera">Nave Frigorifera</option>
                                        <option value="Portarinfuse">Portarinfuse</option>
                                        <option value="Roll-on/Roll-off">Roll-on/Roll-off</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputQuantitaMAX">Quantit&aacute; Massima
                                        Trasportabile</label>
                                    <input name="quantita" class="form-control py-4" id="inputQuantitaMAX"
                                           type="number" step="1000"
                                           value="2000"
                                           required/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputBandiera">Bandiera</label>
                                    <select name="bandiera" id="inputBandiera" class="custom-select" required>
                                        <c:forEach items="AF,AL,DZ,AD,AO,AI,AQ,AG,AN,SA,AR,AM,AW,AU,AT,AZ,BS,BH,BD,BB,BE,BZ,BJ,BM,BY,BT,BO,BA,BW,BR,BN,BG,BF,BI,KH,CM,CA,CV,TD,CL,CN,CY,VA,CO,KM,KP,KR,CR,CI,HR,CU,DK,DM,EC,EG,IE,SV,AE,ER,EE,ET,RU,FJ,PH,FI,FR,GA,GM,GE,DE,GH,JM,JP,GI,DJ,JO,GR,GD,GL,GP,GU,GT,GN,GW,GQ,GY,GF,HT,HN,HK,IN,ID,IR,IQ,BV,CX,HM,KY,CC,CK,FK,FO,MH,MP,UM,NF,SB,TC,VI,VG,IL,IS,IT,KZ,KE,KG,KI,KW,LA,LV,LS,LB,LR,LY,LI,LT,LU,MO,MK,MG,MW,MV,MY,ML,MT,MA,MQ,MR,MU,YT,MX,MD,MC,MN,MS,MZ,MM,NA,NR,NP,NI,NE,NG,NU,NO,NC,NZ,OM,NL,PK,PW,PA,PG,PY,PE,PN,PF,PL,PT,PR,QA,GB,CZ,CF,CG,CD,DO,RE,RO,RW,EH,KN,PM,VC,WS,AS,SM,SH,LC,ST,SN,XK,SC,SL,SG,SY,SK,SI,SO,ES,LK,FM,US,ZA,GS,SD,SR,SJ,SE,CH,SZ,TJ,TH,TW,TZ,IO,TF,PS,TL,TG,TK,TO,TT,TN,TR,TM,TV,UA,UG,HU,UY,UZ,VU,VE,VN,WF,YE,ZM,ZW,RS,ME,TP,GG" var="item">
                                            <option value="${item}">${item}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputAnnoCostruzione">Anno Costruzione</label>
                                    <input name="anno" class="form-control py-4" id="inputAnnoCostruzione"
                                           type="number" value="2000" step="1"
                                           required/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputlunghezza">Lunghezza</label>
                                    <input name="lunghezza" class="form-control py-4" id="inputlunghezza"
                                           type="number" step="1"
                                           value="125"
                                           required/>
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputAmpiezza">Ampiezza</label>
                                    <input name="ampiezza" class="form-control py-4" id="inputAmpiezza"
                                           type="number" step="1"
                                           value="125"
                                           required/>
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputAltezza">Altezza (Pescaggio)</label>
                                    <input name="altezza" class="form-control py-4" id="inputAltezza"
                                           type="number" step="1"
                                           value="125"
                                           required/>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-12">
                                        <label class="small mb-1" for="inputDocumentoImbarcazione">Documento</label>
                                        <input name="documento" class="form-control py-4"
                                               id="inputDocumentoImbarcazione"
                                               type="file"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                            <button type="submit" class="btn btn-primary">Aggiungi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${sessionScope.utente.cliente}">
        <div class="modal fade" id="aggiungiRichiesta" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Aggiungi Richiesta</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form enctype="multipart/form-data" action="aggiungi-richiesta" method="post"
                          onsubmit="return validateRichiesta()">
                        <div class="modal-body">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="tipoCarico">Tipo di Carico</label>
                                    <select name="tipoCarico" id="tipoCarico" class="custom-select" required>
                                        <option selected value="Container">Container</option>
                                        <option value="Carico alla Rinfusa">Carico alla Rinfusa</option>
                                        <option value="Prodotti Chimici Solidi">Prodotti Chimici Solidi</option>
                                        <option value="Prodotti Chimici Liquidi">Prodotti Chimici Liquidi</option>
                                        <option value="Prodotti Chimici Gassosi">Prodotti Chimici Gassosi</option>
                                        <option value="Autoveicoli">Autoveicoli</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputQuantita">Quantit&aacute;</label>
                                    <input name="quantita" class="form-control py-4" id="inputQuantita"
                                           type="number"
                                           placeholder="Inserisci Quantit&aacute;"
                                           min="1"
                                           required/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="portoPartenza">Porto di Partenza</label>
                                    <select name="portoPartenza" id="portoPartenza" onfocusout="porto()"
                                            class="custom-select" required>
                                        <c:forEach items="${requestScope.porti}" var="porto">
                                            <option value="${porto.localcode}">${porto.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="dataPartenza">Data di Partenza</label>
                                    <input name="dataPartenza" class="form-control py-4" id="dataPartenza"
                                           onfocusout="data()"
                                           type="date"
                                           required/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="portoArrivo">Porto di Arrivo</label>
                                    <select name="portoArrivo" id="portoArrivo" class="custom-select"
                                            onfocusout="porto()" required>
                                        <c:forEach items="${requestScope.porti}" var="porto">
                                            <option value="${porto.localcode}">${porto.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="dataArrivo">Data di Arrivo</label>
                                    <input name="dataArrivo" class="form-control py-4" id="dataArrivo"
                                           type="date"
                                           onfocusout="data()"
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
                                           type="file"/>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                            <button type="submit" class="btn btn-primary">Aggiungi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${sessionScope.utente.broker}">
        <div class="modal fade" id="aggiungiMediazione" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Aggiungi Mediazione</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="aggiungi-mediazione" method="post">
                        <div class="modal-body">
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label class="small mb-1" for="inputNome">Nome</label>
                                    <input name="nome" class="form-control py-4" id="inputNome"
                                           type="text" maxlength="50" required/>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                            <button type="submit" class="btn btn-primary">Aggiungi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>

    <script>
        $(document).ready(function () {
            $("#dataArrivo").attr("min", moment().add(1,'days').format("YYYY-MM-DD"));
            $("#dataPartenza").attr("min", moment().add(1,'days').format("YYYY-MM-DD"));
            $("#inputAnnoCostruzione").attr("max", moment().format("YYYY"));
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

</div>
<jsp:include page="footer.jsp"/>
