namespace java es.udc.ws.movies.thrift

struct ThriftMovieDto {
    1: i64 movieId;
    2: string title;
    3: i16 runtime;
    4: string description;
    5: double price
}

exception ThriftInputValidationException {
    1: string message
}

service ThriftMovieService {

   i64 addMovie(1: ThriftMovieDto movieDto) throws (1: ThriftInputValidationException e)

   list<ThriftMovieDto> findMovies(1: string keywords);

}