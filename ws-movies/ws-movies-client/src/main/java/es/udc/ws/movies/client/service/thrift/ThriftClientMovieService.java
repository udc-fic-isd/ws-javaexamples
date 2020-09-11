package es.udc.ws.movies.client.service.thrift;

import es.udc.ws.movies.client.service.ClientMovieService;
import es.udc.ws.movies.client.service.dto.ClientMovieDto;
import es.udc.ws.movies.client.service.exceptions.ClientSaleExpirationException;
import es.udc.ws.movies.thrift.ThriftInputValidationException;
import es.udc.ws.movies.thrift.ThriftInstanceNotFoundException;
import es.udc.ws.movies.thrift.ThriftMovieService;
import es.udc.ws.movies.thrift.ThriftSaleExpirationException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ThriftClientMovieService implements ClientMovieService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "ThriftClientMovieService.endpointAddress";

    private final static String endpointAddress =
            ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);

    @Override
    public Long addMovie(ClientMovieDto movie) throws InputValidationException {

        ThriftMovieService.Client client = getClient();

        TTransport transport = null;
        try  {
            transport = client.getInputProtocol().getTransport();
            transport.open();
            return client.addMovie(ClientMovieDtoToThriftMovieDtoConversor.toThriftMovieDto(movie));

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (transport!=null) {
                transport.close();
            }
        }

    }

    @Override
    public void updateMovie(ClientMovieDto movie) throws InputValidationException, InstanceNotFoundException {

        ThriftMovieService.Client client = getClient();

        TTransport transport = null;
        try  {
            transport = client.getInputProtocol().getTransport();
            transport.open();
            client.updateMovie(ClientMovieDtoToThriftMovieDtoConversor.toThriftMovieDto(movie));

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (ThriftInstanceNotFoundException e) {
            throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (transport!=null) {
                transport.close();
            }
        }

    }

    @Override
    public void removeMovie(Long movieId) throws InstanceNotFoundException {

        ThriftMovieService.Client client = getClient();

        TTransport transport = null;
        try  {
            transport = client.getInputProtocol().getTransport();
            transport.open();
            client.removeMovie(movieId);

        } catch (ThriftInstanceNotFoundException e) {
            throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (transport!=null) {
                transport.close();
            }
        }

    }

    @Override
    public List<ClientMovieDto> findMovies(String keywords) {

        ThriftMovieService.Client client = getClient();

        TTransport transport = null;
        try  {
            transport = client.getInputProtocol().getTransport();
            transport.open();
            return ClientMovieDtoToThriftMovieDtoConversor.toClientMovieDto(client.findMovies(keywords));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (transport!=null) {
                transport.close();
            }
        }

    }

    @Override
    public Long buyMovie(Long movieId, String userId, String creditCardNumber) throws InstanceNotFoundException,
            InputValidationException {

        ThriftMovieService.Client client = getClient();

        TTransport transport = null;
        try  {
            transport = client.getInputProtocol().getTransport();
            transport.open();
            return client.buyMovie(movieId, userId, creditCardNumber);

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (ThriftInstanceNotFoundException e) {
            throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (transport!=null) {
                transport.close();
            }
        }

    }

    @Override
    public String getMovieUrl(Long saleId) throws InstanceNotFoundException, ClientSaleExpirationException {

        ThriftMovieService.Client client = getClient();

        TTransport transport = null;
        try  {
            transport = client.getInputProtocol().getTransport();

            transport.open();
            return client.findSale(saleId).getMovieUrl();

        } catch (ThriftInstanceNotFoundException e) {
            throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());
        } catch (ThriftSaleExpirationException e) {
            throw new ClientSaleExpirationException(e.getSaleId(), LocalDateTime.parse(e.getExpirationDate(),
                    DateTimeFormatter.ISO_DATE_TIME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (transport!=null) {
                transport.close();
            }
        }

    }

    private ThriftMovieService.Client getClient() {

        try {

            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TBinaryProtocol(transport);

            return new ThriftMovieService.Client(protocol);

        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }

    }

}
