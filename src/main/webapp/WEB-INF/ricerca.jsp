<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.utente == null}">
    <c:redirect url="/login"/>
</c:if>
<jsp:include page="../header.jsp">
    <jsp:param name="titolo" value="Richiesta"/>
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
    });
</script>

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
                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Richieste</h3>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="richieste" width="100%"
                                   cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Tipo di carico</th>
                                    <th>Quantit&aacute;</th>
                                    <th>Data di Partenza</th>
                                    <th>Area Partenza</th>
                                    <th>Data di Arrivo</th>
                                    <th>Area Arrivo</th>
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
                                        <c:forEach items="${requestScope.porti}" var="porto">
                                            <c:if test="${porto.localcode == richiesta.portoPartenza}">
                                                <c:forEach items="${requestScope.aree}" var="area">
                                                    <c:if test="${porto.idArea == area.id}">
                                                        <td>${area.nome}</td>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                        <td>${richiesta.dataArrivo}</td>
                                        <c:forEach items="${requestScope.porti}" var="porto">
                                            <c:if test="${porto.localcode == richiesta.portoArrivo}">
                                                <c:forEach items="${requestScope.aree}" var="area">
                                                    <c:if test="${porto.idArea == area.id}">
                                                        <td>${area.nome}</td>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                        <td><a href="visualizza-richiesta?id=${richiesta.id}">
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
            <div class="col-lg-12">
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Imbarcazioni</h3>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="imbarcazioni" width="100%"
                                   cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Imo</th>
                                    <th>Nome</th>
                                    <th>Tipologia</th>
                                    <th>Quantit&agrave; Max Trasportabile</th>
                                    <th>Posizione</th>
                                    <th>Azioni</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestScope.imbarcazioni}" var="imbarcazione"
                                           varStatus="indice">
                                    <tr>
                                        <td>${imbarcazione.imo}</td>
                                        <td>${imbarcazione.nome}</td>
                                        <td>${imbarcazione.tipologia}</td>
                                        <td>${imbarcazione.quantitaMax}</td>
                                        <c:forEach items="${requestScope.aree}" var="area">
                                            <c:if test="${area.id == imbarcazione.posizione}">
                                                <td>${area.nome}</td>
                                            </c:if>
                                        </c:forEach>
                                        <td>
                                            <a href="visualizza-imbarcazione?id=${imbarcazione.id}">
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
    </div>
</main>

<jsp:include page="../footer.jsp"/>
