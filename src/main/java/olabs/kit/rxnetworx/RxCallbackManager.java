package olabs.kit.rxnetworx;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by ttnd on 25/11/16.
 */

public abstract class RxCallbackManager<T> {

    private String baseUrl;
    private HashMap<String, String> requestHeaders = new HashMap<>();
    private Scheduler defaultSubscribeScheduler;
    private InputStream certificateInputStream;
    protected Scheduler androidScheduler;

    public RxCallbackManager(final Scheduler androidScheduler, final String baseUrl, final HashMap<String, String> requestHeaders) {
        this.baseUrl = baseUrl;
        this.requestHeaders = requestHeaders;
        this.androidScheduler = androidScheduler;
    }

    public RxCallbackManager(final Scheduler androidScheduler, final String baseUrl, final InputStream certificateInputStream, final HashMap<String, String> requestHeaders) {
        this.baseUrl = baseUrl;
        this.requestHeaders = requestHeaders;
        this.certificateInputStream = certificateInputStream;
        this.androidScheduler = androidScheduler;
    }

    public <S> S getHttpsServiceClient(final boolean isHeaderUpdate, final Class<S> serviceClass) {
        if (isHeaderUpdate) {
            return RxNetworkServiceFactory.getNewHttpsInstance(certificateInputStream, baseUrl,serviceClass, requestHeaders);
        }
        return RxNetworkServiceFactory.getHttpsInstance(certificateInputStream,baseUrl, serviceClass, requestHeaders);
    }

    public <S> S getServiceClient(final boolean isHeaderUpdate, final Class<S> serviceClass) {
        if (isHeaderUpdate) {
            return RxNetworkServiceFactory.getNewInstance(baseUrl,serviceClass, requestHeaders);
        }
        return RxNetworkServiceFactory.getInstance(baseUrl, serviceClass, requestHeaders);
    }


    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    @SuppressWarnings("unchecked")
    protected void initiateApiCall(Observable observable) {
        observable.observeOn(androidScheduler)
                .subscribeOn(defaultSubscribeScheduler())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        RxCallbackManager.this.onError(getRetroError(e));
                    }

                    @Override
                    public void onNext(T t) {
                        RxCallbackManager.this.onSuccess(t);
                    }
                });

    }

    public abstract void onError(RetroError errorMessage);

    public abstract void onSuccess(T t);


    protected Observable getAPIObservable(Observable observable) {
        return observable.observeOn(androidScheduler)
                .subscribeOn(defaultSubscribeScheduler());
    }

    private RetroError getRetroError(Throwable throwable){

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Response response = httpException.response();
            return new RetroError(RetroError.Kind.HTTP, response.message(),response.code());
        }

        return throwable instanceof IOException ?new RetroError(RetroError.Kind.NETWORK, throwable.getMessage(),-999): new RetroError(RetroError.Kind.UNEXPECTED, throwable.getMessage(), -999);

    }

}
