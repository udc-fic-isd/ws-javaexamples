package es.udc.ws.movies.soapservice;

import es.udc.ws.movies.dto.ServiceMovieDto;
import es.udc.ws.movies.dto.ServiceSaleDto;
import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.movies.model.movie.Movie;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.model.sale.Sale;
import es.udc.ws.movies.serviceutil.MovieToMovieDtoConversor;
import es.udc.ws.movies.serviceutil.SaleToSaleDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(
    name = "MoviesProvider",
    serviceName = "MoviesProviderService",
    targetNamespace = "http://soap.ws.udc.es/"
)
public class SoapMovieService {

    @WebMethod(
        operationName = "addMovie"
    )
    public Long addMovie(@WebParam(name = "movieDto") ServiceMovieDto movieDto)
            throws SoapInputValidationException {
        Movie movie = MovieToMovieDtoConversor.toMovie(movieDto);
        try {
            return MovieServiceFactory.getService().addMovie(movie).getMovieId();
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        }
    }

    @WebMethod(
        operationName = "updateMovie"
    )
    public void updateMovie(@WebParam(name = "movieDto") ServiceMovieDto movieDto)
            throws SoapInputValidationException, SoapInstanceNotFoundException {
        Movie movie = MovieToMovieDtoConversor.toMovie(movieDto);
        try {
            MovieServiceFactory.getService().updateMovie(movie);
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                            ex.getInstanceType()));
        }
    }

    @WebMethod(
        operationName = "removeMovie"
    )
    public void removeMovie(@WebParam(name = "movieId") Long movieId)
            throws SoapInstanceNotFoundException {
        try {
            MovieServiceFactory.getService().removeMovie(movieId);
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(
                            ex.getInstanceId(), ex.getInstanceType()));
        }
    }

    @WebMethod(
        operationName = "findMovies"
    )
    public List<ServiceMovieDto> findMovies(
            @WebParam(name = "keywords") String keywords) {
        List<Movie> movies
                = MovieServiceFactory.getService().findMovies(keywords);
        return MovieToMovieDtoConversor.toMovieDtos(movies);
    }

    @WebMethod(
        operationName = "buyMovie"
    )
    public Long buyMovie(@WebParam(name = "movieId") Long movieId,
            @WebParam(name = "userId") String userId,
            @WebParam(name = "creditCardNumber") String creditCardNumber)
            throws SoapInstanceNotFoundException, SoapInputValidationException {
        try {
            Sale sale = MovieServiceFactory.getService()
                    .buyMovie(movieId, userId, creditCardNumber);
            return sale.getSaleId();
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                            ex.getInstanceType()));
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        }
    }

    @WebMethod(
        operationName = "findSale"
    )
    public ServiceSaleDto findSale(@WebParam(name = "saleId") Long saleId)
            throws SoapInstanceNotFoundException,
            SoapSaleExpirationException {

        try {
            Sale sale = MovieServiceFactory.getService().findSale(saleId);
            return SaleToSaleDtoConversor.toSaleDto(sale);
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                            ex.getInstanceType()));
        } catch (SaleExpirationException ex) {
            throw new SoapSaleExpirationException(
                    new SoapSaleExpirationExceptionInfo(ex.getSaleId(),
                            ex.getExpirationDate()));
        }
    }

}
