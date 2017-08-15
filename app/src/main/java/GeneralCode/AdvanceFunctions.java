package GeneralCode;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Objects;

public class AdvanceFunctions {
    /*Context mContext;

    public AdvanceFunctions(Context mContext) {
        this.mContext = mContext;
    }*/

    public static <T extends Object> T getResource(Context context, String resourceType, String resourceName) {
        int id;
        if (Objects.equals(resourceType.toLowerCase(), "string") || Objects.equals(resourceType.toLowerCase(), "strings")) {
            id = context.getResources().getIdentifier(resourceName.toLowerCase(), resourceType.toLowerCase(), context.getPackageName());
            return (T) context.getResources().getString(id);
        }
        return null;
    }

    public static void shortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    protected abstract static class bgRun {

        protected void startImportant() {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        runInBackground();
                    } finally {
                        afterRun();
                    }

                }
            };
            Thread t = new Thread(r);
            t.setPriority(Thread.MAX_PRIORITY);
            t.setDaemon(true);
            t.start();
        }

        protected void start() {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        runInBackground();
                    } finally {
                        afterRun();
                    }
                }
            };
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.start();
        }

        abstract void runInBackground();

        abstract void afterRun();
    }

    protected static abstract class loader {
        private Context mContext;
        private LoaderManager lm;
        private Bundle args = null;
        private LoaderManager.LoaderCallbacks<Object> loader = new LoaderManager.LoaderCallbacks<Object>() {

            @Override
            public Loader<Object> onCreateLoader(int i, Bundle bundle) {
                return new AsyncTaskLoader<Object>(mContext) {
                    @Override
                    public Object loadInBackground() {
                        onStart();
                        return 1;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<Object> loader, Object o) {
                onFinish();
            }

            @Override
            public void onLoaderReset(Loader<Object> loader) {
                onReset();
            }
        };

        public loader(Context mContext) {
            this.mContext = mContext;
            lm = ((Activity) mContext).getLoaderManager();
        }

        protected abstract void onStart();

        protected abstract void onFinish();

        protected abstract void onReset();

        protected Bundle setArguments(Bundle args) {
            this.args = args;
            return args;
        }

        protected void start(int loader_id) {
            lm.restartLoader(loader_id, args, loader).forceLoad();
        }
    }

}
