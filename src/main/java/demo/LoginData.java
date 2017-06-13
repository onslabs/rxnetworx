package demo;

/**
 * Created by Salil Kaul on 2/11/16.
 */



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("arnName")
    @Expose
    private String arnName;

    @SerializedName("invalidAttemptCnt")
    @Expose
    private Integer invalidAttemptCnt;
    @SerializedName("pwdExpiryDays")
    @Expose
    private Object pwdExpiryDays;
    @SerializedName("lastLoginDate")
    @Expose
    private Object lastLoginDate;
    @SerializedName("userRole")
    @Expose
    private Object userRole;


    /**
     * @return The pwdExpiryDays
     */

    public Object getPwdExpiryDays() {
        return pwdExpiryDays;
    }

    /**
     * @param pwdExpiryDays The pwdExpiryDays
     */
    public void setPwdExpiryDays(Object pwdExpiryDays) {
        this.pwdExpiryDays = pwdExpiryDays;
    }

    /**
     * @return The lastLoginDate
     */

    public Object getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * @param lastLoginDate The lastLoginDate
     */
    public void setLastLoginDate(Object lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * @return The userRole
     */

    public Object getUserRole() {
        return userRole;
    }

    /**
     * @param userRole The userRole
     */

    public void setUserRole(Object userRole) {
        this.userRole = userRole;
    }


}
