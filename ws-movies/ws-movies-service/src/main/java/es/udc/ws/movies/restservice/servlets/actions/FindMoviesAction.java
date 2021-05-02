package es.udc.ws.movies.restservice.servlets.actions;

import es.udc.ws.movies.model.movie.Movie;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.movies.restservice.dto.MovieToRestMovieDtoConversor;
import es.udc.ws.movies.restservice.dto.RestMovieDto;
import es.udc.ws.movies.restservice.json.JsonToRestMovieDtoConversor;
import es.udc.ws.movies.restservice.servlets.Action;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindMoviesAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, InputValidationException {
        ServletUtils.checkEmptyPath(req);
        String keyWords = req.getParameter("keywords");

        List<Movie> movies = MovieServiceFactory.getService().findMovies(keyWords);

        List<RestMovieDto> movieDtos = MovieToRestMovieDtoConversor.toRestMovieDtos(movies);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestMovieDtoConversor.toArrayNode(movieDtos), null);

    }
}
