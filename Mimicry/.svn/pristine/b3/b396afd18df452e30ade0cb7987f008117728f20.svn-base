package com.c.local.mimicry.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.c.local.mimicry.img.UrlImageSave;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class ImageSave {
    private Context context;
    public  static String SAVE_DIRECTORY = "/LOCALCOLOR/PHOTO/";
    
    public ImageSave(Context context) {
        this.context = context;
    }
    public boolean registration() {
    	DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(context);
    	//DatabasePhotoSetOpenHelper databasePhotoSetOpenHelper = new DatabasePhotoSetOpenHelper(context);
        SQLiteDatabase database = null;
        InputStream photoInputStream = null;
        Cursor cursor = null;
        try {
        	
        	
        	database = databaseOpenHelper.getReadableDatabase();
        	cursor = database.query("PHOTO_FEED", null, null, null, null, null, null);
        	cursor.moveToFirst();
        	int count = cursor.getCount();
        	
        	
        	database = databaseOpenHelper.getWritableDatabase();
            database.beginTransaction();
            databaseOpenHelper.onUpgrade(database, 1, 1);
            
            //SDカードのLOCALCOLORフォルダを削除
            File root = Environment.getExternalStorageDirectory();
            File folder = new File(root + "/LOCALCOLOR/PHOTO");
            
            clean(folder);
            
            for (int j = 1; j < 21; j++) {
            	mkdir(folder.toString() + "/" + j);
            }
            
            
            UrlImageSave urlImageSave = null; 
            
            for (int i = 0; i < count; i++) {
            	
            	String id =  cursor.getString(cursor.getColumnIndex("_id"));
                String setID =  cursor.getString(cursor.getColumnIndex("SETID"));
                String img_m =  cursor.getString(cursor.getColumnIndex("IMG_M"));
       
                //SDカードへ画像を登録
                urlImageSave = new UrlImageSave(context);
                urlImageSave.setImgdirectory(setID);
                urlImageSave.setImgFileName(id);
                urlImageSave.setImageUrl(img_m);
                
                cursor.moveToNext();
	            
            }            
            
            
            
  
            return true;
        } catch (Exception exception) {
            Log.e(exception.getClass().getName(), exception.getMessage(), exception);
            
            return false;
        } finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
            if (photoInputStream != null) {
                try {
                	photoInputStream.close();
                } catch (IOException ioException) {
                }
            }
        }
    }
    /**
     * ファイル/ディレクトリを削除する。
     * @param root 削除対象
     */
    public static final void clean( File root ) {
        if ( root == null || !root.exists() ) { return; }
        if ( root.isFile() ) {
            // ファイル削除
            if ( root.exists() && !root.delete() ) {
                root.deleteOnExit();
            }
        } else {
            // ディレクトリの場合、再帰する
            File[] list = root.listFiles();
            for ( int i = 0 ; i < list.length ; i++ ) {
                clean( list[i] );
            }
            if ( root.exists() && !root.delete() ) {
                root.deleteOnExit();
            }
        }
    }
    /**
     * ファイル/ディレクトリを削除する。
     * @param root 削除対象
     */
    public boolean mkdir(String path){
		File file = new File(path);
		return file.mkdirs();
	}
}
