<%@ page isELIgnored ="false" %> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/utilities.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/icons/favicon.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Didasepidos | Início</title>
</head>
<body>
    <header>
        <div class="navbar home-navbar" id="navbar">
            <a class="logo-a" href="${pageContext.request.contextPath}/">
                <h1 class="logo">
                    <span class="text-primary"><i class="fas fa-book-reader"></i> Didas</span>epidos
                </h1>
            </a>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Página Inicial</a></li>
                    <li><a href="#">Sobre</a></li>
                    <li><a href="${pageContext.request.contextPath}/">Contato</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <main>
        <section id="about-page-section" class="container">
            <h1 class="title">Sobre</h1>
            <div class="mini-article">
                <h2 class="article-title">O que é</h2>
                <p>Didasepidos é um serviço criado para facilitar a avaliação de escolas. Seu principal foco é a anonimicidade oferecida: não são coletadas quaisquer informações que possam identificar seus usuários.</p>
            </div>
            <div class="mini-article">
                <h2 class="article-title">Objetivo</h2>
                <p>O público-alvo do serviço são os alunos. Seu objetivo, portanto, é não somente comparar instituições mas também criar um índice que demonstra o nível de satisfação dos estudantes.</p>
            </div>
        </section>
        
        <section id="faq" class="container">
            <h1 class="title">Perguntas Frequentes</h1>
            <div class="mini-article">
                <h2 class="article-title">Que critério é utilizado para selecionar as melhores instituições?</h2>
                <p>O ranking da página inicial mostra as 10 instituições mais bem avaliadas. Para uma instituição chegar ao ranking, ela deve, além de possuir uma alta avaliação média, possuir um número mínimo avaliações. Esse número é, idealmente, igual a 2% do número de usuários mensais do serviço. Porém, caso essa porcentagem resulte em um número menor que 5, o número mínimo de avaliações se torna 5. <u>Em resumo, o número mínimo de avaliações para uma instituição ser considerada para o ranking é ou 5, ou 2% dos usuários mensais do serviço, o que for maior.</u></p>
                <p>Em seguida, selecionam-se, dentre as instituições que satisfazem esse critério, as que possuem as dez maiores notas médias. Se duas instituições possuírem a mesma nota média, escolhe-se aquela com o maior número de avaliações.</p>
            </div>
            <div class="mini-article">
                <h2 class="article-title">Por que há o limite de 1 avaliação por semana?</h2>
                <p>O serviço limita o número de avaliações que você pode enviar para uma instituição para previnir fraudes. Se uma instituição recebesse várias avaliações por dia de um mesmo usuário, sua pontuação não seria precisa.</p>
            </div>
            <div class="mini-article">
                <h2 class="article-title">O que impede o envio de avaliações falsas?</h2>
                <p>As avaliações possuem um sistema de upvotes (likes) e downvotes (dislikes). Se o número de downvotes for maior ou igual a 10, e a razão downvotes/upvotes de uma avaliação em específico for maior do que 5, a avaliação será anulada.</p>
            </div>
            <div class="mini-article">
                <h2 class="article-title">Que critério é utilizado para selecionar as avaliações da seção "Top 10 avaliações do mês"?</h2>
                <p>Essa seção contém as avaliações enviadas nos últimos 30 dias com o maior número de upvotes (likes).</p>
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
        const navbar = document.getElementById('navbar');

        $('#navbar a, btn').on('click', function (e) {
            if(this.hash !== '') {
                e.preventDefault();

                const hash = this.hash;

                $('html, body').animate(
                    {
                        scrollTop: $(hash).offset().top - 100,
                    },
                    800
                );
            }
        });
    </script>
</body>
</html>