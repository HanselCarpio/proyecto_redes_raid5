# proyecto_redes_raid5

Proyecto #2 de Redes y Comunicación de Datos. Servidor y cliente RAID5, (Bryan Keihl, Hansel Carpio &amp; Victor Fernandez)


Requerimentos para ejecutar correctamente este proyecto:

Java Development Kit (Java SE 16): https://www.oracle.com/java/technologies/javase-jdk16-downloads.html

Se puede utilizar cualquier ide para desarrollar en Java y en este caso, se desarrolló y ejecutó dicho lenguaje en

Netbeans 12.4: https://www.apache.org/dyn/closer.cgi/netbeans/netbeans/12.4/Apache-NetBeans-12.4-bin-windows-x64.exe

De igual forma, tambien se puede utilizar 

Visual Studio Code v1.56: https://code.visualstudio.com/Download


Teniendo esto previemente instalado en la pc, procederemos a descargar la version más reciente del proyecto, para ello es necesario dar click en la fecha que esta ubicada en el boton de color verde "CODE" y luego de que se desplegue el menú, darle click a al opción "DOWNLOAD ZIP"

IMAGEN

Luego de descargar correctamente el proyecto, lo colocamos en la ubicacion de prefecenecia y se precede a descomprimir el .zip y se obtendran los siguientes archivos

IMAGEN

Donde en la carpeta llamada "Proyecto Redes (B63727_B40425)" se encontrarán los archivos correspondientes al proyecto java

IMAGEN

Luego de esto, abriremos Apache Netbeans y daremos en la siguiente opcion para poder cargar los proyectos

IMAGEN

Luego, buscaremos la ubicacion donde tenemos nuestro proyecto correspondiente

IMAGEN

El siguiente paso a seguir es el de seleccionar uno de esos dos proyectos, en este caso empezaremos con Client_RAID5, donde al abrirlo se desplegará una ventana emergente con una advertencia sobre librerias faltantes

IMAGEN

En este punto debemos seleccionar la opción de solucionar problemas (Resolve Problems), donde nos aparecera el sguiente cuadro

IMAGEN

Una vez ahí seleccionamos el primer problema y le daremos a la casilla de resolver (Resolve) y se nos desplegará otra venta donde vamos a buscar  la carpeta "MySQL Jar" que esta dentro de la carpeta que descomprimimos al principio

IMAGEN

Dentro de esa carpeta habrán 2 archivos de extención.jar que son las librerias faltantes, elegimos la opción correcta del problema a resolver y le damos a la opción de "Open"

IMAGEN

Una vez realizado esto, repetimos los pasos para resolver el otro problema de faltante de libreria

IMAGEN

Donde elegimos el otro archivo .jar que nos detalla en el margen superior, la misma ventana de busqueda

IMAGEN

Luego de esto, se verá reflejado en la ventana, con un check, que los dos problemas ya están solucionados

IMAGEN

Al finalizar esto, procedemos a buscar el otro proyecto, que sería el de Server_RAID5

IMAGEN

Cuando abrimos este, se nos desplegara la misma ventena que nos indica los miemos errores de faltante de librerias

IMAGEN

Para resolver estos, tenemos que realizar los mismos pasos que con el Client_RIAD5...

Una vez realizado, procedemos a verificar que todo este corecto, donde en la parte derecha de Apache Netbeans estará el menu de los dos proyectos y su respectivo despliegue de archivos. Aqui nos iremos a la opción de "Libraries" y tiene que aparecer de la siguiente manera

IMAGEN

Como parte del proyecto, estos archivos con extencion .jar son utilizados para la conexion desde java, se necesita un Java Database Connectivity, más conocida por sus siglas JDBC, que es una API que permite la ejecución de operaciones sobre bases de datos desde el lenguaje de programación Java.


Concluido todo esto, se tiene lo necesario para ejecutar el proyecto, para saber sobre su utilizacion dirigirse a la wiki respectiva
