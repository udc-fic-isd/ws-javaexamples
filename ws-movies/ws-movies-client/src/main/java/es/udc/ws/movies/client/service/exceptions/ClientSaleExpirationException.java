package es.udc.ws.movies.client.service.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class ClientSaleExpirationException extends Exception {

    private Long saleId;
    private Calendar expirationDate;

    public ClientSaleExpirationException(Long saleId, Calendar expirationDate) {
        super("Sale with id=\"" + saleId
                + "\" has expired (expirationDate = \""
                + expirationDate + "\")");
        this.saleId = saleId;
        this.expirationDate = expirationDate;
    }

    public Long getSaleId() {
        return saleId;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }
}
