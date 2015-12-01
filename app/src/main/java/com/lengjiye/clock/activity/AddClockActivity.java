package com.lengjiye.clock.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lengjiye.clock.R;
import com.lengjiye.clock.model.SongInformation;
import com.lengjiye.clock.utils.BitmapHelper;
import com.lengjiye.clock.utils.Constants;
import com.lengjiye.clock.utils.MediaPlayerUtils;
import com.lengjiye.clock.utils.StringUtils;
import com.lengjiye.clock.view.ActionBar;
import com.lengjiye.clock.view.CheckSwitchButton;
import com.lengjiye.clock.view.MyDialog;
import com.lengjiye.clock.view.wheelview.NumericWheelAdapter;
import com.lengjiye.clock.view.wheelview.OnWheelScrollListener;
import com.lengjiye.clock.view.wheelview.WheelView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 添加闹钟
 */
public class AddClockActivity extends BaseActivity {

    private Context mContext;
    @ViewInject(R.id.action_bar)
    private ActionBar actionBar;
    @ViewInject(R.id.et_tittle)
    private EditText etTittle;
    @ViewInject(R.id.tv_repeat_time)
    private TextView tvRepeatTime;
    @ViewInject(R.id.tv_ring)
    private TextView tvRing;
    @ViewInject(R.id.csb_shake)
    private CheckSwitchButton csbShake;
    @ViewInject(R.id.iv_image)
    private ImageView ivImage;
    @ViewInject(R.id.tv_setting_time)
    private TextView tvSettingTime;
    private MyDialog myDialog;
    private WheelView wvHour;
    private WheelView wvMinute;

    private int hour;
    private int minute;
    public static final int MAX_NUM = 9;
    public static final int PICK_CODE = 1;
    private final int TAKE_PHOTO_CODE = 2;
    private List<String> eventImgUrl;
    private String QUESTION_TEMP_IMAGE;
    private ActivityRequestCode requestCodeEnum = ActivityRequestCode.getDefault();
    private SongInformation information;

