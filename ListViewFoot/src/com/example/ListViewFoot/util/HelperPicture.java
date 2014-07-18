package com.example.ListViewFoot.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;
import java.net.URL;

public class HelperPicture {
    public static Bitmap getAssertImage(Context context, String name) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
           try {
            BitmapFactory.decodeStream(context.getAssets().open(name), null, opts);

        } catch (IOException e) {
            e.printStackTrace();
        }
        int imageH = opts.outHeight;
        int imageW = opts.outWidth;
        int scale = 1;
        int scaleX = imageW / Resolution.getInstance().getScreenWidth(), scaleY = imageH / Resolution.getInstance().getScreenHeight();
        if (scaleX >= scaleY & scaleY >= 1) {
            scale = scaleX;
        }
        if (scaleY >= scaleX & scaleX >= 1) {
            scale = scaleY;
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        opts.inTempStorage = new byte[16 * 1024];
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;

        InputStream is = null;
        try {
            is = context.getAssets().open(name);
            Bitmap bmp;
            bmp = BitmapFactory.decodeStream(is, null, opts);

            return bmp;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.gc();
            }
        }

        return null;


    }

    public static Bitmap getBtimap(File f) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        //InputStream fIn = new FileInputStream(f.getPath());
        BitmapFactory.decodeFile(f.getPath(), opts);
        int imageH = opts.outHeight;
        int imageW = opts.outWidth;
        int scale = 1;
        int scaleX = imageW / Resolution.getInstance().getScreenWidth(), scaleY = imageH / Resolution.getInstance().getScreenHeight();
        if (scaleX >= scaleY & scaleY >= 1) {
            scale = scaleX;
        }
        if (scaleY >= scaleX & scaleX >= 1) {
            scale = scaleY;
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        opts.inTempStorage = new byte[16 * 1024];
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;

        FileInputStream is = null;
        try {
            is = new FileInputStream(f.getPath());
            Bitmap bmp;
            bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);

            return bmp;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.gc();
            }
        }
        return null;


    }

    public static Bitmap getBtimap(URL url) {
        //校验文件完整性
        InputStream is = null;
        try {
            is = url.openStream();
            byte[] bytes = getBytes(is);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            int imageH = opts.outHeight;
            int imageW = opts.outWidth;
            int scale = 0;
            int scaleX = imageW / Resolution.getInstance().getScreenWidth(), scaleY = imageH / Resolution.getInstance().getScreenHeight();
            if (scaleX >= scaleY & scaleY >= 1) {
                scale = scaleX;
            }
            if (scaleY >= scaleX & scaleX >= 1) {
                scale = scaleY;
            }
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = scale;

            Bitmap decodeBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);


            return decodeBitmap;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;


    }

    public static Bitmap getBtimap(InputStream is) {
        //校验文件完整性
        Bitmap decodeBitmap = null;
        try {

            byte[] bytes = getBytes(is);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            int imageH = opts.outHeight;
            int imageW = opts.outWidth;
            int scale = 0;
            int scaleX = imageW / Resolution.getInstance().getScreenWidth(), scaleY = imageH / Resolution.getInstance().getScreenHeight();
            if (scaleX >= scaleY & scaleY >= 1) {
                scale = scaleX;
            }
            if (scaleY >= scaleX & scaleX >= 1) {
                scale = scaleY;
            }
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = scale;

            decodeBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);


            return decodeBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


    }

    public static Bitmap getBtimap(String path) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        int imageH = opts.outHeight;
        int imageW = opts.outWidth;
        int scale = 1;
        int scaleX = imageW / Resolution.getInstance().getScreenWidth(), scaleY = imageH / Resolution.getInstance().getScreenHeight();
        if (scaleX >= scaleY & scaleY >= 1) {
            scale = scaleX;
        }
        if (scaleY >= scaleX & scaleX >= 1) {
            scale = scaleY;
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        opts.inTempStorage = new byte[16 * 1024];
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;

        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
            Bitmap bmp;
            bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);

            return bmp;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.gc();
            }
        }


        return null;


    }

    private static byte[] getBytes(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[2048];
        int len = 0;
        try {
            while ((len = is.read(b, 0, 2048)) != -1) {
                baos.write(b, 0, len);
                baos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static Bitmap BytesBimap(Bitmap bmpDefaultPic) {
        // 获得图片的宽高
        if (bmpDefaultPic == null)
            return null;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmpDefaultPic, 130, 130, true);
        bmpDefaultPic.recycle();
        return thumbBmp;

    }

    public static Bitmap BytesBimap(String path) {
        Bitmap bmpDefaultPic = HelperPicture.getBtimap(path);
        // 获得图片的宽高
        if (bmpDefaultPic == null)
            return null;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmpDefaultPic, 130, 130, true);
        bmpDefaultPic.recycle();
        return thumbBmp;
    }

    public static Bitmap BytesBimap(String path, int sizePic) {

        Bitmap bmpDefaultPic = HelperPicture.getBtimap(path);
        // 获得图片的宽高
        if (bmpDefaultPic == null)
            return null;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmpDefaultPic, 130, 130, true);
        bmpDefaultPic.recycle();
        return thumbBmp;


    }

}
