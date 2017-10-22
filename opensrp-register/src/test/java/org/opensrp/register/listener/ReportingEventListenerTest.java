package org.opensrp.register.listener;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.opensrp.common.util.ANMIndicatorSummaryFactory.createSummaryForIUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.scheduler.domain.MotechEvent;
import org.opensrp.common.domain.ANMReport;
import org.opensrp.common.domain.ReportDataDeleteRequest;
import org.opensrp.common.domain.ReportDataUpdateRequest;
import org.opensrp.common.domain.ReportingData;
import org.opensrp.common.util.HttpAgent;
import org.opensrp.common.util.HttpResponse;
import org.opensrp.register.service.reporting.ANMReportingService;

import com.google.gson.Gson;

public class ReportingEventListenerTest {
    @Mock
    private HttpAgent agent;
    @Mock
    private ANMReportingService anmReportingService;

    private ReportingEventListener listener;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        listener = new ReportingEventListener(anmReportingService, agent, "http://drishti");
    }

    @Test
    public void shouldSubmitReportingData() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("data", new ReportingData("Boo").with("abc", "def"));
        when(agent.post(eq("http://drishti/submit"), any(String.class), eq("application/json"))).thenReturn(new HttpResponse(true, null));

        listener.submitReportingData(new MotechEvent("SUBJECT", data));

        verify(agent).post("http://drishti/submit", "{\"type\":\"Boo\",\"data\":{\"abc\":\"def\"}}", "application/json");
    }

    @Test
    public void shouldUpdateReportingData() throws Exception {
        Map<String, Object> data = new HashMap<>();
        ReportingData reportingData = new ReportingData("Boo").with("abc", "def");
        ReportDataUpdateRequest dataRequest = new ReportDataUpdateRequest().withType("Boo")
                .withReportingData(asList(reportingData))
                .withStartDate("2013-01-26")
                .withEndDate("2013-02-25");
        data.put("data", dataRequest);
        when(agent.post(eq("http://drishti/update"), any(String.class), eq("application/json"))).thenReturn(new HttpResponse(true, null));

        listener.updateReportingData(new MotechEvent("SUBJECT", data));

        verify(agent).post("http://drishti/update",
                "{\"startDate\":\"2013-01-26\",\"endDate\":\"2013-02-25\",\"type\":\"Boo\",\"reportingData\":[{\"type\":\"Boo\",\"data\":{\"abc\":\"def\"}}]}",
                "application/json");
    }

    @Test
    public void shouldFetchANMReportingDataAndPassDataToANMReportService() throws Exception {
        Map<String, Object> data = new HashMap<>();
        List<ANMReport> anmReports = new ArrayList<>();
        anmReports.add(new ANMReport("ANM X", asList(createSummaryForIUD())));
        when(agent.getWithSocketTimeout(eq("http://drishti/fetchForAllANMs"))).thenReturn(new HttpResponse(true, new Gson().toJson(anmReports)));

        listener.fetchANMReports(new MotechEvent("SUBJECT", data));

        verify(agent).getWithSocketTimeout("http://drishti/fetchForAllANMs");
        verify(anmReportingService).processReports(anmReports);
    }

    @Test
    public void shouldAddANMReportsThatAreGeneratedFromEntities() throws Exception {
        Map<String, Object> data = new HashMap<>();
        when(agent.getWithSocketTimeout(eq("http://drishti/fetchForAllANMs"))).thenReturn(new HttpResponse(true, new Gson().toJson(new ArrayList<ANMReport>())));

        listener.fetchANMReports(new MotechEvent("SUBJECT", data));

        verify(anmReportingService).reportFromEntityData();
    }

    @Test
    public void shouldNotPassDataToANMReportServiceIfRequestFailed() throws Exception {
        Map<String, Object> data = new HashMap<>();
        when(agent.getWithSocketTimeout(eq("http://drishti/fetchForAllANMs"))).thenReturn(new HttpResponse(false, null));

        listener.fetchANMReports(new MotechEvent("SUBJECT", data));

        verify(agent).getWithSocketTimeout("http://drishti/fetchForAllANMs");
        verify(anmReportingService, times(0)).processReports(anyList());
    }


    @Test
    public void shouldNotPassDataToANMReportServiceIfNoReportIsFetched() throws Exception {
        Map<String, Object> data = new HashMap<>();
        when(agent.getWithSocketTimeout(eq("http://drishti/fetchForAllANMs"))).thenReturn(new HttpResponse(true, new Gson().toJson(new ArrayList<ANMReport>())));

        listener.fetchANMReports(new MotechEvent("SUBJECT", data));

        verify(agent).getWithSocketTimeout("http://drishti/fetchForAllANMs");
        verify(anmReportingService, times(0)).processReports(anyList());
    }

    @Test
    public void shouldDeleteReportingData() throws Exception {
        Map<String, Object> data = new HashMap<>();
        ReportDataDeleteRequest dataRequest = new ReportDataDeleteRequest().withType("Boo")
                .withDristhiEntityId("entity id 1");
        data.put("data", dataRequest);
        when(agent.post(eq("http://drishti/delete"), any(String.class), eq("application/json"))).thenReturn(new HttpResponse(true, null));

        listener.deleteReportingData(new MotechEvent("SUBJECT", data));

        verify(agent).post("http://drishti/delete", "{\"type\":\"Boo\",\"dristhiEntityId\":\"entity id 1\"}", "application/json");
    }
}
