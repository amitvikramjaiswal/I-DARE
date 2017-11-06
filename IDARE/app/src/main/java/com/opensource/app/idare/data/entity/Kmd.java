
package com.opensource.app.idare.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Kmd {

    @SerializedName("lmt")
    @Expose
    private String lmt;
    @SerializedName("ect")
    @Expose
    private String ect;
    @SerializedName("authtoken")
    @Expose
    private String authtoken;

    public String getLmt() {
        return lmt;
    }

    public void setLmt(String lmt) {
        this.lmt = lmt;
    }

    public String getEct() {
        return ect;
    }

    public void setEct(String ect) {
        this.ect = ect;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("lmt", lmt).append("ect", ect).append("authtoken", authtoken).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(authtoken).append(ect).append(lmt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Kmd) == false) {
            return false;
        }
        Kmd rhs = ((Kmd) other);
        return new EqualsBuilder().append(authtoken, rhs.authtoken).append(ect, rhs.ect).append(lmt, rhs.lmt).isEquals();
    }

}
