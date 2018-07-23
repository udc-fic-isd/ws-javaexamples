package es.udc.ws.movies.test.model.movieservice;

import static es.udc.ws.movies.model.util.ModelConstants.BASE_URL;
import static es.udc.ws.movies.model.util.ModelConstants.MAX_PRICE;
import static es.udc.ws.movies.model.util.ModelConstants.MAX_RUNTIME;
import static es.udc.ws.movies.model.util.ModelConstants.MOVIE_DATA_SOURCE;
import static es.udc.ws.movies.model.util.ModelConstants.SALE_EXPIRATION_DAYS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.movies.model.movie.Movie;
import es.udc.ws.movies.model.movieservice.MovieService;
import es.udc.ws.movies.model.movieservice.MovieServiceFactory;
import es.udc.ws.movies.model.sale.Sale;
import es.udc.ws.movies.model.sale.SqlSaleDao;
import es.udc.ws.movies.model.sale.SqlSaleDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import es.udc.ws.movies.model.movieservice.exceptions.SaleExpirationException;

public class MovieServiceTest {

	private final long NON_EXISTENT_MOVIE_ID = -1;
	private final long NON_EXISTENT_SALE_ID = -1;
	private final String USER_ID = "ws-user";

	private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
	private final String INVALID_CREDIT_CARD_NUMBER = "";

	private static MovieService movieService = null;

	private static SqlSaleDao saleDao = null;

	@BeforeClass
	public static void init() {

		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.movies.model.movieservice.MovieService"
		 */
		DataSource dataSource = new SimpleDataSource();

		/* Add "dataSource" to "DataSourceLocator". */
		DataSourceLocator.addDataSource(MOVIE_DATA_SOURCE, dataSource);

		movieService = MovieServiceFactory.getService();

		saleDao = SqlSaleDaoFactory.getDao();

	}

	private Movie getValidMovie(String title) {
		return new Movie(title, (short) 85, "Movie description", 19.95F);
	}

	private Movie getValidMovie() {
		return getValidMovie("Movie title");
	}

	private Movie createMovie(Movie movie) {

		Movie addedMovie = null;
		try {
			addedMovie = movieService.addMovie(movie);
		} catch (InputValidationException e) {
			throw new RuntimeException(e);
		}
		return addedMovie;

	}

