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

    <main>
        <section id="review-creation">
            <div class="container">
                <section id="review-creating-title">
                    <h1>Nova Avaliação: <span class="light-text">${institution.name}</span></h1>
                    <p>
                        <strong>Lembre-se:</strong> <br>
                        Instituições não podem ser avaliadas mais do que <em>uma</em> vez por semana; <br>
                        Não é necessário avaliar todas as matérias.
                    </p>
                </section>

                <section id="review-creation-form">
                    <form:form accept-charset="UTF-8" action="${pageContext.request.contextPath}/app/sendRating" method="POST" id="review-form">
                        <input type="hidden" name="target" id="target" value="${institution.id}">
                        <table>
                            <tr>
                                <th>Matéria</th>
                                <th>Nota</th>
                            </tr>
                            <c:forEach var="tmpSubject" items="${institution.subjectsTaught}">
                                <tr class="table-data">
                                    <td>${tmpSubject.name}</td>
                                    <td>
                                        <ul class="button-radio-group">
                                            <li>
                                                <input type="radio" name="sub_${tmpSubject.id}_rating" id="${tmpSubject.id}-rating-1" value="1">
                                                <label for="${tmpSubject.id}-rating-1">1</label>
                                            </li>
                                            <li>
                                                <input type="radio" name="sub_${tmpSubject.id}_rating" id="${tmpSubject.id}-rating-2" value="2">
                                                <label for="${tmpSubject.id}-rating-2">2</label>
                                            </li>
                                            <li>
                                                <input type="radio" name="sub_${tmpSubject.id}_rating" id="${tmpSubject.id}-rating-3" value="3">
                                                <label for="${tmpSubject.id}-rating-3">3</label>
                                            </li>
                                            <li>
                                                <input type="radio" name="sub_${tmpSubject.id}_rating" id="${tmpSubject.id}-rating-4" value="4">
                                                <label for="${tmpSubject.id}-rating-4">4</label>
                                            </li>
                                            <li>
                                                <input type="radio" name="sub_${tmpSubject.id}_rating" id="${tmpSubject.id}-rating-5" value="5">
                                                <label for="${tmpSubject.id}-rating-5">5</label>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                        <section id="review-creation-note">
                            <h2>Comentário (opcional):</h2>
                            <textarea name="review-comment" id="review-comment" form="review-form" cols="30" rows="10"></textarea>
                        </section>

                        <input type="submit" class="btn btn-primary btn-outline" value="Enviar">
                    </form:form>
                </section>
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