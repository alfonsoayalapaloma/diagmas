package co.scndfndtion.diagmas.agents;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import co.com.scndfndtion.diagmas.tools.DBCatalogue;

public class DBCatalogueTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetService() {
		String actual=DBCatalogue.getService("WIN0002");
		System.out.println(actual);
		String expected="{\"id\": \"WIN0002\",\"gsr\": \"GFLORA\",\"ifGsr\": \"GigabitEthernet0/1/7.351\",\"swt\": \"hermita2\",\"ifSwt\": \"Ethernet3/0/42\",\"ipCpe\": \"10.175.248.182\"}";
		assertEquals(expected, actual);
	}
}
