package es.udc.ws.util.sql;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.configuration.PropertiesUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.Map;

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
    private static Integer maxTotal;
    private static Integer maxIdle;
    private static Integer maxWait;
    private static String validationQuery;

    private static Map<String, String> parameters;

    private DbcpBasicDataSourceCreator() { }

    private static synchronized void readConfiguration() {
        if (url == null) {
            try {
                parameters = PropertiesUtil.readProperties(CONFIGURATION_FILE);
                /*
                 * Read configuration parameters.
                 */
                url = parameters.get(URL_PARAMETER);
                user = parameters.get(USER_PARAMETER);
                password = parameters.get(PASSWORD_PARAMETER);
                maxTotal = Integer.parseInt(parameters.get(MAX_TOTAL_PARAMETER));
                String maxIdleAsString = parameters.get(MAX_IDLE_PARAMETER);
                maxIdle = (maxIdleAsString!=null) ?
                        Integer.parseInt(maxIdleAsString) : null;
                String maxWaitAsString = parameters.get(MAX_IDLE_PARAMETER);
                maxWait = (maxWaitAsString!=null) ?
                        Integer.parseInt(maxWaitAsString) : null;
                validationQuery = parameters.get(VALIDATION_QUERY_PARAMETER);

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
        if (maxIdle!=null) {
            dataSource.setMaxIdle(maxIdle);
        }
        if (maxWait!=null) {
            dataSource.setMaxWaitMillis(maxWait);
        }
        if (validationQuery!=null) {
            dataSource.setValidationQuery(validationQuery);
        }

        return dataSource;

    }

}
