package es.udc.ws.movies.restservice.json;

import java.util.Calendar;

import com.fasterxml.jackson.databind.JsonNode;
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
        	set("expirationDate", getExpirationDate(sale.getExpirationDate()));
        
        return saleNode;
    }


    private static ObjectNode getExpirationDate(Calendar expirationDate) {

		ObjectNode releaseDateObject = JsonNodeFactory.instance.objectNode();

        int day = expirationDate.get(Calendar.DAY_OF_MONTH);
        int month = expirationDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = expirationDate.get(Calendar.YEAR);

        releaseDateObject.put("day", day).
			put("month", month).
			put("year", year);

        return releaseDateObject;

    }
}
