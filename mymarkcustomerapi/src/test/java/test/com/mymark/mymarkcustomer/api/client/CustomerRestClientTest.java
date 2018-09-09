package test.com.mymark.mymarkcustomer.api.client;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mymark.customer.api.CustomerDto;
import com.mymark.mymarkcustomer.api.client.ClientException;
import com.mymark.mymarkcustomer.api.client.CustomerRestClient;

@RunWith(JUnit4.class)
public class CustomerRestClientTest {

	private static CustomerRestClient client;

	protected final static Logger log = LoggerFactory.getLogger(CustomerRestClientTest.class);

	public static String SERVICE_URL = "http://localhost:8081/api";
	public static String USERNAME = "appuser";
	public static String PASSWORD = "password";
	

	public static String[][] customerData = { { "John", "Smith", "jsmith01", "jsmith01@foo.com", "password1234" },
//			{ "Jane", "Riggs", "jriggs01", "jriggs01@foo.com", "password1234" },
//			{ "Bill", "Nye", "sciguy1234", "sciguy1234@foo.com", "password1234" },
//			{ "Alex", "Lifeson", "lerxst", "lerxst@foo.com", "password1234" },
//			{ "Geddy", "Lee", "dirk", "dirk@foo.com", "password1234" },
//			{ "Neil", "Peart", "pratt", "pratt@foo.com", "password1234" } 
	};

	public static String[][] badCustomerData = { { null, "Smith", "jsmith01", "jsmith01@foo.com", "password1234" },
			{ "Jane", null, "jriggs01", "jriggs01@foo.com", "password1234" },
			{ "Bill", "Nye", null, "sciguy1234@foo.com", "password1234" },
			{ "Alex", "Lifeson", "lerxst", null, "password1234" }, { "Geddy", "Lee", "dirk", "dirk@foo.com", null } };

	public static String[][] dupCustomerData = { { "James", "Smith", "jsmith02", "jsmith02@foo.com", "password1234" },
			{ "James", "Smith", "jsmith02", "jsmith03@foo.com", "password1234" },
			{ "James", "Smith", "jsmith03", "jsmith02@foo.com", "password1234" } };

	public CustomerRestClientTest() {
		// TODO Auto-generated constructor stub
	}

	@BeforeClass
	public static void setup() {
		client = new CustomerRestClient(SERVICE_URL, USERNAME, PASSWORD);
	}

	@Test
	public void createNewCustomer() {

		ArrayList<String> identList = new ArrayList<String>();

		for (int x = 0; x < customerData.length; x++) {
			try {
				CustomerDto dto = client.createNewCustomer(customerData[x][0], customerData[x][1], customerData[x][2],
						customerData[x][3], customerData[x][4]);
				org.junit.Assert.assertNotNull("REST Client call to POST /customer has returned null.", dto);
				log.info("REST Client POST to /customer has returned: " + dto);
				identList.add(dto.getIdentifier());
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		for (String ident : identList) {
			try {
				client.deleteCustomer(ident);
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Ignore
	@Test
	public void testMissingData() {

		for (int x = 0; x < badCustomerData.length; x++) {
			try {
				CustomerDto dto = client.createNewCustomer(badCustomerData[x][0], badCustomerData[x][1],
						badCustomerData[x][2], badCustomerData[x][3], badCustomerData[x][4]);
				org.junit.Assert.fail(
						"REST Client call to POST /customer with missing first name has not thrown a ClientException.");
			} catch (ClientException e) {
				org.junit.Assert.assertNotNull(
						"REST Client call to POST /customer with missing first name has not returned the HTTP status code.",
						e.getHttpStatusCode());
				log.info("ClientException thrown for misssing data: HTTP Status Code: " + e.getHttpStatusCode()
						+ "; Message: " + e.getMessage());
			}
		}
	}

	@Ignore
	@Test
	public void testDuplicateData() {

		String customerId = null;

		// Create first user
		try {
			CustomerDto dto = client.createNewCustomer(dupCustomerData[0][0], dupCustomerData[0][1],
					dupCustomerData[0][2], dupCustomerData[0][3], dupCustomerData[0][4]);
			customerId = dto.getIdentifier();
		} catch (ClientException e) {
			e.printStackTrace();
		}

		// Create user with dup username
		try {
			CustomerDto dupDto = client.createNewCustomer(dupCustomerData[1][0], dupCustomerData[1][1],
					dupCustomerData[1][2], dupCustomerData[1][3], dupCustomerData[1][4]);
			org.junit.Assert.fail(
					"REST Client call to POST /customer with duplicate user name has not thrown a ClientException.");
		} catch (ClientException e) {
			org.junit.Assert.assertNotNull(
					"REST Client call to POST /customer with duplicate user name has not returned the HTTP status code.",
					e.getHttpStatusCode());
			log.info("ClientException thrown for duplicate user name: HTTP Status Code: " + e.getHttpStatusCode()
					+ "; Message: " + e.getMessage());
		}

		// Create user with dup email
		try {
			CustomerDto dupDto = client.createNewCustomer(dupCustomerData[2][0], dupCustomerData[2][1],
					dupCustomerData[2][2], dupCustomerData[2][3], dupCustomerData[2][4]);
			org.junit.Assert
					.fail("REST Client call to POST /customer with duplicate email has not thrown a ClientException.");
		} catch (ClientException e) {
			org.junit.Assert.assertNotNull(
					"REST Client call to POST /customer with duplicate email has not returned the HTTP status code.",
					e.getHttpStatusCode());
			log.info("ClientException thrown for duplicate email: HTTP Status Code: " + e.getHttpStatusCode()
					+ "; Message: " + e.getMessage());
		}
		
		// Clean up
		try {
			client.deleteCustomer(customerId);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@AfterClass
	public static void after() {
	}

}
