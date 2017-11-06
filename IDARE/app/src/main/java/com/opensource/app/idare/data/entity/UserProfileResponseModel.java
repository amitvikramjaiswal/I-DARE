
package com.opensource.app.idare.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class UserProfileResponseModel {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("alternate")
    @Expose
    private String alternate;
    @SerializedName("_kmd")
    @Expose
    private Kmd kmd;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_acl")
    @Expose
    private Acl acl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public Kmd getKmd() {
        return kmd;
    }

    public void setKmd(Kmd kmd) {
        this.kmd = kmd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Acl getAcl() {
        return acl;
    }

    public void setAcl(Acl acl) {
        this.acl = acl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("username", username).append("password", password).append("name", name).append("email", email).append("mobile", mobile).append("alternate", alternate).append("kmd", kmd).append("id", id).append("acl", acl).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(kmd).append(alternate).append(username).append(acl).append(email).append(name).append(password).append(mobile).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserProfileResponseModel) == false) {
            return false;
        }
        UserProfileResponseModel rhs = ((UserProfileResponseModel) other);
        return new EqualsBuilder().append(id, rhs.id).append(kmd, rhs.kmd).append(alternate, rhs.alternate).append(username, rhs.username).append(acl, rhs.acl).append(email, rhs.email).append(name, rhs.name).append(password, rhs.password).append(mobile, rhs.mobile).isEquals();
    }

}
