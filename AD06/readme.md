Tarefa AD06

Nesta tarefa vamos utilizar MongoDB para crear unha aplicación similar a twitter pero con varias limitacións.
Descripción do problema

A aplicación terá as seguintes características:

    Cando se inicia a aplicación teremos un menú con dúas opcións: rexistrarse e loguearse.

    No rexistro: Para cada usuario gardaremos o nome completo, o username (non poden existir dous iguais), e o seu contrasinal. O contrasinal neste caso non temos que gardalo cun hash (nun caso real si que faría falta, isto facémolo para facelo máis sinxelo.)

    No login: Teremos que comprobar se o nome de usuario e contrasinal son os correctos. Se son os correctos pasaremos ao menú inicio.

    O menú inicio terá as seguintes opcións:
        Ver tódalas mensaxes.
        Ver mensaxes de usuarios que sigo.
        Buscar por hashtag. Un hashtag ten a seguinte forma “#palabra”.
        Escribir unha mensaxe.
        Buscar usuarios.

    Ver tódalas mensaxes:: mostrará todalas mensaxes que hai na aplicación. Primeiro mostraranse as máis recentes. Só se mostrarán de 5 en 5. Polo tanto hai que paxinar os resultados para poder ir navegando entre mensaxes. As mensaxes deben mostrar o nome e username de quen creou a mensaxe, o contido da mensaxe e a data de creación da mensaxe.

    Ver mensaxes de usuarios que sigo:: mostrará todalas mensaxes dos usuarios que sigo. Mostraranse de xeito similar a “ver tódalas mensaxes”.

    Buscar por hashtag: permitirá introducir un hastag e devolverá tódolas mensaxes con ese hashtag. Mostraranse de xeito similar a “ver tódalas mensaxes”.

    Escribir unha mensaxe: permitirá escribir unha mensaxe. Esta non ten límite de caracteres. Debemos gardar o usuario que a creou, o contido da mensaxe e a data de creación.

    Buscar usuarios: o usuario introducirá un username e buscaranse tódolos usuarios que coincidan en parte con ese username. Por exemplo se buscamos por “manu” poderían aparecer: “manuel”, “emmanuel”, etc. Para iso faranse consultas con expresións regulares. Cos resultados, debidamente paxinados, poderemos elixir un usuario para seguir.

Para non vos complicar co modelado de datos e poder facer unha posta en común utilizaremos o seguinte modelo de datos. Pode ser que haxa algún erro. Por favor se vedes que este modelo non é correcto ou hai algo que falte, suxeride cambios no foro.
