package es.udc.ws.movies.restservice.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.movies.restservice.servlets.actions.AddMovieAction;
import es.udc.ws.movies.restservice.servlets.actions.FindMoviesAction;
import es.udc.ws.movies.restservice.servlets.actions.RemoveMovieAction;
import es.udc.ws.movies.restservice.servlets.actions.UpdateMovieAction;

@SuppressWarnings("serial")
public class MoviesServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Action.execute(new AddMovieAction(), req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Action.execute(new UpdateMovieAction(), req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Action.execute(new RemoveMovieAction(), req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Action.execute(new FindMoviesAction(), req, resp);
	}

}
