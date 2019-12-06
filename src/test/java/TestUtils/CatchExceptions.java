package TestUtils;

import java.io.IOException;
import org.testng.Assert;
import org.xml.sax.SAXException;
import com.relevantcodes.extentreports.LogStatus;
import TestUtils.ExtentReports.ExtentTestManager;

public class CatchExceptions {
	public static void exceptionLogging(String methodName, Throwable e) {
		if (e instanceof AssertionError) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					methodName + " :Expected and Actual results doesn't match - " + e.getMessage());
			Assert.fail();
		} else if (e instanceof IOException) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					methodName + " :I/O Exception has occurred - " + e.getMessage());
			Assert.fail();
		} else if (e instanceof ClassNotFoundException) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					methodName + " :Unable to create MySQL Connection - " + e.getMessage());
			Assert.fail();
		} else if (e instanceof IndexOutOfBoundsException) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					methodName + " :Index of some sort (such as to an array, to a string etc) is out of range - "
							+ e.getMessage());
			Assert.fail();
		} else if (e instanceof NoSuchFieldError) {
			ExtentTestManager.getTest()
					.log(LogStatus.FAIL, methodName
							+ " :All required property values are not available in respective properties file - "
							+ e.getMessage());
			Assert.fail();
		} else if (e instanceof ClassCastException) {
			ExtentTestManager.getTest()
					.log(LogStatus.FAIL, methodName
							+ " :An attempt to cast a key object to a subclass of which it is not an instance - "
							+ e.getMessage());
			Assert.fail();
		} else if (e instanceof SAXException) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					methodName + " :Unable to parse given InputStream into XML Document object - " + e.getMessage());
			Assert.fail();
		}
	}
}
