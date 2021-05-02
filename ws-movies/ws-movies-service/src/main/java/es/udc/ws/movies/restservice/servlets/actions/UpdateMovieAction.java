package es.udc.ws.movies.restservice.servlets.actions;

import es.udc.ws.movies.model.movie.Movie;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.restservice.dto.MovieToRestMovieDtoConversor;
import es.udc.ws.movies.restservice.dto.RestMovieDto;
import es.udc.ws.movies.restservice.json.JsonToRestMovieDtoConversor;
import es.udc.ws.movies.restservice.servlets.Action;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateMovieAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, InstanceNotFoundException, InputValidationException {
        Long movieId = ServletUtils.getIdFromPath(req, "movie");
        RestMovieDto movieDto;
        try {
            movieDto = JsonToRestMovieDtoConversor.toRestMovieDto(req.getInputStream());
        } catch (ParsingException ex) {
            throw new InputValidationException(ex.getMessage());
        }
        if (!movieId.equals(movieDto.getMovieId())) {
            throw new InputValidationException("Invalid Request: " + "invalid movieId");
        }
        Movie movie = MovieToRestMovieDtoConversor.toMovie(movieDto);

        MovieServiceFactory.getService().updateMovie(movie);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);

    }
}
