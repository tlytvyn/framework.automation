package webtests.proxydemotest;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import webtests.pages.GooglePage;
import webtests.pages.ResultPage;
import webtests.testbase.WebTestBase;
import framework.automation.driver.web.ProxyServerForBrowser;
import framework.automation.driver.web.WebBrowser;
import framework.automation.driver.web.WebDriverFactory;
import framework.automation.page.web.WebPage;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class WebDriverDemoTest2 extends WebTestBase {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(WebDriverDemoTest2.class);

	//protected String url = "http://www.google.com/";
    protected String url = "http://www.gmail.com/";
	protected WebDriver webDriver;
	protected GooglePage googlePage;

	@Parameters({ "browserName", "proxyEnabled" })
	@BeforeMethod(alwaysRun = true, dependsOnMethods = { "setupTestBaseMethod" })
	public void setup(String browserName, String proxyEnabled) throws Exception {
		LOG.info("Before Test WebDriver Demo precondition");
	
		WebBrowser browser = WebBrowser.getInstance(browserName, url, proxyEnabled);

        webDriver = WebDriverFactory.getWebDriver(browser);

        WebPage<GooglePage> page = new GooglePage(webDriver);
		googlePage = page.navigateToPageUrl(url, GooglePage.class);

	}

	@AfterMethod(alwaysRun = true)
	// (dependsOnMethods = { "tearDownTestBaseMethod" })
	public void tearDown() {
		LOG.info("After Test WebDriver Demo killing driver");
        WebDriverFactory.killDriverInstance();

	}

	@Test
	public void searchEleksTest3() {
		ResultPage result1 = googlePage.searchText("Eleks").openResultPage(1);
		Assert.assertTrue(
				result1.isResultPageCorrectAndContainsTextInTitle("Softwre"),
				"Page title is incorrect - it does not contain 'Softwre'");

	}

	@Test
	public void searchEleksTest4() throws IOException {
		ResultPage result2 = googlePage.searchText("Eleks").openResultPage(2);

		Assert.assertTrue(
				result2.isResultPageCorrectAndContainsTextInTitle("DOUppp"),
				"Page title is incorrect - it does not contain 'DOUppp'");
		
		ProxyServerForBrowser.getServerInstance().getWrittenHar();

	}

}
