package es.udc.ws.movies.restservice.servlets;

import es.udc.ws.movies.restservice.servlets.actions.BuyMovieAction;
import es.udc.ws.movies.restservice.servlets.actions.FindSaleAction;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class SalesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Action.execute(new BuyMovieAction(), req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Action.execute(new FindSaleAction(), req, resp);
    }
}
