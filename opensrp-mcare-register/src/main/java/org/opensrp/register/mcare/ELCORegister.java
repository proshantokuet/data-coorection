package org.opensrp.register.mcare;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ELCORegister {

    private final List<ELCORegisterEntry> ecRegisterEntries;

    public ELCORegister(List<ELCORegisterEntry> ecRegisterEntries) {
        this.ecRegisterEntries = ecRegisterEntries;
    }

    public List<ELCORegisterEntry> ecRegisterEntries() {
        return ecRegisterEntries;
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
