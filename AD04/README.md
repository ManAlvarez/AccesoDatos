Tarefa AD04

Nesta tarefa vamos poñer en práctica o visto nesta unidade intentando facer un caso práctico e relativamente funcional. Imos partir da tarefa AD03 modificando a persistencia de datos. Imos pasar de utilizar SQLite a MySQL con Hibernate.
Descripción do problema

A situación é a seguinte: necesitamos un programa para xestionar as tendas dunha franquicia de venta de equipos informáticos.
Necesitamos gardar a seguinte información:

    Temos que gardar as provincias de España. Esta telas no arquivo provincias.json. Deberase gardar o id e o nome da provincia. Só se deben gardar se non existen xa na base de datos.
    As tendas co seu nome, a provincia e a súa cidade.
    Os productos co seu nome, descripción e prezo.
    Cada tenda terá unha selección de productos. Para cada tenda temos que gardar o stock que ten dese productos.
    Cada tenda terá unha serie de empragados. Cada empregado pode traballar en unha ou varias tendas. Necesitamos gardar o número de horas semanais que traballa en cada tenda. Ademais necesitamos gardar o se nome e apelidos.
    Os clientes da franquicia. Estes non son clientes de cada tenda, senón da franquicia en xeral. De cada cliente debemos gardar o seu nome, apelidos e email.

A aplicación deberá poder facer as seguintes accións aos seus usuarios:

    Engadir unha nova tenda.
    Mostrar as tendas.
    Eliminar unha tenda.
    Engadir un producto.
    Mostrar os productos da franquicia.
    Mostrar os productos dunha tenda.
    Engadir un producto a unha tenda.
    Actualizar o stock dun producto nunha determinada tenda.
    Mostrar o stock dun producto dunha tenda.
    Eliminar un producto dunha determinada tenda.
    Eliminar un producto.
    Engadir un cliente.
    Mostrar os clientes
    Eliminar un cliente.
    Xerar un informe json co stock de todos os productos en formato JSON.
    Ler os titulares do periódico El Pais. (Explícase máis abaixo)
    Sair da aplicación.

Non é necesario realizar unha interface gráfica. Pódese facer un menú que pida os datos pola consola.
A persistencia debe de facerse do seguinte xeito:

    Deberas gardar os datos nunha base de datos MySQL utilizando o ORM Hibernate.
    Os datos de conexión da base de datos e otros datos de configuración de Hibernate terá que obterse do ficherio config.json. Este ten o seguinte formato:

{
    "dbConnection":{
        "address":"192.168.56.101",
        "port":"3306",
        "name":"hibernate",
        "user":"userhibernate",
        "password":"abc123."    
    },
    "hibernate":{
        "driver":"com.mysql.cj.jdbc.Driver",
        "dialect":"org.hibernate.dialect.MySQL5Dialect",
        "HBM2DDL_AUTO":"create",
        "SHOW_SQL": true
    }
}

    A base de datos ten que estar creada antes de iniciar o programa. O que si ten que comprobar e que esten tódalas provincias insertadas.
    Cada vez que se produza un cambio nos datos teremos que actualizar a base de datos.

Para realizar a carga de datos das provincias, alectura de configuración e para xerar o informe de stock débese de utilizar a liberías GSON.

En canto a lectura dos titulares de “El País” a aplicación tera que ler un RSS. Estes están en formato XML. Tan só se deberán mostrar por pantalla os titulares. Para iso utiliza SAX para parsear o documento XML. O arquivo XML telo debaixo da tarefa. Como ampliación e optativo sería interesante en lugar de ler o arquivo descargado, poder ler o arquivo online. Así sempre teriamos os titulares actualizados. O enlace dese XML é o seguinte: RSS de El País.
Observacións

Obrigatorio usar Maven.
