package com.megvii.camera;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;

/**
 * @Project CameraTestBed
 * @Package com.megvii.camera
 * @Author zhuangzaiku
 * @Date 2019/2/1
 */
abstract public class DisplayOrientationDetector {
    private final OrientationEventListener mOrientationEventListener;

    static final SparseIntArray DISPLAY_ORIENTATION = new SparseIntArray();

    static {
        DISPLAY_ORIENTATION.put(Surface.ROTATION_0, 0);
        DISPLAY_ORIENTATION.put(Surface.ROTATION_90, 90);
        DISPLAY_ORIENTATION.put(Surface.ROTATION_180, 180);
        DISPLAY_ORIENTATION.put(Surface.ROTATION_270, 270);
    }

    Display mDisplay;

    private int mLastKnownDisplayOrientation = 0;

    public DisplayOrientationDetector(Context context) {
        mOrientationEventListener = new OrientationEventListener(context) {
            @Override
            public void onOrientationChanged(int orientation) {
                if(orientation == OrientationEventListener.ORIENTATION_UNKNOWN || mDisplay == null) {
                    return;
                }
                final int rotation = mDisplay.getRotation();
                if(mLastKnownDisplayOrientation != rotation) {
                    mLastKnownDisplayOrientation = rotation;
                    onDisplayOrientationChanged(DISPLAY_ORIENTATION.get(rotation));
                }
            }
        };
    }

    public void enable(Display display) {
        mDisplay = display;
        mOrientationEventListener.enable();
        dispatchOnDisplayOrientationChanged(DISPLAY_ORIENTATION.get(display.getRotation()));
    }

    public void disable() {
        mOrientationEventListener.disable();
        mDisplay = null;
    }

    public int getLastKnownDisplayOrientation() {
        return mLastKnownDisplayOrientation;
    }

    void dispatchOnDisplayOrientationChanged(int displayOrientation) {
        mLastKnownDisplayOrientation = displayOrientation;
        onDisplayOrientationChanged(displayOrientation);
    }

    /**
     *
     * @param displayOrientation One of 0, 90, 180, and 270.
     */
    public abstract void onDisplayOrientationChanged(int displayOrientation);
}
