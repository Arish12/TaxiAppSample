package arish.aaataxi.arisetech.com.aaataxi.bottombarview;


import android.content.Context;


import arish.aaataxi.arisetech.com.aaataxi.R;

public class TabMessage {
    Context context;
    public static String get(int menuItemId, boolean isReselection) {
      String message = "contnets for";

        switch (menuItemId) {
            case R.id.tab_recents:

//             UserMaps driver = new UserMaps();
//                FragmentManager frag = context.
//                frag.beginTransaction().replace(R.id.frame_layout,UserMaps.newInstance()).commit();
                message += "recents";
                break;
            case R.id.tab_favorites:
                message += "favorites";
                break;
            case R.id.tab_nearby:
                message += "nearby";

                break;
//            case R.id.tab_friends:
//                message += "friends";
//                break;
//            case R.id.tab_food:
//                message += "food";
//                break;
        }


        if (isReselection) {
            message += " WAS RESELECTED! YAY!";
        }

        return message;
    }
}
