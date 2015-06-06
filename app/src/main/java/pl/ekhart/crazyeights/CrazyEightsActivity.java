package pl.ekhart.crazyeights;

import android.app.Activity;
import android.os.Bundle;


public class CrazyEightsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        CrazyEightsView view = new CrazyEightsView(this);
        TitleView view = new TitleView(this);
        setContentView(view);
    }
}
