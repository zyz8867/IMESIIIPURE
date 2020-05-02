package com.sunland.TrafficAccident.adapter;


import android.content.Context;
import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sunland.TrafficAccident.R;

import java.util.List;

public class im_adapter extends RecyclerView.Adapter<im_adapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Bitmap> bitmaps;//压缩图片

    private OnItemClickedListener onItemClickedListener;
    private OnItemDeleteClickedListener onItemDeleteClickedListener;
    private boolean isDeleting; //当前是否处于删除状态

    private Animation animation;
    private int default_size;
//
    private boolean[] hasLoadedPics;//是否已加载了图片
    private boolean[] hasLoadedEle;//是否加载了基本控件
    private boolean[] hasStartAnim;//是否开始动画
    private boolean[] hasShowLoading;//在展示模式下，item是否已展示过loading界面

//    private boolean hasLoadedPics;//是否已加载了图片
//    private boolean hasLoadedEle;//是否加载了基本控件
//    private boolean hasStartAnim;//是否开始动画
//    private boolean hasShowLoading;//在展示模式下，item是否已展示过loading界面

    //private int pic_status;  //每个相片槽下照片当前处于的状态
    private int[] pic_status;  //每个相片槽下照片当前处于的状态
    private int delete_pos = -1;
    public static final int ADD = 0;//可添加图片
    public static final int SHOW = 1;//不可编辑，纯展示

    private static final int pic_init = 0;//起始状态
    private static final int has_pic = 1;//图片槽拥有图片
    private static final int shaking = 2;//图片摇晃

    private int count = 0;
    private int mode;

    public im_adapter(Context context, List<Bitmap> bitmaps) {
        super();
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bitmaps = bitmaps;
//        this.upload_pic = upload_pic;
        animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.pic_delete_ready_shimmy);
    }

    public void clearStatus() {
        isDeleting = false;
        this.notifyDataSetChanged();
    }

    public void setDefaultSize(int size) {
        //this.default_size = size;
        this.default_size = 1;

        hasShowLoading = new boolean[default_size];
        hasLoadedPics = new boolean[default_size];
        hasLoadedEle = new boolean[default_size];
        pic_status = new int[default_size];
        hasStartAnim = new boolean[default_size];

        for (int i = 0; i < default_size; i++) {
            hasLoadedPics[i] = false;
            hasLoadedEle[i] = false;
            pic_status[i] = pic_init;
            hasStartAnim[i] = false;
            hasShowLoading[i] = false;

        }

    }



    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setOnItemDeleteClickedListener(OnItemDeleteClickedListener onItemDeleteClickedListener) {
        this.onItemDeleteClickedListener = onItemDeleteClickedListener;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public im_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.rv_imgs_item, viewGroup, false);
        return new im_adapter.MyViewHolder(view);
    }

