/**
 * The ChildRegisterService class implements Child registry. 
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import static org.opensrp.common.util.DateUtil.getTimestamp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.register.mcare.ChildRegister;
import org.opensrp.register.mcare.ChildRegisterEntry;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildRegisterService {

	private final AllChilds allChilds;

	@Autowired
	public ChildRegisterService(AllChilds allChilds) {
		this.allChilds = allChilds;
	}

	public ChildRegister getChildRegisterForProvider(String providerId) {
		ArrayList<ChildRegisterEntry> childRegisterEntries = new ArrayList<>();
		List<Child> childs = allChilds.allOpenChilds();

		for (Child child : childs) {
			ChildRegisterEntry childRegisterEntry = new ChildRegisterEntry()
			.withCaseId(child.caseId())
			.withTODAY(child.TODAY())
			.withSTART(child.START())
			.withPROVIDERID(child.PROVIDERID())
			.withLOCATIONID(child.LOCATIONID())
			.withINSTANCEID(child.INSTANCEID())
			.setEncc1_current_form_status(child.getEncc1_current_form_status())
			.setEncc2_current_form_status(child.getEncc2_current_form_status())
			.setEncc3_current_form_status(child.getEncc3_current_form_status())
			.withEND(child.END()).withDetails(child.details())
			.withENCCVisitOne(child.enccVisitOne())
			.withENCCVisitTwo(child.enccVisitTwo())
			.withENCCVisitThree(child.enccVisitThree());

			childRegisterEntries.add(childRegisterEntry);
		}
		return new ChildRegister(childRegisterEntries);
	}

	public ChildRegister getChildRegister(String type, String startKey, String endKey) {
		long start = getTimestamp(startKey);
		long end = getTimestamp(endKey);

		ArrayList<ChildRegisterEntry> childRegisterEntries = new ArrayList<>();
		List<Child> childs = allChilds.allChildsCreatedBetween2Dates(type, start, end);

		for (Child child : childs) {
			ChildRegisterEntry childRegisterEntry = new ChildRegisterEntry()
			.withCaseId(child.caseId())
			.withTODAY(child.TODAY())
			.withSTART(child.START())
			.withPROVIDERID(child.PROVIDERID())
			.withLOCATIONID(child.LOCATIONID())
			.withINSTANCEID(child.INSTANCEID())
			.setEncc1_current_form_status(child.getEncc1_current_form_status())
			.setEncc2_current_form_status(child.getEncc2_current_form_status())
			.setEncc3_current_form_status(child.getEncc3_current_form_status())
			.withEND(child.END()).withDetails(child.details())
			.withENCCVisitOne(child.enccVisitOne())
			.withENCCVisitTwo(child.enccVisitTwo())
			.withENCCVisitThree(child.enccVisitThree());

			childRegisterEntries.add(childRegisterEntry);
		}
		return new ChildRegister(childRegisterEntries);
	}

}
