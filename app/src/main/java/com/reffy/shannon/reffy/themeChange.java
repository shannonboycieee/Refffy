package com.reffy.shannon.reffy;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Shannon on 06/04/2018.
 */

public class themeChange {

    private static int Theme;
    public final static int AppTheme =0;
    public final static int BLACK = 1;
    public final static int BLUE = 2;

    public static void changeToTheme(Activity activity, int theme)

    {

        Theme = theme;

        activity.finish();

activity.startActivity(new Intent(activity, activity.getClass()));


}

    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (Theme)

        {

            case BLACK:

                activity.setTheme(R.style.BlackTheme);

                break;

            case BLUE:

                activity.setTheme(R.style.BlueTheme);

                break;

        }

    }

}
