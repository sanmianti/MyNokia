package sanmianti.com.mynokia;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * @author sanmianti
 * @description 所有activity基类，提供基本功能
 * @date 2019/9/28 11:22
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToastMessage(final String msg) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
