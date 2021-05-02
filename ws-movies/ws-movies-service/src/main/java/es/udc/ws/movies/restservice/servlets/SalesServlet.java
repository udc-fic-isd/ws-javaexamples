package es.udc.ws.movies.restservice.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.movies.restservice.servlets.actions.BuyMovieAction;
import es.udc.ws.movies.restservice.servlets.actions.FindSaleAction;

@SuppressWarnings("serial")
public class SalesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action.execute(new BuyMovieAction(), req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action.execute(new FindSaleAction(), req, resp);
    }
}
