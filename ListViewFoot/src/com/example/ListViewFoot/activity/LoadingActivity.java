package com.example.ListViewFoot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.example.ListViewFoot.ParentActivity;
import com.example.ListViewFoot.R;
import com.example.ListViewFoot.util.DeviceDetailInfo;
import com.example.ListViewFoot.util.HelperPicture;
import com.example.ListViewFoot.util.SharePrefrenceUtil;

public class LoadingActivity extends ParentActivity {


    /**
     * Called when the activity is first created.
     */

    private ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      if ( SharePrefrenceUtil.getBoolean(SharePrefrenceUtil.CACHE_MANAGER,context,getString(R.string.app_name)+ DeviceDetailInfo.getAppVersion(context),false)){
          Intent intent=new Intent();
          intent.setAction("main.activity");
          context.startActivity(intent);
          finish();
      }
        imageView= (ImageView) findViewById(R.id.imagebg);
        imageView.setImageBitmap(HelperPicture.getAssertImage(context,"boot.jpg"));
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                SharePrefrenceUtil.saveBoolean(SharePrefrenceUtil.CACHE_MANAGER, context, getString(R.string.app_name) + DeviceDetailInfo.getAppVersion(context), true);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent();
                intent.setAction("main.activity");
                context.startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.setAnimation(alphaAnimation);
        //广播一次注册的问题


    }





}
