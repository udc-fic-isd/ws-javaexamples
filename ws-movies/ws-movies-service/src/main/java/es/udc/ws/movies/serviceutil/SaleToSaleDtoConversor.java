package es.udc.ws.movies.serviceutil;

import es.udc.ws.movies.dto.ServiceSaleDto;
import es.udc.ws.movies.model.sale.Sale;

public class SaleToSaleDtoConversor {

    public static ServiceSaleDto toSaleDto(Sale sale) {
        return new ServiceSaleDto(sale.getSaleId(), sale.getMovieId(), sale
                .getExpirationDate().toString(), sale.getMovieUrl());
    }

}
