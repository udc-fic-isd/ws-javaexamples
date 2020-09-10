# Instalación / Configuración entorno ISD / 2020-2021 - Linux y macOS
-------------------------------------------------------------------------------

## Descargar y copiar el SW
> Disponible desde ftp://ftp.fic.udc.es/POJOyWS/

- Seleccionar la versión adecuada al operativo (Linux macOS) / 
  arquitectura del ordenador (32 o 64 bits).
  
- [Linux] 
    - Descargar y descomprimir en `/opt` el siguiente software
        - Maven 3.6.x o superior 
            - https://maven.apache.org/download.cgi
            - Descargar el "Binary tar.gz archive".
        - IntelliJ IDEA
            - https://www.jetbrains.com/es-es/idea/download
            - Se puede utilizar la versión Community (libre) o la versión Ultimate 
              (solicitando una licencia para estudiantes). 
    - Instalar como paquete
        - AdoptOpenJDK 11
            - Instalar como paquete siguiendo las instrucciones que se 
              indican en la sección "Linux RPM and DEB installer packages" de 
              https://adoptopenjdk.net/installation.html.
            - Instalar la version "Open JDK 11 (LTS)" con la JVM "Hotspot"
              (adoptopenjdk-11-hotspot).
        - MySQL 8
            - Seguir las instrucciones que se indican en 
              https://dev.mysql.com/doc/refman/8.0/en/linux-installation.html

- [macOS] 
    - Descargar y descomprimir en `/opt`
        - Maven 3.6.x o superior 
            - https://maven.apache.org/download.cgi
            - Descargar el "Binary tar.gz archive"
    - Deescargar e instalar
        - IntelliJ IDEA
            - https://www.jetbrains.com/es-es/idea/download
            - Se puede utilizar la versión Community (libre) o la versión Ultimate 
              (solicitando una licencia para estudiantes). 
            - Instalar usando las opciones por defecto.
        - AdoptOpenJDK 11
            - https://adoptopenjdk.net/
            - Seleccionar la version "Open JDK 11 (LTS)" y la JVM "Hotspot".
            - Descargar el instalador .pkg para macOS e instalar usando las opciones por defecto.
        - MySQL 8
            - https://dev.mysql.com/downloads/mysql/
            - Descargar el instalador .dmg para macOS
            - Instalar con las opciones por defecto.
            - Preferencias del sistema -> MySQL -> Elegir "Start MySQL when your computer starts up".
            - Más información: https://dev.mysql.com/doc/refman/8.0/en/osx-installation.html

- Descargar y descomprimir en `$HOME/software` el siguiente software
    - Tomcat 9.x 
        + https://tomcat.apache.org/download-90.cgi
        + En el apartado "Binary Distributions" / "Core" descargar el .tar.gz.

- Descargar e instalar el compilador de Apache Thrift:
    - TODO
         
## Descargar y descomprimir los ejemplos de la asignatura
- Descargar en `$HOME/software`

> Disponibles en moodle

```shell
    cd $HOME/software
    tar zxf ws-javaexamples-3.4.0-src.tar.gz
```

## [Linux] Establecer variables de entorno
- Añadir al fichero `$HOME/.bashrc` lo siguiente 
> NOTA: Los valores de las variables MAVEN_HOME y JAVA_HOME deben sustituirse por los 
  directorios donde se haya descomprimido Maven e instalado AdoptOpenJDK respectivamente

```shell
    # AdoptOpenJDK (Linux)
    export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

    PATH=$JAVA_HOME/bin:$PATH

    # Maven
    MAVEN_HOME=/opt/apache-maven-3.6.3
    PATH=$MAVEN_HOME/bin:$PATH
    export MAVEN_OPTS="-Xms512m -Xmx1024m"

    # IntelliJ IDEA
    IDEA_HOME=/opt/idea
    PATH=$IDEA_HOME/bin:$PATH
```

- Cerrar todos los terminales y abrir terminales nuevos

- Comprobar que el entorno ha quedado correctamente configurado comprobando 
  las salidas de los siguientes comandos

```shell
    which java
    which mvn
    which idea
```

## [macOS] Establecer variables de entorno
- Añadir al fichero `$HOME/.bash_profile` lo siguiente:
> NOTA: Los valores de las variables MAVEN_HOME y JAVA_HOME deben sustituirse por los 
  directorios donde se haya descomprimido Maven e instalado AdoptOpenJDK respectivamente

```shell
    # AdoptOpenJDK (macOS)
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home

    PATH=$JAVA_HOME/bin:$PATH

    # Maven
    MAVEN_HOME=/opt/apache-maven-3.6.3
    PATH=$MAVEN_HOME/bin:$PATH
    export MAVEN_OPTS="-Xms512m -Xmx1024m"
```

- Cerrar todos los terminales y abrir terminales nuevos

- Comprobar que el entorno ha quedado correctamente configurado comprobando 
  las salidas de los siguientes comandos

```shell
    which java
    which mvn
```
    
## Creación de bases de datos necesarias para los ejemplos

- Arrancar MySQL (sólo si el arranque no es automático)

```shell
    mysqld
```

> NOTA: En los siguientes pasos, al ejecutar los comandos  `mysqladmin` y `myqsl` 
  con la opción `-p` las password que nos solicitarán es la password del usuario
  root que se especificó al instalar MySQL.

- Creación de bases de datos ws y wstest (abrir en una consola diferente)

```shell
  mysqladmin -u root create ws -p
  mysqladmin -u root create wstest -p
```

- Creación de usuario ws con password con permisos sobre ws y wstest

```shell
    mysql -u root -p
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
    cd $HOME/software/ws-javaexamples-3.4.0
    mvn sql:execute install
```
    
## Finalizar la ejecución de la BD

- Finalizar la ejecución de la BD (sólo si el arranque no es automático)

```shell
    mysqladmin -u root shutdown
```

## Configuración de IntelliJ IDEA
- Se recomienda instalar el plugin de Thrift (lo sugerirá el editor al abrir un fichero .thrift)


## Instalación y configuración básica de Git
---------------------------------------------------------------------
> NOTA: Este paso no es necesario si ya utilizó y configuró Git en otras asignaturas

- Instalación en Linux
    - https://git-scm.com/downloads
    - Hacer clic en "Linux/Unix" y seguir las instrucciones según la distribución de linux utilizada.
     
- Instalación en macOS
    - https://git-scm.com/downloads
    - Hacer clic en "MacOs". En la siguiente pantalla hacer clic en "installer"
      dentro de la sección "Binary Installer" y descargar la última versión del instalador .dmg.
    - Instalar con las opciones por defecto.

- Configuración básica (Linux y macOS)

```shell
    git config --global user.email "your_email@udc.es"
    git config --global user.name "Your Name"
```

> El siguiente comando ilustra como configurar Sublime como editor por defecto de Git, aunque se puede utilizar otro editor instalado en el sistema operativo.

```shell
    git config --global core.editor "subl -w"
```

- (Opcional) Instalación de utilidad de autocompletado para Git
    - Seguir las instrucciones indicadas en https://github.com/bobthecow/git-flow-completion/wiki/Install-Bash-git-completion

## Creación y configuración de claves SSH
> NOTA: Este paso no es necesario si ya utilizó Git en otras asignaturas

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

- Puede utilizarse cualquier herramienta cliente (https://git-scm.com/downloads/guis)
