# Instalación / Configuración entorno ISD / 2018-2019 - Linux Mac OS
-------------------------------------------------------------------------------

## Descargar y copiar el SW
> Disponible desde ftp://ftp.fic.udc.es/POJOyWS/

- Seleccionar la versión adecuada al operativo (Linux o Mac OS) / 
  arquitectura del ordenador (32 o 64 bits).
  
- Descargar y descomprimir en `/opt` el siguiente software
    - jdk
    - maven
    - eclipse
    - mysql
	 
- Descargar y descomprimir en `$HOME/software` el siguiente software
    - tomcat
	 
## Descargar y descomprimir los ejemplos de la asignatura
- Descargar en `$HOME/software`

> Disponibles en moodle
  
	cd $HOME/software
	tar zxf ws-javaexamples-3.2.4-src.tar.gz


## Pasos de pre-instalación de MySQL 8.0.11
- Instalación de MySQL como usuario root y ejecución como usuario normal

	sudo chown -R root:root mysql-8.0.11-linux-glibc2.12-x86_64
    
> NOTE: For Mac OS X, use "root:wheel" instead of "root:root" with "chown".

	sudo chmod -R 755 mysql-8.0.11-linux-glibc2.12-x86_64
	sudo ln -s /opt/mysql-8.0.11-linux-glibc2.12-x86_64 /usr/local/mysql

- En Ubuntu debe instalarse la librería libio1

	sudo apt-get install libaio1

## Establecer variables de entorno
- Añadir al fichero `$HOME/.bashrc` lo siguiente (en el caso de Mac OS X utilizar 
  el fichero `$HOME/.bash_profile`)

	# J2SE
	export JAVA_HOME=/opt/jdk1.8.0_181

	# For Mac OS X, use:
	#export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home
	# For convenience.
	PATH=$JAVA_HOME/bin:$PATH

	# Maven
	MAVEN_HOME=/opt/apache-maven-3.5.4
	PATH=$MAVEN_HOME/bin:$PATH
	export MAVEN_OPTS="-Xms512m -Xmx1024m"

	# MySQL.
	MYSQL_HOME=/opt/mysql-8.0.11-linux-glibc2.12-x86_64
	PATH=$MYSQL_HOME/bin:$PATH

	# Eclipse.
	PATH=/opt/eclipse:$PATH


- Cerrar todos los terminales y abrir terminales nuevos

- Comprobar que el entorno ha quedado correctamente configurado comprobando 
  salidas de los siguientes comandos
  
	which java
	which mvn
	which eclipse
	which mysqld

	
# Configuración de MySQL ( inicialización de la zona de datos )

- Creación del directorio de datos

	mkdir $HOME/software/.MySQLData


- Crear un fichero $HOME/.my.cnf con el siguiente contenido (en lugar de \<login\>
  poner el login del usuario)

	[mysqld]
	datadir=/home/<login>/software/.MySQLData

- Creación de zona de datos del servidor MySQL (se creará el usuario root con 
  password vacía)
  
	cd /usr/local/mysql/bin
	mysqld --initialize-insecure
	

> NOTA: En caso de producirse errores siguiendo los pasos anteriores, se 
      recomienda seguir las instrucciones que se indican en 
      http://dev.mysql.com/doc/refman/8.0/en/installing.html


## Creación de bases de datos necesarias para los ejemplos

- Arrancar MySQL

	mysqld

- Creación de bases de datos ws y wstest (abrir en una consola diferente)

	mysqladmin -u root create ws
	mysqladmin -u root create wstest


- Creación de usuario ws con password con permisos sobre ws y wstest

	mysql -u root
        CREATE USER 'ws'@'localhost' IDENTIFIED BY 'ws';
		GRANT ALL PRIVILEGES ON ws.* to 'ws'@'localhost' WITH GRANT OPTION;
		GRANT ALL PRIVILEGES ON wstest.* to 'ws'@'localhost' WITH GRANT OPTION;
		exit

- Comprobar acceso a BD

	mysql -u ws --password=ws ws
		exit

	mysql -u ws --password=ws wstest
		exit

		
## Inicialización de datos de ejemplo y compilación de los ejemplos

- Inicialización de la base de datos y compilación de los ejemplos

	cd $HOME/software/ws-javaexamples-3.2.4
	mvn sql:execute install

	
## Finalizar la ejecución de la BD

- Finalizar la ejecución de la BD

	mysqladmin -u root shutdown


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
  
