package com.shinc.duobaohui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.utils.PictureActivityUtil;


/**
 * 自定义·用户头像
 *
 * @author sheng
 */
public class ModifyPictureActivity extends BaseActivity implements OnClickListener {
    private TextView cancel;
    private TextView fromPhotoStore;
    private TextView takePhoto;
    private int request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_person_head);
        request = getIntent().getIntExtra("extra", -1);
        setupView();
    }

    private void setupView() {
        this.cancel = (TextView) findViewById(R.id.tv_cancle_modify_person_head);
        this.fromPhotoStore = (TextView) findViewById(R.id.tv_from_photo_album_modify_person_head);
        this.takePhoto = (TextView) findViewById(R.id.tv_photograph_modify_person_head);

        cancel.setOnClickListener(this);
        fromPhotoStore.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_cancle_modify_person_head) {
            finish();
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);

        } else if (i == R.id.tv_from_photo_album_modify_person_head) {
            PictureActivityUtil.doPickPhotoFromGallery(
                    ModifyPictureActivity.this, request);

        } else if (i == R.id.tv_photograph_modify_person_head) {
            String status = Environment.getExternalStorageState();

            if (status.equals(Environment.MEDIA_MOUNTED)) {
                PictureActivityUtil.doTakePhoto(ModifyPictureActivity.this,
                        request);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode >= PictureActivityUtil.PHOTO_CROP && data != null) {
            Intent intent = new Intent();
            intent.putExtra("data", data);
            setResult(resultCode, intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);
        } else if (requestCode < 1000) {
            if (PictureActivityUtil.getmCurrentPhotoFile() != null) {
                PictureActivityUtil.doCropPhoto(ModifyPictureActivity.this, Uri.fromFile(PictureActivityUtil.getmCurrentPhotoFile()), requestCode);
            }
        } else {
            if (data != null) {
                PictureActivityUtil.doCropPhoto(ModifyPictureActivity.this,
                        data.getData(), requestCode / 1000 / 2);
            }
        }

    }

}
