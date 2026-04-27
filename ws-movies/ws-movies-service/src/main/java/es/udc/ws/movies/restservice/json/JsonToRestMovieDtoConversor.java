package es.udc.ws.movies.restservice.json;

import java.io.InputStream;
import java.util.List;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.JsonNodeType;
import tools.jackson.databind.node.ObjectNode;

import es.udc.ws.movies.restservice.dto.RestMovieDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

public class JsonToRestMovieDtoConversor {

	public static ObjectNode toObjectNode(RestMovieDto movie) {

		ObjectNode movieObject = JsonNodeFactory.instance.objectNode();

		movieObject.put("movieId", movie.getMovieId()).
				put("title", movie.getTitle()).
				put("runtime", movie.getRuntime()).
				put("price", movie.getPrice()).
				put("description", movie.getDescription());

		return movieObject;
	}

	public static ArrayNode toArrayNode(List<RestMovieDto> movies) {

		ArrayNode moviesNode = JsonNodeFactory.instance.arrayNode();
		for (RestMovieDto movieDto : movies) {
			ObjectNode movieObject = toObjectNode(movieDto);
			moviesNode.add(movieObject);
		}

		return moviesNode;
	}

	public static RestMovieDto toRestMovieDto(InputStream jsonMovie) throws ParsingException {
		try {
			ObjectMapper objectMapper = ObjectMapperFactory.instance();
			JsonNode rootNode = objectMapper.readTree(jsonMovie);
			
			if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
				throw new ParsingException("Unrecognized JSON (object expected)");
			} else {
				ObjectNode movieObject = (ObjectNode) rootNode;

				JsonNode movieIdNode = movieObject.get("movieId");
				Long movieId = (movieIdNode != null) ? movieIdNode.longValue() : null;

				String title = movieObject.get("title").asString().trim();
				String description = movieObject.get("description").asString().trim();
				short runtime =  movieObject.get("runtime").shortValue();
				float price = movieObject.get("price").floatValue();

				return new RestMovieDto(movieId, title, runtime, description, price);
			}
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

}
