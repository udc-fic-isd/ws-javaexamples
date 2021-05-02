package es.udc.ws.movies.restservice.servlets.actions;

import es.udc.ws.movies.model.movie.Movie;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.restservice.dto.MovieToRestMovieDtoConversor;
import es.udc.ws.movies.restservice.dto.RestMovieDto;
import es.udc.ws.movies.restservice.json.JsonToRestMovieDtoConversor;
import es.udc.ws.movies.restservice.servlets.Action;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddMovieAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, InputValidationException {
        ServletUtils.checkEmptyPath(req);

        RestMovieDto movieDto;
        try {
            movieDto = JsonToRestMovieDtoConversor.toRestMovieDto(req.getInputStream());
        } catch (ParsingException ex) {
            throw new InputValidationException(ex.getMessage());
        }
        Movie movie = MovieToRestMovieDtoConversor.toMovie(movieDto);

        movie = MovieServiceFactory.getService().addMovie(movie);

        movieDto = MovieToRestMovieDtoConversor.toRestMovieDto(movie);
        String movieURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + movie.getMovieId();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", movieURL);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestMovieDtoConversor.toObjectNode(movieDto), headers);

    }
}

