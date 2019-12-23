package es.udc.ws.movies.client.service.soap;

import es.udc.ws.movies.client.service.ClientMovieService;
import es.udc.ws.movies.client.service.dto.ClientMovieDto;
import es.udc.ws.movies.client.service.exceptions.ClientSaleExpirationException;
import es.udc.ws.movies.client.service.soap.wsdl.*;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.util.List;
import javax.xml.ws.BindingProvider;

public class SoapClientMovieService implements ClientMovieService {

    private final static String ENDPOINT_ADDRESS_PARAMETER
            = "SoapClientMovieService.endpointAddress";

    private String endpointAddress;

    private MoviesProvider moviesProvider;

    public SoapClientMovieService() {
        init(getEndpointAddress());
    }

    private void init(String moviesProviderURL) {
        MoviesProviderService moviesProviderService = new MoviesProviderService();
        moviesProvider = moviesProviderService
                .getMoviesProviderPort();
        ((BindingProvider) moviesProvider).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                moviesProviderURL);
    }

    @Override
    public Long addMovie(ClientMovieDto movie)
            throws InputValidationException {
        try {
            return moviesProvider.addMovie(MovieDtoToSoapMovieDtoConversor
                    .toServiceSoapMovieDto(movie));
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        }
    }

    @Override
    public void updateMovie(ClientMovieDto movie)
            throws InputValidationException, InstanceNotFoundException {
        try {
            moviesProvider.updateMovie(MovieDtoToSoapMovieDtoConversor
                    .toServiceSoapMovieDto(movie));
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
    }

    @Override
    public void removeMovie(Long movieId)
            throws InstanceNotFoundException {
        try {
            moviesProvider.removeMovie(movieId);
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
    }

    @Override
    public List<ClientMovieDto> findMovies(String keywords) {
        return MovieDtoToSoapMovieDtoConversor.toClientMovieDtos(
                moviesProvider.findMovies(keywords));
    }

    @Override
    public Long buyMovie(Long movieId, String userId, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException {
        try {
            return moviesProvider.buyMovie(movieId, userId, creditCardNumber);
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
    }

    @Override
    public String getMovieUrl(Long saleId)
            throws InstanceNotFoundException, ClientSaleExpirationException {
        try {
            return moviesProvider.findSale(saleId).getMovieUrl();
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        } catch (SoapSaleExpirationException ex) {
            throw new ClientSaleExpirationException(ex.getFaultInfo().getSaleId(),
                    ex.getFaultInfo().getExpirationDate()
                    .toGregorianCalendar());
        }
    }

    private String getEndpointAddress() {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(
                    ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;
    }

}
