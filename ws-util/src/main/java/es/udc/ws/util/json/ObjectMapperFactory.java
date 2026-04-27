package es.udc.ws.util.json;

import tools.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {
    private static final ObjectMapper mapper = new ObjectMapper();
    
    private ObjectMapperFactory() { } 
    
    public static ObjectMapper instance() { 
        return mapper; 
    }

}
