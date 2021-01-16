<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.utente == null}">
    <c:redirect url="/login"/>
</c:if>
<jsp:include page="../header.jsp">
    <jsp:param name="titolo" value="Notifica"/>
</jsp:include>

<main>
    <header class="page-header page-header-compact page-header-light border-bottom bg-white mb-4">
        <div class="container-fluid">
            <h1 class="mt-4">Notifica</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active"><a href="index.jsp">Dashboard</a>/Notifica</li>
            </ol>
        </div>
    </header>
    <div class="container mt-4">
        <div class="row">
            <div class="col-lg-12">
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                    <div class="card-header"><h3 class="text-center font-weight-light my-4">${requestScope.oggetto}</h3>
                    </div>
                    <div class="card-body">
                        <p>${requestScope.corpo}</p>
                    </div>
                    <div class="card-footer text-right">
                        <a href="index"><button class="btn btn-primary">Indietro</button></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>


<jsp:include page="../footer.jsp"/>
