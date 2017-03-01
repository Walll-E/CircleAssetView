package walle.com.circleassetviewdemo;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.walle.circleassetview.CircleAssetView;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            assetView.setDegree(270);
        }
    };
    private CircleAssetView assetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assetView = (CircleAssetView) findViewById(R.id.assetView);
        assetView.setMoneyText("￥2900.0");
        assetView.setMoneyTextColor(Color.rgb(33,33,33));
        assetView.setMoneyTextHint("总资产(元)");
        assetView.setTextVisibility(CircleAssetView.AssetTextVisibility.Visible);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                handler.sendMessage(message);
            }
        },2000);
    }
}
