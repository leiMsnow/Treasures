package com.droid.treasures.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dangdang on 15/6/15.
 */
public class ImageUtils {
    /**
     * 创建水印效果
     *
     * @param context
     * @param src
     * @param res
     * @return
     */
    public static Bitmap createWatermarkBitmap(Context context, Bitmap src, int res) {
        Bitmap watermark = BitmapFactory.decodeResource(context.getResources(), res);
        float w = src.getWidth();
        float h = src.getHeight();
        float ww = watermark.getWidth();
        float wh = watermark.getHeight();

        // 缩放图片的尺寸
        float scaleWidth = w / 2 / ww;
        float scaleHeight = h / 2 / wh;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 产生缩放后的Bitmap对象
        Bitmap resizeBitmap = Bitmap.createBitmap(watermark, 0, 0, (int) ww, (int) wh, matrix, false);

        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap((int) w, (int) h, Bitmap.Config.ARGB_8888);
        // 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        cv.drawBitmap(src, 0, 0, paint);// 在 0，0坐标开始画入src
        cv.drawBitmap(resizeBitmap, 0, 0, null);// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        // resizeBitmap.recycle();
        return newb;
    }

    public static String saveToSD(String filePath) {
        Bitmap bitmap = decodeFile(new File(filePath), 540, 960);
        return saveToSD(bitmap, filePath);
    }

    public static String saveToSD(Bitmap bitmap, String filePath) {
        String localPath = Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + "/temp/";
        return saveToSDCard(localPath, bitmap, filePath);
    }


    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        return baos.toByteArray();
    }


    public static void saveSnapShot(Bitmap mBitmap) {
        mBitmap = decodeSanpShotBytes(Bitmap2Bytes(mBitmap));
        File f = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/temp/", "snapshot.png");

        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(f);
            if (mBitmap != null)
                mBitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String saveToSDCard(String localPath, Bitmap bitmap,
                                       String filePath) {
        String status = Environment.getExternalStorageState();
        String filename = localPath + System.currentTimeMillis()
                + filePath.substring(filePath.lastIndexOf("."));
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(localPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                File imageFile = new File(filename);
                imageFile.createNewFile();
                FileOutputStream fs = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fs);
                fs.flush();
                fs.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }

    public static Bitmap decodeSanpShotBytes(byte[] f) {
        BitmapFactory.Options o2;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inPreferredConfig = Bitmap.Config.RGB_565;
            o.inPurgeable = true;
            o.inInputShareable = true;
            BitmapFactory.decodeByteArray(f, 0, f.length);
            o2 = new BitmapFactory.Options();
            o2.inSampleSize = calculateInSampleSize(o, 480, 800);
            Bitmap bitmap = null;
            try {
                o2.inPreferredConfig = Bitmap.Config.RGB_565;
                o2.inPurgeable = true;
                o2.inInputShareable = true;
                bitmap = BitmapFactory.decodeByteArray(f, 0, f.length, o2);

            } catch (Throwable e) {
                if (e instanceof OutOfMemoryError) {
                    o2.inSampleSize *= 2;
                    bitmap = BitmapFactory.decodeByteArray(f, 0, f.length, o2);
                }
            }
            if (bitmap == null) {
                return null;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap decodeFile(File f, int reqWidth, int reqHeight) {
        BitmapFactory.Options o2;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inPreferredConfig = Bitmap.Config.RGB_565;
            o.inPurgeable = true;
            o.inInputShareable = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            o2 = new BitmapFactory.Options();
            o2.inSampleSize = calculateInSampleSize(o, reqWidth, reqHeight);
            Bitmap bitmap = null;
            try {
                o2.inPreferredConfig = Bitmap.Config.RGB_565;
                o2.inPurgeable = true;
                o2.inInputShareable = true;
                bitmap = BitmapFactory.decodeStream(new FileInputStream(f),
                        null, o2);
            } catch (Throwable e) {
                if (e instanceof OutOfMemoryError) {
                    o2.inSampleSize *= 2;
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(f),
                            null, o2);
                }
            }
            if (bitmap == null) {
                return null;
            }
            int degree = readPictureDegree(f.getAbsolutePath());
            if (degree != 0) {
                bitmap = rotateBitmap(bitmap, degree);
            }
            return bitmap;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            final float totalPixels = width * height;
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        Bitmap createBitmap = null;
        try {
            mtx.postRotate(rotate);
            createBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
        } catch (Throwable e) {
            mtx.setScale(0.5f, 0.5f);
            mtx.postRotate(rotate);
            createBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
        }
        return createBitmap;
    }

    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}
