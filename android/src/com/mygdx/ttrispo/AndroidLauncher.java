package com.mygdx.ttrispo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.ttrispo.com.mygdx.ttrispo.camara.CamaraHelper;
import com.mygdx.ttrispo.com.mygdx.ttrispo.camara.InterfazCamara;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AndroidLauncher extends AndroidApplication {
	private InterfazCamara interfazCamara;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		interfazCamara=new CamaraHelper(this);
		initialize(new MyGdxGame(interfazCamara), config);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				//h=0;
				File f = new File(Environment.getExternalStorageDirectory().toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpeg")) {
						f = temp;
						File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpeg");
						break;
					}
				}
				try {
					Bitmap bitmap;
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = getCropBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions));
					bitmap = getCircularBitmap(bitmap);

					//insertar url e imagen en firestore
					interfazCamara.setPath(f.getAbsolutePath());

					ExifInterface ei = new ExifInterface(f.getAbsolutePath());
					int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
							ExifInterface.ORIENTATION_UNDEFINED);
					Bitmap rotatedBitmap = null;
					switch(orientation) {
						case ExifInterface.ORIENTATION_ROTATE_90:
							rotatedBitmap = rotateImage(bitmap, 90);
							break;
						case ExifInterface.ORIENTATION_ROTATE_180:
							rotatedBitmap = rotateImage(bitmap, 180);
							break;
						case ExifInterface.ORIENTATION_ROTATE_270:
							rotatedBitmap = rotateImage(bitmap, 270);
							break;
						case ExifInterface.ORIENTATION_NORMAL:
						default:
							rotatedBitmap = bitmap;
					}

					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
					byte[] byteArray = stream.toByteArray();
					interfazCamara.setDatos(byteArray);

					interfazCamara.setResultado1(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == 2) {
				Uri selectedImage = data.getData();
				String[] filePath = {MediaStore.Images.Media.DATA};
				Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
				c.moveToFirst();

				int columnIndex = c.getColumnIndex(filePath[0]);
				String picturePath = c.getString(columnIndex);
				c.close();

				Bitmap thumbnail = getCropBitmap(BitmapFactory.decodeFile(picturePath));
				Log.w("path:", picturePath + "\n\n\n\n\n");
				thumbnail = getCircularBitmap(thumbnail);

				//insertar url e imagen para luego subirlo como atributo String al usuario en firebase
				interfazCamara.setPath(picturePath);

				//Bitmap bmp = getIntent().getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();
                interfazCamara.setDatos(byteArray);

				interfazCamara.setResultado1(true);
			}
		}
	}
	//rotar imagen bien
	public Bitmap rotateImage(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
				matrix, true);
	}
	//imagenes redondas
    public Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        float r = 0;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    //imagenes cuadradas y centradas
    public Bitmap getCropBitmap(Bitmap srcBmp){
        Bitmap dstBmp;
	    if (srcBmp.getWidth() >= srcBmp.getHeight()){

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        }else{

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
	    return dstBmp;
    }
}
