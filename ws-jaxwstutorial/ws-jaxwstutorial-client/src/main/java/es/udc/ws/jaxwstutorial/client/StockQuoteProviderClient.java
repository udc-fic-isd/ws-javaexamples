package es.udc.ws.jaxwstutorial.client;

import es.udc.ws.jaxwstutorial.wsdl.IncorrectTickerSymbolException;
import es.udc.ws.jaxwstutorial.wsdl.StockQuoteProvider;
import es.udc.ws.jaxwstutorial.wsdl.StockQuoteProviderService;
import es.udc.ws.jaxwstutorial.wsdl.TradePrice;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.BindingProvider;

public class StockQuoteProviderClient {

    public static final StockQuoteProviderService stockQuoteProviderService = 
            new StockQuoteProviderService();

    public static void main(String args[]) {

        try {

            if (args.length < 2) {
                System.err.println(
                    MessageFormat.format("Usage: {0} stockQuoteProviderURL [tickerSymbol1 tickerSymbol2 ...]",
                    StockQuoteProviderClient.class.getName()));
                System.exit(-1);
            }

            String stockQuoteProviderURL = args[0];
            List<String> tickerSymbols = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                tickerSymbols.add(args[i]);
            }

            StockQuoteProvider stockQuoteProvider = stockQuoteProviderService
                    .getStockQuoteProviderPort();
            ((BindingProvider) stockQuoteProvider).getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY, stockQuoteProviderURL);

            List<TradePrice> tradePrices =
                    stockQuoteProvider.getLastTradePrices(tickerSymbols);

            for (int i = 0; i < tradePrices.size(); i++) {
                System.out.println(
                    MessageFormat.format("Ticker symbol = {0} | Price = {1} | Elapsed seconds = {2}",
                    tradePrices.get(i).getTickerSymbol(),
                    Double.valueOf(tradePrices.get(i).getPrice()),
                    Integer.valueOf(tradePrices.get(i).getElapsedSeconds())));
            }

        } catch (IncorrectTickerSymbolException e) {
            System.out.println(
                    MessageFormat.format("Unable to get ticker symbol \"{0}\"",
                    e.getFaultInfo().getIncorrectTickerSymbol()));
        }

    }
}
