package com.concretejungle.picgallery;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PicGalleryManager {

    private Dialog dialog;
    private ViewPager viewPager;

    private BannerIndicator indicator;

    private static PicGalleryManager manager;

    private TextView tv_index;

    private List<View> views;

    private MyPagerAdapter mpageAdapter;
    private Context context;

    private LayoutInflater layoutInflater;

    private PicGalleryManager() {

    }

    public static PicGalleryManager getInstance() {
        if (manager == null) {
            synchronized (PicGalleryManager.class) {
                if (manager == null) {
                    manager = new PicGalleryManager();
                }
            }
        }
        return manager;
    }

    private void initWindow(Window window, View view) {
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setContentView(view);
        window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    public void initPicGallery(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        Window window = dialog.getWindow();

        if (window == null) {
            throw new IllegalStateException("window is null");
        }

        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pic_gallery, null);
        initWindow(window, view);

        viewPager = view.findViewById(R.id.vpimg);
        tv_index = view.findViewById(R.id.index);
        indicator = view.findViewById(R.id.indicators);

        views = new ArrayList<>();
        mpageAdapter = new MyPagerAdapter(views, dialog);

        // TODO: 2019/5/24/024 预加载全部view,优化为业务代码可配置 
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mpageAdapter);
        viewPager.setPageMargin(20);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mpageAdapter.recycle();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    public void setCurrentIndex(int index) {
        viewPager.setCurrentItem(index);
    }

    public void addBitmaps(List<Bitmap> pics) {

        for (int i = 0; i < pics.size(); i++) {
            Bitmap bitmap = pics.get(i);
            if (bitmap != null) {
                ZoomImageView zoomImageView = new ZoomImageView(context);
                zoomImageView.setImageBitmap(bitmap);
                views.add(zoomImageView);
            }
        }

        mpageAdapter.notifyDataSetChanged();

        if (views.size() == 1) {
            indicator.setItem_nums(0);
        } else {
            indicator.setItem_nums(views.size());
        }

        indicator.setMovingDotColor(Color.WHITE);
        indicator.setDotsColor(Color.GRAY);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean start = true;

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                indicator.setCurrentPosition(i, v);
                //使用一次该函数来显示index,然后都使用onPageSelected()
                if (start) {
                    tv_index.setText(i + 1 + "/" + views.size());
                    start = false;
                }
            }

            @Override
            public void onPageSelected(int i) {
                tv_index.setText(i + 1 + "/" + views.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

        });

    }

    public void addFiles(List<File> files) {

        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (file != null) {
                View view = layoutInflater.inflate(R.layout.imgloading, null);
                views.add(view);
            }
        }
        mpageAdapter.setFiles(files);

        mpageAdapter.notifyDataSetChanged();

        if (views.size() == 1) {
            indicator.setItem_nums(0);
        } else {
            indicator.setItem_nums(views.size());
        }

        indicator.setMovingDotColor(Color.WHITE);
        indicator.setDotsColor(Color.GRAY);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean start = true;

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                indicator.setCurrentPosition(i, v);
                //使用一次该函数来显示index,然后都使用onPageSelected()
                if (start) {
                    tv_index.setText(i + 1 + "/" + views.size());
                    start = false;
                }
            }

            @Override
            public void onPageSelected(int i) {
                tv_index.setText(i + 1 + "/" + views.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void addFilePathes(List<String> filePathes) {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < filePathes.size(); i++) {
            String path = filePathes.get(i);
            if (path != null) {
                View view = layoutInflater.inflate(R.layout.imgloading, null);
                views.add(view);
                files.add(new File(path));
            } else {
                files.add(null);
            }
        }

        mpageAdapter.setFiles(files);

        mpageAdapter.notifyDataSetChanged();

        if (views.size() == 1) {
            indicator.setItem_nums(0);
        } else {
            indicator.setItem_nums(views.size());
        }

        indicator.setMovingDotColor(Color.WHITE);
        indicator.setDotsColor(Color.GRAY);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean start = true;

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                indicator.setCurrentPosition(i, v);
                //使用一次该函数来显示index,然后都使用onPageSelected()
                if (start) {
                    tv_index.setText(i + 1 + "/" + views.size());
                    start = false;
                }
            }

            @Override
            public void onPageSelected(int i) {
                tv_index.setText(i + 1 + "/" + views.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void showPics() {
        dialog.show();
    }

}
