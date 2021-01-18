<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.utente == null}">
    <c:redirect url="/login"/>
</c:if>
<jsp:include page="../header.jsp">
    <jsp:param name="titolo" value="Imbarcazione"/>
</jsp:include>
<main>
    <header class="page-header page-header-compact page-header-light border-bottom bg-white mb-4">
        <div class="container-fluid">
            <h1 class="mt-4">Imbarcazione</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active"><a href="index.jsp">Dashboard</a>/Imbarcazione</li>
            </ol>
        </div>
    </header>
    <div class="container mt-4">
        <div class="row">
            <div class="col-lg-12">
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Visualizza Imbarcazione</h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) == 0}">
                        <form enctype="multipart/form-data" action="modifica-imbarcazione" method="post">
                            </c:if>
                            <input name="id" value="${requestScope.imbarcazione.id}" type="hidden"/>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputImo">Imo</label>
                                    <input name="imo" class="form-control py-4" id="inputImo"
                                           type="text" maxlength="7"
                                           placeholder="Inserisci Imo"
                                           value="${requestScope.imbarcazione.imo}"
                                           readonly
                                           required/>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputNomeImbarcazione">Nome</label>
                                    <input name="nome" class="form-control py-4" id="inputNomeImbarcazione"
                                           type="text"
                                           value="${requestScope.imbarcazione.nome}"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>

                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="tipologia">Tipologia</label>
                                    <c:choose>
                                        <c:when test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                            <c:forEach
                                                    items="Portacontainer,Carboniera,Chimichiera,Lift-on/Lift-off,Nave da CaricoNave Frigorifera,Portarinfuse,Roll-on/Roll-off"
                                                    var="item">
                                                <c:if
                                                        test="${requestScope.imbarcazione.tipologia.compareTo(item) == 0}">${item}</c:if>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="tipologia" id="tipologia" class="custom-select" required>
                                                <c:forEach
                                                        items="Portacontainer,Carboniera,Chimichiera,Lift-on/Lift-off,Nave da CaricoNave Frigorifera,Portarinfuse,Roll-on/Roll-off"
                                                        var="item">
                                                    <option <c:if
                                                            test="${requestScope.imbarcazione.tipologia.compareTo(item) == 0}"> selected </c:if>
                                                            value="${item}">${item}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputQuantitaMAX">Quantit&aacute; Massima
                                        Trasportabile</label>
                                    <input name="quantita" class="form-control py-4" id="inputQuantitaMAX"
                                           type="number" step="1000"
                                           value="${requestScope.imbarcazione.quantitaMax}"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputBandiera">Bandiera</label>
                                    <c:choose>
                                        <c:when test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                            <c:forEach
                                                    items="AF,AL,DZ,AD,AO,AI,AQ,AG,AN,SA,AR,AM,AW,AU,AT,AZ,BS,BH,BD,BB,BE,BZ,BJ,BM,BY,BT,BO,BA,BW,BR,BN,BG,BF,BI,KH,CM,CA,CV,TD,CL,CN,CY,VA,CO,KM,KP,KR,CR,CI,HR,CU,DK,DM,EC,EG,IE,SV,AE,ER,EE,ET,RU,FJ,PH,FI,FR,GA,GM,GE,DE,GH,JM,JP,GI,DJ,JO,GR,GD,GL,GP,GU,GT,GN,GW,GQ,GY,GF,HT,HN,HK,IN,ID,IR,IQ,BV,CX,HM,KY,CC,CK,FK,FO,MH,MP,UM,NF,SB,TC,VI,VG,IL,IS,IT,KZ,KE,KG,KI,KW,LA,LV,LS,LB,LR,LY,LI,LT,LU,MO,MK,MG,MW,MV,MY,ML,MT,MA,MQ,MR,MU,YT,MX,MD,MC,MN,MS,MZ,MM,NA,NR,NP,NI,NE,NG,NU,NO,NC,NZ,OM,NL,PK,PW,PA,PG,PY,PE,PN,PF,PL,PT,PR,QA,GB,CZ,CF,CG,CD,DO,RE,RO,RW,EH,KN,PM,VC,WS,AS,SM,SH,LC,ST,SN,XK,SC,SL,SG,SY,SK,SI,SO,ES,LK,FM,US,ZA,GS,SD,SR,SJ,SE,CH,SZ,TJ,TH,TW,TZ,IO,TF,PS,TL,TG,TK,TO,TT,TN,TR,TM,TV,UA,UG,HU,UY,UZ,VU,VE,VN,WF,YE,ZM,ZW,RS,ME,TP,GG"
                                                    var="item">
                                                <c:if test="${requestScope.imbarcazione.bandiera.compareTo(item) == 0}"> ${item} </c:if>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="bandiera" id="inputBandiera" class="custom-select"
                                                    required>
                                                <c:forEach
                                                        items="AF,AL,DZ,AD,AO,AI,AQ,AG,AN,SA,AR,AM,AW,AU,AT,AZ,BS,BH,BD,BB,BE,BZ,BJ,BM,BY,BT,BO,BA,BW,BR,BN,BG,BF,BI,KH,CM,CA,CV,TD,CL,CN,CY,VA,CO,KM,KP,KR,CR,CI,HR,CU,DK,DM,EC,EG,IE,SV,AE,ER,EE,ET,RU,FJ,PH,FI,FR,GA,GM,GE,DE,GH,JM,JP,GI,DJ,JO,GR,GD,GL,GP,GU,GT,GN,GW,GQ,GY,GF,HT,HN,HK,IN,ID,IR,IQ,BV,CX,HM,KY,CC,CK,FK,FO,MH,MP,UM,NF,SB,TC,VI,VG,IL,IS,IT,KZ,KE,KG,KI,KW,LA,LV,LS,LB,LR,LY,LI,LT,LU,MO,MK,MG,MW,MV,MY,ML,MT,MA,MQ,MR,MU,YT,MX,MD,MC,MN,MS,MZ,MM,NA,NR,NP,NI,NE,NG,NU,NO,NC,NZ,OM,NL,PK,PW,PA,PG,PY,PE,PN,PF,PL,PT,PR,QA,GB,CZ,CF,CG,CD,DO,RE,RO,RW,EH,KN,PM,VC,WS,AS,SM,SH,LC,ST,SN,XK,SC,SL,SG,SY,SK,SI,SO,ES,LK,FM,US,ZA,GS,SD,SR,SJ,SE,CH,SZ,TJ,TH,TW,TZ,IO,TF,PS,TL,TG,TK,TO,TT,TN,TR,TM,TV,UA,UG,HU,UY,UZ,VU,VE,VN,WF,YE,ZM,ZW,RS,ME,TP,GG"
                                                        var="item">
                                                    <option <c:if
                                                            test="${requestScope.imbarcazione.bandiera.compareTo(item) == 0}"> selected </c:if>
                                                            value="${item}">${item}</option>
                                                </c:forEach>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="small mb-1" for="inputAnnoCostruzione">Anno
                                        Costruzione</label>
                                    <input name="anno" class="form-control py-4" id="inputAnnoCostruzione"
                                           type="number" value="${requestScope.imbarcazione.annoCostruzione}"
                                           step="1"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputlunghezza">Lunghezza</label>
                                    <input name="lunghezza" class="form-control py-4" id="inputlunghezza"
                                           type="number" step="1"
                                           value="${requestScope.imbarcazione.lunghezza}"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputAmpiezza">Ampiezza</label>
                                    <input name="ampiezza" class="form-control py-4" id="inputAmpiezza"
                                           type="number" step="1"
                                           value="${requestScope.imbarcazione.ampiezza}"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="small mb-1" for="inputAltezza">Altezza (Pescaggio)</label>
                                    <input name="altezza" class="form-control py-4" id="inputAltezza"
                                           type="number" step="1"
                                           value="${requestScope.imbarcazione.altezza}"
                                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                                readonly
                                            </c:if>
                                           required/>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-4">
                                        <label class="small mb-1" for="inputStato">Disponibile</label>
                                        <input name="nome" class="form-control py-4" id="inputStato"
                                               type="text"
                                               value="${requestScope.imbarcazione.disponibile ? 'Disponibile' : 'Non Disponibile'}"
                                               readonly/>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label class="small mb-1" for="inputStato">Posizione</label>
                                        <input name="nome" class="form-control py-4" id="posizione"
                                               type="text"
                                                <c:forEach items="${requestScope.aree}" var="area">
                                                    <c:if test="${area.id == requestScope.imbarcazione.posizione}">
                                                        value="${area.nome}"
                                                    </c:if>
                                                </c:forEach>
                                               readonly/>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label class="small mb-1"
                                               for="inputDocumentoImbarcazione">Documento</label>
                                        <input name="documento" class="form-control py-4"
                                               id="inputDocumentoImbarcazione"
                                                <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) != 0}">
                                                    readonly
                                                </c:if>
                                               value="${requestScope.imbarcazione.documento}"
                                               type="file"/>
                                        <c:if test="${!requestScope.imbarcazione.caricato}">
                                            <p>Documento non caricato</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) == 0}">
                            <button type="submit" class="btn btn-primary">Modifica</button>
                        </form>
                        </c:if>
                    </div>
                    <div class="card-footer">
                        <c:if test="${sessionScope.utente.codFiscale.compareTo(requestScope.imbarcazione.codFiscaleUtente) == 0}">
                            <button class="btn btn-${requestScope.imbarcazione.disponibile ? 'danger' : 'success' }"
                                    data-toggle="modal" data-target="#rimuovi"
                                    type="button">
                                Rendi ${requestScope.imbarcazione.disponibile ? 'Indisponibile' : 'Disponibile'}
                            </button>
                        </c:if>

                        <c:if test="${requestScope.imbarcazione.caricato}">
                            <a href="visualizza-imbarcazione-documento?id=${requestScope.imbarcazione.id}">
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

    <c:if test="${sessionScope.utente.armatore}">
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
                        <form action="disponibile-indisponibile" method="post">
                            <input name="id" value="${requestScope.imbarcazione.id}" type="hidden"/>
                            <button type="submit" class="btn btn-primary">Conferma</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

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
                        <form action="aggiungi-imbarcazione-mediazione" method="post">
                            <select name="mediazione" class="custom-select" required>
                                <c:forEach items="${requestScope.mediazioni}" var="mediazione">
                                    <option value="${mediazione.id}">${mediazione.nome}</option>
                                </c:forEach>
                            </select>
                            <input name="id" value="${requestScope.imbarcazione.id}" type="hidden"/>
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
        $("#inputAnnoCostruzione").attr("max", moment().format("YYYY"));
    });
</script>

<jsp:include page="../footer.jsp"/>
