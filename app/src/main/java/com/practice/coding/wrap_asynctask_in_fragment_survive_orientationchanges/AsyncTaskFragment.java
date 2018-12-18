package com.practice.coding.wrap_asynctask_in_fragment_survive_orientationchanges;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

public class AsyncTaskFragment extends Fragment {

    private static final String TAG = "TAG";

    public interface MyTaskHandler
    {
        void onChangeData(String data);
    }

    private MyTaskHandler taskListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        -->setRetainInstance When Orienatation changes it saves the fragment java object in the memory...
         */
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.i(TAG, "Fragment Attached");

        if(context instanceof MyTaskHandler)
        {
            taskListener = (MyTaskHandler) context;
        }
    }

    public void runAsyncTask(String... songsPlaylist)
    {
        MyTask mTask = new MyTask();
        mTask.execute(songsPlaylist);
    }

    class MyTask extends AsyncTask<String, String, String> {
        private static final String TAG = "TAG";

        @Override
        protected String doInBackground(String... strings) {
            log("doInBackground executing...");
            for (String value : strings) {
                //check if the task id cancelled
                if (isCancelled()) {
                    publishProgress("Task Cancelled!");
                    break;
                    /*
                    -->if we cancel the task during running then onProgress mehtod cannot update views the the last progress
                    like it cannot show msg to the user like task has been cancelled.
                     */
                }
                log("Start Downloading. . .");
                publishProgress("Start Downloading. . .");
                /*
                for update the progress we have publishProgress method in we pass the value like here that song is downloaded
                we pass it in that method.
                 -->if we cancel the task during running then onProgress mehtod cannot update views the the last progress
                 like it cannot show msg to the user like task has been cancelled.
                */
                try {
                    Thread.sleep(3000);

                    log("Download Complete :  " + value);
                    publishProgress("Song Downloaded :  " + value);
                } catch (InterruptedException e) {

                }
            }

            /*
            after execution of code doInBackground method return the same type of data that we pass generic paramater
            like here we pass string so it returns us a string...
            --> we have the onPostExecute method in which we retrieve the value that return by doInBackground method...
            in the onPostExecute method also run on the main thread so we update our views in that method..
            like stop the progress bar..update the user that his task is completed.
             */
            return "Execution Completed!!!";
        }

        /*
       --> onProgressUpdate(String... values); we use this method for updating the views.
       --> This mehtod run on the main/UI Thread.so in this method we can acess our UI views
       -->if we cancel the task during running then onProgressUpdate mehtod cannot update the views
         */

        @Override
        protected void onProgressUpdate(String... values) {
            //values[0] i am access only one value so pass values[0].
            taskListener.onChangeData("\n" + values[0]);
        }

        /*
           --> onPostExecute() method in which we retrieve the value that return by doInBackground method...
           in the onPostExecute method also run on the main thread so we update our views in that method..
           like stop the progress bar..update the user that his task is completed.

           -->if we cancel the task during running then onPostExecute mehtod cannot update the views
            */
        @Override
        protected void onPostExecute(String s) {
            taskListener.onChangeData("\n" + s);
        }


        public void log(String msg) {
            Log.i(TAG, msg);
        }
    }
}
