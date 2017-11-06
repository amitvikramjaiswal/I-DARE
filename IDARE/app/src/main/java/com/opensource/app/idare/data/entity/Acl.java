
package com.opensource.app.idare.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Acl {

    @SerializedName("creator")
    @Expose
    private String creator;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("creator", creator).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(creator).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Acl) == false) {
            return false;
        }
        Acl rhs = ((Acl) other);
        return new EqualsBuilder().append(creator, rhs.creator).isEquals();
    }

}
