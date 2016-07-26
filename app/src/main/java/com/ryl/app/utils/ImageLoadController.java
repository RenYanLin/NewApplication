package com.ryl.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.ryl.app.callback.ImageCallBack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ImageLoadController {
    private static ImageLoaderConfiguration configuration;
    private static DisplayImageOptions options;
    public static ImageLoadingListenerImpl mImageLoadingListenerImpl;

    /**
     * @功能描述: 图片加载配置
     * @作 者: ztj
     * @时 间: 2015年8月14日上午10:13:49
     * @版 本: V1.0.0.0
     */
    public static void imageload(Context context) {

        configuration = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(400, 800)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 3)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
                // 建议内存设在5-10M,可以有比较好的表现
                .memoryCacheSize(5 * 1024 * 1024)
                .discCacheSize(100 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(10000)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())// imageLoader.getDiscCache().get(imageUri).getPath()；
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(context.getApplicationContext(),
                                5 * 1000, 30 * 1000)) // connectTimeout (5 s),
                // readTimeout (30 s)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(configuration);
        mImageLoadingListenerImpl = new ImageLoadingListenerImpl();
    }

    public static DisplayImageOptions options(int ic_id) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(ic_id)
                .showImageForEmptyUri(ic_id)
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 防止内存溢出
                .showImageOnFail(ic_id).cacheInMemory(true)//R.drawable.ic_load
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .delayBeforeLoading(0)//等待多少秒加载
                .build();
        return options;
        /*
         * .displayer(new RoundedBitmapDisplayer(20))// 圆角 EXACTLY
		 * :图像将完全按比例缩小的目标大小
		 *
		 * EXACTLY_STRETCHED:图片会缩放到目标大小完全
		 *
		 * IN_SAMPLE_INT:图像将被二次采样的整数倍
		 *
		 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
		 *
		 * NONE:图片不会调整
		 */
        /*
         * listView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
		 * pauseOnScroll, pauseOnFling));gridView.setOnScrollListener(new
		 * PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
		 * pauseOnScroll—是否在滑动过程中停止加载图片
		 *
		 * pauseOnFling—是否在快速猛的滑动中停止加载图片
		 */
    }

    public static ImageCallBack callBack;

    // 监听图片异步加载
    private static class ImageLoadingListenerImpl extends
            SimpleImageLoadingListener {

        public static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
            if (bitmap != null) {
                ImageView imageView = (ImageView) view;
                boolean isFirstDisplay = !displayedImages.contains(imageUri);
                if (isFirstDisplay) {
                    // 图片的淡入效果
                    if (callBack != null)
                        callBack.bitLoad(bitmap);
                    FadeInBitmapDisplayer.animate(imageView, 0);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
    // 参数传入匿名内部类
//    imageLoader.displayImage(imageUrl, imageView, options, new SimpleImageLoadingListener() {
//        @Override
//        public void onLoadingStarted(String imageUri, View view) {
//            // 可显示进度条
//        }
//
//        @Override
//        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//            String message = null;
//            switch (failReason.getType()) {     // 获取图片失败类型
//                case IO_ERROR:              // 文件I/O错误
//                    message = "Input/Output error";
//                    break;
//                case DECODING_ERROR:        // 解码错误
//                    message = "Image can't be decoded";
//                    break;
//                case NETWORK_DENIED:        // 网络延迟
//                    message = "Downloads are denied";
//                    break;
//                case OUT_OF_MEMORY:         // 内存不足
//                    message = "Out Of Memory error";
//                    break;
//                case UNKNOWN:               // 原因不明
//                    message = "Unknown error";
//                    break;
//            }
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//            // 不显示圆形进度条
//        }
//    });
}
