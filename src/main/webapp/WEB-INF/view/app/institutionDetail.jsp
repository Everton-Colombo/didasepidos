<%@ page isELIgnored ="false" %> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% pageContext.setAttribute("newLineChar", "\n"); %>

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
    <title>Didasepidos | ${institution.name}</title>
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

    <main id="institution-display">
        <section id="institution-detail">
            <div class="container">
                <h1 class="ta-left">${institution.name}</h1>
                <h2 class="ta-right"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="1" value="${avgRating}"/>/5</h2>
                <div class="marker ta-left">
                    <c:choose>
                        <c:when test="${institution.inPrivateSector}">
                            <p class="marker-a">Rede Privada</p>
                        </c:when>
                        <c:otherwise>
                            <p class="marker-b">Rede Pública</p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <p class="ta-center info-p">
                    ${institution.location.street}, ${institution.location.city} - ${institution.location.divisionUnit} - ${institution.location.countryCode}
                </p>

                <div class="buttons">
                    <c:url var="rateLink" value="/app/rate">
                        <c:param name="institutionId" value="${institution.id}"/>
                    </c:url>
                    <a href="${rateLink}" class="btn btn-outline chevron-btn"><i class="fas fa-chevron-right"></i> Avaliar</a>

                    <c:url var="reviewsLink" value="/app/reviews">
                        <c:param name="institutionId" value="${institution.id}"/>
                        <c:param name="page" value="1"/>
                    </c:url>
                    <a href="${reviewsLink}" class="btn btn-outline chevron-btn"><i class="fas fa-chevron-right"></i> Ver Avalições</a>
                </div>
            </div>
        </section>

        <section id="top-reviews" class="container">
            <h3 class="section-header">Top 10 avaliações do mês</h3>
            <div class="reviews-list">
                <c:forEach var="tmpReview" items="${topReviews}">
                    <div class="review-list-item">
                        <header class="review-header">
                            <h1 class="ta-left"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="1" value="${tmpReview.averageRating}"/>/5</h1>
                            <div class="review-components-display">
                                <c:forEach var="tmpReviewComponent" items="${tmpReview.reviewComponents}">
                                    <div>
                                        <h2>${tmpReviewComponent.subject.name}</h2>
                                        <h3>${tmpReviewComponent.rating}/5</h3>
                                    </div>
                                </c:forEach>
                            </div>
                        </header>
                        <p class="review-comment">${fn:replace(tmpReview.comment, newLineChar, "<br>")}</p>
                        <footer class="review-footer">
                            <p><i class="fas fa-user"></i> - <fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${tmpReview.datetime}" /></p>
                            <ul class="review-votes">
                                <li class="vote">
                                    <input type="checkbox" class="like-button" name="${tmpReview.id}-votes" id="${tmpReview.id}-votes-like" value="1">
                                    <label for="${tmpReview.id}-votes-like"><i class="fas fa-arrow-up"></i> <span>${tmpReview.likes}</span></label>
                                </li>
                                <li class="vote">
                                    <input type="checkbox" class="dislike-button" name="${tmpReview.id}-votes" id="${tmpReview.id}-votes-dislike" value="0">
                                    <label for="${tmpReview.id}-votes-like"><i class="fas fa-arrow-down"></i> <span>${tmpReview.dislikes}</span></label>
                                </li>
                            </ul>
                        </footer>
                    </div>
                </c:forEach>
            </div>
        </section>

        <section id="institution-average-reviews">
            <div class="container">
                <div class="subjects-table">
                    <h3 class="section-header">Médias</h3>
                    <table>
                        <tr>
                            <th>Matéria</th>
                            <th>Média das avaliações (últimos 30 dias)</th>
                            <th>Média de todas as avaliações</th>
                        </tr>
                        <c:forEach var="ratingRow" items="${avgRatingRows}">
                            <tr class="table-data">
                                <td>${ratingRow.subject}</td>
                                <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="1" value="${ratingRow.recent}"/>/5</td>
                                <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="1" value="${ratingRow.allTime}"/>/5</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
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

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script>
        var voteElements = $(".review-votes");
        <c:forEach var="tmpVote" items="${votes}" varStatus="vsts">
            <c:choose>
                <c:when test="${tmpVote == 1}">
                    voteElements.eq(${vsts.index}).find("> .vote > .like-button").prop("checked", true);
                </c:when>
                <c:when test="${tmpVote == 0}">
                    voteElements.eq(${vsts.index}).find("> .vote > .dislike-button").prop("checked", true);
                </c:when>
            </c:choose>
        </c:forEach>

        $(":checkbox").change(function() {
            if(this.checked) {
                var otherCheckbox = $(this).parent().siblings().children("input");
                
                if(otherCheckbox.prop("checked")) { // Update other counter if needed
                    otherCheckbox.prop("checked", false);
                    otherCheckbox.siblings("label").children("span").text(function(i, old) {
                        return +old - 1;
                    });
                }

                $(this).siblings("label").children("span").text(function(i, old) {  // add one to this counter
                    return +old + 1;
                });
            } else {
                $(this).siblings("label").children("span").text(function(i, old) {
                    return +old - 1;    // remove one from this counter
                });
            }
            
            var data = {
                    "checkboxName": $(this).attr("name"),
                    "likeChecked": $(this).parents(".review-votes").find("> .vote > .like-button").prop("checked"),
                    "dislikeChecked": $(this).parents(".review-votes").find("> .vote > .dislike-button").prop("checked")
                };

            $.ajax({
                url: "${pageContext.request.contextPath}/app/voteReview",
                type: "POST",
                dataType : 'json',
                contentType: "application/json",
                data: JSON.stringify(data)
            });
        });
    </script>
</body>
</html>