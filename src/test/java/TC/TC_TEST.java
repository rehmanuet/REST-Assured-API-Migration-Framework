package TC;

import TestUtils.*;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.*;
import org.testng.annotations.*;
import com.relevantcodes.extentreports.LogStatus;
import TestUtils.ExtentReports.*;
import java.io.IOException;

import static TestUtils.BaseUtils.Header;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static TestUtils.ExtentReports.ExtentTestManager.*;


public class TC_TEST {
    private ResponseSpecification rspec;
    @DataProvider(name = "DP")
    public static Object[][] dataProviderMethod() {
        return BaseUtils.getMyAccTestData("GET");
    }


    //Testing common things to check across multiple tests by adding .spec(rspec) at the end of every TC
//    @BeforeTest
    public void requestSpec() throws IOException {  //Reading config.properties file


        //End of config
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectContentType(ContentType.JSON);
        builder.expectStatusCode(200);
        rspec = builder.build();
    }


    //    @BeforeMethod(groups = "Customer Reada")


    @Test(groups = "Customer Reada", dataProvider = "DP")
    void getCustomerReadStats(String legacy, String java) throws IOException {
//        String BASE_URL_JAVA=Config.getProp("baseJava")+java;
        String BASE_URL_LEGACY=BaseUtils.getProp("baseLegacy")+legacy;
        System.out.println(BASE_URL_LEGACY);

        ExtentTestManager.startTest("Test00000000000" + ": StatusCode", "StatusCodeTest");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Java Config Path " + legacy);

        given().headers(Header()).when().get(BASE_URL_LEGACY).body().prettyPrint();
        given().headers(Header()).when().get(BASE_URL_LEGACY).then().spec(rspec);



    }

    @Test(groups = "Customer Reada", dataProvider = "DP")
    void getCustomerReadStats1(String legacy, String java) {
        System.out.println("");
//        ExtentTestManager.startTest("Test111111111111111111" + ": StatusCode", "StatusCodeTest");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Java Config Path " + legacy);


        given().headers(Header()).when().get(java).body().prettyPrint();
        System.out.println(java);


    }

    @AfterSuite(groups = "Customer Reada")
    void flush() {
        flushReport();
    }

    @Test(groups = "Customer Read111")
    void getCustomerReadStatus() {
        RestAssured.baseURI = "http://api-int.dit.connectcdk.com";
        // Single API Response assertion test
        given().headers(Header()).when().get().then().spec(rspec).log().ifError();

        // API status code
        Response s1 = given().headers(Header()).when().get();
        Response s2 = given().headers(Header()).when().get();

        // Assertion
        Assert.assertEquals(s1.getStatusCode(), s2.getStatusCode());
    }

    @Test(groups = "Customer Reada")
    void getCustomerReadResponse() {
        // Single API Response assertion test

        given().headers(Header()).expect().body("id", equalTo("NB4370")).when().get();

        // API Response Body
        Response r_l = given().headers(Header()).get().thenReturn();
        Response r_j = given().headers(Header()).get().thenReturn();

        // Replacing "" with null
        String r1 = r_l.getBody().asString().replace("\"\"", "null");
        String r2 = r_j.getBody().asString().replace("\"\"", "null");


        // Assertion
        Assert.assertEquals(r1, r2);
    }

    @Test(groups = "Customer Reada")
    void getCustomerReadSchemaValidator() {
        // JSON Schema Validator and Assertion
        given().headers(Header()).get().then().assertThat().body(matchesJsonSchemaInClasspath("customer_read_schema.json"));
    }

    @Test(groups = "Customer Read")
    void getCustomerReadAttributeCount() {

        // API Response Body
        Response r_l = given().headers(Header()).get();
        Response r_j = given().headers(Header()).get();
        // Assertion
        Assert.assertEquals(r_l.body().path("size()"), r_j.body().path("size()"));

    }
}
