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

```shell
    cd $HOME/software
    tar zxf ws-javaexamples-3.2.4-src.tar.gz
```

## Pasos de pre-instalación de MySQL 8.0.12
- Instalación de MySQL como usuario root y ejecución como usuario normal

```shell
    sudo chown -R root:root mysql-8.0.12-linux-glibc2.12-x86_64
```

> NOTE: For Mac OS X, use "root:wheel" instead of "root:root" with "chown".

```shell
    sudo chmod -R 755 mysql-8.0.12-linux-glibc2.12-x86_64
    sudo ln -s /opt/mysql-8.0.12-linux-glibc2.12-x86_64 /usr/local/mysql
```

- En Ubuntu debe instalarse la librería libio1

```shell
    sudo apt-get install libaio1
```

## Establecer variables de entorno
- Añadir al fichero `$HOME/.bashrc` lo siguiente (en el caso de Mac OS X utilizar 
  el fichero `$HOME/.bash_profile`)

```shell
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
    MYSQL_HOME=/opt/mysql-8.0.12-linux-glibc2.12-x86_64
    PATH=$MYSQL_HOME/bin:$PATH

    # Eclipse.
    PATH=/opt/eclipse:$PATH
```

- Cerrar todos los terminales y abrir terminales nuevos

- Comprobar que el entorno ha quedado correctamente configurado comprobando 
  salidas de los siguientes comandos

```shell
    which java
    which mvn
    which eclipse
    which mysqld
```
    
# Configuración de MySQL ( inicialización de la zona de datos )

- Creación del directorio de datos

```shell
    mkdir $HOME/software/.MySQLData
```

- Crear un fichero $HOME/.my.cnf con el siguiente contenido (en lugar de \<login\>
  poner el login del usuario)

```shell
    [mysqld]
    datadir=/home/<login>/software/.MySQLData
```

- Creación de zona de datos del servidor MySQL (se creará el usuario root con 
  password vacía)

```shell
    cd /usr/local/mysql/bin
    mysqld --initialize-insecure
```    

> NOTA: En caso de producirse errores siguiendo los pasos anteriores, se 
      recomienda seguir las instrucciones que se indican en 
      http://dev.mysql.com/doc/refman/8.0/en/installing.html


## Creación de bases de datos necesarias para los ejemplos

- Arrancar MySQL

```shell
    mysqld
```

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
    cd $HOME/software/ws-javaexamples-3.2.4
    mvn sql:execute install
```
    
## Finalizar la ejecución de la BD

- Finalizar la ejecución de la BD

```shell
    mysqladmin -u root shutdown
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

- Instalación en Linux (Ubuntu)

```shell
    sudo apt-get install git
```

- Instalación en Mac OS
    - Descargar el instalador de [ftp://ftp.fic.udc.es/POJOyWS/](ftp://ftp.fic.udc.es/POJOyWS/)
    - Doble-click en el instalador e instalar con las opciones por defecto

- Configuración básica (Linux y Mac)

```shell
    git config --global user.email "your_email@udc.es"
    git config --global user.name "Your Name"
```

> The following line illustrates how to set Sublime as the Git default editor, but you can use any other editor installed in your OS

```shell
    git config --global core.editor "subl -w"
```

- Instalación de utilidad de autocompletado para Git
    - Descargar del ftp el script `.git-completion.bash` y guardarlo en `$HOME` con el mismo nombre
    - Añadir al fichero `$HOME/.bashrc` lo siguiente (en el caso de Mac OS X utilizar el fichero `$HOME/.bash_profile`)

```shell
    # Git
    source ~/.git-completion.bash
    PS1='\h \W$(__git_ps1 " [%s]"): '
```

## Creación y configuración de claves SSH

- Desde un terminal ejecutar:
> Generar las claves en la ruta por defecto ($HOME/.ssh) y con los nombres 
  por defecto
       
```shell
    ssh-keygen -t rsa -b 4096 -C "your_email@udc.es"
```

- Acceder a [https://git.fic.udc.es/profile/keys](https://git.fic.udc.es/profile/keys)
- En el campo "Key" copiar la clave pública, es decir, el contenido del fichero 
  `$HOME/.ssh/id_rsa.pub`
- En el campo "Title" ponerle un nombre
- Clic en "Add key"

- Comprobar conexión SSH con el servidor de git y añadirlo a la lista de hosts 
  conocidos 
  
> Contestar "yes" a "Are you sure you want to continue connecting (yes/no)?"
    
```shell
    ssh -T git@git.fic.udc.es
```


## Instalación de una herramienta cliente gráfica para Git

- Linux: En el ftp está disponible "SmartGit" pero puede utilizarse cualquier otra (https://git-scm.com/downloads/guis)
    - Descargar `smartgit-linux-18_1_4.tar.gz` de [ftp://ftp.fic.udc.es/POJOyWS/git-gui-clients](ftp://ftp.fic.udc.es/POJOyWS/git-gui-clients) y descomprimirlo en `/opt`
    - Para ejecutar la herramienta utilizar el script `/opt/smartgit/bin/smartgit.sh`

    
- Mac OS: En el ftp está disponible "SourceTree" pero puede utilizarse cualquier otra ([ftp://ftp.fic.udc.es/POJOyWS/git-gui-clients](ftp://ftp.fic.udc.es/POJOyWS/git-gui-clients))
- Instalación en Mac OS
    - Descargar `SourceTree_2.7.6a.zip` de [ftp://ftp.fic.udc.es/POJOyWS/git-gui-clients](ftp://ftp.fic.udc.es/POJOyWS/git-gui-clients) y descomprimirlo en `/opt`
    - Copiar el fichero `SourceTree.app` a la carpeta `Aplicaciones`.
    