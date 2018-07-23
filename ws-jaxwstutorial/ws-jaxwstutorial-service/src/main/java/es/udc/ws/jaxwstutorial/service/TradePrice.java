package es.udc.ws.jaxwstutorial.service;

public class TradePrice {

    protected int elapsedSeconds;
    protected double price;
    protected String tickerSymbol;

    /**
     * Gets the value of the elapsedSeconds property.
     *
     */
    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    /**
     * Sets the value of the elapsedSeconds property.
     *
     */
    public void setElapsedSeconds(int value) {
        this.elapsedSeconds = value;
    }

    /**
     * Gets the value of the price property.
     *
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     *
     */
    public void setPrice(double value) {
        this.price = value;
    }

    /**
     * Gets the value of the tickerSymbol property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Sets the value of the tickerSymbol property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTickerSymbol(String value) {
        this.tickerSymbol = value;
    }

    @Override
    public String toString() {
        return tickerSymbol + " = " + price + "(" + elapsedSeconds + ")";
    }

}
