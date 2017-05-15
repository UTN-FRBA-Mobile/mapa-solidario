# Aplicación - Mapa Solidario

La aplicación está basada principalmente en un mapa colaborativo en el cual se generan y consultan diferentes puntos de necesidad en tiempo real.
Existen cuatro tipos de puntos: Individuo, Heladera Comunitaria, Ropero Comunitario o Emergencia.
El usuario puede consultar los puntos existentes y colaborar con los mismos. A su vez, puede generar nuevos puntos de necesidad que pueden ser satisfechos por otros usuarios.
De ésta manera la aplicación se retroalimenta.
El acceso a la misma se realiza mediante el login de Facebook.
La información de los puntos como la de los usuarios se persiste en una base de datos.

* Login

El usuario podrá loguearse en la aplicación mediante Facebook. Se utiliza su API y se genera el perfil del usuario en la aplicación cuando ingresa por primera vez.

* Mapa

Se presenta el mapa de Google en el cual se visualizan los distintos puntos generados. Se utiliza la API de Google Maps para interactuar con el mapa.
Los cuatro tipo de puntos se generan desde la misma pantalla y son los siguientes:

* Punto de tipo Individuo: Éste punto está orientado a la gente en situación de calle que presenta distintas necesidades. Como usuario de la aplicación se consulta a la persona que presenta la necesidad y se genera dicho punto en el mapa.

* Punto de tipo Heladera Solidaria: Éste punto está orientado a lugares físicos en los que se almacenan alimentos específicos para luego ser distribuidos. Como usuario de la aplicación y responsable del punto de recolección se genera dicho punto en el mapa.

* Punto de tipo Ropero Solidaria: Éste punto está orientado a lugares físicos en los que se almacena indumentaria específica para luego ser distribuida. Como usuario de la aplicación y responsable del punto de recolección se genera dicho punto en el mapa.

* Punto de tipo Emergencia: Éste punto está orientado a sucesos, por ej: inundaciones, para los cuales se disponen puntos de recolección de donaciones. Como usuario de la aplicación y responsable del punto de recolección se genera dicho punto en el mapa.

* Mapa 
    * Carga del Punto

Para los cuatro puntos se presenta la misma interfaz de carga:

Una vez logueado en la aplicación, se presenta en el mapa un marcador con la posición actual o por default (en caso de no permitir acceso a localización).
El usuario puede arrastrar dicho marcador hasta el punto deseado y luego presionar el botón Nuevo Punto para comenzar a generarlo.

Se presentará una pantalla en donde se solicitará indicar:

* Tipo de punto (cada punto utiliza un marker específico, se presentarán cuatro botones)
    * Título del punto (textbox-obligatorio)
    * Descripción de necesidad del punto (textbox-obligatorio)
    * Fecha de expiración del punto (textbox-obligatorio-DD/MM/AAAA)
 
Una vez ingresados los datos, si se presiona Aceptar, se cargará dicho punto y actualizará en el mapa. Si se presiona Cancelar, se volverá a presentar el mapa en el estado anterior.

* Mapa
    * Visualización del Punto

Se dispondrán los puntos en el mapa que no se encuentren expirados. Al presionar sobre éstos se podrá observar una ventana de información.

En dicha pantalla se visualizará lo siguiente:

* Título del punto
* Descripción de necesidad del punto
* Fecha de creación del punto
* Fecha de expiración del punto
* Usuario creador
* Botón de Ayuda
* Contador de Ayuda

El usuario mediante el botón de ayuda indicará que colaboró con dicho punto y se incrementará el contador.
Los puntos expirados no se visualizarán en el mapa.

Se dispondrá de un filtro para poder visualizar los puntos de un tipo específico en el mapa (uno o varios tipos a la vez).

* Sistema de puntos
Con el objetivo de incentivar la colaboración se otorgarán puntos cada vez que se genere una nueva necesidad o se colabore con las existentes.

* Novedades
Se dispondrá de una sección de novedades en la que se puedan visualizar los últimos tres puntos generados de cada tipo. Se priorizarán los puntos de tipo Emergencia.
Al hacer click en la novedad nos permitirá visualizar el punto generado en el mapa.

* Usuarios

    Los usuarios tendrán los siguientes atributos: Nombre, Apellido y Correo Electrónico.  
Estos datos serán obtenidos en primera instancia de la cuenta de Facebook con los permisos de perfil público que se nos brinda al iniciar sesión. Esta información será guardada en nuestra base de datos para mantener un registro de usuarios y brindar la posibilidad al usuario de actualizar su perfil.
También dispondrá de un historial catalogado por puntos creados y puntos que fueron de su interés con un rápido acceso a los mismos.
En ésta misma sección se encontrará el botón de logout.  

* Servidor

    Se trabajará con tres colecciones: para los puntos, novedades y para los usuarios.

    Los puntos tendrán los siguientes atributos: tipo, latitud, longitud, fecha de creación, fecha de expiración, título, texto libre, usuario creador y contador de ayuda.

    Los puntos se crearán en la base de datos mediante api rest (post).
Los puntos se consultarán en la base mediante api rest (get). Se podrán consultar todos los puntos o los puntos de un tipo específico, siempre y cuando no hayan expirado.
Los puntos se modificarán en la base mediante api rest (update). Se utilizará para incrementar el contador de ayuda. 



En la aplicación se utilizó lo siguiente: Login mediante API Facebook, Mapa con API Google Maps, Geolocation, Comunicación con servidor - interfaz API REST.


