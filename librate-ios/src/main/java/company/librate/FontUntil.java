package company.librate;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by diepnt on 7/28/2017.
 */


public class FontUntil {
    public static Typeface getTypeface(String name, Context context) {
        return Typeface.createFromAsset(context.getAssets(), name);
    }
}
