package com.adaps.ain043.samples.miracast.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class TinyDB {
    public static String lastImagePath = "";
    String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    Context mContext;
    File mFolder = null;
    SharedPreferences preferences;

    public TinyDB(Context appContext) {
        this.mContext = appContext;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
    }

    public Bitmap getImage(String path) {
        Bitmap theGottenBitmap = null;
        try {
            theGottenBitmap = BitmapFactory.decodeFile(path);
        } catch (Exception e) {
        }
        return theGottenBitmap;
    }

    public String getSavedImagePath() {
        return lastImagePath;
    }

    public String putImagePNG(String theFolder, String theImageName, Bitmap theBitmap) {
        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder;
        String mFullPath = setupFolderPath(theImageName);
        saveBitmapPNG(mFullPath, theBitmap);
        lastImagePath = mFullPath;
        return mFullPath;
    }

    public Boolean putImagePNGwithfullPath(String fullPath, Bitmap theBitmap) {
        return Boolean.valueOf(saveBitmapPNG(fullPath, theBitmap));
    }

    private String setupFolderPath(String imageName) {
        this.mFolder = new File(Environment.getExternalStorageDirectory(), this.DEFAULT_APP_IMAGEDATA_DIRECTORY);
        if (!(this.mFolder.exists() || this.mFolder.mkdirs())) {
            Log.e("While creatingsave path", "Default Save Path Creation Error");
        }
        return this.mFolder.getPath() + '/' + imageName;
    }

    private boolean saveBitmapPNG(String strFileName, Bitmap bitmap) {
        Exception e;
        boolean bSuccess3;
        if (strFileName == null || bitmap == null) {
            return false;
        }
        boolean bSuccess1 = false;
        File saveFile = new File(strFileName);
        if (saveFile.exists() && !saveFile.delete()) {
            return false;
        }
        try {
            bSuccess1 = saveFile.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        OutputStream out = null;
        boolean bSuccess2;
        try {
            OutputStream out2 = new FileOutputStream(saveFile);
            try {
                bSuccess2 = bitmap.compress(CompressFormat.PNG, 100, out2);
                out = out2;
            } catch (Exception e2) {
                e = e2;
                out = out2;
                e.printStackTrace();
                bSuccess2 = false;
                if (out != null) {
                    bSuccess3 = false;
                } else {
                    try {
                        out.flush();
                        out.close();
                        bSuccess3 = true;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        bSuccess3 = false;
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e32) {
                                e32.printStackTrace();
                            }
                        }
                    } catch (Throwable th) {
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e322) {
                                e322.printStackTrace();
                            }
                        }
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e3222) {
                        e3222.printStackTrace();
                    }
                }
                return !bSuccess1 ? false : false;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            bSuccess2 = false;
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                    bSuccess3 = true;
                } else {
                    bSuccess3 = false;
                }
                if (out != null) {
                    out.close();
                }
                if (!bSuccess1) {
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        try {
            if (out != null) {
                out.flush();
                out.close();
                bSuccess3 = true;
            } else {
                bSuccess3 = false;
            }
            if (out != null) {
                out.close();
            }
            if (!bSuccess1 && bSuccess2 && bSuccess3) {
                return true;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public int getInt(String key) {
        return this.preferences.getInt(key, 0);
    }

    public long getLong(String key) {
        return this.preferences.getLong(key, 0);
    }

    public String getString(String key) {
        return this.preferences.getString(key, "");
    }

    public double getDouble(String key) {
        try {
            return Double.parseDouble(getString(key));
        } catch (NumberFormatException e) {
            return 0.0d;
        }
    }

    public void putInt(String key, int value) {
        Editor editor = this.preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        Editor editor = this.preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putDouble(String key, double value) {
        putString(key, String.valueOf(value));
    }

    public void putString(String key, String value) {
        Editor editor = this.preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putList(String key, ArrayList<String> marray) {
        Editor editor = this.preferences.edit();
        editor.putString(key, TextUtils.join("‚‗‚", (String[]) marray.toArray(new String[marray.size()])));
        editor.apply();
    }

    public ArrayList<String> getList(String key) {
        return new ArrayList(Arrays.asList(TextUtils.split(this.preferences.getString(key, ""), "‚‗‚")));
    }

    public void putListInt(String key, ArrayList<Integer> marray, Context context) {
        Editor editor = this.preferences.edit();
        editor.putString(key, TextUtils.join("‚‗‚", (Integer[]) marray.toArray(new Integer[marray.size()])));
        editor.apply();
    }

    public ArrayList<Integer> getListInt(String key, Context context) {
        ArrayList<String> gottenlist = new ArrayList(Arrays.asList(TextUtils.split(this.preferences.getString(key, ""), "‚‗‚")));
        ArrayList<Integer> gottenlist2 = new ArrayList();
        for (int i = 0; i < gottenlist.size(); i++) {
            gottenlist2.add(Integer.valueOf(Integer.parseInt((String) gottenlist.get(i))));
        }
        return gottenlist2;
    }

    public void putListBoolean(String key, ArrayList<Boolean> marray) {
        ArrayList<String> origList = new ArrayList();
        Iterator it = marray.iterator();
        while (it.hasNext()) {
            if (((Boolean) it.next()).booleanValue()) {
                origList.add("true");
            } else {
                origList.add("false");
            }
        }
        putList(key, origList);
    }

    public ArrayList<Boolean> getListBoolean(String key) {
        ArrayList<String> origList = getList(key);
        ArrayList<Boolean> mBools = new ArrayList();
        Iterator it = origList.iterator();
        while (it.hasNext()) {
            if (((String) it.next()).equals("true")) {
                mBools.add(Boolean.valueOf(true));
            } else {
                mBools.add(Boolean.valueOf(false));
            }
        }
        return mBools;
    }

    public void putBoolean(String key, boolean value) {
        Editor editor = this.preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return this.preferences.getBoolean(key, false);
    }

    public void putFloat(String key, float value) {
        Editor editor = this.preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key) {
        return this.preferences.getFloat(key, 0.0f);
    }

    public void remove(String key) {
        Editor editor = this.preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public Boolean deleteImage(String path) {
        return Boolean.valueOf(new File(path).delete());
    }

    public void clear() {
        Editor editor = this.preferences.edit();
        editor.clear();
        editor.apply();
    }

    public Map<String, ?> getAll() {
        return this.preferences.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
