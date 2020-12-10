Tarefa AD02

Nesta tarefa vamos poñer en práctica o visto nesta unidade intentando facer un caso práctico e relativamente funcional.
Descripción Tarefa

A situación é a seguinte: necesitamos un programa para xestionar as tendas dunha franquicia de venta de equipos informáticos. Necesitamos gardar para cada tenda o seu nome e a cidade na que se encontra. Ademais para canda tenda teremos que gardar os productos que hai en stock. Para cada producto necesitamos gardar o seu identificador, a súa descripción, o seu prezo e a cantidade que hai en stock. Ademais debe mostrar para canda tenda os empregados que están traballando. Para cada empregado teremos que gardar o nome e os apelidos. Por último, debemos gardar unha lista con clientes. Estos non son propios de cada tenda como os empregados, senón que son da compañía. Para cada cliente debemos gardar o nome, apelidos e o seu email.
As accións que se poden facer nesta aplicación son as seguintes:

    Engadir unha tenda.
    Eliminar unha tenda (elimínanse tódolos productos e empragados desta).
    Engadir un producto a tenda.
    Eliminiar un producto a tenda.
    Engadir un empregado a tenda.
    Eliminar un emprega a tenda.
    Engadir un cliente.
    Eliminar un cliente.
    Crear unha copia de seguriadade dos datos (Explícase máis abaixo).
    Ler os titulares do periódico El País. (Explícase máis abaixo)
    Sair do programa.

Non é necesario realizar unha interface gráfica. Pódese facer un menú que pida os datos pola consola.
A persistencia debe de facerse do seguinte xeito:

    Debese gardar os datos da aplicación nun arquivo JSON.
    Cando se inicie o programa lese ese arquivo para poder traballar cos datos.
    Cada vez que se produza un cambio nos datos teremos que gardar estes no arquivo JSON.
    Utiliza a librería GSON.

Para facer unha copia de seguridade dos datos deberase realizar unha copia do arquivo JSON onde se gardan os datos. Para realizar isto utilizaremos o visto en “Lectura e escritura de arquivos binarios”. O novo arquivo debe levar o sufixo .backup.

En canto a lectura dos titulares de “El País” a aplicación tera que ler un RSS. Estes están en formato XML. Tan só se deberán mostrar por pantalla os titulares. Para iso utiliza SAX para parsear o documento XML. O arquivo XML telo debaixo da tarefa. Como ampliación e optativo sería interesante en lugar de ler o arquivo descargado, poder ler o arquivo online. Así sempre teriamos os titulares actualizados. O enlace dese XML é o seguinte: RSS de El País.
