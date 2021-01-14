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
                            <a href="aggiungi-imbarcazione.jsp">
                                <button class="btn btn-primary">Aggiungi
                                    Imbarcazione
                                </button>
                            </a>

                        </c:when>
                        <c:when test="${sessionScope.utente.ruolo.compareTo('armatore') == 0}">
                            <a href="aggiungi-richiesta.jsp">
                                <button class="btn btn-primary">Aggiungi Richiesta
                                </button>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="aggiungi-mediazione.jsp">
                                <button class="btn btn-primary">Aggiungi Mediazione
                                </button>
                            </a>
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
                                    <c:forEach items="${requestScope.notifiche}" var="notifica" varStatus="indice">
                                        <tr>
                                            <td>${indice.count}</td>
                                            <td>${notifica.oggetto}</td>
                                            <td><a href="visualizzanotifica?id=${notifica.id}">
                                                <button class="btn btn-primary">Visualizza</button>
                                            </a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
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
                                <table class="table table-bordered" id="mediazioni" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>N.</th>
                                        <th>Nome</th>
                                        <th>Stato</th>
                                        <th>Azioni</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${requestScope.mediazioni}" var="mediazione" varStatus="indice">
                                        <tr>
                                            <td>${indice.count}</td>
                                            <td>${mediazione.nome}</td>
                                            <td>${mediazione.stato}</td>
                                            <td><a href="visualizzamediazione?id=${mediazione.id}">
                                                <button class="btn btn-primary">Visualizza</button>
                                            </a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
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
                                                    <td><a href="visualizzaimbarcazione?id=${imbarcazione.imo}">
                                                        <button class="btn btn-primary">Visualizza</button>
                                                    </a></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
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
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="richieste" width="100%" cellspacing="0">
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
                                                    <td><a href="visualizzarichiesta?id=${richiesta.id}">
                                                        <button class="btn btn-primary">Visualizza</button>
                                                    </a></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                </c:choose>

            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"/>
