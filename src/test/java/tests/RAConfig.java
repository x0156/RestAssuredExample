package tests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;
import io.restassured.RestAssured;

public abstract class RAConfig {

	static{
		 System.setProperty("java.util.logging.SimpleFormatter.format", 
		            "%5$s%6$s%n");
	}
    public static final String REST_URI = "http://jsonplaceholder.typicode.com/";

    @Rule public Timeout globalTimeout = new Timeout(200000);
    @Rule public TestName name = new TestName();

    @Before
    public void setUp() {
        System.out.println("--> "+name.getMethodName());
    }

    @BeforeClass
    public static void setUpCaseFlow() {
    	RestAssured.proxy("host",8080);
    	RestAssured.proxy.withAuth("uname", "g0tch@..1");
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI=REST_URI;
    }

}
