package es.udc.ws.movies.model.movieservice.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class SaleExpirationException extends Exception {

    private Long saleId;
    private Calendar expirationDate;

    public SaleExpirationException(Long saleId, Calendar expirationDate) {
        super("Sale with id=\"" + saleId + 
              "\" has expired (expirationDate = \"" + 
              expirationDate + "\")");
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