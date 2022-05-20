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

    private final static String PORT_PARAMETER =
            "ThriftMovieServer.port";

    private final static String port =
            ConfigurationParametersManager.getParameter(PORT_PARAMETER);

    public static void main(String args[]) {

        try {

            /*
             * Create a data source and add it to "DataSourceLocator"
             */
            DataSourceLocator.addDataSource(MOVIE_DATA_SOURCE, DbcpBasicDataSourceCreator.createDataSource());

            TServerSocket transport = new TServerSocket(Integer.parseInt(port));
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).
                    protocolFactory(createProtocolFactory()).processor(createProcessor()));
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

  /*  private static DataSource createDataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/ws?useSSL=false&serverTimezone=Europe/Madrid&allowPublicKeyRetrieval=true");
        dataSource.setUsername("ws");
        dataSource.setPassword("ws");
        dataSource.setMaxTotal(4);
        dataSource.setMaxIdle(2);
        dataSource.setMaxWaitMillis(10000);
        dataSource.setTimeBetweenEvictionRunsMillis(30000);
        dataSource.setRemoveAbandonedOnMaintenance(true);
        dataSource.setRemoveAbandonedTimeout(60);
        dataSource.setLogAbandoned(true);
        dataSource.setValidationQuery("SELECT 1");

        return dataSource;

    }*/
}
