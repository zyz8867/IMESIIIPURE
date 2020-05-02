package com.sunland.TrafficAccident.Db;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created By YPT on 2019/3/5/005
 * project name: parkeSystem
 * " [xh] integer primary key autoincrement " + //序号
 * ",[lsh] text" +//流水号 (关联主记录主键  可能有重复)
 * ",[lrsj] datetime" +//录入时间
 * ",[zt] char(1)" +//上传状态
 * ",[yhdm] nvarchar(20)" +//用户代码
 * ",[info] text" +//json格式上传内容
 * ",[path] text" +//文件路径
 * ",[yl1] text" +//预留1
 * ",[yl2] text);";//预留2
 */

@Entity(tableName = "upload")
public class UploadCarInfoBean {

    @PrimaryKey
    @NonNull
    private long lsh;//使用时间戳来表示


    private String qzpzh;//强制凭证单号

    private int zt;//0未上传，1已上传

    @NonNull
    private String yhdm;

    private String json;

    public long getLsh() {
        return lsh;
    }

    public void setLsh(long lsh) {
        this.lsh = lsh;
    }

    public String getQzpzh() {
        return qzpzh;
    }

    public void setQzpzh(String qzpzh) {
        this.qzpzh = qzpzh;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    @NonNull
    public String getYhdm() {
        return yhdm;
    }

    public void setYhdm(@NonNull String yhdm) {
        this.yhdm = yhdm;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