	private void removeMovie(Long movieId) {

		try {
			movieService.removeMovie(movieId);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	private void removeSale(Long saleId) {

		DataSource dataSource = DataSourceLocator.getDataSource(MOVIE_DATA_SOURCE);

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				/* Do work. */
				saleDao.remove(connection, saleId);

				/* Commit. */
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw new RuntimeException(e);
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void updateSale(Sale sale) {

		DataSource dataSource = DataSourceLocator.getDataSource(MOVIE_DATA_SOURCE);

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				/* Do work. */
				saleDao.update(connection, sale);

				/* Commit. */
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw new RuntimeException(e);
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void testAddMovieAndFindMovie() throws InputValidationException, InstanceNotFoundException {

		Movie movie = getValidMovie();
		Movie addedMovie = null;

		addedMovie = movieService.addMovie(movie);
		Movie foundMovie = movieService.findMovie(addedMovie.getMovieId());

		assertEquals(addedMovie, foundMovie);

		// Clear Database
		removeMovie(addedMovie.getMovieId());

	}

	@Test
	public void testAddInvalidMovie() {

		Movie movie = getValidMovie();
		Movie addedMovie = null;
		boolean exceptionCatched = false;

		try {
			// Check movie title not null
			movie.setTitle(null);
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie title not empty
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setTitle("");
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie runtime >= 0
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setRuntime((short) -1);
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie runtime <= MAX_RUNTIME
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setRuntime((short) (MAX_RUNTIME + 1));
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie description not null
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setDescription(null);
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie description not null
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setDescription("");
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie price >= 0
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setPrice((short) -1);
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie price <= MAX_PRICE
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setRuntime((short) (MAX_PRICE + 1));
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

		} finally {
			if (!exceptionCatched) {
				// Clear Database
				removeMovie(addedMovie.getMovieId());
			}
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentMovie() throws InstanceNotFoundException {

		movieService.findMovie(NON_EXISTENT_MOVIE_ID);

	}

	@Test
	public void testUpdateMovie() throws InputValidationException, InstanceNotFoundException {

		Movie movie = createMovie(getValidMovie());
		try {
			Movie movieToUpdate = new Movie(movie.getMovieId(), "new title", (short) 80, "new description", 20);

			movieService.updateMovie(movieToUpdate);

			Movie updatedMovie = movieService.findMovie(movie.getMovieId());
			
			movieToUpdate.setCreationDate(movie.getCreationDate());
			assertEquals(movieToUpdate, updatedMovie);

		} finally {
			// Clear Database
			removeMovie(movie.getMovieId());
		}

	}

	@Test(expected = InputValidationException.class)
	public void testUpdateInvalidMovie() throws InputValidationException, InstanceNotFoundException {

		Movie movie = createMovie(getValidMovie());
		try {
			// Check movie title not null
			movie = movieService.findMovie(movie.getMovieId());
			movie.setTitle(null);
			movieService.updateMovie(movie);
		} finally {
			// Clear Database
			removeMovie(movie.getMovieId());
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateNonExistentMovie() throws InputValidationException, InstanceNotFoundException {

		Movie movie = getValidMovie();
		movie.setMovieId(NON_EXISTENT_MOVIE_ID);
		movie.setCreationDate(Calendar.getInstance());
		movieService.updateMovie(movie);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testRemoveMovie() throws InstanceNotFoundException {

		Movie movie = createMovie(getValidMovie());
		boolean exceptionCatched = false;
		try {
			movieService.removeMovie(movie.getMovieId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(!exceptionCatched);

		movieService.findMovie(movie.getMovieId());

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testRemoveNonExistentMovie() throws InstanceNotFoundException {

		movieService.removeMovie(NON_EXISTENT_MOVIE_ID);

	}

	@Test
	public void testFindMovies() {

		// Add movies
		List<Movie> movies = new LinkedList<Movie>();
		Movie movie1 = createMovie(getValidMovie("movie title 1"));
		movies.add(movie1);
		Movie movie2 = createMovie(getValidMovie("movie title 2"));
		movies.add(movie2);
		Movie movie3 = createMovie(getValidMovie("movie title 3"));
		movies.add(movie3);

		try {
			List<Movie> foundMovies = movieService.findMovies("moVie Title");
			assertEquals(movies, foundMovies);

			foundMovies = movieService.findMovies("Mo Title 2");
			assertEquals(1, foundMovies.size());
			assertEquals(movies.get(1), foundMovies.get(0));

			foundMovies = movieService.findMovies("title 5");
			assertEquals(0, foundMovies.size());
		} finally {
			// Clear Database
			for (Movie movie : movies) {
				removeMovie(movie.getMovieId());
			}
		}

	}

	@Test
	public void testBuyMovieAndFindSale()
			throws InstanceNotFoundException, InputValidationException, SaleExpirationException {

		Movie movie = createMovie(getValidMovie());
		Sale sale = null;

		try {

			/* Buy movie. */
			Calendar beforeExpirationDate = Calendar.getInstance();
			beforeExpirationDate.add(Calendar.DAY_OF_MONTH, SALE_EXPIRATION_DAYS);
			beforeExpirationDate.set(Calendar.MILLISECOND, 0);

			sale = movieService.buyMovie(movie.getMovieId(), USER_ID, VALID_CREDIT_CARD_NUMBER);

			Calendar afterExpirationDate = Calendar.getInstance();
			afterExpirationDate.add(Calendar.DAY_OF_MONTH, SALE_EXPIRATION_DAYS);
			afterExpirationDate.set(Calendar.MILLISECOND, 0);

			/* Find sale. */
			Sale foundSale = movieService.findSale(sale.getSaleId());

			/* Check sale. */
			assertEquals(sale, foundSale);
			assertEquals(VALID_CREDIT_CARD_NUMBER, foundSale.getCreditCardNumber());
			assertEquals(USER_ID, foundSale.getUserId());
			assertEquals(movie.getMovieId(), foundSale.getMovieId());
			assertTrue(movie.getPrice() == foundSale.getPrice());
			assertTrue((foundSale.getExpirationDate().compareTo(beforeExpirationDate) >= 0)
					&& (foundSale.getExpirationDate().compareTo(afterExpirationDate) <= 0));
			assertTrue(Calendar.getInstance().after(foundSale.getSaleDate()));
			assertTrue(foundSale.getMovieUrl().startsWith(BASE_URL + sale.getMovieId()));

		} finally {
			/* Clear database: remove sale (if created) and movie. */
			if (sale != null) {
				removeSale(sale.getSaleId());
			}
			removeMovie(movie.getMovieId());
		}

	}

	@Test(expected = InputValidationException.class)
	public void testBuyMovieWithInvalidCreditCard() throws InputValidationException, InstanceNotFoundException {

		Movie movie = createMovie(getValidMovie());
		try {
			Sale sale = movieService.buyMovie(movie.getMovieId(), USER_ID, INVALID_CREDIT_CARD_NUMBER);
			removeSale(sale.getSaleId());
		} finally {
			/* Clear database. */
			removeMovie(movie.getMovieId());
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testBuyNonExistentMovie() throws InputValidationException, InstanceNotFoundException {

		Sale sale = movieService.buyMovie(NON_EXISTENT_MOVIE_ID, USER_ID, VALID_CREDIT_CARD_NUMBER);
		/* Clear database. */
		removeSale(sale.getSaleId());

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentSale() throws InstanceNotFoundException, SaleExpirationException {

		movieService.findSale(NON_EXISTENT_SALE_ID);

	}

	@Test(expected = SaleExpirationException.class)
	public void testGetExpiredMovieUrl()
			throws InputValidationException, SaleExpirationException, InstanceNotFoundException {

		Movie movie = createMovie(getValidMovie());
		Sale sale = null;
		try {
			sale = movieService.buyMovie(movie.getMovieId(), USER_ID, VALID_CREDIT_CARD_NUMBER);

			sale.getExpirationDate().add(Calendar.DAY_OF_MONTH, -1 * (SALE_EXPIRATION_DAYS + 1));
			updateSale(sale);

			movieService.findSale(sale.getSaleId());
		} finally {
			// Clear Database (sale if it was created and movie)
			if (sale != null) {
				removeSale(sale.getSaleId());
			}
			removeMovie(movie.getMovieId());
		}

	}

}
