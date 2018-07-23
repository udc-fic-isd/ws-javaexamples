package es.udc.ws.movies.client.service;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class ClientMovieServiceFactory {

    private final static String CLASS_NAME_PARAMETER
            = "ClientMovieServiceFactory.className";
    private static Class<ClientMovieService> serviceClass = null;

    private ClientMovieServiceFactory() {
    }

    @SuppressWarnings("unchecked")
    private synchronized static Class<ClientMovieService> getServiceClass() {

        if (serviceClass == null) {
            try {
                String serviceClassName = ConfigurationParametersManager
                        .getParameter(CLASS_NAME_PARAMETER);
                serviceClass = (Class<ClientMovieService>) Class.forName(serviceClassName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return serviceClass;

    }

    public static ClientMovieService getService() {

        try {
            return (ClientMovieService) getServiceClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
