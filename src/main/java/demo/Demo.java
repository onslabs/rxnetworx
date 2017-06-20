package demo;

import java.util.HashMap;

import onsterlabs.network.rxnetwork.APISubscriber;
import onsterlabs.network.rxnetwork.RetroError;
import onsterlabs.network.rxnetwork.RXEventBus;
import onsterlabs.network.NetworkClient;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Action1;

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
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        Retrofit retrofit = NetworkClient.getRestAdapter("qa", AppConstant.BASE_URL, headerMap, null);

        ILoginAPI iLoginAPI = (ILoginAPI) retrofit.create(ILoginAPI.class);
        Observable<LoginResponse> loginResponseObservable = iLoginAPI.loginRequesturl("ARN-1690", "password", "Best@123");
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
        loginResponseObservable.subscribe(new APISubscriber<LoginResponse>());


    }
}
