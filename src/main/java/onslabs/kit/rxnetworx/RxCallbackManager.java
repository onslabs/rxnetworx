package onslabs.kit.rxnetworx;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ttnd on 25/11/16.
 */

public abstract class RxCallbackManager<T> implements Action1<T> {

    private String baseUrl;
    private HashMap<String, String> requestHeaders = new HashMap<>();
    private Scheduler defaultSubscribeScheduler;
    private InputStream certificateInputStream;
    protected HashMap<Class, Subscription> subscriptionsHashMap = new HashMap<>();
    protected HashMap<Class, List<Subscription>> errorSubscriptions = new HashMap<>();
    protected Scheduler androidScheduler;

    public RxCallbackManager(final Scheduler androidScheduler, final String baseUrl, final HashMap<String, String> requestHeaders) {
        this.baseUrl = baseUrl;
        this.requestHeaders = requestHeaders;
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
    protected void initiateApiCall(Class responseType, Observable observable, APISubscriber apiSubscriber) {
        register(RetroError.class);
        register(responseType);
        //register(((ParameterizedType)apiSubscriber.getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
        observable.observeOn(androidScheduler)
                .subscribeOn(defaultSubscribeScheduler())
                .subscribe(apiSubscriber);

    }


    @Override
    public void call(T t) {
        if (t instanceof RetroError) {
            onError((RetroError) t);
        } else {
            onSuccess(t);
        }
        Subscription subscription =  subscriptionsHashMap.get(t.getClass());
        if(subscription!=null) {
            subscription.unsubscribe();
            subscriptionsHashMap.remove(t.getClass());
        }
        unRegisterError();
    }

    public abstract void onError(RetroError errorMessage);

    public abstract void onSuccess(T o);

    public void register(Class S){
        if(S.getName().equals(RetroError.class.getName())){
            if(errorSubscriptions.get(S)!=null){
                errorSubscriptions.get(S).add(RxEventBus.getInstance().register(S, this));
            }else{
                List<Subscription> errorList = new ArrayList<>();
                errorList.add(RxEventBus.getInstance().register(S, this));
                errorSubscriptions.put(S,errorList);
            }
        }
        else
            subscriptionsHashMap.put(S, RxEventBus.getInstance().register(S, this));
    }

    private void unRegisterError(){
        if(!errorSubscriptions.get(RetroError.class).isEmpty()){
            int size = errorSubscriptions.get(RetroError.class).size();
            Subscription subscription = errorSubscriptions.get(RetroError.class).get(size-1);
            subscription.unsubscribe();
            errorSubscriptions.get(RetroError.class).remove(size-1);

        }

    }

}
