package petfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.Json;
import visitor.pattern.PetfinderUrlVisitor;
import model.Photo;

/**
 * Request animals from Petfinder.com API. See
 * <code> https://www.petfinder.com/developers/v2/docs/ </code>
 * 
 * 
 * @author Tim
 *
 * @apiNote Originally made by Hao, Updated by Tim
 */
public class PetFinderConnectionUtil {
	private static final PetFinderConnectionUtil instance = new PetFinderConnectionUtil();

	private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
	private static Logger logger = LogManager.getRootLogger();
	private static PetfinderUrlVisitor petVisitor = new PetfinderUrlVisitor();
	
	private static final String authentication = Base64.getEncoder().encodeToString(petVisitor.getAuth().getBytes());
	private static String currentToken;

	private PetFinderConnectionUtil() {
	}

	public void requestNewToken() throws MalformedURLException {
		URL tokenUrl = petVisitor.getTokenURL();
		String content = "grant_type=client_credentials";
		BufferedReader reader = null;
		HttpsURLConnection connection = null;

		try {
			connection = (HttpsURLConnection) tokenUrl.openConnection();
			logger.debug("tokenUrl: " + tokenUrl);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + authentication);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");
			PrintStream prntStrm = new PrintStream(connection.getOutputStream());
			prntStrm.print(content);
			prntStrm.close();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			String response = out.toString();
			Matcher matcher = pat.matcher(response);
			if (matcher.matches() && matcher.groupCount() > 0) {
				currentToken = matcher.group(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			connection.disconnect();
		}
	}

	/**
	 * Get animals based on the location of the user.
	 * @param location: city, state; latitude,longitude; or postal code.
	 * @param radius:   in miles
	 * @throws MalformedURLException
	 */
	public String requestAnimalsByLocation(String location, int radius) throws MalformedURLException {
		URL apiUrl = petVisitor.makePetfinderURL(
				petVisitor.getPetFinderAnimalsString() + petVisitor.getLocationString(location, radius));
		try {
			HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
			connection.setRequestProperty("Authorization", "Bearer " + currentToken);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			connection.disconnect();
			String response = out.toString();
			StringBuilder sbResponse = parseOutAnimalInformation(response);
			if (null != sbResponse) {
				return "[" + sbResponse + "]";
			} else {
				logger.debug("animal Json object was null {}", sbResponse);
			}
		} catch (Exception e) {
			logger.warn("Exception Message: {}", e.getMessage());
			logger.warn("Stack Trace: ", e);
		}
		return null;
	}
	
	/**
	 * Get animals with no restrictions
	 * @throws MalformedURLException
	 */
	public String requestAnimals() throws MalformedURLException {
		URL apiUrl = petVisitor.makePetfinderURL(petVisitor.getPetFinderAnimalsString());
		try {
			HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
			connection.setRequestProperty("Authorization", "Bearer " + currentToken);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			connection.disconnect();
			String response = out.toString();
			StringBuilder sbResponse = parseOutAnimalInformation(response);
			if (null != sbResponse) {
				return "[" + sbResponse + "]";
			} else {
				logger.debug("animal Json object was null {}", sbResponse);
			}
		} catch (Exception e) {
			logger.warn("Exception Message: {}", e.getMessage());
			logger.warn("Stack Trace: ", e);
		}
		return null;
	}

	/**
	 * This will build a JSON out of the response from petfinder.com for our needs.
	 * id and photos. If there is an animal without photos, that animal will be
	 * removed from the creation.
	 * 
	 * @param response = the information from petfinder.com
	 * @return StringBuilder JSON value OR NULL. if null something went wrong.
	 * 
	 * @apiNote The Json format looks like:
	 * <code>
	   	[{
        	"id": 46794611,
        	"photoId": 1575856900,
        	"type": "Dog",
        	"fullUrl": https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46794611/4/?bust=1575856900
    	}]</code>
	 */
	private StringBuilder parseOutAnimalInformation(String response) {
		JsonNode jsonNode = Json.readString(response, PetFinderConnectionUtil.class);
		StringBuilder sb = new StringBuilder();
		boolean firstForPassed = false;
		// Create Json formatted String
		for (JsonNode animal : jsonNode.get("animals")) {
			if (null != (animal.get("photos").get(0))) {
				for (JsonNode photo : animal.get("photos")) {
					if (firstForPassed) {
						sb.append(",");
					} else {
						firstForPassed = true;
					}
					String urlString = "" + photo.get("full");
					String[] urlArray = urlString.replace("\"", "").split("=");
					if (urlArray.length > 1) {
						sb.append("{\"id\":" + animal.get("id") 
								+ ",\"photoId\":" + urlArray[1] 
								+ ",\"type\":" + animal.get("type") 
								+ ",\"fullUrl\":" + "\"" + urlArray[0] + "=" + urlArray[1] + "\""
								+ "}");
						// logger information
						logger.trace("\n{" + "\n\t\"id\":" + animal.get("id") 
								+ ",\n\t\"photoId\":" + urlArray[1]
								+ ",\"type\":" + animal.get("type") 
								+ ",\n\t\"fullUrl\":" + "\"" + urlArray[0] + "="
									+ urlArray[1] + "\"" + "\n}");
					}
				}
			}
		}
		return sb;
	}

	/**
	 * @deprecated Not currently used.
	 * @param response
	 * @return
	 */
	private List<Photo> parseAnimalInformationAsList(String response) {
		JsonNode jsonNode = null;
		ObjectMapper om = new ObjectMapper();
		try {
			jsonNode = om.readValue(response, JsonNode.class);
		} catch (IOException e) {
			logger.warn("IOException Message: {}", e.getMessage());
			logger.warn("Stack Trace: ", e);
		}
		JsonNode animals = jsonNode.get("animals");
		List<Photo> animalList = new ArrayList<Photo>();

		for (JsonNode animal : animals) {
			if (null != (animal.get("photos").get(0))) {
				for (JsonNode photo : animal.get("photos")) {
					String urlString = "" + photo.get("full");
					String[] urlArray = urlString.split("=");
					if (urlArray.length > 1) {
						String photoId = urlArray[1];
						animalList.add(new Photo("" + animal.get("id"), photoId.replace("\"", ""),
								urlString.replace("\"", "")));
					}
				}
			}
		}
		logger.info(animalList);
		return animalList;
	}

	public String getCurrentToken() {
		return currentToken;
	}

	public static PetFinderConnectionUtil getInstance() {
		return instance;
	}
}
