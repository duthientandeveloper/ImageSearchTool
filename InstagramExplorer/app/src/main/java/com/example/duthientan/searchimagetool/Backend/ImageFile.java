package com.example.duthientan.searchimagetool.Backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.duthientan.searchimagetool.Utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Du Thien Tan on 7/14/2015.
 */
public class ImageFile {
    public static void saveImage(Bitmap bitmap,String url,String key){
        String logo = "";
        String name = "";
        File file = null;
        File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/saved_images");
        if(!myDir.isDirectory()){
            myDir.mkdirs();
        }
        if(url.contains("pbs.twimg.com"))
            logo="Twitter";
        else if (url.contains("staticflickr"))
            logo="Flickr";
        else if(url.contains("drscdn.500px.org"))
            logo="Px";
        else if(url.contains("scontent.cdninstagram.com"))
            logo="Instargram";
        else logo = "Google";
        do {
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            name = key+"_"+logo + n + ".jpg";
            file =new File (myDir, name);
        }while (file.exists());
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Bitmap> getBitmap(String social){
        List<Bitmap> bitmap= new ArrayList<Bitmap>();
        File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/saved_images");
        List<File> files = getListFiles(myDir,social);
        BitmapFactory.Options options = new BitmapFactory.Options();
        for(File file :files) {
            bitmap.add(BitmapFactory.decodeFile(file.getAbsolutePath(), options));
        }
        return  bitmap;
    }

    public static List<File> getListFiles(File parentDir,String social) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if(file.getName().contains(social)){
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    public static List<Bitmap> getBitmapbyKey(String key){
        ArrayList<File> inFiles = new ArrayList<File>();
        List<Bitmap> bitmap= new ArrayList<Bitmap>();
        File[] files = new File(Environment.getExternalStorageDirectory().toString() + "/saved_images").listFiles();
        for (File file : files) {
            if(file.getName().contains(key)){
                inFiles.add(file);
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        for(File file :files) {
            bitmap.add(BitmapFactory.decodeFile(file.getAbsolutePath(), options));
        }
        return bitmap;
    }
}