    private String validateErrorMsg;
    private String tittle;
    private int repeatTime;
    private int shakeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clock);
        ViewUtils.inject(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mContext = this;
        eventImgUrl = new ArrayList<String>();
        eventImgUrl.add(String.valueOf(R.drawable.add_picture));
        actionBar.setLiftVisibility(View.INVISIBLE);
        actionBar.setRightText("确定");
        actionBar.setLiftVisibility(View.VISIBLE);
        actionBar.setTittleText("添加闹钟");
        actionBar.setLiftText("返回");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && "ChooseMusicActivity".equals(bundle.getString("activity"))) {
            information = (SongInformation) bundle.getSerializable("SongInformation");
            tvRing.setText(information.getSongName());
            MediaPlayerUtils.playAudio(mContext, Uri.parse(information.getPath()));
        } else if (bundle != null && "MainActivity".equals(bundle.getString("activity"))) {
            // 修改值

        } else {
            // 设置默认值
            shakeSwitch = 0;
            Ringtone ringtone = getSystemDefultRingtone();
            tvRing.setText(ringtone.getTitle(mContext));
            information = new SongInformation();
            information.setSongName(ringtone.getTitle(mContext));
            information.setPath(RingtoneManager.getActualDefaultRingtoneUri(mContext,
                    RingtoneManager.TYPE_ALARM).toString());
            ivImage.setImageResource(R.drawable.default_image);
        }

    }

    @OnClick({R.id.btn_right, R.id.btn_lift, R.id.rl_repeat_time, R.id.rl_ring, R.id.rl_image, R.id.rl_setting_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_repeat_time:
                // TODO 重复时间
                break;
            case R.id.rl_setting_time:
                // TODO 设置时间
                settingTime();
                break;
            case R.id.rl_ring:
                // TODO 铃声
                Intent intent = new Intent(mContext, ChooseMusicFolderActivity.class);
                startActivityForResult(intent, PICK_CODE);
                break;
            case R.id.rl_image:
                // TODO 图片
                pickPicture();
                break;
            case R.id.btn_lift:
                finish();
                break;
            case R.id.btn_right:
                // TODO 点击确定，添加数据到数据库
                Intent intent1 = new Intent(mContext, MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    private void settingTime() {
        myDialog = new MyDialog(mContext, R.layout.dialog_wheel_view);
        // 得到系统时间
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        LinearLayout linearLayout = myDialog.getDialogView();
        // 时
        wvHour = (WheelView) linearLayout.findViewById(R.id.wv_hour);
        wvHour.setAdapter(new NumericWheelAdapter(0, 23, "%02d"));
        wvHour.setCurrentItem(hour);
        wvHour.setLabel("时");
        wvHour.addScrollingListener(scrollListener);

        // 分
        wvMinute = (WheelView) linearLayout.findViewById(R.id.wv_minute);
        wvMinute.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
        wvMinute.setCurrentItem(minute);
        wvMinute.setLabel("分");
        // 设置可循环
        wvMinute.setCyclic(true);
        wvMinute.addScrollingListener(scrollListener);

        Button btnCancel = (Button) linearLayout.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new OnClickListener());
        Button btnConfirm = (Button) linearLayout.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new OnClickListener());
        myDialog.show();
    }

    /**
     * 弹出拍照对话框
     */
    private void pickPicture() {
        myDialog = new MyDialog(mContext, R.layout.dialog_take_picture_view);
        LinearLayout linearLayout = myDialog.getDialogView();
        TextView tvTakePicture = (TextView) linearLayout.findViewById(R.id.tv_take_picture);
        tvTakePicture.setOnClickListener(new OnClickListener());
        TextView tvPickPicture = (TextView) linearLayout.findViewById(R.id.tv_pick_picture);
        tvPickPicture.setOnClickListener(new OnClickListener());
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new OnClickListener());
        myDialog.show();
    }

    /**
     * 控件滑动开始、结束监听事件
     */
    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            // 滑动开始
        }

        public void onScrollingFinished(WheelView wheel) {
            // 滑动结束
            hour = wvHour.getCurrentItem();
            minute = wvMinute.getCurrentItem();
        }
    };

    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_confirm:
                    // TODO 确定
                    closeMyDialog();
                    tvSettingTime.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));
                    break;
                case R.id.btn_cancel:
                    // TODO 取消
                    closeMyDialog();
                    break;
                case R.id.tv_take_picture:
                    // TODO 拍照
                    closeMyDialog();
                    takePicture();
                    break;
                case R.id.tv_pick_picture:
                    // TODO 从相册选取
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");// 相片类型
                    startActivityForResult(intent, ActivityRequestCode.REQUEST_CODE_PICK_IMAGE.getValue());
                    closeMyDialog();
                    break;
                case R.id.tv_cancel:
                    // TODO 取消
                    closeMyDialog();
                    break;
            }
        }
    }

    /**
     * 关闭对话框
     */
    private void closeMyDialog() {
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }

    /**
     * 拍照
     */
    private void takePicture() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File folder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/"
                    + Constants.CLOCK_PICTURE_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            QUESTION_TEMP_IMAGE = folder.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpeg";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(QUESTION_TEMP_IMAGE)));
            startActivityForResult(intent, ActivityRequestCode.REQUEST_CODE_CAMERA_IMAGE.getValue());
        } else {
            Toast.makeText(mContext, "您还没有安装SD卡无法进行图片上传功能，请确保已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCodeEnum.getRequestCodeByInt(requestCode)) {
            case REQUEST_CODE_PICK_IMAGE:
                if (data == null) {
                    return;
                }
                // 得到相册图片的Uri
                Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                // 根据Uri得到图片的绝对路径
                ContentResolver cr = this.getContentResolver();
                Cursor cursor = cr.query(uri, null, null, null, null);
                cursor.moveToFirst();
                BitmapHelper.createThumbnailFolder();
                BitmapHelper.getBitmapUtils(mContext).display(ivImage, BitmapHelper.getThumbnail(cursor.getString(1), ivImage.getMeasuredWidth()),
                        BitmapHelper.setBitmapDisplayConfig(getResources().getDrawable(R.drawable.default_picture),
                                getResources().getDrawable(R.drawable.fail_picture)));
                if (cursor != null) {
                    cursor.close();
                }
                break;
            case REQUEST_CODE_CAMERA_IMAGE:
                if (Environment.getExternalStorageDirectory() + "/" + QUESTION_TEMP_IMAGE != null) {
                    BitmapHelper.createThumbnailFolder();
                    BitmapHelper.getBitmapUtils(mContext).display(ivImage, BitmapHelper.getThumbnail(QUESTION_TEMP_IMAGE, ivImage.getMeasuredWidth()),
                            BitmapHelper.setBitmapDisplayConfig(getResources().getDrawable(R.drawable.default_picture),
                                    getResources().getDrawable(R.drawable.fail_picture)));
                }

                break;
        }
    }

    /**
     * activity的requestcode
     */
    private enum ActivityRequestCode {
        REQUEST_CODE_PICK_IMAGE(0x01),
        REQUEST_CODE_CAMERA_IMAGE(0x02);
        private int value = 0x01;

        ActivityRequestCode(int value) {
            this.value = value;
        }

        public static ActivityRequestCode getDefault() {
            return REQUEST_CODE_PICK_IMAGE;
        }

        public int getValue() {
            return value;
        }

        public ActivityRequestCode getRequestCodeByInt(int value) {
            for (ActivityRequestCode code : ActivityRequestCode.values()) {
                if (code.getValue() == value) {
                    return code;
                }
            }
            return getDefault();
        }
    }

    /**
     * 校验数据
     */
    private boolean verifyData() {
        tittle = etTittle.getText().toString().trim();
        if (StringUtils.isBlank(tittle)) {
            tittle = "没有主题";
        }
        if (StringUtils.isBlank(tvRepeatTime.getText().toString().trim())) {
            repeatTime = 1;
        }
        if (StringUtils.isBlank(tvSettingTime.getText().toString().trim())) {
            validateErrorMsg = "你还没有选择时间";
            return false;
        }
        if (csbShake.isChecked()) {
            shakeSwitch = 0;
        } else {
            shakeSwitch = 1;
        }
        return true;
    }

    /**
     * 获取系统默认铃声的Ringtone对象
     *
     * @return 系统默认铃声的Ringtone对象
     */
    private Ringtone getSystemDefultRingtone() {
        return RingtoneManager.getRingtone(this, RingtoneManager.getActualDefaultRingtoneUri(mContext,
                RingtoneManager.TYPE_ALARM));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeMyDialog();
        MediaPlayerUtils.stopPlayer();
    }
}
