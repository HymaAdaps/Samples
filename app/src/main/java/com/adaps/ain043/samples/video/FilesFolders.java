package com.adaps.ain043.samples.video;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesFolders {
    public static File imagePath;
    public static boolean isExist = false;

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String secToDate(long seconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateString = formatter.format(new Date(seconds * 1000L));
        return dateString;
    }

    public static void updateGallery(Context mContext, String path) {
        MediaScannerConnection.scanFile(mContext,
                new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    public static void deleteFileFromGallery(final ContentResolver contentResolver, final String path) {
        String canonicalPath = path;
        final Uri uri = MediaStore.Files.getContentUri("external");
        final int result = contentResolver.delete(uri,
                MediaStore.Files.FileColumns.DATA + "=?", new String[]{canonicalPath});
        if (result == 0) {
            final String absolutePath = path;
            if (!absolutePath.equals(canonicalPath)) {
                contentResolver.delete(uri,
                        MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
            }
        }
    }


    public static File saveImage(String storagePath, Bitmap bitmap) {
//        File dir = new File(StaticUtils.GALLERY_FOLDER_PATH);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
        File file = new File(storagePath, File.separator + getDate() + "drawing.png");
        Log.e("path", file.getAbsolutePath());
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String copyFile(String outPath, String inputPath, String inputFile) {
        File output = new File(outPath, File.separator + inputFile);
        Log.e("tag", output.getAbsolutePath());
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(output);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;
        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        File fdelete = new File(inputPath);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("file Deleted :", fdelete.getAbsolutePath());
            } else {
                Log.e("file not Deleted :", fdelete.getAbsolutePath());
            }
        }
        return output.getAbsolutePath();
    }

    public static File setUpVideoFile(String storagePath) {
        File dir = new File(storagePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(storagePath, File.separator + getDate() + "video.mp4");
        Log.e("path", file.getAbsolutePath());
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File setUpPhotoFile(String storagePath) {
        File file = new File(storagePath, File.separator + getDate() + "camera.png");
        Log.e("path", file.getAbsolutePath());
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static Bitmap decodeFile(File f) {
        Bitmap b = null;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int IMAGE_MAX_SIZE = 1000;
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteFilesOf0size(String directory) {
        //go to my file...
        File filenew = new File(directory);
        if (filenew != null) {
            // so we can list all files
            File[] filenames = filenew.listFiles();
            // loop through each file and cancel
            if (filenames != null && filenames.length > 0) {
                for (File tmpf : filenames) {
                    int file_size = Integer.parseInt(String.valueOf(tmpf.length() / 1024));
                    if (file_size == 0) {
                        tmpf.delete();
                    } else {
                        //do nothing...
                    }
                }
            }
        }
    }

    public static void deleteDirectory(String directory) {
        File path = new File(directory);
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i].toString());
                    } else {
                        files[i].delete();
                    }
                }
            }
        }
    }


}