package es.udc.ws.movies.model.sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlSaleDao implements SqlSaleDao {

    protected AbstractSqlSaleDao() {
    }

    @Override
    public Sale find(Connection connection, Long saleId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT movieId, userId, expirationDate,"
                + " creditCardNumber, price, movieUrl, saleDate FROM Sale WHERE saleId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, saleId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(saleId,
                        Sale.class.getName());
            }

            /* Get results. */
            i = 1;
            Long movieId = resultSet.getLong(i++);
            String userId = resultSet.getString(i++);
            Calendar expirationDate = Calendar.getInstance();
            expirationDate.setTime(resultSet.getTimestamp(i++));
            String creditCardNumber = resultSet.getString(i++);
            float price = resultSet.getFloat(i++);
            String movieUrl = resultSet.getString(i++);
            Calendar saleDate = Calendar.getInstance();
            saleDate.setTime(resultSet.getTimestamp(i++));

            /* Return sale. */
            return new Sale(saleId, movieId, userId, expirationDate,
                    creditCardNumber, price, movieUrl, saleDate);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Connection connection, Sale sale)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE Sale"
                + " SET movieId = ?, userId = ?, expirationDate = ?, "
                + " creditCardNumber = ?, price = ? WHERE saleId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, sale.getMovieId());
            preparedStatement.setString(i++, sale.getUserId());
            Timestamp date = sale.getExpirationDate() != null ? new Timestamp(
                    sale.getExpirationDate().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, date);
            preparedStatement.setString(i++, sale.getCreditCardNumber());
            preparedStatement.setFloat(i++, sale.getPrice());
            preparedStatement.setLong(i++, sale.getSaleId());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(sale.getMovieId(),
                        Sale.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(Connection connection, Long saleId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "DELETE FROM Sale WHERE" + " saleId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, saleId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(saleId,
                        Sale.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
