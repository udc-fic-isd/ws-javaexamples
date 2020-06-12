package es.udc.ws.movies.thriftservice;

import es.udc.ws.movies.model.movie.Movie;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.movies.model.sale.Sale;
import es.udc.ws.movies.thrift.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

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
    public void updateMovie(ThriftMovieDto movieDto) throws ThriftInputValidationException,
            ThriftInstanceNotFoundException {

        Movie movie = MovieToThriftMovieDtoConversor.toMovie(movieDto);

        try {
            MovieServiceFactory.getService().updateMovie(movie);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(), e.getInstanceType());
        }

    }


    @Override
    public void removeMovie(long movieId) throws ThriftInstanceNotFoundException {

        try {
            MovieServiceFactory.getService().removeMovie(movieId);
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(), e.getInstanceType());
        }

    }

    @Override
    public List<ThriftMovieDto> findMovies(String keywords) {

        List<Movie> movies = MovieServiceFactory.getService().findMovies(keywords);

        return MovieToThriftMovieDtoConversor.toThriftMovieDtos(movies);

    }

    @Override
    public long buyMovie(long movieId, String userId, String creditCardNumber) throws ThriftInputValidationException,
            ThriftInstanceNotFoundException {

        try {

            Sale sale = MovieServiceFactory.getService().buyMovie(movieId, userId, creditCardNumber);
            return sale.getSaleId();

        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(), e.getInstanceType());
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

    }

    @Override
    public ThriftSaleDto findSale(long saleId) throws ThriftInstanceNotFoundException, ThriftSaleExpirationException {

        try {

            Sale sale = MovieServiceFactory.getService().findSale(saleId);
            return SaleToThriftSaleDtoConversor.toThriftSaleDto(sale);

        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(), e.getInstanceType());
        } catch (SaleExpirationException e) {
            throw new ThriftSaleExpirationException(e.getSaleId(), e.getExpirationDate().toString());
        }

    }
}
