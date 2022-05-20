package es.udc.ws.util.sql;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DbcpBasicDataSourceCreator {

    private static final String CONFIGURATION_FILE = "BasicDataSource.properties";

    private static final String URL_PARAMETER = "BasicDataSource.url";
    private static final String USER_PARAMETER = "BasicDataSource.user";
    private static final String PASSWORD_PARAMETER = "BasicDataSource.password";
    private static final String MAX_TOTAL_PARAMETER = "BasicDataSource.maxTotal";
    private static final String MAX_IDLE_PARAMETER = "BasicDataSource.maxIdle";
    private static final String MAX_WAIT_PARAMETER = "BasicDataSource.maxWait";
    private static final String VALIDATION_QUERY_PARAMETER = "BasicDataSource.validationQuery";

    private static String url;
    private static String user;
    private static String password;
    private static int maxTotal;
    private static int maxIdle;
    private static int maxWait;
    private static String validationQuery;

    private DbcpBasicDataSourceCreator() { }

    private static synchronized void readConfiguration() {
        if (url == null) {
            try {
                /*
                 * Read configuration parameters.
                 */
                url = ConfigurationParametersManager.getParameter(URL_PARAMETER);
                user = ConfigurationParametersManager.getParameter(USER_PARAMETER);
                password = ConfigurationParametersManager.getParameter(PASSWORD_PARAMETER);
                maxTotal = Integer.parseInt(ConfigurationParametersManager.getParameter(MAX_TOTAL_PARAMETER));
                maxIdle = Integer.parseInt(ConfigurationParametersManager.getParameter(MAX_IDLE_PARAMETER));
                maxWait = Integer.parseInt(ConfigurationParametersManager.getParameter(MAX_WAIT_PARAMETER));
                validationQuery = ConfigurationParametersManager.getParameter(VALIDATION_QUERY_PARAMETER);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static DataSource createDataSource() {
        readConfiguration();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(maxTotal);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMaxWaitMillis(maxWait);
        dataSource.setValidationQuery(validationQuery);

        return dataSource;

    }

}
