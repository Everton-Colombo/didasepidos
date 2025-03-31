<%@ page isELIgnored ="false" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="pt">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/utilities.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
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
            <form:form accept-charset="UTF-8" aria-autocomplete="off" class="search-bar" action="${pageContext.request.contextPath}/app/list" method="GET">
                <input autocomplete="off" type="text" name="keywords" placeholder="Buscar"> <button type="submit"><i class="fas fa-search"></i></button>
            </form:form>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/app">Início</a></li>
                    <li><a href="${pageContext.request.contextPath}/app/user-reviews">Minhas Avaliações</a></li>
                    <li><a href="${pageContext.request.contextPath}/app/settings">Configurações</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <h1 id="review-list-counter" class="container ta-left">
        ${reviewCount} <c:choose><c:when test="${reviewCount != 1}">avaliações</c:when><c:otherwise>avaliação</c:otherwise></c:choose>:
    </h1>
    <section class="reviews-list container">
        <c:forEach var="tmpReview" items="${reviews}">
            <div class="review-list-item">
                <div class="review-pi">
                    <h2 class="review-target-institution">${tmpReview.targetInstitution.name}</h2>
                    <c:url var="deleteReview" value="/app/deleteReview">
                        <c:param name="reviewId" value="${tmpReview.id}"/>
                    </c:url>
                    <a class="delete-review-button" href="${deleteReview}"><i class="fas fa-trash"></i></a>
                </div>
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
                <p class="review-comment">${tmpReview.comment}</p>
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
    </section>

    <section class="page-selector">
        <div class="container">
            <c:if test="${param.page > 1}">
                <c:url var="prevPage" value="/app/reviews">
                    <c:param name="institutionId" value="${param.institutionId}"/>
                    <c:param name="page" value="${param.page - 1}"/>
                </c:url>
                <a href="${prevPage}" class="btn btn-primary"><i class="fas fa-chevron-left"></i> Página Anterior</a>
            </c:if>
            <c:if test="${(empty param.page && pageCount > 1) || param.page < pageCount}">
                <c:url var="nextPage" value="/app/reviews">
                    <c:param name="institutionId" value="${param.institutionId}"/>
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

    <footer class="footer bg-dark">
        <div class="flex-line">
            <div class="social">
                <a href="#"><i class="fab fa-twitter fa-2x"></i></a>
                <a href="#"><i class="fab fa-youtube fa-2x"></i></a>
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