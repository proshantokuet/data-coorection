package org.opensrp.register.mcare.mapper;

import static ch.lambdaj.collection.LambdaCollections.with;

import java.util.List;

import org.opensrp.dto.register.Child_RegisterDTO;
import org.opensrp.dto.register.Child_RegisterEntryDTO;
import org.opensrp.register.mcare.ChildRegister;
import org.opensrp.register.mcare.ChildRegisterEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.lambdaj.function.convert.Converter;

@Component
public class ChildRegisterMapper {

	private static Logger logger = LoggerFactory.getLogger(ChildRegisterMapper.class.toString());

	public Child_RegisterDTO mapToDTO(ChildRegister childRegister) {
		List<Child_RegisterEntryDTO> childRegisterEntryDTOs = with(childRegister.childRegisterEntries()).convert(
				new Converter<ChildRegisterEntry, Child_RegisterEntryDTO>() {
					@Override
					public Child_RegisterEntryDTO convert(ChildRegisterEntry entry) {
						return new Child_RegisterEntryDTO()
						.withCaseId(entry.caseId())
						.withTODAY(entry.TODAY())
						.withSTART(entry.START())
						.withPROVIDERID(entry.PROVIDERID())
						.withLOCATIONID(entry.LOCATIONID())
						.withINSTANCEID(entry.INSTANCEID())
						.setEncc1_current_form_status(entry.getEncc1_current_form_status())
						.setEncc2_current_form_status(entry.getEncc2_current_form_status())
						.setEncc3_current_form_status(entry.getEncc3_current_form_status())
						.withEND(entry.END())
						.withDetails(entry.details())
						.withENCCVisitOne(entry.enccVisitOne())
						.withENCCVisitTwo(entry.enccVisitTwo())
						.withENCCVisitThree(entry.enccVisitThree());
					}
				});

		return new Child_RegisterDTO(childRegisterEntryDTOs);

	}

}
