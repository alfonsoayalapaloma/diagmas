package co.scndfndtion.diagmas.agents;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import co.com.scndfndtion.diagmas.tools.JSONParser;

public class JSONParserTest {

	String rawdata=null;
	@Before
	public void setUp() throws Exception {
		
		rawdata="{\"id\": \"WIN0002\",\"gsr\": \"GFLORA\",\"ifGsr\": \"GigabitEthernet0/1/7.351\",\"swt\": \"hermita2\",\"ifSwt\": \"Ethernet3/0/42\",\"ipCpe\": \"10.175.248.182\"}";
	}

	@Test
	public void testGet() {
		String expected="GFLORA";
		String actual =JSONParser.get(rawdata,"gsr");
		System.out.println(actual);
		assertEquals(actual, expected);
		
		actual =JSONParser.get(rawdata,"ipCpe");
		expected="10.175.248.182";
		assertEquals(actual, expected);
		
		
		
	}

}
