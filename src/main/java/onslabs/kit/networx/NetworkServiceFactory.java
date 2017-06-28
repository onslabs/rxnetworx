package onslabs.kit.networx;

import java.util.HashMap;

import retrofit2.Retrofit;


public class NetworkServiceFactory {

    private static NetworkServiceFactory sDataService;
    private Retrofit mRestClient;
    private TokenAuthenticator mTokenAuthenticator;

    private NetworkServiceFactory() {
    }

    private NetworkServiceFactory(Retrofit restClient) {
        mRestClient = restClient;
    }

    public static <S> S getInstance(String buildVariant, String baseUrl, Class<S> serviceClass, HashMap requestHeaderMap, boolean isHttps) {
        if (sDataService == null) {
            sDataService = new NetworkServiceFactory(NetworkClient.getRestAdapter(buildVariant, baseUrl, requestHeaderMap, isHttps));
        }
        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewInstance(String buildVariant, String baseUrl, Class<S> serviceClass, HashMap requestHeaderMap, boolean isHttps) {
        sDataService = null;
        sDataService = new NetworkServiceFactory(NetworkClient.getRestAdapter(buildVariant, baseUrl, requestHeaderMap, isHttps));

        return sDataService.getClient(serviceClass);
    }


    private <S> S getClient(Class<S> serviceClass) {
        return mRestClient.create(serviceClass);
    }

}

