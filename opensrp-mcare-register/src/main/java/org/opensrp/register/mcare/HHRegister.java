package org.opensrp.register.mcare;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class HHRegister {

    private final List<HHRegisterEntry> hhRegisterEntries;

    public HHRegister(List<HHRegisterEntry> hhRegisterEntries) {
        this.hhRegisterEntries = hhRegisterEntries;
    }

    public List<HHRegisterEntry> hhRegisterEntries() {
        return hhRegisterEntries;
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
