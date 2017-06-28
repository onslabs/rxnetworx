package demo;

import java.util.HashMap;

import onslabs.kit.networx.NetworkClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by salil-kaul on 13/6/17.
 */

public class Demo {
    //Demo
    public static void main(String[] args) {
        System.out.println("Hi");
        HashMap headerMap = new HashMap();

        headerMap.put("app-type", "W");
        headerMap.put("Content-Type", "JSON");
        headerMap.put("DUMMY", "DUMMY");
        headerMap.put(AppConstant.AUTHORIZATION, AppConstant.APP_AUTHORIZATION);
        Retrofit retrofit = NetworkClient.getRestAdapter("qa", AppConstant.BASE_URL, headerMap,true);

        ILoginAPI iLoginAPI = (ILoginAPI) retrofit.create(ILoginAPI.class);

        //RX Code Commented
        /*Observable<LoginResponse> loginResponseObservable = iLoginAPI.loginRequesturl("ARN-1690", "password", "Best@123");
        RXEventBus.getInstance().register(LoginResponse.class, new Action1<LoginResponse>() {
            @Override
            public void call(LoginResponse loginResponse) {
                System.out.println("Success");
            }
        });

        RXEventBus.getInstance().register(RetroError.class, new Action1<RetroError>() {
            @Override
            public void call(RetroError retroError) {
                System.out.println("Failure");
            }
        });
        loginResponseObservable.subscribe(new APISubscriber<LoginResponse>());*/


        Call<LoginResponse> loginResponseCall = iLoginAPI.loginRequesturlNoRX("ARN-1690", "password", "Best@123");
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("Success");
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("Failure");
            }
        });
    }
}
