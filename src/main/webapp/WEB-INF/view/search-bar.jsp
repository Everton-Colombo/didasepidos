<%@ page isELIgnored ="false" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/utilities.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <title>Didasepios | Contato</title>
</head>
<body>
    <!-- <header>
        <div class="navbar" id="navbar">
            <a class="logo-a" href="${pageContext.request.contextPath}/">
                <h1 class="logo">
                    <span class="text-primary"><i class="fas fa-book-reader"></i> Didas</span>epidos
                </h1>
            </a>
            <form:form class="search-bar" action="app/list" method="GET">
                <input type="text" name="keyword" placeholder="Buscar"> <button type="submit"><i class="fas fa-search"></i></button>
            </form:form>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/app">Início</a></li>
                    <li><a href="${pageContext.request.contextPath}/app/user-reviews">Minhas Avaliações</a></li>
                    <li><a href="${pageContext.request.contextPath}/app/settings">Configurações</a></li>
                </ul>
            </nav>
        </div>
    </header> -->
	
    <main>
        <section id="search-main">
            <div>
                <a class="logo-a" href="${pageContext.request.contextPath}/">
                    <h1 class="logo">
                        <span class="text-primary"><i class="fas fa-book-reader"></i> Didas</span>epidos
                    </h1>
                </a>

                <form:form class="search-bar" action="app/list" method="GET">
                        <input type="text" name="keyword" placeholder="Buscar"> <button type="submit"><i class="fas fa-search"></i></button>
                </form:form>
            </div>
        </section>
    </main>
</body>
</html>