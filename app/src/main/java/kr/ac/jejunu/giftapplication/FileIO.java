/**
 * Author Aerain
 * SSLAB
 * Jeju National University
 */

package kr.ac.jejunu.giftapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIO {
    public static void saveImage(ImageView view, String fileName, Context context) {
        Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(b);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}