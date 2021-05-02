package es.udc.ws.movies.restservice.servlets.actions;

import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.movies.model.sale.Sale;
import es.udc.ws.movies.restservice.dto.RestSaleDto;
import es.udc.ws.movies.restservice.dto.SaleToRestSaleDtoConversor;
import es.udc.ws.movies.restservice.json.JsonToRestSaleDtoConversor;
import es.udc.ws.movies.restservice.json.MoviesExceptionToJsonConversor;
import es.udc.ws.movies.restservice.servlets.Action;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ExceptionToJsonConversor;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindSaleAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, InstanceNotFoundException, InputValidationException, SaleExpirationException {
        Long saleId = ServletUtils.getIdFromPath(req, "sale");

        Sale sale = MovieServiceFactory.getService().findSale(saleId);

        RestSaleDto saleDto = SaleToRestSaleDtoConversor.toRestSaleDto(sale);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestSaleDtoConversor.toObjectNode(saleDto), null);
    }
}
