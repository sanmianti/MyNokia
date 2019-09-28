package sanmianti.com.mynokia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;

import sanmianti.com.mynokia.databinding.ActivityMainBinding;
import sanmianti.com.mynokia.utils.StringUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    Vibrator vibrator;
    StringBuilder inputStr = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        WebSettings settings = binding.webviewKeyboard.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        binding.webviewKeyboard.loadUrl("file:///android_asset/15694580471773267.html");
        binding.webviewKeyboard.addJavascriptInterface(new MainActivityJS(), "jsObj");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        binding.show.setOnClickListener(this);
        binding.quit.setOnClickListener(this);

        onHome();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onSMS("讯息： \n\n有内鬼，终止交易！");
            }
        }, 3000);
    }

    /**
     * 有短信，显示讯息，显示【显示】【退出】按钮
     */
    private void onSMS(String sms) {

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(this, notification);
        r.play();
        binding.show.setVisibility(View.VISIBLE);
        binding.quit.setVisibility(View.VISIBLE);
        binding.input.setText(sms);
    }

    /**
     * 返回首页，隐藏【显示】【退出】按钮，显示时间
     */
    private void onHome() {
        binding.show.setVisibility(View.GONE);
        binding.quit.setVisibility(View.GONE);

        if (inputStr.length() > 0) {
            inputStr.delete(0, inputStr.length());
        }
        binding.input.setText(StringUtils.formatTime(System.currentTimeMillis(), "yyyy年MM月dd日HH:mm"));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                showToastMessage("已上报110");
                onHome();
                break;
            case R.id.quit:
                onHome();
                break;
            default:
                break;
        }
    }


    private class MainActivityJS {

        @JavascriptInterface
        public void onClick(final String key) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    vibrator.vibrate(25);
                    playMusic();
                    switch (key) {
                        case "home":
                            //首页
                            onHome();
                            break;
                        case "address":
                            //通讯录
                            toAddressList();
                            break;
                        case "function":
                            //功能
                            showToastMessage("功能");
                            break;
                        case "confirm":
                            //确认
                            showToastMessage("确认");
                            break;
                        case "sms":
                            //短信
                            showToastMessage("短信");
                            toSMSList();
                            break;
                        case "call":
                            //拨号
                            callPhone(inputStr.toString());
                            break;
                        case "delete":
                            if (inputStr.length() > 0) {
                                inputStr.delete(inputStr.length() - 1, inputStr.length());
                            }
                            if (inputStr.length() > 0){
                                binding.input.setText(inputStr.toString());
                            }else {
                                onHome();
                            }
                            break;
                        case "stop":
                            //挂断
                            showToastMessage("挂断");
                            break;
                        case "zero":
                            inputStr.append("0");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "one":
                            inputStr.append("1");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "two":
                            inputStr.append("2");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "three":
                            inputStr.append("3");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "four":
                            inputStr.append("4");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "five":
                            inputStr.append("5");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "six":
                            inputStr.append("6");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "seven":
                            inputStr.append("7");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "eight":
                            inputStr.append("8");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "nine":
                            inputStr.append("9");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "star":
                            //星号
                            inputStr.append("*");
                            binding.input.setText(inputStr.toString());
                            break;
                        case "sharp":
                            //井号
                            inputStr.append("#");
                            binding.input.setText(inputStr.toString());
                            break;
                        default:
                            break;

                    }
                }
            });

        }

        /**
         * 拨打电话
         * @param number 电话号码
         */
        private void callPhone(String number) {
            if (TextUtils.isEmpty(number)) {
                showToastMessage("请输入手机号");
            }else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + number);
                intent.setData(data);
                startActivity(intent);
            }
        }

        private void playMusic() {
            MediaPlayer mMediaPlayer;
            mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.voice_2);
            mMediaPlayer.start();
        }

        /**
         * 跳转至通讯录
         */
        private void toAddressList(){
            Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 0);
        }

        /**
         * 跳转至短信列表
         */
        private void toSMSList(){
            Intent intent4 = new Intent();
            intent4.setClassName("com.android.mms","com.android.mms.ui.ConversationList");
            startActivity(intent4);
        }
    }


}
