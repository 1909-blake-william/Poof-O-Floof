package petfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Hao
 *
 */
public class PetFinderConnectionUtil {
	private static final PetFinderConnectionUtil instance = new PetFinderConnectionUtil();

	private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
	private static Logger logger = LogManager.getRootLogger();
	private static Properties props = getProperties();
	
	private static final String PET_FINDER_API_KEY = props.getProperty("petfinder.key");
	private static final String PET_FINDER_SECRET = props.getProperty("petfinder.secret");
	private static final String TOKEN_URL = "https://api.petfinder.com/v2/oauth2/token";
	private static final String PET_FINDER_API_ROOT = "https://api.petfinder.com/v2/";
	private static final String PET_FINDER_API_ANIMALS = PET_FINDER_API_ROOT + "animals";
	private static final String auth = PET_FINDER_API_KEY + ":" + PET_FINDER_SECRET;
	private static final String authentication = Base64.getEncoder().encodeToString(auth.getBytes());
	private static String currentToken;

	private PetFinderConnectionUtil() {
	}
	
	/**
	 * load properties from application.properties
	 * @return
	 */
	private static Properties getProperties() {
		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
			return props;
		} catch (IOException | NullPointerException e) {
			logger.error("Unable to locate Properties at src/main/resources/application.properties");
			logger.error("Stack Trace: ", e);
			throw new RuntimeException("Check Logs; Failed to get properties");
		}
	}

	public void requestNewToken() throws MalformedURLException {
		URL tokenUrl = new URL(TOKEN_URL);
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
	 * Request animals from Petfinder.com API. See
	 * https://www.petfinder.com/developers/v2/docs/
	 * 
	 * @param location: city, state; latitude,longitude; or postal code.
	 * @param radius:   in miles
	 * @throws MalformedURLException
	 */
	public void requestAnimalsByLocation(String location, int radius) throws MalformedURLException {
		String params = String.format("?location=%s&radius=%d", location, radius);
		URL apiUrl = new URL(PET_FINDER_API_ANIMALS + params);
		BufferedReader reader = null;
		try {
			HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
			connection.setRequestProperty("Authorization", "Bearer " + currentToken);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			String response = out.toString();
			logger.debug(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCurrentToken() {
		return currentToken;
	}

	public static PetFinderConnectionUtil getInstance() {
		return instance;
	}
}
