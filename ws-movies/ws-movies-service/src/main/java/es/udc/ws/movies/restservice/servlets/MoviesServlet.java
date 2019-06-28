package es.udc.ws.movies.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.movies.dto.ServiceMovieDto;
import es.udc.ws.movies.model.movie.Movie;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.restservice.json.JsonServiceExceptionConversor;
import es.udc.ws.movies.restservice.json.JsonServiceMovieDtoConversor;
import es.udc.ws.movies.serviceutil.MovieToMovieDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class MoviesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path != null && path.length() > 0) {
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
					JsonServiceExceptionConversor.toInputValidationException(
							new InputValidationException("Invalid Request: " + "invalid path " + path)),
					null);
			return;
		}
    	ServiceMovieDto movieDto;
        try {
        	movieDto = JsonServiceMovieDtoConversor.toServiceMovieDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonServiceExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;
        }
        Movie movie = MovieToMovieDtoConversor.toMovie(movieDto);
        try {
            movie = MovieServiceFactory.getService().addMovie(movie);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
            		JsonServiceExceptionConversor.toInputValidationException(ex), null);
            return;
        }
        movieDto = MovieToMovieDtoConversor.toMovieDto(movie);

        String movieURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + movie.getMovieId();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", movieURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonServiceMovieDtoConversor.toObjectNode(movieDto), headers);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonServiceExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid movie id")),
                    null);
            return;
        }
        String movieIdAsString = path.substring(1);
        Long movieId;
        try {
            movieId = Long.valueOf(movieIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
            		JsonServiceExceptionConversor.toInputValidationException(new InputValidationException(
                            "Invalid Request: " + "invalid movie id '" + movieIdAsString + "'")),
                    null);
            return;
        }

        ServiceMovieDto movieDto;
        try {
            movieDto = JsonServiceMovieDtoConversor.toServiceMovieDto(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonServiceExceptionConversor
                    .toInputValidationException(new InputValidationException(ex.getMessage())), null);
            return;

        }
        if (!movieId.equals(movieDto.getMovieId())) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
            		JsonServiceExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid movieId")),
                    null);
            return;
        }
        Movie movie = MovieToMovieDtoConversor.toMovie(movieDto);
        try {
            MovieServiceFactory.getService().updateMovie(movie);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
            		JsonServiceExceptionConversor.toInputValidationException(ex), null);
            return;
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
            		JsonServiceExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
            		JsonServiceExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid movie id")),
                    null);
            return;
        }
        String movieIdAsString = path.substring(1);
        Long movieId;
        try {
            movieId = Long.valueOf(movieIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
            		JsonServiceExceptionConversor.toInputValidationException(new InputValidationException(
                            "Invalid Request: " + "invalid movie id '" + movieIdAsString + "'")),
                    null);

            return;
        }
        try {
            MovieServiceFactory.getService().removeMovie(movieId);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
            		JsonServiceExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            String keyWords = req.getParameter("keywords");
            List<Movie> movies = MovieServiceFactory.getService().findMovies(keyWords);
            List<ServiceMovieDto> movieDtos = MovieToMovieDtoConversor.toMovieDtos(movies);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonServiceMovieDtoConversor.toArrayNode(movieDtos), null);
        } else {
            String movieIdAsString = path.substring(1);
            Long movieId;
            try {
                movieId = Long.valueOf(movieIdAsString);
            } catch (NumberFormatException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                		JsonServiceExceptionConversor.toInputValidationException(new InputValidationException(
                                "Invalid Request: " + "invalid movie id'" + movieIdAsString + "'")),
                        null);

                return;
            }
            Movie movie;
            try {
                movie = MovieServiceFactory.getService().findMovie(movieId);
            } catch (InstanceNotFoundException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                		JsonServiceExceptionConversor.toInstanceNotFoundException(ex), null);
                return;
            }
            ServiceMovieDto movieDto = MovieToMovieDtoConversor.toMovieDto(movie);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonServiceMovieDtoConversor.toObjectNode(movieDto), null);
        }
    }

}
