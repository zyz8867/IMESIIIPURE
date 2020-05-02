package com.concretejungle.picgallery;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyPagerAdapter extends PagerAdapter {

    private ExecutorService executorService;
    private List<View> dataSet;
    private Dialog dialog;
    private List<File> files;
    private List<Bitmap> bitmaps;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            int position = msg.what;
            View view = dataSet.get(position);
            ViewHolder vh = (ViewHolder) view.getTag();
            vh.ziv.setImageBitmap(bitmap);
            vh.ziv.setVisibility(View.VISIBLE);
            vh.pb.setVisibility(View.GONE);
        }
    };

    public MyPagerAdapter(List<View> dataSet, Dialog dialog) {
        this.dataSet = dataSet;
        this.dialog = dialog;
        executorService = Executors.newCachedThreadPool();
        bitmaps = new ArrayList<>();
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = dataSet.get(position);
        if (files != null && files.get(position) != null) {
            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    Bitmap bitmap = BitmapFactory.decodeFile(files.get(position).getAbsolutePath(), options);
                    bitmaps.add(bitmap);
                    Message msg = handler.obtainMessage();
                    msg.obj = bitmap;
                    msg.what = position;
                    handler.sendMessage(msg);
                }
            });
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        bitmaps.get(position).recycle();
    }

    public void recycle() {
        for (Bitmap bitmap : bitmaps) {
            bitmap.recycle();
        }
    }

    class ViewHolder {
        public ZoomImageView ziv;
        public ProgressBar pb;

        public ViewHolder(View view) {
            ziv = view.findViewById(R.id.ziv);
            pb = view.findViewById(R.id.loading);
        }
    }
}
