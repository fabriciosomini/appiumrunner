package unesc.com.unesctcc3.utilitarios;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ArquivoUtilitario {

    public static void writeToFile(File file, String data, Context context) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStreamWriter = new FileOutputStream(file);
            outputStreamWriter.write(data.getBytes());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
