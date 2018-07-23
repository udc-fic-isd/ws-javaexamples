package es.udc.ws.movies.client.service;

import es.udc.ws.movies.client.service.dto.ClientMovieDto;
import es.udc.ws.movies.client.service.exceptions.ClientSaleExpirationException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.util.List;

public interface ClientMovieService {

    public Long addMovie(ClientMovieDto movie)
            throws InputValidationException;

    public void updateMovie(ClientMovieDto movie)
            throws InputValidationException, InstanceNotFoundException;

    public void removeMovie(Long movieId) throws InstanceNotFoundException;

    public List<ClientMovieDto> findMovies(String keywords);

    public Long buyMovie(Long movieId, String userId, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException;

    public String getMovieUrl(Long saleId)
            throws InstanceNotFoundException, ClientSaleExpirationException;

}
