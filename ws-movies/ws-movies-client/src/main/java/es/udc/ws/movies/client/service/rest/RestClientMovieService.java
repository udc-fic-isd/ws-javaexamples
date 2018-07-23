package es.udc.ws.movies.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import es.udc.ws.movies.client.service.ClientMovieService;
import es.udc.ws.movies.client.service.dto.ClientMovieDto;
import es.udc.ws.movies.client.service.exceptions.ClientSaleExpirationException;
import es.udc.ws.movies.client.service.rest.xml.XmlClientExceptionConversor;
import es.udc.ws.movies.client.service.rest.xml.XmlClientMovieDtoConversor;
import es.udc.ws.movies.client.service.rest.xml.XmlClientSaleDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.xml.exceptions.ParsingException;

public class RestClientMovieService implements ClientMovieService {

    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientMovieService.endpointAddress";
    private String endpointAddress;

    @Override
    public Long addMovie(ClientMovieDto movie) throws InputValidationException {

        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "movies").
                    bodyStream(toInputStream(movie), ContentType.create("application/xml")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return XmlClientMovieDtoConversor.toClientMovieDto(response.getEntity().getContent()).getMovieId();

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateMovie(ClientMovieDto movie) throws InputValidationException,
            InstanceNotFoundException {

        try {

            HttpResponse response = Request.Put(getEndpointAddress() + "movies/" + movie.getMovieId()).
                    bodyStream(toInputStream(movie), ContentType.create("application/xml")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

        } catch (InputValidationException | InstanceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeMovie(Long movieId) throws InstanceNotFoundException {

        try {

            HttpResponse response = Request.Delete(getEndpointAddress() + "movies/" + movieId).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<ClientMovieDto> findMovies(String keywords) {

        try {

            HttpResponse response = Request.Get(getEndpointAddress() + "movies?keywords="
                            + URLEncoder.encode(keywords, "UTF-8")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return XmlClientMovieDtoConversor.toClientMovieDtos(response.getEntity()
                    .getContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Long buyMovie(Long movieId, String userId, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException {

        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "sales").
                    bodyForm(
                            Form.form().
                            add("movieId", Long.toString(movieId)).
                            add("userId", userId).
                            add("creditCardNumber", creditCardNumber).
                            build()).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return XmlClientSaleDtoConversor.toClientSaleDto(
                    response.getEntity().getContent()).getSaleId();

        } catch (InputValidationException | InstanceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getMovieUrl(Long saleId) throws InstanceNotFoundException,
            ClientSaleExpirationException {

        try {

            HttpResponse response = Request.Get(getEndpointAddress() + "sales/" + saleId).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return XmlClientSaleDtoConversor.toClientSaleDto(
                    response.getEntity().getContent()).getMovieUrl();

        } catch (InstanceNotFoundException | ClientSaleExpirationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private synchronized String getEndpointAddress() {
        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager
                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }
        return endpointAddress;
    }

    private InputStream toInputStream(ClientMovieDto movie) {

        try {

            ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

            outputter.output(XmlClientMovieDtoConversor.toXml(movie), xmlOutputStream);

            return new ByteArrayInputStream(xmlOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void validateStatusCode(int successCode, HttpResponse response)
            throws InstanceNotFoundException, ClientSaleExpirationException,
            InputValidationException, ParsingException {

        try {

            int statusCode = response.getStatusLine().getStatusCode();

            /* Success? */
            if (statusCode == successCode) {
                return;
            }

            /* Handler error. */
            switch (statusCode) {

                case HttpStatus.SC_NOT_FOUND:
                    throw XmlClientExceptionConversor.fromInstanceNotFoundExceptionXml(
                            response.getEntity().getContent());

                case HttpStatus.SC_BAD_REQUEST:
                    throw XmlClientExceptionConversor.fromInputValidationExceptionXml(
                            response.getEntity().getContent());

                case HttpStatus.SC_GONE:
                    throw XmlClientExceptionConversor.fromSaleExpirationExceptionXml(
                            response.getEntity().getContent());

                default:
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
