//package com.infinity.infinitysalesapp.api;
//
//import com.github.simonpercic.oklog3.OkLogInterceptor;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.infinity.infinitysalesapp.config.Config;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class ApiClient_duplicate
//{
//
//    private static final String BASE_URL = Config.MAIN_URL;
//    private static final String BASE_LOCAL_URL = Config.MAIN_LOCAL_URL;
//    private static Retrofit retrofit = null;
//
//    private static Gson gson = new GsonBuilder()
//            .setLenient()
//            .create();
//
//    public static Retrofit getClient()
//    {
//
//
//
//        //ppppppppppppppppppppppppppppppp
//
//        OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
//
//        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
//
//        okHttpBuilder.addInterceptor(okLogInterceptor);
//
//        OkHttpClient okHttpClient = okHttpBuilder.build();
//
//
//
//
//
//
//
//        if (retrofit==null)
//        {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_LOCAL_URL)
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit;
//    }
//
////    public static Retrofit getClient()
////    {
//////pppppppppppppppppppppppppppppppp
////
//////        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//////// set your desired log level
//////        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
////////        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//////        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//////// add your other interceptors …
//////// add logging as last interceptor
//////        httpClient.addInterceptor(logging);  // <-- this is the important line!
////
////
////        //ppppppppppppppppppppppppppppppp
////
////        OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
////
////        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
////
////        okHttpBuilder.addInterceptor(okLogInterceptor);
////
////        OkHttpClient okHttpClient = okHttpBuilder.build();
////
////
////
////
////
////
////
////        if (retrofit==null)
////        {
////            retrofit = new Retrofit.Builder()
////                    .baseUrl(BASE_URL)
////                    .client(okHttpClient)
////                    .addConverterFactory(GsonConverterFactory.create(gson))
////                    .build();
////        }
////        return retrofit;
////    }
//    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .readTimeout(300, TimeUnit.SECONDS)
//            .connectTimeout(300, TimeUnit.SECONDS)
//            .build();
//}
