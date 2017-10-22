package org.opensrp.rest.it;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.rest.repository.LuceneElcoRepository;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneResult;

@Ignore
public class LuceneElcoRepositoryTest {

	private LuceneAwareCouchDbConnector connector;
	private LuceneElcoRepository luceneElcoRepository;

	@Before
	public void setUp() throws IOException {
		HttpClient httpClient = new StdHttpClient.Builder().
				host("localhost")		
				.port(5984).socketTimeout(10000)
				.username("Admin").password("mPower@1234")
				.build();
		CouchDbInstance instance = new StdCouchDbInstance(httpClient);

		connector = new LuceneAwareCouchDbConnector("opensrp", instance);
		connector.createDatabaseIfNotExists();
		luceneElcoRepository = new LuceneElcoRepository(2, connector);
		// createDocuments();
	}

	@After
	public void tearDown() {
		// deleteDocuments();
		connector = null;
	}

	public long convertDateToTimestampMills(String date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date day = null;
		try {
			day = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day.getTime();
	}

	
	@Test
	public void testInit() throws ParseException {
		
		System.out.println("this.convertDateToTimestampMills(\"2016-06-08\") - " + this.convertDateToTimestampMills("2016-06-08"));
		System.out.println("this.convertDateToTimestampMills(\"2016-06-14\") - " + this.convertDateToTimestampMills("2016-06-14"));
		System.out.println("this.convertDateToTimestampMills(\"2016-06-15\") - " + this.convertDateToTimestampMills("2016-06-15"));
		System.out.println("this.convertDateToTimestampMills(\"2016-06-21\") - " + this.convertDateToTimestampMills("2016-06-21"));

		
		String makeQueryString = "type:Elco" + " AND " + "SUBMISSIONDATE:[1465840800000 TO 1465840800000]";

		LuceneResult result = luceneElcoRepository.findDocsByProvider(makeQueryString);
		System.out.println(result.getRows().size() + " -count with getRows().size()");
		System.out.println(result.getTotalRows() + " -count with getTotalRows()");

		

		
	}
}