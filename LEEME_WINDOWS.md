# Instalación / Configuración entorno ISD / 2019-2020 - Windows
-------------------------------------------------------------------------------

## Descargar y copiar el SW 
> Disponible desde ftp://ftp.fic.udc.es/POJOyWS/

- Seleccionar la versión adecuada al operativo (Windows) / arquitectura del 
  ordenador (32 o 64 bits).

> NOTA: Se recomienda utilizar un usuario de Windows sin espacios en el nombre 
  para evitar problemas con Eclipse y Maven.

- Descargar y descomprimir en `C:\Program Files\Java` el siguiente software
    - maven
    - eclipse
    - tomcat

- Descargar e instalar en la ruta por defecto el JDK
    - Doble-click en `jdk-8u181-windows-<xxx>.exe`. Usar las opciones por defecto.
	 
- Descargar e instalar en la ruta por defecto MySQL:
    - Doble-click en `mysql-installer-community-8.0.12.0.msi`
    - Aceptar la licencia   
    - Elegir "Server only" o "Custom" (para instalar Server + Workbench) y usar 
     las opciones por defecto.
    - Después de la instalación, se ejecutará el wizard de Configuración de 
     MySQL Server. Utilizar las opciones por defecto excepto las siguientes:
         + Debe introducirse una contraseña no vacía para el usuario `root` (e.g. `root`)

> NOTA: Comprobar que la opción "Start the MySQL Server at System Startup"
  está marcada, para que se instale como servicio Windows.
    
## Descargar y descomprimir los ejemplos de la asignatura 

> Disponibles en moodle

- Descargar en `C:\software`
  
## Establecer variables de entorno

- Ir a "Panel de Control > Sistema > Configuración avanzada del sistema > Variables de entorno ..."

- En la sección "Variables de usuario para `<user>`", crear las siguientes
  variables de entorno (para cada una pulsar en "Nueva ...", introducir el 
  nombre y el valor, y pulsar "Aceptar")
    - Nombre: `JAVA_HOME`
        + Valor: `C:\Program Files\Java\jdk1.8.0_181`
    - Nombre: `MAVEN_HOME`
        + Valor: `C:\Program Files\Java\apache-maven-3.6.1`
    - Nombre: `MAVEN_OPTS`
        + Valor: `-Xms512m -Xmx1024m`
    - Nombre: `MYSQL_HOME`
        + Valor: `C:\Program Files\MySQL\MySQL Server 8.0`

- En la sección "Variables de usuario para `<user>`", modificar la variable de
  entorno `PATH`. Para ello hay que seleccionarla, pulsar en "Editar..." y 
  añadir al principio de su valor (sin borrar su valor antiguo):
  
  `%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%MYSQL_HOME%\bin;C:\Program Files\Java\eclipse;`
  
> NOTA: Si la variable de entorno PATH no existiese, entonces habría que 
    crearla procediendo de igual forma que se hizo con las variables anteriores.
    
- Cerrar todos los terminales y abrir terminales nuevos

- Comprobar que el entorno ha quedado correctamente configurado comprobando 
  salidas de los siguientes comandos:
  
```shell 
	java -version
	mvn -version
	mysqld --version
	eclipse             # (pulsar en "Cancel" en la ventana que se abre)
```

## Creación de bases de datos necesarias para los ejemplos
- Arrancar MySQL
  - Si se ha instalado como servicio seguramente se haya iniciado de forma 
    automática. En otro caso habría que iniciar el servicio manualmente.
    
> NOTA: En Panel de Control, Servicios Locales se puede configurar arranque 
  automático o manual. También se puede arrancar y detener.
           
> NOTA: Si se produce un error de conexión al ejecutar los siguientes comandos
  (`mysqladmin` o `myqsl`), probar a ejecutarlos añadiendo la opción `-p` para que
  solicite la password del usuario root.

- Creación de bases de datos ws y wstest (abrir en una consola diferente)

```shell
	mysqladmin -u root create ws
	mysqladmin -u root create wstest
```

- Creación de usuario ws con password con permisos sobre ws y wstest

```shell
	mysql -u root
        CREATE USER 'ws'@'localhost' IDENTIFIED BY 'ws';
		GRANT ALL PRIVILEGES ON ws.* to 'ws'@'localhost' WITH GRANT OPTION;
		GRANT ALL PRIVILEGES ON wstest.* to 'ws'@'localhost' WITH GRANT OPTION;
		exit
```

- Comprobar acceso a BD

```shell
	mysql -u ws --password=ws ws
		exit

	mysql -u ws --password=ws wstest
		exit
```

## Inicialización de datos de ejemplo y compilación de los ejemplos

- Inicialización de la base de datos y compilación de los ejemplos

```shell
	cd C:/software/ws-javaexamples-3.3.0
	mvn sql:execute install
```
	
## Configuración de eclipse
> NOTA: El wizard "Preferences" está accesible desde el menú "Window" (menú
  "Eclipse" en Mac OS X)

- Utilizar Java 1.8:
    + En "Preferences>Java>Compiler" seleccionar "1.8" en "Compiler
    compliance level".
    + En "Preferences>Java>Installed JREs" seleccionar la JVM 1.8.0(Java SE 8).

- Establecer UTF-8 como el encoding por defecto de Eclipse
     + En "Preferences>General>Workspace" seleccionar UTF-8 en "Text File Encoding"
  
- Establecer UTF-8 como el encoding por defecto para ficheros properties Java
    + En "Preferences>General>Content Types>Text>Java Properties File", escribir "UTF-8" y pulsar "Update"

## Instalación y configuración básica de Git
---------------------------------------------------------------------

- Instalación
    - Descargar el instalador de [ftp://ftp.fic.udc.es/POJOyWS/git](ftp://ftp.fic.udc.es/POJOyWS/git)
    - Doble-click en el instalador e instalar con las opciones por defecto

- Configuración básica
    - Ejecutar git-bash (`$GIT_HOME/git-bash.exe`) y desde ese intérprete de  comandos ejecutar:
    
```shell
    git config --global user.email "your_email@udc.es"
    git config --global user.name "Your Name"
```

> The following line illustrates how to set Sublime as the Git default editor, but you can use any other editor installed in your OS
    
```shell
    git config --global core.editor "'C:\Program Files\Sublime Text 3\sublime_text.exe' -w"
```

## Creación y configuración de claves SSH

- Desde el intérprete de comandos git-bash ejecutar:
> Generar las claves en la ruta por defecto ($HOME/.ssh) y con los nombres  por defecto 
      
```shell
    ssh-keygen -t rsa -b 4096 -C "your_email@udc.es"
```    
    
- Acceder a [https://git.fic.udc.es/profile/keys](https://git.fic.udc.es/profile/keys)
- En el campo "Key" copiar la clave pública, es decir, el contenido del fichero `$HOME/.ssh/id_rsa.pub`
- En el campo "Title" ponerle un nombre
- Clic en "Add key"

- Comprobar conexión SSH con el servidor de git y añadirlo a la lista de hosts conocidos. Desde git-bash:
  
> Contestar "yes" a "Are you sure you want to continue connecting (yes/no)?"
   
```shell
    ssh -T git@git.fic.udc.es
```
    
## Instalación de una herramienta cliente gráfica para Git

- En el ftp está disponible "SourceTree" pero puede utilizarse cualquier otra (https://git-scm.com/downloads/guis)
    - Descargar el instalador de [ftp://ftp.fic.udc.es/POJOyWS/git-gui-clients](ftp://ftp.fic.udc.es/POJOyWS/git-gui-clients)
    - Doble-click en el instalador e instalar con las opciones por defecto
    