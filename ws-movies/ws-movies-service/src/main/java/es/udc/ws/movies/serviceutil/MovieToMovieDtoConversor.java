package es.udc.ws.movies.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.movies.dto.ServiceMovieDto;
import es.udc.ws.movies.model.movie.Movie;

public class MovieToMovieDtoConversor {

	public static List<ServiceMovieDto> toMovieDtos(List<Movie> movies) {
		List<ServiceMovieDto> movieDtos = new ArrayList<>(movies.size());
		for (int i = 0; i < movies.size(); i++) {
			Movie movie = movies.get(i);
			movieDtos.add(toMovieDto(movie));
		}
		return movieDtos;
	}

	public static ServiceMovieDto toMovieDto(Movie movie) {
		return new ServiceMovieDto(movie.getMovieId(), movie.getTitle(), movie.getRuntime(), movie.getDescription(),
				movie.getPrice());
	}

	public static Movie toMovie(ServiceMovieDto movie) {
		return new Movie(movie.getMovieId(), movie.getTitle(), movie.getRuntime(), movie.getDescription(),
				movie.getPrice());
	}

}
