package olabs.kit.rxnetworx;

import rx.functions.Func2;

public class CombineSubscriber<T> implements Func2 {
    @Override
    public Object call(Object o, Object o2) {
        return null;
    }

    /*@Override
    public T call(Object o, Object o2) {
        //return new ;
    }

    Observable<T> combined = Observable.zip(ob1, ob2, new Func2<JsonObject, JsonArray, T>() {
        @Override
        public T call(JsonObject jsonObject, JsonArray jsonElements) {

            if (T instanceof UserAndEvents )
             return new UserAndEvents(jsonObject, jsonElements);
        }
    });
*/
}
