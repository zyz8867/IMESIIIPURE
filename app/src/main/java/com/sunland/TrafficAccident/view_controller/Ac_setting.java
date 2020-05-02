package com.sunland.TrafficAccident.view_controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunland.TrafficAccident.Db.MyDataBase;
import com.sunland.TrafficAccident.Db.OpenRoomDBHelper;
import com.sunland.TrafficAccident.Db.UrlBean;
import com.sunland.TrafficAccident.Db.UrlBeanDao;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.Rv_Item_decoration;
import com.sunlandgroup.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_setting extends Ac_base implements Frg_add_url.OnUrlDialogClickedListener {

    @BindView(R.id.create_url)
    public TextView tv_create_url;
    @BindView(R.id.url_list)
    public RecyclerView rv_url_list;
    @BindView(R.id.current_url)
    public TextView tv_current_url;


    private MyDataBase myDataBase;
    private MyUrlAdapter myUrlAdapter;
    private List<UrlBean> urlBeanList;

    private UrlBeanDao urlBeanDao;
    private final int SAVE_STATIC_URL = 0;
    private final int CREATE_URL = 1;
    private final int DELETE_URL = 2;
    private final int INITIATE_URLS = 3;
    private ExecutorService executorService;

    private int cur_selected_pos = 0;//当前选中的url对应列表中的位置

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CREATE_URL:
                    myUrlAdapter.notifyDataSetChanged();
                    break;
                case DELETE_URL:
                    myUrlAdapter.notifyDataSetChanged();
                    break;
                case INITIATE_URLS:
                    initRv();
                    break;
                case SAVE_STATIC_URL:
                    loadAllUrlBean();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_setting);
        //showLightStatusBar();
        showToolBar(true);
        setToolBarTitle("设置");
        tv_option_text.setVisibility(View.VISIBLE);
        tv_option_text.setText("保存");
        myDataBase = OpenRoomDBHelper.createDBHelper(this).getDb();
        urlBeanList = new ArrayList<>();
        executorService = Executors.newCachedThreadPool();
        saveUrl();
        initView();
    }

    private void saveUrl() {
        urlBeanDao = myDataBase.getUrlBeanDao();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                UrlBean urlBean = new UrlBean();
                urlBean.ip = Global.ip;
                urlBean.port = Global.port;
                urlBean.timeStamp = 0;
                urlBeanDao.insert(urlBean);
                handler.sendEmptyMessage(SAVE_STATIC_URL);
            }
        });
    }

    private void initView() {
        SharedPreferences sp = getSharedPreferences("cur_url", MODE_PRIVATE);
        String ip = sp.getString("ip", null);
        String port = sp.getString("port", null);
        int pos = sp.getInt("pos", 0);
        if (ip != null) {
            Global.ip = ip;
        }
        if (port != null) {
            Global.port = port;
        }
        cur_selected_pos = pos;

        tv_current_url.setText(Global.ip + ":" + Global.port);
    }

    @Override
    public void onPositiveClicked(final String ip, final String port, final String desc) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                UrlBean urlBean = new UrlBean();
                urlBean.ip = ip;
                urlBean.port = port;
                urlBean.desc = desc;
                urlBean.timeStamp = System.currentTimeMillis();
                urlBeanDao.insert(urlBean);
                urlBeanList.clear();
                urlBeanList.addAll(urlBeanDao.getAllUrls());
                handler.sendEmptyMessage(CREATE_URL);
            }
        });
    }

    @OnClick({R.id.create_url, R.id.option_text})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.create_url:
                DialogFragment create_dialog = new Frg_add_url();
                create_dialog.show(getSupportFragmentManager(), "create_url");
                break;
            case R.id.option_text:
                SharedPreferences sp = getSharedPreferences("cur_url", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("ip", Global.ip);
                editor.putString("port", Global.port);
                editor.putInt("pos", cur_selected_pos);
                editor.apply();
                finish();
                break;
        }
    }

    public void loadAllUrlBean() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<UrlBean> list = urlBeanDao.getAllUrls();
                urlBeanList.clear();
                urlBeanList.addAll(list);
                handler.sendEmptyMessage(INITIATE_URLS);
            }
        });
    }

    private void initRv() {
        myUrlAdapter = new MyUrlAdapter(this, urlBeanList);
        myUrlAdapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void onDeleteClicked(final UrlBean urlBean, final int position) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        myDataBase.getUrlBeanDao().delete(urlBean);
                        urlBeanList.remove(urlBean);
                        Message msg = handler.obtainMessage(DELETE_URL);
                        msg.obj = position;
                        handler.sendMessage(msg);
                    }
                });
            }

            @Override
            public void onUseClicked(UrlBean urlBean, int position) {
                tv_current_url.setText(urlBean.ip + ":" + urlBean.port);
                cur_selected_pos = position;
                Global.ip = urlBean.ip;
                Global.port = urlBean.port;
            }
        });
        rv_url_list.setAdapter(myUrlAdapter);
        rv_url_list.setLayoutManager(new LinearLayoutManager(this));
        rv_url_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    private class MyUrlAdapter extends RecyclerView.Adapter<MyUrlAdapter.MyViewHolder> {

        List<UrlBean> dataSet;
        LayoutInflater layoutInflater;
        OnItemClickedListener onItemClickedListener;
        Context context;
        int pre_clicked_pos;


        public MyUrlAdapter(Context context, List<UrlBean> dataSet) {
            super();
            this.context = context;
            this.dataSet = dataSet;
            layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
            this.onItemClickedListener = onItemClickedListener;
        }

        @NonNull
        @Override
        public MyUrlAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = layoutInflater.inflate(R.layout.rv_url_item_view, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyUrlAdapter.MyViewHolder myViewHolder, final int i) {

            final UrlBean urlBean = dataSet.get(i);
            myViewHolder.tv_url.setText(urlBean.ip + ":" + urlBean.port);
            if (i == cur_selected_pos) {
                myViewHolder.tv_url.setTextColor(context.getResources().getColor(R.color.fourt));
            } else if (i == pre_clicked_pos) {
                myViewHolder.tv_url.setTextColor(context.getResources().getColor(R.color.black));
            }
            myViewHolder.tv_description.setText(urlBean.desc);
            if (onItemClickedListener != null) {
                myViewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickedListener.onDeleteClicked(urlBean, i);
                    }
                });

                myViewHolder.tv_replace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickedListener.onUseClicked(urlBean, i);

                    }
                });
            }

            if (i == 0) {
                myViewHolder.tv_delete.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_url;
            TextView tv_replace;
            TextView tv_delete;
            TextView tv_description;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_url = itemView.findViewById(R.id.url);
                tv_replace = itemView.findViewById(R.id.replace);
                tv_delete = itemView.findViewById(R.id.delete);
                tv_description = itemView.findViewById(R.id.description);
            }
        }
    }

    public interface OnItemClickedListener {
        void onDeleteClicked(UrlBean urlBean, int position);

        void onUseClicked(UrlBean urlBean, int position);
    }

}

