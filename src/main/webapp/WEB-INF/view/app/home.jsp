<%@ page isELIgnored ="false" %> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <title>Didasepidos | App</title>
</head>
<body>
    <header>
        <div class="navbar" id="navbar">
            <a class="logo-a" href="${pageContext.request.contextPath}/">
                <h1 class="logo">
                    <span class="text-primary"><i class="fas fa-book-reader"></i> Didas</span>epidos
                </h1>
            </a>
            <form:form accept-charset="UTF-8" aria-autocomplete="off" class="search-bar" action="app/list" method="GET">
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
        <section id="tri-podium" class="container">
            <div class="place first-place">
                <span class="fa-stack fa-2x">
                    <i class="fa fa-circle fa-stack-2x icon-background"></i>
                    <i class="fa fa-crown fa-stack-1x"></i>
                </span>

                <h2>1</h2>
                <c:url var="firstPlaceLink" value="/app/institution">
                    <c:param name="institutionId" value="${topSummary[0].institution.id}"/>
                </c:url>
                <a href="${firstPlaceLink}"><h3>${topSummary[0].institution.name}</h3></a>
                <div>
                    <h4><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="1" value="${topSummary[0].avgRating}"/>/5</h4>
                    <p>${topSummary[0].ratingCount} <c:choose><c:when test="${topSummary[0].ratingCount != 1}">avaliações</c:when><c:otherwise>avaliação</c:otherwise></c:choose></p>
                </div>
            </div>

            <div class="place second-place">
                <span class="fa-stack fa-2x">
                    <i class="fa fa-circle fa-stack-2x icon-background"></i>
                    <i class="fa fa-trophy fa-stack-1x"></i>
                </span>

                <h2>2</h2>
                <c:url var="secondPlaceLink" value="/app/institution">
                    <c:param name="institutionId" value="${topSummary[1].institution.id}"/>
                </c:url>
                <a href="${secondPlaceLink}"><h3>${topSummary[1].institution.name}</h3></a>
                <div>
                    <h4><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="1" value="${topSummary[1].avgRating}"/>/5</h4>
                    <p>${topSummary[1].ratingCount} <c:choose><c:when test="${topSummary[1].ratingCount != 1}">avaliações</c:when><c:otherwise>avaliação</c:otherwise></c:choose></p>
                </div>
            </div>

            <div class="place third-place">
                <span class="fa-stack fa-2x">
                    <i class="fa fa-circle fa-stack-2x icon-background"></i>
                    <i class="fa fa-trophy fa-stack-1x"></i>
                </span>

                <h2>3</h2>
                <c:url var="thirdPlaceLink" value="/app/institution">
                    <c:param name="institutionId" value="${topSummary[2].institution.id}"/>
                </c:url>
                <a href="${thirdPlaceLink}"><h3>${topSummary[2].institution.name}</h3></a>
                <div>
                    <h4><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="1" value="${topSummary[2].avgRating}"/>/5</h4>
                    <p>${topSummary[2].ratingCount} <c:choose><c:when test="${topSummary[2].ratingCount != 1}">avaliações</c:when><c:otherwise>avaliação</c:otherwise></c:choose></p>
                </div>
            </div>
        </section>
        
        <section id="three-to-ten-list" class="container">
            <ul>
                <c:forEach var="tmpSummary" items="${topSummary}" begin="3" varStatus="loop">
                    <li>
                        <i class="fas fa-award fa-2x"></i>
                        <h2>${loop.index + 1}</h2>
                        <c:url var="instLink" value="/app/institution">
                            <c:param name="institutionId" value="${tmpSummary.institution.id}"/>
                        </c:url>
                        <a href="${instLink}"><h3>${tmpSummary.institution.name}</h3></a>
                        <div>
                            <h4><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="1" value="${tmpSummary.avgRating}"/>/5</h4>
                            <p>${tmpSummary.ratingCount} <c:choose><c:when test="${tmpSummary.ratingCount != 1}">avaliações</c:when><c:otherwise>avaliação</c:otherwise></c:choose></p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
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