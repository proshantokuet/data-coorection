package dashboard.test;

import static org.junit.Assert.*;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.dashboard.domain.Privilege;
import org.opensrp.dashboard.repository.AllPrivileges;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class PrivilegeServiceTest {
 
	@Autowired  
	private AllPrivileges allPrivileges;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	
    @Before
    public void setUp() throws Exception {
       HttpClient httpClient = new StdHttpClient.Builder() 
        .host("localhost") 
       	//.host("192.168.19.55")
        .username("Admin").password("mPower@1234") 
        .port(5984) 
        .socketTimeout(1000) 
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		 
		stdCouchDbConnector.createDatabaseIfNotExists();
		allPrivileges = new AllPrivileges(2, stdCouchDbConnector);
    	//initMocks(this);
    }

    @Test 
    public void testPrivilegeService() throws Exception {
		Privilege testPrivilege = allPrivileges.privilegeByName("testPrivilege");
		assertNull(testPrivilege);
		
		Privilege privilege = new Privilege();
		privilege.withName("testPrivilege");
		privilege.withStatus("testStatus");
		allPrivileges.add(privilege);
		
		testPrivilege = allPrivileges.privilegeByName("testPrivilege");
		assertNotNull(testPrivilege);
		
		allPrivileges.remove(testPrivilege);
		
		testPrivilege = allPrivileges.privilegeByName("testPrivilege");
		assertNull(testPrivilege);
    }
    
    /*@Test 
    public void check() throws Exception {		
		Privilege privilege = new Privilege();
		privilege.withName("testPrivilege");
		privilege.withStatus("testStatus");
		allPrivileges.add(privilege);	
    }*/
}
