package org.opensrp.register.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.IntegerUtil;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;
import org.opensrp.register.CondomFPDetails;
import org.opensrp.register.FemaleSterilizationFPDetails;
import org.opensrp.register.IUDFPDetails;
import org.opensrp.register.MaleSterilizationFPDetails;
import org.opensrp.register.OCPFPDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.opensrp.common.AllConstants.ECRegistrationFields.WIFE_AGE;
import static org.opensrp.common.AllConstants.ECRegistrationFields.WIFE_DOB;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.CURRENT_FP_METHOD_FIELD_NAME;

@TypeDiscriminator("doc.type === 'EligibleCouple'")
public class EligibleCouple extends MotechBaseDataObject {
    @JsonProperty
    private String caseId;
    @JsonProperty
    private String ecNumber;
    @JsonProperty
    private String wifeName;
    @JsonProperty
    private String husbandName;
    @JsonProperty
    private String anmIdentifier;
    @JsonProperty
    private String locationId;
    @JsonProperty
    private String isOutOfArea;
    @JsonProperty
    private String isClosed;
    @JsonProperty
    private Map<String, String> details;
    @JsonProperty
    private List<IUDFPDetails> iudFPDetails;
    @JsonProperty
    private List<CondomFPDetails> condomFPDetails;
    @JsonProperty
    private List<OCPFPDetails> ocpFPDetails;
    @JsonProperty
    private List<MaleSterilizationFPDetails> maleSterilizationFPDetails;
    @JsonProperty
    private List<FemaleSterilizationFPDetails> femaleSterilizationFPDetails;

    public EligibleCouple() {
    }

    public EligibleCouple(String caseId, String ecNumber) {
        this.caseId = caseId;
        this.ecNumber = ecNumber;
        this.isOutOfArea = "false";
        this.setIsClosed(false);
    }

    public EligibleCouple withCouple(String wifeName, String husbandName) {
        this.wifeName = wifeName;
        this.husbandName = husbandName;
        return this;
    }

    public EligibleCouple withANMIdentifier(String anmIdentifier) {
        this.anmIdentifier = anmIdentifier;
        return this;
    }
    
    public EligibleCouple withLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public EligibleCouple withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }

    public EligibleCouple withIUDFPDetails(List<IUDFPDetails> iudfpDetails) {
        this.iudFPDetails = iudfpDetails;
        return this;
    }

    public EligibleCouple withCondomFPDetails(List<CondomFPDetails> condomFPDetails) {
        this.condomFPDetails = condomFPDetails;
        return this;
    }

    public EligibleCouple withOCPFPDetails(List<OCPFPDetails> ocpfpDetails) {
        this.ocpFPDetails = ocpfpDetails;
        return this;
    }

    public EligibleCouple withMaleSterilizationFPDetails(List<MaleSterilizationFPDetails> maleSterilizationFPDetails) {
        this.maleSterilizationFPDetails = maleSterilizationFPDetails;
        return this;
    }

    public EligibleCouple withFemaleSterilizationFPDetails(List<FemaleSterilizationFPDetails> femaleSterilizationFPDetails) {
        this.femaleSterilizationFPDetails = femaleSterilizationFPDetails;
        return this;
    }

    public EligibleCouple asOutOfArea() {
        this.isOutOfArea = "true";
        return this;
    }

    public String wifeName() {
        return wifeName;
    }

    public String husbandName() {
        return husbandName;
    }

    public String caseId() {
        return caseId;
    }

    public String ecNumber() {
        return ecNumber;
    }

    public String anmIdentifier() {
        return anmIdentifier;
    }
    
    public String locationId() {
        return locationId;
    }

    public Map<String, String> details() {
        return details;
    }

    public EligibleCouple setIsClosed(boolean isClosed) {
        this.isClosed = Boolean.toString(isClosed);
        return this;
    }

    public String currentMethod() {
        return details.get(CURRENT_FP_METHOD_FIELD_NAME);
    }

    private String getCaseId() {
        return caseId;
    }

    public String getDetail(String name) {
        return details.get(name);
    }

    public List<IUDFPDetails> iudFPDetails() {
        if (this.iudFPDetails == null)
            this.iudFPDetails = new ArrayList<>();
        return iudFPDetails;
    }

    public List<CondomFPDetails> condomFPDetails() {
        if (this.condomFPDetails == null)
            this.condomFPDetails = new ArrayList<>();
        return condomFPDetails;
    }

    public List<OCPFPDetails> ocpFPDetails() {
        if (this.ocpFPDetails == null)
            this.ocpFPDetails = new ArrayList<>();
        return ocpFPDetails;
    }

    public List<MaleSterilizationFPDetails> maleSterilizationFPDetails() {
        if (this.maleSterilizationFPDetails == null)
            this.maleSterilizationFPDetails = new ArrayList<>();
        return maleSterilizationFPDetails;
    }

    public List<FemaleSterilizationFPDetails> femaleSterilizationFPDetails() {
        if (this.femaleSterilizationFPDetails == null)
            this.femaleSterilizationFPDetails = new ArrayList<>();
        return femaleSterilizationFPDetails;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, "id", "revision");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id", "revision");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String wifeDOB() {
        String wifeDOB = details.get(WIFE_DOB);
        if (wifeDOB != null) {
            return wifeDOB;
        }
        int wifeAge = IntegerUtil.tryParse(details.get(WIFE_AGE), 0);
        return String.valueOf((wifeAge != 0) ? DateUtil.today().minusYears(wifeAge) : null);
    }
}
