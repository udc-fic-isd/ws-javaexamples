package es.udc.ws.movies.restservice.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.movies.dto.ServiceSaleDto;

public class JsonServiceSaleDtoConversor {

	public static ObjectNode toObjectNode(ServiceSaleDto sale) {		

		ObjectNode saleNode = JsonNodeFactory.instance.objectNode();

        if (sale.getSaleId() != null) {
        	saleNode.put("saleId", sale.getSaleId());
        }
        saleNode.put("movieId", sale.getMovieId()).
        	put("movieUrl", sale.getMovieUrl()).
        	put("expirationDate", sale.getExpirationDate());
        
        return saleNode;
    }

}
