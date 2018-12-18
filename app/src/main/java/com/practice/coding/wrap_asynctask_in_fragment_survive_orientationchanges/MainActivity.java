package com.practice.coding.wrap_asynctask_in_fragment_survive_orientationchanges;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AsyncTaskFragment.MyTaskHandler {

    private static final String RANDOM_TAG = "asyncTaskFragment";
    private TextView textView;
    private AsyncTaskFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        FragmentManager manager = getSupportFragmentManager();
        mFragment = (AsyncTaskFragment) manager.findFragmentByTag(RANDOM_TAG);
        if(mFragment == null)
        {
            mFragment = new AsyncTaskFragment();
            manager.beginTransaction().add(mFragment, RANDOM_TAG).commit();
        }
    }

    public void setData(String text) {
        textView.append(text);
    }

    public void runCode(View view) {
        setData("\nAsync Task Running ...");
        mFragment.runAsyncTask("Afreen Afreen", "Dil Dil Paksitan", "Jazba ha Janoon");
    }


    public void clearText(View view) {
            textView.setText("");
    }

    @Override
    public void onChangeData(String data) {
        setData(data);
    }
}
