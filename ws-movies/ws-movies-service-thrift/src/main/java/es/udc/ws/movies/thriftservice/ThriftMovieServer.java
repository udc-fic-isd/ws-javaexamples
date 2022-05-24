package es.udc.ws.movies.thriftservice;

import es.udc.ws.movies.thrift.ThriftMovieService;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.sql.DbcpBasicDataSourceCreator;
import es.udc.ws.util.sql.DataSourceLocator;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import static es.udc.ws.movies.model.util.ModelConstants.MOVIE_DATA_SOURCE;

public class ThriftMovieServer {

    private final static int defaultPort = 5000;

    public static void main(String args[]) {

        try {

            if (args.length>1) {
                System.err.println("Use: ThriftMovieServer [port]");
                System.exit(-1);
            }

            int port = defaultPort;
            if (args.length==1) {
                try {
                    port = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.err.println("Use: ThriftMovieServer [port]");
                    System.exit(-1);
                }
            }


            /* Create a data source and add it to "DataSourceLocator" */
            DataSourceLocator.addDataSource(MOVIE_DATA_SOURCE, DbcpBasicDataSourceCreator.createDataSource());

            TServerSocket transport = new TServerSocket(port);
            TServer server = new TThreadPoolServer(
                    /*
                     * The minimum and maximum number of threads in the thread pool can be configured using
                     * maxWorkerThreads and minWorkerThreads. Its default values are 5 and Integer.MAX_VALUE,
                     * respectively
                     */
                    new TThreadPoolServer.Args(transport).
                            protocolFactory(createProtocolFactory()).
                            processor(createProcessor()));

            System.out.println("Server running on port " + port + " ...");

            server.serve();

        } catch (TTransportException e) {
            e.printStackTrace();
        }

    }

    private static TProcessor createProcessor() {

        return new ThriftMovieService.Processor<ThriftMovieService.Iface>(
                new ThriftMovieServiceImpl());

    }

    private static TProtocolFactory createProtocolFactory() {

        return new TBinaryProtocol.Factory();

    }

}
