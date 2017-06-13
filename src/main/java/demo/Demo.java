package demo;

import java.util.HashMap;

import onslabs.kit.rxnetworx.APISubscriber;
import onslabs.kit.rxnetworx.RetroError;
import onslabs.kit.rxnetworx.RxEventBus;
import onslabs.kit.rxnetworx.RxNetworkClient;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by salil-kaul on 13/6/17.
 */

public class Demo {
    public static void main(String[] args) {
        System.out.println("Hi");
        HashMap map = new HashMap();
        Retrofit retrofit = RxNetworkClient.getRestAdapter(AppConstant.BASE_URL, map);
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
