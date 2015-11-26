package mockobj;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockHttpServletResponse;

import junit.framework.*;;

public class TestLab extends TestCase {
	
	public TestLab(String name){
		super(name);
	}
	
	public void testNull()throws Exception{
		TestingLabConverterServlet s = new TestingLabConverterServlet();
		MockHttpServletRequest request =
			new MockHttpServletRequest();
		MockHttpServletResponse response =
			new MockHttpServletResponse();
		String value = null;
		request.setupAddParameter("farenheitTemperature", value);
		response.setExpectedContentType("text/html");
		s.doGet(request,response);
		response.verify();
		assertEquals("<html><head><title>No"
				+ " Temperature</title></head><body><h2>Need to"
				+ " enter a temperature!</h2></body></html>",
				response.getOutputStreamContents().trim());
	}
	
	public void testBad() throws Exception{
		TestingLabConverterServlet s = new TestingLabConverterServlet();
	    MockHttpServletRequest request = 
	      new MockHttpServletRequest();
	    MockHttpServletResponse response = 
	      new MockHttpServletResponse();
	    
	    request.setupAddParameter("farenheitTemperature", "Joce");
	    response.setExpectedContentType("text/html");
	    s.doGet(request,response);
	    response.verify();
		assertEquals("<html><head><title>Bad Temperature</title>"
				+ "</head><body><h2>Need to enter a valid temperature!"
				+ "Got a NumberFormatException on Joce"
				+ "</h2></body></html>",response.getOutputStreamContents().trim());
	}
	
	public void testNormal() throws Exception{
		TestingLabConverterServlet s = new TestingLabConverterServlet();
	    MockHttpServletRequest request = 
	      new MockHttpServletRequest();
	    MockHttpServletResponse response = 
	      new MockHttpServletResponse();
		CityTemperatureServiceProvider.turnOnMockMode();
	    request.setupAddParameter("farenheitTemperature", "86");
	    response.setExpectedContentType("text/html");
	    s.doGet(request,response);
	    response.verify();
	    
		String expected = "<html><head><title>Temperature Converter Result</title></head>"
				+ "<body><h2>86 Farenheit = 30 Celsius </h2>\r\n" 
				+ "<p><h3>The temperature in Austin is 451 degrees Farenheit</h3>\r\n"          
				+ "</body></html>";
				assertTrue(expected.equals(response.getOutputStreamContents().trim()));
	}
	
	
	public static void main(String args[]) {	
		String[] testCaseName =
			{ TestLab.class.getName() };
		junit.textui.TestRunner.main(testCaseName);
	}
}
