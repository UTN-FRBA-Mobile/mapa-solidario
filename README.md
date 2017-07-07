# Aplicación - Mapa Solidario

La aplicación está basada principalmente en un mapa colaborativo en el cual se generan y consultan diferentes puntos de necesidad en tiempo real.
Existen cuatro tipos de puntos: Individuo, Heladera Solidaria, Ropero Solidario o Emergencia.
El usuario puede consultar los puntos existentes y colaborar con los mismos. A su vez, puede generar nuevos puntos de necesidad que pueden ser satisfechos por otros usuarios.
De ésta manera la aplicación se retroalimenta.
El acceso a la misma se realiza mediante el login de Facebook.
La información de los puntos como la de los usuarios se persiste en una base de datos.

* Login

El usuario podrá loguearse en la aplicación mediante Facebook. Se utiliza su API y se genera el perfil del usuario en la aplicación cuando ingresa por primera vez.
<p align="center">
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499450824.png?raw=true" width="350"/>
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499450883.png?raw=true" width="350"/>
</p>

# Mapa

Se presenta el mapa de Google en el cual se visualizan los distintos puntos generados. Se utiliza la API de Google Maps para interactuar con el mapa.
Los cuatro tipo de puntos se generan desde la misma pantalla y son los siguientes:

* Punto de tipo Individuo: Éste punto está orientado a la gente en situación de calle que presenta distintas necesidades. Como usuario de la aplicación se consulta a la persona que presenta la necesidad y se genera dicho punto en el mapa.

* Punto de tipo Heladera Solidaria: Éste punto está orientado a lugares físicos en los que se almacenan alimentos específicos para luego ser distribuidos. Como usuario de la aplicación y responsable del punto de recolección se genera dicho punto en el mapa.

* Punto de tipo Ropero Solidario: Éste punto está orientado a lugares físicos en los que se almacena indumentaria específica para luego ser distribuida. Como usuario de la aplicación y responsable del punto de recolección se genera dicho punto en el mapa.

* Punto de tipo Emergencia: Éste punto está orientado a sucesos, por ej: inundaciones, para los cuales se disponen puntos de recolección de donaciones. Como usuario de la aplicación y responsable del punto de recolección se genera dicho punto en el mapa.

**Carga del Punto**

Para los cuatro puntos se presenta la misma interfaz de carga:

Una vez logueado en la aplicación, se presenta en el mapa un botón +. Al presionarlo se agrega un marcador en la posición actual o por default (en caso de no permitir acceso a localización).
El usuario puede arrastrar dicho marcador hasta el punto deseado si difiere de su ubicación, y luego presionar el botón de OK para comenzar a generarlo o X para descartarlo.

Se presentará una pantalla en donde se solicitará indicar:

* Vista previa del marcador en el mapa

* Dirección del marcador en el mapa

* Tipo de punto (cada punto utiliza un marker específico, combobox-obligatorio)

* Título del punto (textbox-obligatorio)

* Fecha de vencimiento del punto (SI(fecha)/NO)

* Descripción de necesidad del punto (textbox-obligatorio)

Una vez ingresados los datos, si se presiona guardar, se cargará dicho punto y actualizará en el mapa. Si se presiona Volver, se volverá a presentar el mapa en el estado anterior.

<p align="center">
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499451088.png?raw=true" width="350"/>
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499451100.png?raw=true" width="350"/>
</p>

	 
**Visualización del Punto**

Se dispondrán los puntos en el mapa que no se encuentren expirados. Al presionar sobre éstos se podrá observar una ventana de información.

En dicha pantalla se visualizará lo siguiente:

* Título del punto
* Link al detalle del punto

Los puntos expirados no se visualizarán en el mapa.

* Sistema de puntos
Con el objetivo de incentivar la colaboración se otorgarán puntos cada vez que se genere una nueva necesidad o se colabore con las existentes.

<p align="center">
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499450942.png?raw=true" width="350"/>
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499450954.png?raw=true" width="350"/>
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499450977.png?raw=true" width="350"/>
</p>

# Novedades
	 
Se dispondrá de una sección de novedades en la que se puedan visualizar los últimos diez puntos generados. Se priorizarán los puntos con vencimiento.
Al hacer click en la novedad nos permitirá visualizar el detalle del punto generado.

<p align="center">
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499451018.png?raw=true" width="350"/>
</p>

# Usuarios
	 
Los usuarios tendrán los siguientes atributos: Nombre, Apellido y Correo Electrónico.  
Estos datos serán obtenidos en primera instancia de la cuenta de Facebook con los permisos de perfil público que se nos brinda al iniciar sesión. Esta información será guardada en nuestra base de datos para mantener un registro de usuarios y brindar la posibilidad al usuario de actualizar su perfil.
También dispondrá de un historial catalogado por los puntos creados por el usuario, a los cuales podrá acceder para visualizar, modificar o borrar .
También se podrá visualizar el puntaje acumulado en base a la creación de puntos y ayudas realizadas.
En ésta misma sección se encontrará el botón de logout.

<p align="center">
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499451030.png?raw=true" width="350"/>
  <img src="https://github.com/UTN-FRBA-Mobile/mapa-solidario/blob/master/img/Screenshot_1499451077.png?raw=true" width="350"/>
</p>
