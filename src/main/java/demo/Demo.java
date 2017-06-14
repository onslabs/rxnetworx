package demo;

import java.util.HashMap;

import onsterlabs.network.rxnetwork.APISubscriber;
import onsterlabs.network.rxnetwork.RetroError;
import onsterlabs.network.rxnetwork.RxEventBus;
import onsterlabs.network.RxNetworkClient;
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
        headerMap.put("app-type", "M");
        headerMap.put("Content-Type", "application/json");
        headerMap.put(AppConstant.AUTHORIZATION, AppConstant.APP_AUTHORIZATION);
        Retrofit retrofit = RxNetworkClient.getRestAdapter(AppConstant.BASE_URL, headerMap);
        ILoginAPI iLoginAPI = (ILoginAPI) retrofit.create(ILoginAPI.class);
        Observable<LoginResponse> loginResponseObservable = iLoginAPI.loginRequesturl("ARN-1690", "password", "Best@123");
        RxEventBus.getInstance().register(LoginResponse.class, new Action1<LoginResponse>() {
            @Override
            public void call(LoginResponse loginResponse) {
                System.out.println("Success");
            }
        });

        RxEventBus.getInstance().register(RetroError.class, new Action1<RetroError>() {
            @Override
            public void call(RetroError retroError) {
                System.out.println("Failure");
            }
        });
        loginResponseObservable.subscribe(new APISubscriber<LoginResponse>());


    }
}
