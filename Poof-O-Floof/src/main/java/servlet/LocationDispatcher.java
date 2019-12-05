package servlet;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import petfinder.PetFinderConnectionUtil;
import util.Json;

public class LocationDispatcher implements Dispatcher {
	
	private static final Logger logger = LogManager.getLogger();
	
	private static final int RADIUS = 10;

	@Override
	public boolean supports(HttpServletRequest request) {
		return isPostLocation(request);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("does this work?????");
			//get coordinates
			String location = (String) Json.read(request.getInputStream(), String.class);
			logger.info("location is: " + location);
			
			//put coordinates into thing
			try {
				String animalList = PetFinderConnectionUtil.getInstance().requestAnimalsByLocation(location, RADIUS);
				
				//pass only photos to front end
			}
			catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isPostLocation(HttpServletRequest req) {
		return req.getMethod().equals("POST") && 
				req.getRequestURI().contentEquals("/Poof-O-Floof/api/location");
	}
	
}
