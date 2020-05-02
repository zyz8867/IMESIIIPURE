package com.sunland.TrafficAccident.view_controller;

/**
 * Created By YPT on 2019/2/20/020
 * project name: parkeSystem
 */
public interface PermissionRequest {
    void reqPermissions(String[] permissions, int requestCode);

    void reqPermission(String permission, int requestCode);
}
