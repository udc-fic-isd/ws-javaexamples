package es.udc.ws.movies.client.service.soap;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.movies.client.service.dto.ClientMovieDto;
import es.udc.ws.movies.client.service.soap.wsdl.ServiceMovieDto;

public class MovieDtoToSoapMovieDtoConversor {

    public static ServiceMovieDto toServiceSoapMovieDto(ClientMovieDto movie) {
        ServiceMovieDto soapMovieDto = new ServiceMovieDto();
        soapMovieDto.setMovieId(movie.getMovieId());
        soapMovieDto.setTitle(movie.getTitle());
        soapMovieDto.setRuntime((short) (movie.getRuntimeHours() * 60 +
                movie.getRuntimeMinutes()));
        soapMovieDto.setDescription(movie.getDescription());
        soapMovieDto.setPrice(movie.getPrice());
        return soapMovieDto;
    }

    public static ClientMovieDto toClientMovieDto(ServiceMovieDto movie) {
        return new ClientMovieDto(movie.getMovieId(), movie.getTitle(),
                (short) (movie.getRuntime() / 60), (short) (movie.getRuntime() % 60), 
                movie.getDescription(), movie.getPrice());
    }

    public static List<ClientMovieDto> toClientMovieDtos(List<ServiceMovieDto> movies) {
        List<ClientMovieDto> movieDtos = new ArrayList<>(movies.size());
        for (int i = 0; i < movies.size(); i++) {
            ServiceMovieDto movie = movies.get(i);
            movieDtos.add(toClientMovieDto(movie));
        }
        return movieDtos;
    }

}
