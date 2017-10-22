package org.opensrp.register.service.scheduling.fpMethodStrategy;

import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.CONDOM_FP_METHOD_VALUE;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.DMPA_INJECTABLE_FP_METHOD_VALUE;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.FEMALE_STERILIZATION_FP_METHOD_VALUE;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.IUD_FP_METHOD_VALUE;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.MALE_STERILIZATION_FP_METHOD_VALUE;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.OCP_FP_METHOD_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FPMethodStrategyFactory {
    private final DMPAInjectableStrategy dmpaInjectableStrategy;
    private final OCPStrategy ocpStrategy;
    private final CondomStrategy condomStrategy;
    private final FemaleSterilizationStrategy femaleSterilizationStrategy;
    private final MaleSterilizationStrategy maleSterilizationStrategy;
    private final IUDStrategy iudStrategy;
    private final DefaultFPMethodStrategy defaultFPMethodStrategy;

    @Autowired
    public FPMethodStrategyFactory(DMPAInjectableStrategy dmpaInjectableStrategy,
                                   OCPStrategy ocpStrategy,
                                   CondomStrategy condomStrategy,
                                   FemaleSterilizationStrategy femaleSterilizationStrategy,
                                   MaleSterilizationStrategy maleSterilizationStrategy,
                                   IUDStrategy iudStrategy,
                                   DefaultFPMethodStrategy defaultFPMethodStrategy) {
        this.dmpaInjectableStrategy = dmpaInjectableStrategy;
        this.ocpStrategy = ocpStrategy;
        this.condomStrategy = condomStrategy;
        this.femaleSterilizationStrategy = femaleSterilizationStrategy;
        this.maleSterilizationStrategy = maleSterilizationStrategy;
        this.iudStrategy = iudStrategy;
        this.defaultFPMethodStrategy = defaultFPMethodStrategy;
    }

    public FPMethodStrategy getStrategyFor(String fpMethod) {
        if (DMPA_INJECTABLE_FP_METHOD_VALUE.equalsIgnoreCase(fpMethod)) {
            return dmpaInjectableStrategy;
        }
        if (OCP_FP_METHOD_VALUE.equalsIgnoreCase(fpMethod)) {
            return ocpStrategy;
        }
        if (CONDOM_FP_METHOD_VALUE.equalsIgnoreCase(fpMethod)) {
            return condomStrategy;
        }
        if (FEMALE_STERILIZATION_FP_METHOD_VALUE.equalsIgnoreCase(fpMethod)) {
            return femaleSterilizationStrategy;
        }
        if (MALE_STERILIZATION_FP_METHOD_VALUE.equalsIgnoreCase(fpMethod)) {
            return maleSterilizationStrategy;
        }
        if (IUD_FP_METHOD_VALUE.equalsIgnoreCase(fpMethod)) {
            return iudStrategy;
        }
        return defaultFPMethodStrategy;
    }
}
