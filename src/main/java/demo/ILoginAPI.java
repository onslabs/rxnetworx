package demo;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ILoginAPI {


    @FormUrlEncoded
    @POST("v1/oauth/token")
    Observable<LoginResponse> loginRequesturl(@Field("username") String arnCode, @Field("grant_type") String grant_type, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/oauth/token")
    Observable<LoginResponse> callApi(@Field("username") String arnCode, @Field("grant_type") String grant_type, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/oauth/token")
    Call<LoginResponse> loginRequesturlNoRX(@Field("username") String arnCode, @Field("grant_type") String grant_type, @Field("password") String password);

}
