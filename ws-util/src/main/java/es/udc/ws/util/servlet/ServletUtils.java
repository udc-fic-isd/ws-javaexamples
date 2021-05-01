package es.udc.ws.util.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.json.ExceptionToJsonConversor;
import es.udc.ws.util.json.ObjectMapperFactory;

public class ServletUtils {

	public static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

	public static void writeServiceResponse(HttpServletResponse response, int responseCode, JsonNode rootNode,
			Map<String, String> headers) throws IOException {

		writeResponse(response, responseCode, "application/json", headers);

		if (rootNode != null) {
			ObjectMapper objectMapper = ObjectMapperFactory.instance();
			objectMapper.writer(new DefaultPrettyPrinter()).writeValue(response.getOutputStream(), rootNode);
		}
	}

	public static void writeServiceResponse(HttpServletResponse response, int responseCode, String contentType,
			InputStream content, Map<String, String> headers) throws IOException {

		writeResponse(response, responseCode, contentType, headers);

		if (content != null) {
			copy(content, response.getOutputStream());
		}
	}

	private static void writeResponse(HttpServletResponse response, int responseCode, String contentType,
			Map<String, String> headers) {
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				response.setHeader(key, value);
			}
		}
		response.setStatus(responseCode);
		if (contentType != null) {
			response.setContentType(contentType);
		}
	}

	public static String normalizePath(String url) {
		if (url != null && url.endsWith("/")) {
			return url.substring(0, url.length() - 1);
		} else {
			return url;
		}
	}

	private static int copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static String getMandatoryParameter(HttpServletRequest req, HttpServletResponse resp, String paramName) throws IOException {
		String paramValue = req.getParameter(paramName);
		if (paramValue == null) {
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
					ExceptionToJsonConversor.toInputValidationException(
							new InputValidationException("Invalid Request: " + "parameter " + paramName + " is mandatory")),
					null);
		}
		return paramValue;
	}

	public static Long getMandatoryParameterAsLong(HttpServletRequest req, HttpServletResponse resp, String paramName) throws IOException {
		String paramValue = null;
		Long paramValueAsLong = null;
		if ((paramValue = getMandatoryParameter(req, resp, paramName)) != null) {
			try {
				paramValueAsLong = Long.valueOf(paramValue);
			} catch (NumberFormatException ex) {
				ServletUtils
						.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
								ExceptionToJsonConversor.toInputValidationException(new InputValidationException(
										"Invalid Request: " + "parameter '"+ paramName +"' is invalid '" + paramValue + "'")),
								null);
			}
		}
		return paramValueAsLong;
	}

	public static boolean checkEmptyPath(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path != null && path.length() > 0) {
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
					ExceptionToJsonConversor.toInputValidationException(
							new InputValidationException("Invalid Request: " + "invalid path " + path)),
					null);
			return false;
		}
		return true;
	}

	public static Long getIdFromPath(HttpServletRequest req, HttpServletResponse resp, String resourceName) throws IOException {
		Long id = null;
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null || path.length() == 0) {
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
					ExceptionToJsonConversor.toInputValidationException(
							new InputValidationException("Invalid Request: " + "invalid " + resourceName + " id")),
					null);
			return id;
		}
		String idAsString = path.substring(1);
		try {
			id = Long.valueOf(idAsString);
		} catch (NumberFormatException ex) {
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
					ExceptionToJsonConversor.toInputValidationException(
							new InputValidationException("Invalid Request: invalid " + resourceName + " id '" + idAsString + "'")),
					null);
		}
		return id;
	}
}
