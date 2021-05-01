package es.udc.ws.movies.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.movies.restservice.dto.RestSaleDto;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.movies.model.sale.Sale;
import es.udc.ws.movies.restservice.json.MoviesExceptionToJsonConversor;
import es.udc.ws.movies.restservice.json.JsonToRestSaleDtoConversor;
import es.udc.ws.movies.restservice.dto.SaleToRestSaleDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ExceptionToJsonConversor;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class SalesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletUtils.checkEmptyPath(req,resp)) {
            return;
        }
        Long movieId;
        if ((movieId = ServletUtils.getMandatoryParameterAsLong(req, resp,"movieId")) == null) {
            return;
        }
        String userId;
        if ((userId = ServletUtils.getMandatoryParameter(req, resp,"userId")) == null) {
            return;
        }
        String creditCardNumber;
        if ((creditCardNumber = ServletUtils.getMandatoryParameter(req, resp,"creditCardNumber")) == null) {
            return;
        }
        Sale sale;
        try {
            sale = MovieServiceFactory.getService().buyMovie(movieId, userId, creditCardNumber);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
            		ExceptionToJsonConversor.toInstanceNotFoundException(ex), null);
            return;
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
            		ExceptionToJsonConversor.toInputValidationException(ex), null);
            return;
        }
        RestSaleDto saleDto = SaleToRestSaleDtoConversor.toRestSaleDto(sale);

        String saleURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + sale.getSaleId().toString();

        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", saleURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestSaleDtoConversor.toObjectNode(saleDto), headers);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long saleId;
        if ((saleId = ServletUtils.getIdFromPath(req, resp, "sale")) == null) {
            return;
        }
        Sale sale;
        try {
            sale = MovieServiceFactory.getService().findSale(saleId);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
            		ExceptionToJsonConversor.toInstanceNotFoundException(ex), null);
            return;
        } catch (SaleExpirationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
            		MoviesExceptionToJsonConversor.toSaleExpirationException(ex),
                    null);

            return;
        }

        RestSaleDto saleDto = SaleToRestSaleDtoConversor.toRestSaleDto(sale);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestSaleDtoConversor.toObjectNode(saleDto), null);

    }
}
