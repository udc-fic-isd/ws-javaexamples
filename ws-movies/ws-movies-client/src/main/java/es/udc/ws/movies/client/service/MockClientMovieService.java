package es.udc.ws.movies.client.service;

import es.udc.ws.movies.client.service.dto.ClientMovieDto;
import java.util.ArrayList;
import java.util.List;

public class MockClientMovieService implements ClientMovieService {

    @Override
    public Long addMovie(ClientMovieDto movie) {
        // TODO Auto-generated method stub
        return (long) 0;
    }

    @Override
    public void updateMovie(ClientMovieDto movie) {

    }

    @Override
    public void removeMovie(Long movieId) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<ClientMovieDto> findMovies(String keywords) {

        List<ClientMovieDto> movies = new ArrayList<>();

        movies.add(new ClientMovieDto(1L, "movie1",
                (short) 2, (short) 0, "movie1 description", 10F));
        movies.add(new ClientMovieDto(2L, "movie2",
                (short) 2, (short) 0, "movie2 description", 10F));

        return movies;

    }

    @Override
    public Long buyMovie(Long movieId, String userId, String creditCardNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getMovieUrl(Long saleId) {
        // TODO Auto-generated method stub
        return null;
    }

}
