package onsterlabs.network;


import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class
RxEventBus {
    private static RxEventBus rxEventBus;

    private RxEventBus() {

    }


    private final Subject<Object, Object> mBusSubject = new SerializedSubject<>(PublishSubject.create());

    public static RxEventBus getInstance() {
        if (rxEventBus == null) {
            rxEventBus = new RxEventBus();
        }
        return rxEventBus;
    }

    //Method to Subscribe
    public <T> Subscription register(final Class<T> eventClass, Action1<T> onNext) {
        return mBusSubject
                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                        return o.getClass().equals(eventClass);
                    }
                }).map(new Func1<Object, T>() {
                    @Override
                    public T call(Object o) {
                        return (T)o;
                    }
                }).subscribe(onNext);
    }

    // Method to post data
    public void post(Object object) {
        mBusSubject.onNext(object);
    }


}
