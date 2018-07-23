package es.udc.ws.movies.soapservice;

import java.util.Calendar;

public class SoapSaleExpirationExceptionInfo {

    private Long saleId;
    private Calendar expirationDate;

    public SoapSaleExpirationExceptionInfo() {
    }

    public SoapSaleExpirationExceptionInfo(Long saleId,
            Calendar expirationDate) {
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
