<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                            <c:when test="${sessionScope.utente.ruolo.compareTo('cliente') == 0}">
                                <button class="btn btn-primary" data-toggle="modal"
                                        data-target="#aggiungiRichiesta" type="button">Aggiungi Richiesta
                                </button>
                            </c:when>
                            <c:when test="${sessionScope.utente.ruolo.compareTo('armatore') == 0}">
                                <button class="btn btn-primary" data-toggle="modal"
                                        data-target="#aggiungiImbarcazione" type="button">Aggiungi
                                    Imbarcazione
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-primary" data-toggle="modal"
                                        data-target="#aggiungiMediazione" type="button">Aggiungi Mediazione
                                </button>
                                <a href="ricerca.jsp">
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
                    <c:when test="${sessionScope.utente.ruolo.compareTo('armatore') == 0}">
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
                                                                <a href="visualizza-imbarcazione?id=${imbarcazione.imo}">
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
                    <c:when test="${sessionScope.utente.ruolo.compareTo('cliente') == 0}">
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
                                                        <th>Quantità</th>
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

    <c:if test="${sessionScope.utente.ruolo.compareTo('armatore') == 0}">
    <div class="modal fade" id="aggiungiImbarcazione" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Aggiungi Imbarcazione</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="aggiungi-imbarcazione" method="post">
                    <input type="hidden" value="${sessionScope.utente.codFiscale}" name="codFiscale">
                    <div class="modal-body">
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="inputImo">Imo</label>
                                <input name="imo" class="form-control py-4" id="inputImo"
                                       type="text" maxlength="30"
                                       placeholder="Inserisci Imo"
                                       required/>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="inputNomeImbarcazione">Nome</label>
                                <input name="nome" class="form-control py-4" id="inputNomeImbarcazione"
                                       type="text"
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
                                       required/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="inputBandiera">Bandiera</label>
                                <select name="bandiera" id="inputBandiera" class="custom-select" required>
                                    <option value="AF">AF</option>
                                    <option value="AL">AL</option>
                                    <option value="DZ">DZ</option>
                                    <option value="AD">AD</option>
                                    <option value="AO">AO</option>
                                    <option value="AI">AI</option>
                                    <option value="AQ">AQ</option>
                                    <option value="AG">AG</option>
                                    <option value="AN">AN</option>
                                    <option value="SA">SA</option>
                                    <option value="AR">AR</option>
                                    <option value="AM">AM</option>
                                    <option value="AW">AW</option>
                                    <option value="AU">AU</option>
                                    <option value="AT">AT</option>
                                    <option value="AZ">AZ</option>
                                    <option value="BS">BS</option>
                                    <option value="BH">BH</option>
                                    <option value="BD">BD</option>
                                    <option value="BB">BB</option>
                                    <option value="BE">BE</option>
                                    <option value="BZ">BZ</option>
                                    <option value="BJ">BJ</option>
                                    <option value="BM">BM</option>
                                    <option value="BY">BY</option>
                                    <option value="BT">BT</option>
                                    <option value="BO">BO</option>
                                    <option value="BA">BA</option>
                                    <option value="BW">BW</option>
                                    <option value="BR">BR</option>
                                    <option value="BN">BN</option>
                                    <option value="BG">BG</option>
                                    <option value="BF">BF</option>
                                    <option value="BI">BI</option>
                                    <option value="KH">KH</option>
                                    <option value="CM">CM</option>
                                    <option value="CA">CA</option>
                                    <option value="CV">CV</option>
                                    <option value="TD">TD</option>
                                    <option value="CL">CL</option>
                                    <option value="CN">CN</option>
                                    <option value="CY">CY</option>
                                    <option value="VA">VA</option>
                                    <option value="CO">CO</option>
                                    <option value="KM">KM</option>
                                    <option value="KP">KP</option>
                                    <option value="KR">KR</option>
                                    <option value="CR">CR</option>
                                    <option value="CI">CI</option>
                                    <option value="HR">HR</option>
                                    <option value="CU">CU</option>
                                    <option value="DK">DK</option>
                                    <option value="DM">DM</option>
                                    <option value="EC">EC</option>
                                    <option value="EG">EG</option>
                                    <option value="IE">IE</option>
                                    <option value="SV">SV</option>
                                    <option value="AE">AE</option>
                                    <option value="ER">ER</option>
                                    <option value="EE">EE</option>
                                    <option value="ET">ET</option>
                                    <option value="RU">RU</option>
                                    <option value="FJ">FJ</option>
                                    <option value="PH">PH</option>
                                    <option value="FI">FI</option>
                                    <option value="FR">FR</option>
                                    <option value="GA">GA</option>
                                    <option value="GM">GM</option>
                                    <option value="GE">GE</option>
                                    <option value="DE">DE</option>
                                    <option value="GH">GH</option>
                                    <option value="JM">JM</option>
                                    <option value="JP">JP</option>
                                    <option value="GI">GI</option>
                                    <option value="DJ">DJ</option>
                                    <option value="JO">JO</option>
                                    <option value="GR">GR</option>
                                    <option value="GD">GD</option>
                                    <option value="GL">GL</option>
                                    <option value="GP">GP</option>
                                    <option value="GU">GU</option>
                                    <option value="GT">GT</option>
                                    <option value="GN">GN</option>
                                    <option value="GW">GW</option>
                                    <option value="GQ">GQ</option>
                                    <option value="GY">GY</option>
                                    <option value="GF">GF</option>
                                    <option value="HT">HT</option>
                                    <option value="HN">HN</option>
                                    <option value="HK">HK</option>
                                    <option value="IN">IN</option>
                                    <option value="ID">ID</option>
                                    <option value="IR">IR</option>
                                    <option value="IQ">IQ</option>
                                    <option value="BV">BV</option>
                                    <option value="CX">CX</option>
                                    <option value="HM">HM</option>
                                    <option value="KY">KY</option>
                                    <option value="CC">CC</option>
                                    <option value="CK">CK</option>
                                    <option value="FK">FK</option>
                                    <option value="FO">FO</option>
                                    <option value="MH">MH</option>
                                    <option value="MP">MP</option>
                                    <option value="UM">UM</option>
                                    <option value="NF">NF</option>
                                    <option value="SB">SB</option>
                                    <option value="TC">TC</option>
                                    <option value="VI">VI</option>
                                    <option value="VG">VG</option>
                                    <option value="IL">IL</option>
                                    <option value="IS">IS</option>
                                    <option value="IT">IT</option>
                                    <option value="KZ">KZ</option>
                                    <option value="KE">KE</option>
                                    <option value="KG">KG</option>
                                    <option value="KI">KI</option>
                                    <option value="KW">KW</option>
                                    <option value="LA">LA</option>
                                    <option value="LV">LV</option>
                                    <option value="LS">LS</option>
                                    <option value="LB">LB</option>
                                    <option value="LR">LR</option>
                                    <option value="LY">LY</option>
                                    <option value="LI">LI</option>
                                    <option value="LT">LT</option>
                                    <option value="LU">LU</option>
                                    <option value="MO">MO</option>
                                    <option value="MK">MK</option>
                                    <option value="MG">MG</option>
                                    <option value="MW">MW</option>
                                    <option value="MV">MV</option>
                                    <option value="MY">MY</option>
                                    <option value="ML">ML</option>
                                    <option value="MT">MT</option>
                                    <option value="MA">MA</option>
                                    <option value="MQ">MQ</option>
                                    <option value="MR">MR</option>
                                    <option value="MU">MU</option>
                                    <option value="YT">YT</option>
                                    <option value="MX">MX</option>
                                    <option value="MD">MD</option>
                                    <option value="MC">MC</option>
                                    <option value="MN">MN</option>
                                    <option value="MS">MS</option>
                                    <option value="MZ">MZ</option>
                                    <option value="MM">MM</option>
                                    <option value="NA">NA</option>
                                    <option value="NR">NR</option>
                                    <option value="NP">NP</option>
                                    <option value="NI">NI</option>
                                    <option value="NE">NE</option>
                                    <option value="NG">NG</option>
                                    <option value="NU">NU</option>
                                    <option value="NO">NO</option>
                                    <option value="NC">NC</option>
                                    <option value="NZ">NZ</option>
                                    <option value="OM">OM</option>
                                    <option value="NL">NL</option>
                                    <option value="PK">PK</option>
                                    <option value="PW">PW</option>
                                    <option value="PA">PA</option>
                                    <option value="PG">PG</option>
                                    <option value="PY">PY</option>
                                    <option value="PE">PE</option>
                                    <option value="PN">PN</option>
                                    <option value="PF">PF</option>
                                    <option value="PL">PL</option>
                                    <option value="PT">PT</option>
                                    <option value="PR">PR</option>
                                    <option value="QA">QA</option>
                                    <option value="GB">GB</option>
                                    <option value="CZ">CZ</option>
                                    <option value="CF">CF</option>
                                    <option value="CG">CG</option>
                                    <option value="CD">CD</option>
                                    <option value="DO">DO</option>
                                    <option value="RE">RE</option>
                                    <option value="RO">RO</option>
                                    <option value="RW">RW</option>
                                    <option value="EH">EH</option>
                                    <option value="KN">KN</option>
                                    <option value="PM">PM</option>
                                    <option value="VC">VC</option>
                                    <option value="WS">WS</option>
                                    <option value="AS">AS</option>
                                    <option value="SM">SM</option>
                                    <option value="SH">SH</option>
                                    <option value="LC">LC</option>
                                    <option value="ST">ST</option>
                                    <option value="SN">SN</option>
                                    <option value="XK">XK</option>
                                    <option value="SC">SC</option>
                                    <option value="SL">SL</option>
                                    <option value="SG">SG</option>
                                    <option value="SY">SY</option>
                                    <option value="SK">SK</option>
                                    <option value="SI">SI</option>
                                    <option value="SO">SO</option>
                                    <option value="ES">ES</option>
                                    <option value="LK">LK</option>
                                    <option value="FM">FM</option>
                                    <option value="US">US</option>
                                    <option value="ZA">ZA</option>
                                    <option value="GS">GS</option>
                                    <option value="SD">SD</option>
                                    <option value="SR">SR</option>
                                    <option value="SJ">SJ</option>
                                    <option value="SE">SE</option>
                                    <option value="CH">CH</option>
                                    <option value="SZ">SZ</option>
                                    <option value="TJ">TJ</option>
                                    <option value="TH">TH</option>
                                    <option value="TW">TW</option>
                                    <option value="TZ">TZ</option>
                                    <option value="IO">IO</option>
                                    <option value="TF">TF</option>
                                    <option value="PS">PS</option>
                                    <option value="TL">TL</option>
                                    <option value="TG">TG</option>
                                    <option value="TK">TK</option>
                                    <option value="TO">TO</option>
                                    <option value="TT">TT</option>
                                    <option value="TN">TN</option>
                                    <option value="TR">TR</option>
                                    <option value="TM">TM</option>
                                    <option value="TV">TV</option>
                                    <option value="UA">UA</option>
                                    <option value="UG">UG</option>
                                    <option value="HU">HU</option>
                                    <option value="UY">UY</option>
                                    <option value="UZ">UZ</option>
                                    <option value="VU">VU</option>
                                    <option value="VE">VE</option>
                                    <option value="VN">VN</option>
                                    <option value="WF">WF</option>
                                    <option value="YE">YE</option>
                                    <option value="ZM">ZM</option>
                                    <option value="ZW">ZW</option>
                                    <option value="RS">RS</option>
                                    <option value="ME">ME</option>
                                    <option value="TP">TP</option>
                                    <option value="GG">GG</option>
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
                                <label class="small mb-1" for="inputLughezza">Lunghezza</label>
                                <input name="lunghezza" class="form-control py-4" id="inputLughezza"
                                       type="number" step="1"
                                       required/>
                            </div>
                            <div class="form-group col-md-4">
                                <label class="small mb-1" for="inputAmpiezza">Ampiezza</label>
                                <input name="ampiezza" class="form-control py-4" id="inputAmpiezza"
                                       type="number" step="1"
                                       required/>
                            </div>
                            <div class="form-group col-md-4">
                                <label class="small mb-1" for="inputAltezza">Altezza (Pescaggio)</label>
                                <input name="altezza" class="form-control py-4" id="inputAltezza"
                                       type="number" step="1"
                                       required/>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label class="small mb-1" for="inputDocumentoImbarcazione">Documento</label>
                                    <input name="documento" class="form-control py-4" id="inputDocumentoImbarcazione"
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

    <c:if test="${sessionScope.utente.ruolo.compareTo('cliente') == 0}">
    <div class="modal fade" id="aggiungiRichiesta" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Aggiungi Richiesta</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="aggiungi-richiesta" method="post">
                    <input name="codFiscale" type="hidden" value="${sessionScope.utente.codFiscale}">
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
                                <input name="nome" class="form-control py-4" id="inputQuantita"
                                       type="number"
                                       placeholder="Inserisci Quantit&aacute;"
                                       required/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="portoPartenza">Porto di Partenza</label>
                                <select name="portoPartenza" id="portoPartenza" class="custom-select" required>
                                        <%--                                    <option value="Autoveicoli">Autoveicoli</option>--%>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="dataPartenza">Data di Partenza</label>
                                <input name="dataPartenza" class="form-control py-4" id="dataPartenza"
                                       type="date"
                                       required/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="portoArrivo">Porto di Arrivo</label>
                                <select name="portoPartenza" id="portoArrivo" class="custom-select" required>
                                        <%--                                    <option value="Autoveicoli">Autoveicoli</option>--%>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="small mb-1" for="dataArrivo">Data di Arrivo</label>
                                <input name="dataArrivo" class="form-control py-4" id="dataArrivo"
                                       type="date"
                                       required/>
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

    <c:if test="${sessionScope.utente.ruolo.compareTo('broker') == 0}">
    <div class="modal fade" id="aggiungiMediazione" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Conferma Operazione</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="aggiungi-mediazione" method="post">
                    <input type="hidden" value="${sessionScope.utente.codFiscale}" name="codFiscale">
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

    <jsp:include page="footer.jsp"/>
