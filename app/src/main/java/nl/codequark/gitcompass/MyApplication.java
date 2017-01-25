package nl.codequark.gitcompass;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import nl.codequark.gitcompass.utils.AnalyticsHelper;
import nl.codequark.gitcompass.utils.FavoReposHelper;
import nl.codequark.gitcompass.utils.LanguageHelper;

/**
 * Created by lao on 15/9/23.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AnalyticsHelper.initialize(this);
        LanguageHelper.init(this);
        FavoReposHelper.init(this);
        initImageLoader(this);
    }


    public void initImageLoader(Context context) {
        /*
        This configuration tuning is custom. You can tune every option, you may tune some of them,
        or you can create default configuration by
        ImageLoaderConfiguration.createDefault(this);
        method.
        */
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSizePercentage(25)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(100 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    /**
     * @return
     */
    @NonNull
    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        // config builder
        return builder.create();
    }


}
