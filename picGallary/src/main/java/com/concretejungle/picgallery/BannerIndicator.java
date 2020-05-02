package com.concretejungle.picgallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created by PeitaoYe on 2018/3/28.
 */

public class BannerIndicator extends LinearLayout {

    Paint paint;
    private MovingView moving;
    private int currentPosition=0;
    private float offset=0;
    private int item_nums;

    public void setItem_nums(int item_nums) {
        this.item_nums = item_nums;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Paint getPaint() {
        return paint;
    }

    public int getItem_nums() {
        return item_nums;
    }

    public int getRadius() {
        return radius;
    }

    int padding=8;
    int radius=5;
    private int gap=(padding+radius)*2;
    private Context context;
    public BannerIndicator(Context context){
        this(context,null);
    }
    public BannerIndicator(Context context, AttributeSet attributeSet){
        this(context,attributeSet,0);
    }

    public BannerIndicator(Context context, AttributeSet attributeSet, int defStyleaAttr){
        super(context,attributeSet,defStyleaAttr);
        this.context=context;
        init();
    }


    private void init(){
        //setOrientation(LinearLayout.HORIZONTAL);
        setWillNotDraw(false);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);

        moving=new MovingView(context);
        addView(moving);
    }

    public void setMovingDotColor(int color){
        moving.setDotColor(color);
    }

    public void setDotsColor(int color){
        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((radius+padding)*2*item_nums,(radius+padding)*2);

    }

    public void setCurrentPosition(int position,float offset){
        this.currentPosition=position;
        this.offset=offset;
        requestLayout();
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int unit_distance=2*(padding+radius);
        moving.layout((int)(unit_distance*(currentPosition+offset)),padding,(int)(unit_distance*(currentPosition+offset+1)),unit_distance);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int unit_distance=radius+padding;
        for(int i=0;i<item_nums;i++){
            canvas.drawCircle((unit_distance)*(i*2+1),unit_distance,radius,paint);
        }
    }

    //the moving indicator which shows position of the page.
    private class MovingView extends View {

        Paint paint;

        public MovingView(Context context) {
            this(context,null);
        }

        public MovingView(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs,0);
        }

        public MovingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        void init(){
            paint=new Paint();
            paint.setColor(Color.CYAN);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        private void setDotColor(int color){
            paint.setColor(color);
        }
        @Override

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(radius*2,radius*2);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(padding+radius,radius,radius,paint);
        }
    }
}