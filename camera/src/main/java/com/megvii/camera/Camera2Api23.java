package com.megvii.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.util.Size;

/**
 * @Project CameraTestBed
 * @Package com.megvii.camera
 * @Author zhuangzaiku
 * @Date 2019/2/1
 */
public class Camera2Api23 extends Camera2 {
    public Camera2Api23(Callback callback, PreviewImpl preview, Context context) {
        super(callback, preview, context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void collectPictureSizes(SizeMap sizes, StreamConfigurationMap map) {
        Size[] outputSize = map.getHighResolutionOutputSizes(ImageFormat.JPEG);
        if(outputSize != null) {
            for(Size size : outputSize) {
                sizes.add(new com.megvii.camera.Size(size.getWidth(), size.getHeight()));
            }
        }
        if(sizes.isEmpty()) {
            super.collectPictureSizes(sizes, map);
        }
    }
}
