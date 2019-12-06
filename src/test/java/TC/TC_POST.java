package TC;

import TestUtils.BaseUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static TestUtils.CONSTANTS.*;
import static TestUtils.BaseUtils.Header;
import static TestUtils.ExtentReports.ExtentTestManager.flushReport;
import static io.restassured.RestAssured.given;

public class TC_POST {

    @DataProvider(name = "DP2")
    public static Object[][] dataProviderMethod() {
        return BaseUtils.getMyAccTestData("POST");
    }

    @Test(groups = "Customer Rea", dataProvider = "DP2")
    void junkTest(String payload, String post_request, String get_request) {

        System.out.println(payload);
        System.out.println(post_request);
        System.out.println(get_request);
    }

    @Test(groups = "Customer Rea", dataProvider = "DP2")
    void postGetVerification(String payload, String post_request, String get_request) {
        //POST Request
        given().headers(Header()).body(payload).post(post_request);
        //GET Request
        Response get = given().headers(Header()).when().get(get_request);
        //Conversion of GET request for assertion
        String get_response = get.getBody().asString();
        //To print the result for verification
        //System.out.println(payload.replaceAll(REGEX_TRIMMER,""));
        //System.out.println(get_response);

        //Assertion of POST Request's body and GET Request fro the particular ID
        Assert.assertEquals(payload.replaceAll(REGEX_TRIMMER, ""), get_response);


    }

    @AfterSuite(groups = "Customer Rea")
    void extentReportFlush() {
        flushReport();
    }
}
