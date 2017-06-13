package demo;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Salil Kaul on 2/11/16.
 */

public class LoginResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private LoginData data;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("access_token")
    @Expose
    private String access_token;

    @SerializedName("token_type")
    @Expose
    private String token_type;

    @SerializedName("refresh_token")
    @Expose
    private String refresh_token;

    @SerializedName("expires_in")
    @Expose
    private String expires_in;

    @SerializedName("scope")
    @Expose
    private String scope;
    private int requestCode;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return The status
     */
    public boolean
    getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return The data
     */
    public LoginData getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(LoginData data) {
        this.data = data;
    }

    /**
     * @return The message
     */


    public int getRequestCode() {
        return requestCode;
    }


    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
