package es.udc.ws.movies.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
        name = "SoapSaleExpirationException",
        targetNamespace = "http://soap.ws.udc.es/"
)
public class SoapSaleExpirationException extends Exception {

    private SoapSaleExpirationExceptionInfo faultInfo;

    protected SoapSaleExpirationException(
            SoapSaleExpirationExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapSaleExpirationExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}
