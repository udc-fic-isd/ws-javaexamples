# ws-javaexamples
---------------------------------------------------------------------

## Installing the development environment

- [Instructions for Unix-like operating systems (spanish)] (https://github.com/udc-fic-isd/isd-entorno/blob/main/LEEME_UNIX.md).
- [Instructions for Windows (spanish)] (https://github.com/udc-fic-isd/isd-entorno/blob/main/LEEME_WINDOWS.md).

## Initializing the database and building the examples

	mvn sql:execute install


## Running the ws-movies example
---------------------------------------------------------------------

`ws-movies` provides a web service applications. `ws-movies` requires the database 
server to be running.

### Running the ws-movies service with Maven/Jetty.

	cd ws-movies/ws-movies-service
	mvn jetty:run


### Running the ws-movies service with Tomcat.

- Copy the `.war` file (e.g. `ws-movies/ws-movies-service/target/ws-movies-service.war`) 
  to Tomcat's `webapps` directory.

- Start Tomcat:

	cd <TOMCAT_HOME>/bin
	startup.sh

- Shutdown Tomcat:

	shutdown.sh


### Running the ws-movies client application

	cd ws-movies/ws-movies-client

Configure `src/main/resources/ConfigurationParameters.properties`
  for specifying the client movie service implementation (Rest or Thrift) and 
  the port number of the web server in the endpoint address (9090 for Jetty, 8080
  for Tomcat)

- AddMovie

	mvn exec:java -Dexec.mainClass="es.udc.ws.movies.client.ui.MovieServiceClient" -Dexec.args="-a 'Star Wars V' 2 32 'Star Wars V - The empire strikes back' 6"
		
- UpdateMovie

	mvn exec:java -Dexec.mainClass="es.udc.ws.movies.client.ui.MovieServiceClient" -Dexec.args="-u 1 'Star Wars V' 2 32 'Star Wars V - The empire strikes back' 6"
	
- FindMovies

	mvn exec:java -Dexec.mainClass="es.udc.ws.movies.client.ui.MovieServiceClient" -Dexec.args="-f 'Star Wars'"

- BuyMovie

	mvn exec:java -Dexec.mainClass="es.udc.ws.movies.client.ui.MovieServiceClient" -Dexec.args="-b 1 'isd-user' '1234567891011213'"
		
- GetMovieURL

	mvn exec:java -Dexec.mainClass="es.udc.ws.movies.client.ui.MovieServiceClient" -Dexec.args="-g 1"

- RemoveMovie

	mvn exec:java -Dexec.mainClass="es.udc.ws.movies.client.ui.MovieServiceClient" -Dexec.args="-r 1"

- Accessing to ws-movies resources (9090 for Jetty, 8080 for Tomcat)
    - movies   - GET [http://localhost:9090/ws-movies-service/movies](http://localhost:9090/ws-movies-service/movies)
    - sale #1  - GET [http://localhost:9090/ws-movies-service/sales/1](http://localhost:9090/ws-movies-service/sales/1)