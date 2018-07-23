package es.udc.ws.movies.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.movies.dto.ServiceSaleDto;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.movies.model.sale.Sale;
import es.udc.ws.movies.restservice.xml.XmlServiceExceptionConversor;
import es.udc.ws.movies.restservice.xml.XmlServiceMovieDtoConversor;
import es.udc.ws.movies.restservice.xml.XmlServiceSaleDtoConversor;
import es.udc.ws.movies.serviceutil.SaleToSaleDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class SalesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String movieIdParameter = req.getParameter("movieId");
        if (movieIdParameter == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "parameter 'movieId' is mandatory")),
                    null);
            return;
        }
        Long movieId;
        try {
            movieId = Long.valueOf(movieIdParameter);
        } catch (NumberFormatException ex) {
            ServletUtils
                    .writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                            XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
                                    "Invalid Request: " + "parameter 'movieId' is invalid '" + movieIdParameter + "'")),
                            null);

            return;
        }
        String userId = req.getParameter("userId");
        if (userId == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "parameter 'userId' is mandatory")),
                    null);
            return;
        }
        String creditCardNumber = req.getParameter("creditCardNumber");
        if (creditCardNumber == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
                            "Invalid Request: " + "parameter 'creditCardNumber' is mandatory")),
                    null);

            return;
        }
        Sale sale;
        try {
            sale = MovieServiceFactory.getService().buyMovie(movieId, userId, creditCardNumber);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    XmlServiceExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(ex), null);
            return;
        }
        ServiceSaleDto saleDto = SaleToSaleDtoConversor.toSaleDto(sale);

        String saleURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + sale.getSaleId().toString();

        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", saleURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                XmlServiceSaleDtoConversor.toResponse(saleDto), headers);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "invalid sale id")),
                    null);
            return;
        }
        String saleIdAsString = path.substring(1);
        Long saleId;
        try {
            saleId = Long.valueOf(saleIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlServiceExceptionConversor.toInputValidationExceptionXml(
                            new InputValidationException("Invalid Request: " + "invalid sale id '" + saleIdAsString)),
                    null);
            return;
        }
        Sale sale;
        try {
            sale = MovieServiceFactory.getService().findSale(saleId);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    XmlServiceExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        } catch (SaleExpirationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    XmlServiceExceptionConversor.toSaleExpirationException(ex, XmlServiceMovieDtoConversor.XML_NS),
                    null);

            return;
        }

        ServiceSaleDto saleDto = SaleToSaleDtoConversor.toSaleDto(sale);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                XmlServiceSaleDtoConversor.toResponse(saleDto), null);

    }
}
