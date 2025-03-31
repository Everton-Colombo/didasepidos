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
    <header id="home" class="hero">
        <div class="navbar top home-navbar" id="navbar">
            <a class="logo-a" href="${pageContext.request.contextPath}/">
                <h1 class="logo">
                    <span class="text-primary"><i class="fas fa-book-reader"></i> Didas</span>epidos
                </h1>
            </a>
            <nav>
                <ul>
                    <li><a href="#home">Página Inicial</a></li>
                    <li><a href="${pageContext.request.contextPath}/about">Sobre</a></li>
                    <li><a href="#contact">Contato</a></li>
                </ul>
            </nav>
        </div>

        <div class="content">
            <h1>Avalie Escolas</h1>
            <p>Nós disponibilizamos um sistema de avaliação rápido e categorizado.</p>
            <a href="${pageContext.request.contextPath}/app" class="btn chevron-btn"><i class="fas fa-chevron-right"></i> Começar</a>
        </div>
    </header>

    <main>
        <section id="about">
            <div>
                <span class="fa-stack fa-2x">
                    <i class="fa fa-circle fa-stack-2x icon-background"></i>
                    <i class="fa fa-user-secret fa-stack-1x"></i>
                  </span>
                <div>
                    <h3>Anônimo</h3>
                    <p>Não necessitamos de infomações como seu nome ou email.</p>
                </div>
            </div>
            <div>
                <span class="fa-stack fa-2x">
                    <i class="fa fa-circle fa-stack-2x icon-background"></i>
                    <i class="fa fa-users-slash fa-stack-1x"></i>
                  </span>
                <div>
                    <h3>Sem Contas</h3>
                    <p>Você não precisa criar uma conta. É só entrar e acessar.</p>
                </div>
            </div>
            <div>
                <span class="fa-stack fa-2x">
                    <i class="fa fa-circle fa-stack-2x icon-background"></i>
                    <i class="fa fa-book fa-stack-1x"></i>
                </span>
                <div>
                    <h3>Categorizado por Matéria</h3>
                    <p>Avalie o desempenho de escolas por matéria.</p>
                </div>
            </div>
            <div>
                <span class="fa-stack fa-2x">
                    <i class="fa fa-circle fa-stack-2x icon-background"></i>
                    <i class="fa fa-graduation-cap fa-stack-1x"></i>
                  </span>
                <div>
                    <h3>Várias Instituições</h3>
                    <p>Avalie e veja o desempenho de escolas de todo o país.</p>
                </div>
            </div>
        </section>

        <section id="inst-count" class="container datashow">
            <div class="data-emphasis">
                <h1>${institutionCount}+</h1>
                <p>escolas já cadastradas</p>
            </div>
            <div class="txt">
                <p>Devido ao nosso modo de operação, qualquer pessoa pode solicitar o cadastro de uma nova escola; até mesmo alunos!</p>
                <a href="#contact" class="btn chevron-btn btn-outline"><i class="fas fa-chevron-right"></i> Solicitar Cadastro</a>
            </div>
        </section>

        <section id="revs-count" class="container datashow layout-reverse">
            <div class="data-emphasis">
                <h1>${reviewCount}+</h1>
                <p>avaliações feitas</p>
            </div>
            <div class="txt">
                <p>Junte-se às várias pessoas que já utilizaram a plataforma para postar avaliações!</p>
                <a href="${pageContext.request.contextPath}/app" class="btn chevron-btn btn-outline"><i class="fas fa-chevron-right"></i> Começar</a>
            </div>
        </section>

        <section id="contact" class="container single-data-display">
            <div class="wrapper">
                <h1>Contato</h1>
                <p>e.rcolombo2@gmail.com</p>
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

        let scrolled = false;

        window.onscroll = function() {
            if(window.pageYOffset > 500) {
                navbar.classList.remove('top');
                // if(!scrolled) {
                //     // navbar.style.transform = 'translateY(-70px)';
                // }
                setTimeout(function() {
                    // navbar.style.transform = 'translateY(0)';
                    scrolled = true;
                }, 200);
            } else {
                navbar.classList.add('top');
                scrolled = false;
            }
        };

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