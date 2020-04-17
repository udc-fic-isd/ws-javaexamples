package es.udc.ws.movies.soapservice;

public class SoapSaleExpirationExceptionInfo {

    private Long saleId;
    private String expirationDate;

    public SoapSaleExpirationExceptionInfo() {
    }

    public SoapSaleExpirationExceptionInfo(Long saleId,
    		String expirationDate) {
        this.saleId = saleId;
        this.expirationDate = expirationDate;
    }

    public Long getSaleId() {
        return saleId;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

}
