package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import util.ConnectionUtil;

public class ConnectionTest {

	private static Logger logger = LogManager.getLogger(ConnectionUtil.class);

	// Smoke Test
	@Test
	public void connectionFactory_SmokeTest() {
		assert ConnectionUtil.getConnection() != null;
	}
	
	@Test
	public void connectionTestAPI() {
		
		
	}
}
