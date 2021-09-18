package company.librate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import company.librate.view.BaseRatingBar;
import company.librate.view.RotationRatingBar;

public class RateDialog extends Dialog {
    private boolean isBack;
    private Context context;
    private String supportEmail;
    private ImageView imageIcon;
    private RotationRatingBar rateBar;
    private SharedPreferences sharedPrefs;

    private static int upperBound = 2;
    private static final String KEY_IS_RATE = "key_is_rate";
    private boolean isRateAppTemp = false;

    public RateDialog(Context context, String supportEmail, boolean isBack) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_rate_ios);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        this.context = context;
        this.supportEmail = supportEmail;
        this.isBack = isBack;
        sharedPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        initDialog();
    }

    private void initDialog() {
        TextView btnOk = (TextView) findViewById(R.id.btn_ok);
        TextView btnNotnow = (TextView) findViewById(R.id.btn_not_now);
        TextView txtAppName = (TextView) findViewById(R.id.txt_name_app);
        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        imageIcon = (ImageView) findViewById(R.id.img_icon_app);
        rateBar = (RotationRatingBar) findViewById(R.id.simpleRatingBar);

        btnOk.setTypeface(FontUntil.getTypeface("fontsapp/" + "ios.otf", context));
        btnNotnow.setTypeface(FontUntil.getTypeface("fontsapp/" + "ios.otf", context));
        txtTitle.setTypeface(FontUntil.getTypeface("fontsapp/" + "ios.otf", context));
        txtAppName.setTypeface(FontUntil.getTypeface("fontsapp/" + "ios_semi_bold.otf", context));
        txtAppName.setText(context.getResources().getString(R.string.app_name));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRateAppTemp && rateBar.getRating() > 0) {
                    if (rateBar.getRating() > upperBound) {
                        openMarket();
                        notShowDialogWhenUserRateHigh();
                    } else {
//                        sendEmail();
                        notShowDialogWhenUserRateHigh();
                    }
                } else {
                    Toast.makeText(context, "please rate 5 stars", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBack) {
                    dismiss();
                    //((Activity) context).finish();
                } else
                    dismiss();
            }
        });
        rateBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating) {
                isRateAppTemp = true;
                if (ratingBar.getRating() > upperBound) {
                    imageIcon.setImageResource(R.drawable.favorite);
                } else {
                    imageIcon.setImageResource(R.drawable.favorite2);
                }
            }
        });
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public boolean isRate() {
        return sharedPrefs.getBoolean(KEY_IS_RATE, false);
    }

    /**
     * update share not show rate when user rate this app > 2 *
     */
    private void notShowDialogWhenUserRateHigh() {
        if (isBack) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putBoolean(KEY_IS_RATE, true);
            editor.apply();
            dismiss();
            ((Activity) context).finish();
        } else
            dismiss();
    }

    private void openMarket() {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void sendEmail() {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/email");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{supportEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "App Report (" + context.getPackageName() + ")");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(emailIntent, "Send mail Report App !"));
    }
}