//    public void setRepeal(boolean repeal) {
//        this.repeal = repeal;
//    }

    @Override
    public void onBindViewHolder(@NonNull final im_adapter.MyViewHolder myViewHolder, final int i) {
        //（01：发动机号照片，02：车架号照片，03：正侧面照片，04：后侧面照片）
        if (mode == ADD) {
            switch (pic_status[1]) {
                case pic_init:
                    myViewHolder.iv_img.setScaleType(ImageView.ScaleType.CENTER);
                    myViewHolder.iv_img.setImageResource(R.drawable.ic_plus);
                    myViewHolder.pb_img_loading.setVisibility(View.GONE);
                    myViewHolder.iv_delete.setVisibility(View.GONE);
                    break;
                case has_pic:
                    myViewHolder.iv_img.setScaleType(ImageView.ScaleType.CENTER);
                    myViewHolder.pb_img_loading.setVisibility(View.GONE);
                    myViewHolder.iv_delete.setVisibility(View.GONE);
                    break;
                case shaking:
                    myViewHolder.iv_img.setScaleType(ImageView.ScaleType.CENTER);
                    myViewHolder.pb_img_loading.setVisibility(View.GONE);
                    myViewHolder.iv_delete.setVisibility(View.VISIBLE);
                    break;
            }

            switch (0) {
                case 0:
                    myViewHolder.tv_des.setText("发动机号照片");
                    break;

            }

            Bitmap bitmap = bitmaps.get(0);
            //当有照片时，加载照片
            if (bitmap != null && !hasLoadedPics[0]) {
                myViewHolder.iv_img.setImageBitmap(bitmap);
//            GlideApp.with(context).asBitmap().load(bitmap).diskCacheStrategy(DiskCacheStrategy.NONE).into(myViewHolder.iv_img);
                delete_pos = -1;
                isDeleting = false;
                hasLoadedPics[0] = true;
                hasStartAnim[0]=false;
            }

            //当处于正要删除状态，且有图片，则展示动画
            if (!hasStartAnim[0] && isDeleting && bitmap != null) {
                myViewHolder.fl_pic_container.startAnimation(animation);
                myViewHolder.iv_delete.setVisibility(View.VISIBLE);
                hasStartAnim[0] = true;
            }

            if (delete_pos != -1 && bitmap != null) {
                bitmap.recycle();
                bitmap = null;
                bitmaps.set(0, null);
//                Bitmap real_pic = upload_pic.get(i);
//                real_pic.recycle();
//                real_pic = null;
                myViewHolder.fl_pic_container.clearAnimation();
                myViewHolder.iv_delete.setVisibility(View.GONE);
                myViewHolder.iv_img.setImageResource(R.drawable.ic_plus);

                //reset flag
                hasStartAnim[0] = false;
                delete_pos = -1;
                hasLoadedPics[0] = false;
                isDeleting = false;
            }

            final boolean hasPic = bitmap != null;

            myViewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_pos = 0;
                    pic_status[0] = pic_init;
                    hasLoadedPics[0] = false;
                    im_adapter.this.notifyItemChanged(0);
                    if (onItemDeleteClickedListener != null) {
                        onItemDeleteClickedListener.onDelete(0);
                    }
                }
            });

            myViewHolder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean hasPicsLeft = false;
                    for (Integer integer : pic_status) {
                        if (integer == shaking) {
                            hasPicsLeft = true;
                        }
                    }
                    //取消摇晃删除状态
                    if (isDeleting && hasPicsLeft) {
                        isDeleting = false;
                        delete_pos = -1;
                        for (int i = 0; i < 1; i++) {
                            if (pic_status[i] == im_adapter.shaking) {
                                pic_status[i] =im_adapter.has_pic;
                                hasStartAnim[i] = false;

                            }
                        }
                        notifyDataSetChanged();
                        return;
                    }
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(i, hasPic);
//                        if (!hasPic) {
//                            pic_status[i] = has_pic;
//                        }
                    }
                }
            });

            myViewHolder.iv_img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //仅当有照片时才允许长点击
                    if (hasPic) {
                        if (isDeleting) {
                            return false;
                        }
                        isDeleting = true;
                        for (int i = 0; i < 1; i++) {
                            if (pic_status[i] == im_adapter.has_pic) {
                                pic_status[i] = im_adapter.shaking;
                            }
                        }
//                        pic_status[i] = shaking;
                        im_adapter.this.notifyDataSetChanged();
                        return false;
                    }
                    return false;
                }
            });

        } else if (mode == SHOW) {
            switch (0) {
                case 0:
                    myViewHolder.tv_des.setText("发动机号照片");
                    break;

            }
            myViewHolder.iv_delete.setVisibility(View.GONE);

//            if (count < default_size&&!repeal) {
//                count++;
//                return;
//            }

            if (!hasShowLoading[0]) {
                hasShowLoading[0] = true;
                return;
            }
            Bitmap bitmap = bitmaps.get(0);
            if (bitmap != null) {
                myViewHolder.tv_no_pic.setVisibility(View.GONE);
                //GlideApp.with(context).asBitmap().load(bitmap).diskCacheStrategy(DiskCacheStrategy.NONE).into(myViewHolder.iv_img);
                myViewHolder.pb_img_loading.setVisibility(View.GONE);
                myViewHolder.iv_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickedListener != null) {
                            onItemClickedListener.onItemClicked(i, true);
                        }
                    }
                });
            } else {
                //GlideApp.with(context).asBitmap().load(bitmap).diskCacheStrategy(DiskCacheStrategy.NONE).into(myViewHolder.iv_img);
                myViewHolder.pb_img_loading.setVisibility(View.GONE);
                myViewHolder.tv_no_pic.setVisibility(View.VISIBLE);
            }
        }
    }


    public void setPicStatu(int i, boolean hasPic) {
        if (hasPic) {
            pic_status[0] = has_pic;
        }
    }

    @Override
    public int getItemCount() {
        return default_size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        ImageView iv_delete;
        ProgressBar pb_img_loading;
        FrameLayout fl_pic_container;
        TextView tv_des;
        TextView tv_no_pic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.img);
            iv_delete = itemView.findViewById(R.id.delete);
            fl_pic_container = itemView.findViewById(R.id.pic_container);
            pb_img_loading = itemView.findViewById(R.id.img_loading);
            tv_des = itemView.findViewById(R.id.description);
            tv_no_pic = itemView.findViewById(R.id.no_pic);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(int position, boolean hasPic);
    }

    public interface OnItemDeleteClickedListener {
        void onDelete(int position);
    }
}