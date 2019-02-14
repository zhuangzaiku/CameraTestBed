package com.megvii.camera;

/**
 * @Project CameraTestBed
 * @Package com.megvii.camera
 * @Author zhuangzaiku
 * @Date 2019/1/31
 */
public class Size implements Comparable<Size> {

    private final int mWidth;
    private final int mHeight;

    /**
     *  Create a new immutable Size instance.
     * @param width The width in pixels
     * @param height The height in pixels
     */
    public Size(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    @Override
    public int compareTo(Size o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Size{" +
                "mWidth=" + mWidth +
                ", mHeight=" + mHeight +
                '}';
    }

    @Override
    public int hashCode() {
        return mHeight ^ ((mWidth << (Integer.SIZE / 2)) | (mWidth >>> (Integer.SIZE / 2)));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(this == obj) {
            return true;
        }
        if(obj instanceof Size) {
            Size size = (Size)obj;
            return mWidth == size.mWidth && mHeight == size.mHeight;
        }
        return false;
    }
}
