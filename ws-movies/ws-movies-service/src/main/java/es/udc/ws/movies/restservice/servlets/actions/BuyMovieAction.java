package es.udc.ws.movies.restservice.servlets.actions;

import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.movies.model.sale.Sale;
import es.udc.ws.movies.restservice.dto.RestSaleDto;
import es.udc.ws.movies.restservice.dto.SaleToRestSaleDtoConversor;
import es.udc.ws.movies.restservice.json.JsonToRestSaleDtoConversor;
import es.udc.ws.movies.restservice.servlets.Action;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ExceptionToJsonConversor;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BuyMovieAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, InstanceNotFoundException, InputValidationException {
        ServletUtils.checkEmptyPath(req);
        Long movieId = ServletUtils.getMandatoryParameterAsLong(req,"movieId");
        String userId = ServletUtils.getMandatoryParameter(req,"userId");
        String creditCardNumber = ServletUtils.getMandatoryParameter(req,"creditCardNumber");

        Sale sale = MovieServiceFactory.getService().buyMovie(movieId, userId, creditCardNumber);

        RestSaleDto saleDto = SaleToRestSaleDtoConversor.toRestSaleDto(sale);
        String saleURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + sale.getSaleId().toString();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", saleURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestSaleDtoConversor.toObjectNode(saleDto), headers);

    }
}
