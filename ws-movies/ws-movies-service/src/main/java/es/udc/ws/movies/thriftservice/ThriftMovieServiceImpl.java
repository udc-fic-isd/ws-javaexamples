package es.udc.ws.movies.thriftservice;

import es.udc.ws.movies.model.movie.Movie;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.thrift.ThriftInputValidationException;
import es.udc.ws.movies.thrift.ThriftMovieService;
import es.udc.ws.movies.thrift.ThriftMovieDto;
import es.udc.ws.util.exceptions.InputValidationException;

import java.util.List;

public class ThriftMovieServiceImpl implements ThriftMovieService.Iface {

    @Override
    public long addMovie(ThriftMovieDto movieDto) throws ThriftInputValidationException {

        Movie movie = MovieToThriftMovieDtoConversor.toMovie(movieDto);

        try {
            return MovieServiceFactory.getService().addMovie(movie).getMovieId();
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

    }

    @Override
    public List<ThriftMovieDto> findMovies(String keywords) {

        List<Movie> movies =
            MovieServiceFactory.getService().findMovies(keywords);

        return MovieToThriftMovieDtoConversor.toThriftMovieDtos(movies);

    }
}
