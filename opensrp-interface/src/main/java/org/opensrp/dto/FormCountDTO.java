package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class FormCountDTO {
	@JsonProperty
	private int householdTotalCount;
	@JsonProperty
	private int householdThisMonthCount;
	@JsonProperty
	private int householdWeek1Count;
	@JsonProperty
	private int householdWeek2Count;
	@JsonProperty
	private int householdWeek3Count;
	@JsonProperty
	private int householdWeek4Count;
	@JsonProperty
	private int psrfTotalCount;
	@JsonProperty
	private int psrfThisMonthCount;
	@JsonProperty
	private int psrfWeek1Count;
	@JsonProperty
	private int psrfWeek2Count;
	@JsonProperty
	private int psrfWeek3Count;
	@JsonProperty
	private int psrfWeek4Count;
	@JsonProperty
	private int censusTotalCount;
	@JsonProperty
	private int censusThisMonthCount;	
	@JsonProperty
	private int censusWeek1Count;
	@JsonProperty
	private int censusWeek2Count;
	@JsonProperty
	private int censusWeek3Count;
	@JsonProperty
	private int censusWeek4Count;
	@JsonProperty
	private int ancTotalCount;
	@JsonProperty
	private int ancThisMonthCount;	
	@JsonProperty
	private int ancWeek1Count;
	@JsonProperty
	private int ancWeek2Count;
	@JsonProperty
	private int ancWeek3Count;
	@JsonProperty
	private int ancWeek4Count;
	@JsonProperty
	private int pncTotalCount;
	@JsonProperty
	private int pncThisMonthCount;	
	@JsonProperty
	private int pncWeek1Count;
	@JsonProperty
	private int pncWeek2Count;
	@JsonProperty
	private int pncWeek3Count;
	@JsonProperty
	private int pncWeek4Count;
	@JsonProperty
	private int enccTotalCount;
	@JsonProperty
	private int enccThisMonthCount;	
	@JsonProperty
	private int enccWeek1Count;
	@JsonProperty
	private int enccWeek2Count;
	@JsonProperty
	private int enccWeek3Count;
	@JsonProperty
	private int enccWeek4Count;
	@JsonProperty
	private int pregnancyTotalCount;
	@JsonProperty
	private int pregnancyThisMonthCount;	
	@JsonProperty
	private int pregnancyWeek1Count;
	@JsonProperty
	private int pregnancyWeek2Count;
	@JsonProperty
	private int pregnancyWeek3Count;
	@JsonProperty
	private int pregnancyWeek4Count;
	@JsonProperty
	private int familyPlanning;
	@JsonProperty
	private int newborn;	
	@JsonProperty
	private int liveBirth;
	@JsonProperty
	private int stillBirth;
	@JsonProperty
	private int miscarriage;
	@JsonProperty
	private int mortalityAtBirth;
	@JsonProperty
	private int mortalityAtDelivery;

	public FormCountDTO(){
		
	}
	
	public FormCountDTO(int householdTotalCount, int householdWeek2Count,
			int householdWeek1Count, int householdThisMonthCount,
			int psrfTotalCount, int householdWeek4Count, int householdWeek3Count,
			int psrfThisMonthCount, int psrfWeek1Count, int psrfWeek2Count, 
			int psrfWeek3Count, int psrfWeek4Count,int censusTotalCount, 
			int censusThisMonthCount, int censusWeek1Count, int censusWeek2Count, 
			int censusWeek3Count, int censusWeek4Count,int ancTotalCount, 
			int ancThisMonthCount, int ancWeek1Count, int ancWeek2Count, 
			int ancWeek3Count, int ancWeek4Count, int pncTotalCount, 
			int pncThisMonthCount, int pncWeek1Count, int pncWeek2Count, 
			int pncWeek3Count, int pncWeek4Count, int enccTotalCount, 
			int enccThisMonthCount, int enccWeek1Count, int enccWeek2Count, 
			int enccWeek3Count, int enccWeek4Count, int pregnancyTotalCount, 
			int pregnancyThisMonthCount, int pregnancyWeek1Count, int pregnancyWeek2Count, 
			int pregnancyWeek3Count, int pregnancyWeek4Count, int familyPlanning, 
			int newborn, int liveBirth, int stillBirth, int miscarriage,
			int mortalityAtBirth, int mortalityAtDelivery) {
		super();
		this.householdTotalCount = householdTotalCount;
		this.householdWeek2Count = householdWeek2Count;
		this.householdWeek1Count = householdWeek1Count;
		this.householdThisMonthCount = householdThisMonthCount;
		this.psrfTotalCount = psrfTotalCount;
		this.householdWeek4Count = householdWeek4Count;
		this.householdWeek3Count = householdWeek3Count;
		this.psrfThisMonthCount = psrfThisMonthCount;
		this.psrfWeek2Count = psrfWeek2Count;
		this.psrfWeek1Count = psrfWeek1Count;
		this.psrfWeek4Count = psrfWeek4Count;
		this.psrfWeek3Count = psrfWeek3Count;
		this.censusTotalCount = censusTotalCount;
		this.censusThisMonthCount = censusThisMonthCount;
		this.censusWeek2Count = censusWeek2Count;
		this.censusWeek1Count = censusWeek1Count;
		this.censusWeek4Count = censusWeek4Count;
		this.censusWeek3Count = censusWeek3Count;
		this.ancTotalCount = ancTotalCount;
		this.ancThisMonthCount = ancThisMonthCount;
		this.ancWeek2Count = ancWeek2Count;
		this.ancWeek1Count = ancWeek1Count;
		this.ancWeek4Count = ancWeek4Count;
		this.ancWeek3Count = ancWeek3Count;
		this.pncTotalCount = pncTotalCount;
		this.pncThisMonthCount = pncThisMonthCount;
		this.pncWeek2Count = pncWeek2Count;
		this.pncWeek1Count = pncWeek1Count;
		this.pncWeek4Count = pncWeek4Count;
		this.pncWeek3Count = pncWeek3Count;
		this.enccTotalCount = enccTotalCount;
		this.enccThisMonthCount = enccThisMonthCount;
		this.enccWeek2Count = enccWeek2Count;
		this.enccWeek1Count = enccWeek1Count;
		this.enccWeek4Count = enccWeek4Count;
		this.enccWeek3Count = enccWeek3Count;	
		this.pregnancyTotalCount = pregnancyTotalCount;
		this.pregnancyThisMonthCount = pregnancyThisMonthCount;
		this.pregnancyWeek2Count = pregnancyWeek2Count;
		this.pregnancyWeek1Count = pregnancyWeek1Count;
		this.pregnancyWeek4Count = pregnancyWeek4Count;
		this.pregnancyWeek3Count = pregnancyWeek3Count;
		this.familyPlanning = familyPlanning;
		this.newborn = newborn;
		this.stillBirth = stillBirth;
		this.liveBirth = liveBirth;
		this.miscarriage = miscarriage;
		this.mortalityAtDelivery = mortalityAtDelivery;
		this.mortalityAtBirth = mortalityAtBirth;
	}
	
	public int getHouseholdTotalCount() {
		return householdTotalCount;
	}

	public void setHouseholdTotalCount(int householdTotalCount) {
		this.householdTotalCount = householdTotalCount;
	}

	public int getHouseholdWeek2Count() {
		return householdWeek2Count;
	}

	public void setHouseholdWeek2Count(int householdWeek2Count) {
		this.householdWeek2Count = householdWeek2Count;
	}

	public int getHouseholdWeek1Count() {
		return householdWeek1Count;
	}

	public void setHouseholdWeek1Count(int householdWeek1Count) {
		this.householdWeek1Count = householdWeek1Count;
	}

	public int getHouseholdThisMonthCount() {
		return householdThisMonthCount;
	}

	public void setHouseholdThisMonthCount(int householdThisMonthCount) {
		this.householdThisMonthCount = householdThisMonthCount;
	}

	public int getPsrfTotalCount() {
		return psrfTotalCount;
	}

	public void setPsrfTotalCount(int psrfTotalCount) {
		this.psrfTotalCount = psrfTotalCount;
	}

	public int getHouseholdWeek4Count() {
		return householdWeek4Count;
	}

	public void setHouseholdWeek4Count(int householdWeek4Count) {
		this.householdWeek4Count = householdWeek4Count;
	}

	public int getHouseholdWeek3Count() {
		return householdWeek3Count;
	}

	public void setHouseholdWeek3Count(int householdWeek3Count) {
		this.householdWeek3Count = householdWeek3Count;
	}

	public int getPsrfThisMonthCount() {
		return psrfThisMonthCount;
	}

	public void setPsrfThisMonthCount(int psrfThisMonthCount) {
		this.psrfThisMonthCount = psrfThisMonthCount;
	}
	
	public int getPsrfWeek2Count() {
		return psrfWeek2Count;
	}
	
	public void setPsrfWeek2Count(int psrfWeek2Count) {
		this.psrfWeek2Count = psrfWeek2Count;
	}
	
	public int getPsrfWeek1Count() {
		return psrfWeek1Count;
	}
	
	public void setPsrfWeek1Count(int psrfWeek1Count) {
		this.psrfWeek1Count = psrfWeek1Count;
	}
	
	public int getPsrfWeek4Count() {
		return psrfWeek4Count;
	}
	
	public void setPsrfWeek4Count(int psrfWeek4Count) {
		this.psrfWeek4Count = psrfWeek4Count;
	}
	
	public int getPsrfWeek3Count() {
		return psrfWeek3Count;
	}
	
	public void setPsrfWeek3Count(int psrfWeek3Count) {
		this.psrfWeek3Count = psrfWeek3Count;
	}

	public int getCensusTotalCount() {
		return censusTotalCount;
	}

	public void setCensusTotalCount(int censusTotalCount) {
		this.censusTotalCount = censusTotalCount;
	}

	public int getCensusThisMonthCount() {
		return censusThisMonthCount;
	}

	public void setCensusThisMonthCount(int censusThisMonthCount) {
		this.censusThisMonthCount = censusThisMonthCount;
	}
	
	public int getCensusWeek2Count() {
		return censusWeek2Count;
	}
	
	public void setCensusWeek2Count(int censusWeek2Count) {
		this.censusWeek2Count = censusWeek2Count;
	}
	
	public int getCensusWeek1Count() {
		return censusWeek1Count;
	}
	
	public void setCensusWeek1Count(int censusWeek1Count) {
		this.censusWeek1Count = censusWeek1Count;
	}
	
	public int getCensusWeek4Count() {
		return censusWeek4Count;
	}
	
	public void setCensusWeek4Count(int censusWeek4Count) {
		this.censusWeek4Count = censusWeek4Count;
	}
	
	public int getCensusWeek3Count() {
		return censusWeek3Count;
	}
	
	public void setCensusWeek3Count(int censusWeek3Count) {
		this.censusWeek3Count = censusWeek3Count;
	}
	
	public int getAncTotalCount() {
		return ancTotalCount;
	}

	public void setAncTotalCount(int ancTotalCount) {
		this.ancTotalCount = ancTotalCount;
	}

	public int getAncThisMonthCount() {
		return ancThisMonthCount;
	}

	public void setAncThisMonthCount(int ancThisMonthCount) {
		this.ancThisMonthCount = ancThisMonthCount;
	}
	
	public int getAncWeek2Count() {
		return ancWeek2Count;
	}
	
	public void setAncWeek2Count(int ancWeek2Count) {
		this.ancWeek2Count = ancWeek2Count;
	}
	
	public int getAncWeek1Count() {
		return ancWeek1Count;
	}
	
	public void setAncWeek1Count(int ancWeek1Count) {
		this.ancWeek1Count = ancWeek1Count;
	}
	
	public int getAncWeek4Count() {
		return ancWeek4Count;
	}
	
	public void setAncWeek4Count(int ancWeek4Count) {
		this.ancWeek4Count = ancWeek4Count;
	}
	
	public int getAncWeek3Count() {
		return ancWeek3Count;
	}
	
	public void setAncWeek3Count(int ancWeek3Count) {
		this.ancWeek3Count = ancWeek3Count;
	}
	
	public int getPncTotalCount() {
		return pncTotalCount;
	}

	public void setPncTotalCount(int pncTotalCount) {
		this.pncTotalCount = pncTotalCount;
	}

	public int getPncThisMonthCount() {
		return pncThisMonthCount;
	}

	public void setPncThisMonthCount(int pncThisMonthCount) {
		this.pncThisMonthCount = pncThisMonthCount;
	}
	
	public int getPncWeek2Count() {
		return pncWeek2Count;
	}
	
	public void setPncWeek2Count(int pncWeek2Count) {
		this.pncWeek2Count = pncWeek2Count;
	}
	
	public int getPncWeek1Count() {
		return pncWeek1Count;
	}
	
	public void setPncWeek1Count(int pncWeek1Count) {
		this.pncWeek1Count = pncWeek1Count;
	}
	
	public int getPncWeek4Count() {
		return pncWeek4Count;
	}
	
	public void setPncWeek4Count(int pncWeek4Count) {
		this.pncWeek4Count = pncWeek4Count;
	}
	
	public int getPncWeek3Count() {
		return pncWeek3Count;
	}
	
	public void setPncWeek3Count(int pncWeek3Count) {
		this.pncWeek3Count = pncWeek3Count;
	}

	public int getEnccTotalCount() {
		return enccTotalCount;
	}

	public void setEnccTotalCount(int enccTotalCount) {
		this.enccTotalCount = enccTotalCount;
	}

	public int getEnccThisMonthCount() {
		return enccThisMonthCount;
	}

	public void setEnccThisMonthCount(int enccThisMonthCount) {
		this.enccThisMonthCount = enccThisMonthCount;
	}
	
	public int getEnccWeek2Count() {
		return enccWeek2Count;
	}
	
	public void setEnccWeek2Count(int enccWeek2Count) {
		this.enccWeek2Count = enccWeek2Count;
	}
	
	public int getEnccWeek1Count() {
		return enccWeek1Count;
	}
	
	public void setEnccWeek1Count(int enccWeek1Count) {
		this.enccWeek1Count = enccWeek1Count;
	}
	
	public int getEnccWeek4Count() {
		return enccWeek4Count;
	}
	
	public void setEnccWeek4Count(int enccWeek4Count) {
		this.enccWeek4Count = enccWeek4Count;
	}
	
	public int getEnccWeek3Count() {
		return enccWeek3Count;
	}
	
	public void setEnccWeek3Count(int enccWeek3Count) {
		this.enccWeek3Count = enccWeek3Count;
	}
	
	public int getPregnancyTotalCount() {
		return pregnancyTotalCount;
	}

	public void setPregnancyTotalCount(int pregnancyTotalCount) {
		this.pregnancyTotalCount = pregnancyTotalCount;
	}

	public int getPregnancyThisMonthCount() {
		return pregnancyThisMonthCount;
	}

	public void setPregnancyThisMonthCount(int pregnancyThisMonthCount) {
		this.pregnancyThisMonthCount = pregnancyThisMonthCount;
	}
	
	public int getPregnancyWeek2Count() {
		return pregnancyWeek2Count;
	}
	
	public void setPregnancyWeek2Count(int pregnancyWeek2Count) {
		this.pregnancyWeek2Count = pregnancyWeek2Count;
	}
	
	public int getPregnancyWeek1Count() {
		return pregnancyWeek1Count;
	}
	
	public void setPregnancyWeek1Count(int pregnancyWeek1Count) {
		this.pregnancyWeek1Count = pregnancyWeek1Count;
	}
	
	public int getPregnancyWeek4Count() {
		return pregnancyWeek4Count;
	}
	
	public void setPregnancyWeek4Count(int pregnancyWeek4Count) {
		this.pregnancyWeek4Count = pregnancyWeek4Count;
	}
	
	public int getPregnancyWeek3Count() {
		return pregnancyWeek3Count;
	}
	
	public void setPregnancyWeek3Count(int pregnancyWeek3Count) {
		this.pregnancyWeek3Count = pregnancyWeek3Count;
	}
	
	public int getFamilyPlanning() {
		return familyPlanning;
	}

	public void setFamilyPlanning(int familyPlanning) {
		this.familyPlanning = familyPlanning;
	}

	public int getNewborn() {
		return newborn;
	}

	public void setNewborn(int newborn) {
		this.newborn = newborn;
	}
	
	public int getStillBirth() {
		return stillBirth;
	}
	
	public void setStillBirth(int stillBirth) {
		this.stillBirth = stillBirth;
	}
	
	public int getLiveBirth() {
		return liveBirth;
	}
	
	public void setLiveBirth(int liveBirth) {
		this.liveBirth = liveBirth;
	}
	
	public int getmiscarriage() {
		return miscarriage;
	}

	public void setmiscarriage(int miscarriage) {
		this.miscarriage = miscarriage;
	}
	
	public int getMortalityAtDelivery() {
		return mortalityAtDelivery;
	}
	
	public void setMortalityAtDelivery(int mortalityAtDelivery) {
		this.mortalityAtDelivery = mortalityAtDelivery;
	}
	
	public int getMortalityAtBirth() {
		return mortalityAtBirth;
	}
	
	public void setMortalityAtBirth(int mortalityAtBirth) {
		this.mortalityAtBirth = mortalityAtBirth;
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
