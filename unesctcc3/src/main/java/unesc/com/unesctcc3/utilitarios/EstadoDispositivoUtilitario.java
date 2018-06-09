package unesc.com.unesctcc3.utilitarios;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class EstadoDispositivoUtilitario {
    private static Activity activity;
    private static EstadoAparelhoMovel estadoAparelhoMovel;

    public static EstadoAparelhoMovel getEstadoAparelhoMovel(Activity activity) {
        if (activity == null) {
            return null;
        }
        EstadoDispositivoUtilitario.activity = activity;
        long availableRamMemorySize = getAvailableRamMemorySize();
        long totalRamMemorySize = getTotalRamMemorySize();
        long availableInternalMemorySize = getAvailableInternalMemorySize();
        long totalInternalMemorySize = getTotalInternalMemorySize();
        long availableExternalMemorySize = getAvailableExternalMemorySize();
        long totalExternalMemorySize = getTotalExternalMemorySize();
        EstadoAparelhoMovel estadoAparelhoMovel = new EstadoAparelhoMovel();
        estadoAparelhoMovel.setAvailableRamMemorySize(availableRamMemorySize);
        estadoAparelhoMovel.setTotalRamMemorySize(totalRamMemorySize);
        estadoAparelhoMovel.setAvailableInternalMemorySize(availableInternalMemorySize);
        estadoAparelhoMovel.setTotalInternalMemorySize(totalInternalMemorySize);
        estadoAparelhoMovel.setAvailableExternalMemorySize(availableExternalMemorySize);
        estadoAparelhoMovel.setTotalExternalMemorySize(totalExternalMemorySize);
        return estadoAparelhoMovel;
    }

    private static long getAvailableRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
        return availableMegs;
    }

    private static long getTotalRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.totalMem / 1048576L;
        return availableMegs;
    }

    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return 0;
        }
    }

    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return 0;
        }
    }


    public static class EstadoAparelhoMovel {
        private long availableRamMemorySize;
        private long totalRamMemorySize;
        private long availableInternalMemorySize;
        private long totalInternalMemorySize;
        private long availableExternalMemorySize;
        private long totalExternalMemorySize;

        public long getAvailableRamMemorySize() {
            return availableRamMemorySize;
        }

        public void setAvailableRamMemorySize(long availableRamMemorySize) {
            this.availableRamMemorySize = availableRamMemorySize;
        }

        public long getTotalRamMemorySize() {
            return totalRamMemorySize;
        }

        public void setTotalRamMemorySize(long totalRamMemorySize) {
            this.totalRamMemorySize = totalRamMemorySize;
        }

        public long getAvailableInternalMemorySize() {
            return availableInternalMemorySize;
        }

        public void setAvailableInternalMemorySize(long availableInternalMemorySize) {
            this.availableInternalMemorySize = availableInternalMemorySize;
        }

        public long getTotalInternalMemorySize() {
            return totalInternalMemorySize;
        }

        public void setTotalInternalMemorySize(long totalInternalMemorySize) {
            this.totalInternalMemorySize = totalInternalMemorySize;
        }

        public long getAvailableExternalMemorySize() {
            return availableExternalMemorySize;
        }

        public void setAvailableExternalMemorySize(long availableExternalMemorySize) {
            this.availableExternalMemorySize = availableExternalMemorySize;
        }

        public long getTotalExternalMemorySize() {
            return totalExternalMemorySize;
        }

        public void setTotalExternalMemorySize(long totalExternalMemorySize) {
            this.totalExternalMemorySize = totalExternalMemorySize;
        }
    }
}
