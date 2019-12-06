package TC;


import TestUtils.BaseUtils;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.*;

import static TestUtils.BaseUtils.Header;
import static TestUtils.ExtentReports.ExtentTestManager.flushReport;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.relevantcodes.extentreports.LogStatus;
import TestUtils.ExtentReports.ExtentTestManager;

import java.io.IOException;

public class TC_GET {

    //Data provider
    @DataProvider(name = "DP")
    public static Object[][] dataProviderMethod() {
        return BaseUtils.getMyAccTestData("GET");
    }


    //Testing common things to check across multiple tests by adding .spec(spec) at the end of every TC
    @BeforeTest
    public void requestSpec() {
        ResponseSpecification spec;
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectContentType(ContentType.JSON);
        spec = builder.build();
    }


    @Test(groups = "Customer Read", dataProvider = "DP")
    void getStatus(String legacy, String java) throws IOException {
        String BASE_URL_JAVA = BaseUtils.getProp("baseJava") + java;
        String BASE_URL_LEGACY = BaseUtils.getProp("baseLegacy") + legacy;

        Response s1 = given().headers(Header()).when().get(BASE_URL_LEGACY);
        Response s2 = given().headers(Header()).when().get(BASE_URL_JAVA);

        ExtentTestManager.startTest("StatusCode: " + java, "Status Code Test");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Java " + java + " Status Code: " + s1.getStatusCode());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Legacy " + legacy + " Status Code: " + s2.getStatusCode());

        Assert.assertEquals(s1.getStatusCode(), s2.getStatusCode());
        ExtentTestManager.endTest();
    }

    @Test(groups = "Customer Read", dataProvider = "DP")
    void getResponse(String legacy, String java) throws IOException {
        ExtentTestManager.startTest("Response : " + java, "ResponseTest");
        String BASE_URL_JAVA = BaseUtils.getProp("baseJava") + java;
        String BASE_URL_LEGACY = BaseUtils.getProp("baseLegacy") + legacy;

        // API Response Body
        Response r_l = given().headers(Header()).get(BASE_URL_LEGACY).thenReturn();
        Response r_j = given().headers(Header()).get(BASE_URL_JAVA).thenReturn();

        // Replacing "" with null
        String r1 = r_l.getBody().asString().replace("\"\"", "null");
        String r2 = r_j.getBody().asString();


        // Assertion
        Assert.assertEquals(r1, r2);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Legacy Response" + r1);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Java Response" + r2);
        ExtentTestManager.getTest().log(LogStatus.PASS, "Passed");

    }

    @Test(groups = "Customer Read", dataProvider = "DP")
    void getSchemaValidator(String legacy, String java) throws IOException {
        ExtentTestManager.startTest("Schema Validation", java);


        String BASE_URL_JAVA = BaseUtils.getProp("baseJava") + java;

        // JSON Schema Validator and Assertion
        given().headers(Header()).get(BASE_URL_JAVA).then().assertThat().body(matchesJsonSchemaInClasspath("customer_read_schema.json"));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Schema");
        ExtentTestManager.getTest().log(LogStatus.PASS, "Passed");

    }

    @Test(groups = "Customer Read", dataProvider = "DP")
    void getAttributeCount(String legacy, String java) throws IOException {
        String BASE_URL_JAVA = BaseUtils.getProp("baseJava") + java;
        String BASE_URL_LEGACY = BaseUtils.getProp("baseLegacy") + legacy;
        // API Response Body
        ExtentTestManager.startTest("Attribute Count : ", java);

        Response r_l = given().headers(Header()).get(BASE_URL_LEGACY);
        Response r_j = given().headers(Header()).get(BASE_URL_JAVA);
        // Assertion

        Assert.assertEquals(r_l.body().path("size()"), r_j.body().path("size()"));

        if (r_l.body().path("size()") == r_j.body().path("size()")) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Passed");

        } else ExtentTestManager.getTest().log(LogStatus.FAIL, "FAILED");


        ExtentTestManager.getTest().log(LogStatus.INFO, "Legacy Count : " + r_l.body().path("size()"));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Java Count : " + r_j.body().path("size()"));


    }

    @AfterSuite(groups = "Customer Read")
    void publishReport() {
        flushReport();
    }

}
