package eu.ict.traveltimeregistration;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CsvFileWriter {

    private static final String TAG = "CsvFileWriter";

    public CsvFileWriter() {
    }

    public static boolean writeConnectEvent(String deviceName, long time) {
        boolean result = false;
        File csvFile = getActiveLogFile();
        if(csvFile != null) {
            try {
                FileOutputStream fos = new FileOutputStream(csvFile, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                StringBuilder sb = new StringBuilder();
                sb.append("CONNECTED,");
                sb.append(deviceName);
                sb.append(',');
                sb.append(new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date(time)));
                sb.append(',');
                sb.append(time);
                sb.append('\n');

                osw.write(sb.toString());
                osw.flush();
                osw.close();
                fos.close();
                result = true;
            }
            catch (IOException e) {
                Log.e(TAG, "Unable to write disconnect event to log", e);
            }
        }
        else {
            Log.e(TAG, "Unable to write connect event to log");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Log.d(TAG, sdf.format(new Date(time)));
        }
        return result;
    }

    public static boolean writeDisconnectEvent(String deviceName, long time) {
        boolean result = false;
        File csvFile = getActiveLogFile();
        if(csvFile != null) {
            try {
                FileOutputStream fos = new FileOutputStream(csvFile, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                StringBuilder sb = new StringBuilder();
                sb.append("DISCONNECTED,");
                sb.append(deviceName);
                sb.append(',');
                sb.append(new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date(time)));
                sb.append(',');
                sb.append(time);
                sb.append('\n');

                osw.write(sb.toString());
                osw.flush();
                osw.close();
                fos.close();
                result = true;
            }
            catch (IOException e) {
                Log.e(TAG, "Unable to write disconnect event to log", e);
            }
        }
        else {
            Log.e(TAG, "Unable to write disconnect event to log");
        }

        return result;
    }

    private static File getActiveLogFile(){
        Calendar cal = Calendar.getInstance();
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        String fileName = "Timeregistration_week_" + week + ".csv";
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
        Log.d(TAG, "Using logfile: "+ f.getPath());
        if(!f.exists())
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Unable to create logfile!", e);
                f = null;
            }
        return f;
    }
}
