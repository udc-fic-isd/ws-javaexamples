package es.udc.ws.movies.restservice.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class JsonServiceExceptionConversor {

    public final static String CONVERSION_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";

	public static ObjectNode toInputValidationException(InputValidationException ex) {
		
    	ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();
    	ObjectNode dataObject = JsonNodeFactory.instance.objectNode();
		
    	dataObject.put("message", ex.getMessage());
    	
    	exceptionObject.set("inputValidationException", dataObject);

        return exceptionObject;
    }

    public static ObjectNode toInstanceNotFoundException(InstanceNotFoundException ex) {

    	ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();
    	ObjectNode dataObject = JsonNodeFactory.instance.objectNode();
		
    	dataObject.put("instanceId", (ex.getInstanceId()!=null) ? ex.getInstanceId().toString() : null);
    	dataObject.put("instanceType", ex.getInstanceType());
    	
    	exceptionObject.set("instanceNotFoundException", dataObject);

        return exceptionObject;
    }

   
    public static ObjectNode toSaleExpirationException(SaleExpirationException ex) {

    	ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();
    	ObjectNode dataObject = JsonNodeFactory.instance.objectNode();
		
    	dataObject.put("saleId", (ex.getSaleId()!=null) ? ex.getSaleId() : null);
        if (ex.getExpirationDate() != null) {
        	dataObject.put("expirationDate", ex.getExpirationDate().toString());
        } else {
        	dataObject.set("expirationDate", null);
        }

        exceptionObject.set("saleExpirationException", dataObject);

        return exceptionObject;
    }
}
