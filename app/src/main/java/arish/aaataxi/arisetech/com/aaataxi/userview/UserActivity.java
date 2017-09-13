package arish.aaataxi.arisetech.com.aaataxi.userview;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import arish.aaataxi.arisetech.com.aaataxi.R;
import arish.aaataxi.arisetech.com.aaataxi.bottombarview.BottomBar;
import arish.aaataxi.arisetech.com.aaataxi.bottombarview.OnTabReselectListener;
import arish.aaataxi.arisetech.com.aaataxi.bottombarview.OnTabSelectListener;
import arish.aaataxi.arisetech.com.aaataxi.login.RegisterForm;

public class UserActivity extends AppCompatActivity {

TextView messageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_maps);
//        messageView = (TextView) findViewById(R.id.messageView);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch(tabId){
                    case R.id.tab_recents:
                        UserMaps driver = new UserMaps();
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_layout,driver).commit();
                        break;
                    case R.id.tab_favorites:
                        DriverListFragment re = new DriverListFragment();
                        android.support.v4.app.FragmentManager fragmetManager = getSupportFragmentManager();
                        fragmetManager.beginTransaction().replace(R.id.frame_layout,re).commit();

                }
//                messageView.setText(TabMessage.get(tabId, false));
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
//                Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });
    }

}
