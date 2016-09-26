package qianfeng.networkapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String str = "http://a1.peoplecdn.cn/6ced1deec309e8bca43ecf15dcdd8fcf.jpg";
    private String ret = "http://a3.peoplecdn.cn/58437834e015451ececdc3cbd45587dd.jpg";
    private Button mBtn_1;
    private Button mBtn_2;
    private ImageView mIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn_1 = ((Button) findViewById(R.id.btn_1));
        mBtn_2 = ((Button) findViewById(R.id.btn_2));
        mIv = ((ImageView) findViewById(R.id.iv));

    }

    public void download_picture1(View view) {  // 下载图片一
        AsyncClass asyncClass = new AsyncClass();
        asyncClass.execute(str);
    }

    public void download_picture2(View view) {  // 下载图片二
        AsyncClass asyncClass = new AsyncClass();
        asyncClass.execute(ret);
    }


    private class AsyncClass extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            return  (Bitmap) http(params[0]);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null)
            {
                mIv.setImageBitmap(bitmap);
            }
        }
    }


    private Bitmap http(String params)
    {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(params);
             httpURLConnection  = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3*1000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode() == 200)
            {
                // 利用BitmapFactory里面的 DecodeStream 获取 httpURLConnection的 getInputStream  // 编码字节流
               return  BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;  // 这个方法还是要有返回值的嘛，万一 响应状态码不是200，那就进入不了那个return了啊
    }


}
