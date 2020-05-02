package com.sunland.TrafficAccident.view_controller;
//
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sunland.TrafficAccident.R;

/**
 * Created By YPT on 2019/2/20/020
 * project name: parkeSystem
 */
public class Frg_dialog extends DialogFragment {

    private Context context;

    private int id = -1;
    private String title;
    private String message;
    private String pos_btn_msg;
    private String neg_btn_msg;

    private AlertDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(pos_btn_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Callback) context).onPositiveClicked(id, Frg_dialog.this.getTag());
                    }
                })
                .setNegativeButton(neg_btn_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Callback) context).onNegativeClicked(id, Frg_dialog.this.getTag());
                    }
                });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPos_btn_msg(String pos_btn_msg) {
        this.pos_btn_msg = pos_btn_msg;
    }

    public void setNeg_btn_msg(String neg_btn_msg) {
        this.neg_btn_msg = neg_btn_msg;
    }

    public interface Callback {
        void onNegativeClicked(int id, String tag);

        void onPositiveClicked(int id, String tag);
    }

    @Override
    public void onResume() {
        super.onResume();
        Resources res = context.getResources();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(res.getColor(R.color.color_primary_blue));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(res.getColor(R.color.color_primary_blue));
    }
}
