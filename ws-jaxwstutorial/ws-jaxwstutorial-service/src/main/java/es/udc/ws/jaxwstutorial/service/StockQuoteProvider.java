package es.udc.ws.jaxwstutorial.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
    name="StockQuoteProvider",
    serviceName="StockQuoteProviderService",
    targetNamespace="http://ws.udc.es/stockquote"
)
public class StockQuoteProvider {

    private Map<String, TradePrice> tradePrices;

    @PostConstruct()
    private void init() {

        TradePrice ibmTradePrice = new TradePrice();
        ibmTradePrice.setTickerSymbol("IBM");
        ibmTradePrice.setPrice(10.5);
        ibmTradePrice.setElapsedSeconds(60*20);
        
        TradePrice sunTradePrice = new TradePrice();
        sunTradePrice.setTickerSymbol("SUN");
        sunTradePrice.setPrice(9.1);
        sunTradePrice.setElapsedSeconds(60*10);
        
        TradePrice micTradePrice = new TradePrice();
        micTradePrice.setTickerSymbol("MIC");
        micTradePrice.setPrice(20.3);
        micTradePrice.setElapsedSeconds(60*5);
            
        tradePrices = new HashMap<>();
        tradePrices.put(ibmTradePrice.getTickerSymbol(), ibmTradePrice);
        tradePrices.put(sunTradePrice.getTickerSymbol(), sunTradePrice);
        tradePrices.put(micTradePrice.getTickerSymbol(), micTradePrice);
        
    }
    
    public StockQuoteProvider() {
    }

    @WebMethod(
        operationName="getLastTradePrices"
    )
    public List<TradePrice> getLastTradePrices(List<String> tickerSymbols)
        throws IncorrectTickerSymbolException {

        List<TradePrice> requestedTradePrices = new ArrayList<>();
        
        for (int i=0; i<tickerSymbols.size(); i++) {
            String tickerSymbol = tickerSymbols.get(i);
            TradePrice tradePrice = tradePrices.get(tickerSymbol);
            if (tradePrice == null) {
                IncorrectTickerSymbolExceptionInfo info =
                        new IncorrectTickerSymbolExceptionInfo();
                info.setIncorrectTickerSymbol(tickerSymbol);
                throw new IncorrectTickerSymbolException(info);
            }
            requestedTradePrices.add(tradePrice);   
        }
        return requestedTradePrices;
        
    }

}
