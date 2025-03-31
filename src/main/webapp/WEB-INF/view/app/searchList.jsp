<%@ page isELIgnored ="false" %> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/utilities.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.png">
    <title>Didasepidos | Lista</title>
</head>
<body>
    <header>
        <div class="navbar" id="navbar">
            <a class="logo-a" href="${pageContext.request.contextPath}/">
                <h1 class="logo">
                    <span class="text-primary"><i class="fas fa-book-reader"></i> Didas</span>epidos
                </h1>
            </a>
            <form:form accept-charset="UTF-8" aria-autocomplete="off" class="search-bar" action="${pageContext.request.contextPath}/app/list" method="GET">
                <input autocomplete="off" type="text" name="keywords" placeholder="Buscar"> <button type="submit"><i class="fas fa-search"></i></button>
            </form:form>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/app">Início</a></li>
                    <li><a href="${pageContext.request.contextPath}/app/user-reviews">Minhas Avaliações</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <main>
        <section id="results-list">
            <div class="container">
                <h1>
                    <c:choose>
                        <c:when test="${found}">
                            ${resultCount} resultado<c:if test="${resultCount > 1}">s</c:if>:
                        </c:when>
                        <c:otherwise>
                            Nenhum resultado encontrado
                        </c:otherwise>
                    </c:choose>
                </h1>

                <ul>
                    <c:forEach var="tmpInst" items="${institutions}">
                       <li>
                            <c:url var="detailsLink" value="/app/institution">
                                <c:param name="institutionId" value="${tmpInst.id}"/>
                            </c:url>

                            <a href="${detailsLink}">
                                <h2 class="ta-left">${tmpInst.name}</h2>
                            </a>
                            <div>
                                <div>
                                    <div class="marker">
                                        <c:choose>
                                            <c:when test="${tmpInst.inPrivateSector}">
                                                <p class="marker-a">Rede Privada</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="marker-b">Rede Pública</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <p class="ta-left">
                                        ${tmpInst.location.city} - ${tmpInst.location.divisionUnit}
                                    </p>
                                </div>
                                <c:url var="rateLink" value="/app/rate">
                                    <c:param name="institutionId" value="${tmpInst.id}"/>
                                </c:url>
                                <a href="${rateLink}" class="btn btn-outline chevron-btn"><i class="fas fa-chevron-right"></i> Avaliar</a>
                            </div>
                       </li>
                    </c:forEach>
                </ul>
            </div>
        </section>

        <section class="page-selector">
            <div class="container">
                <c:if test="${param.page > 1}">
                    <c:url var="prevPage" value="/app/list">
                        <c:param name="keywords" value="${param.keywords}"/>
                        <c:param name="page" value="${param.page - 1}"/>
                    </c:url>
                    <a href="${prevPage}" class="btn btn-primary"><i class="fas fa-chevron-left"></i> Página Anterior</a>
                </c:if>
                <c:if test="${(empty param.page && pageCount > 1) || param.page < pageCount}">
                    <c:url var="nextPage" value="/app/list">
                        <c:param name="keywords" value="${param.keywords}"/>
                        <c:choose>
                            <c:when test="${empty param.page}">
                                <c:param name="page" value="2"/>
                            </c:when>
                            <c:otherwise>
                                <c:param name="page" value="${param.page + 1}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:url>
                    <a href="${nextPage}" class="btn btn-primary">Próxima Página <i class="fas fa-chevron-right"></i></a>
                </c:if>
            </div>
        </section>
    </main>

    <footer class="footer bg-dark">
        <div class="flex-line">
            <div class="social">
                <a target="_blank" href="https://twitter.com/didasepidos"><i class="fab fa-twitter fa-2x"></i></a>
                <a target="_blank" href="https://www.youtube.com/channel/UCUFppSN0YkRxrOVyx07sllw"><i class="fab fa-youtube fa-2x"></i></a>
            </div>
            <p>Copyright &copy; 2021 - Everton R. Colombo. Todos os direitos reservados.</p>
        </div>
        <div class="wallets">
            <span><i class="fab fa-bitcoin fa-2x"></i><p> bc1qya0lxlmwc58pga6hdn48jvgtqzknxc9hxhx838</p></span>
            <span><i class="fab fa-ethereum fa-2x"></i><p> 0x45AD1A63DDB83C5cBf826830fFBE5B5fAB72cBdF</p></span>
        </div>
    </footer>
</body>
</html>