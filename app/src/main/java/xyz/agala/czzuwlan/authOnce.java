package xyz.agala.czzuwlan;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Galaxy on 2016/7/5.
 */

class authOnce extends stuffMain{
    private Context context;
    private stuffMain stuff=new stuffMain();

    private static ArrayList<String> codes = new ArrayList<>();

    authOnce(Context context) {
        super();
        this.context=context;
    }

    void auth() {
        getImg();
        //System.out.println(getCaptcha());
        //Log.i("|getCaptcha|",stuff.getCaptcha());


        stuff.setConnStatement(authTouch());
        //Log.i("|getTouch|",stuff.getConnStatement());

    }
    void test(){
        System.out.println(getCaptcha());
    }
    private String getImg(){
        //stuff=new stuffMain(context);
        String imgUrl = "http://202.196.64.132:8080/sss/zzjlogin3d.dll/zzjgetimg?ids=" + (Math.round(Math.random() * 9000 + 1000));

        OkHttpUtils
                .get()
                .url(imgUrl)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("|postErr|",e.toString());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int i) {
                        //iv.setImageBitmap(convertToBlackWhite(bitmap));
                        int width = bitmap.getWidth(); // 获取位图的宽
                        int height = bitmap.getHeight(); // 获取位图的高
                        int[] pixels = new int[width * height];
                        String s = "";

                        for (i = 0; i < 4; i++) {
                            bitmap.getPixels(pixels, 10 * i, width, 14 * i + 4, 5, 10, 16);
                            String ldString = "";

                            for (int j = 0, length = 160; j < length; j += 4) {
                                int grey = pixels[j];

                                int alpha = 0xFF << 24;
                                int red = ((grey & 0x00FF0000) >> 16);
                                int green = ((grey & 0x0000FF00) >> 8);
                                int blue = (grey & 0x000000FF);

                                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11) > 140 ? 255 : 0;
                                grey = alpha | (grey << 16) | (grey << 8) | grey;
                                pixels[j] = grey;
                            }
                            for (int k = 0; k < 16; k++)
                                for (int l = 0; l < 10; l++)
                                    ldString += (pixels[i * 10 + l + width * k] == -1 ? "1" : "0");
                            s = s + String.valueOf(choose(ldString));
                        }
                        stuff.setCaptcha(s);
                        Log.i("|getCaptcha|",stuff.getCaptcha());
                        stuff.setConnStatement(authPost());
                    }
                });
        return stuff.getCaptcha();
    }
    private String authTouch() {
        //stuff=new stuffMain(context);
        String goUrl = "http://www.baidu.com";
        OkHttpUtils
                .get()
                .url(goUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("|postErr|",e.toString());
                    }

                    @Override
                    public void onResponse(String dom, int i) {
                        Log.i("|authHTML|",dom);
                        if (dom.indexOf("202.196.64.132") > 0) {
                            String url = dom.split("'")[1];
                            Log.i("|authUrl|",url);
                            OkHttpUtils
                                    .get()
                                    .url(url)
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int i) {
                                            Log.e("|postErr|",e.toString());
                                        }

                                        @Override
                                        public void onResponse(String dom, int i) {
                                            stuff.setConnStatement("Connecting");
                                            Log.i("|statement|",stuff.getConnStatement());
                                            Log.i("|Touched|",dom.substring(0,50));

                                        }
                                    });

                        } else {
                            stuff.setConnStatement("Connected");
                            Log.i("|statement|",stuff.getConnStatement());
                        }
                    }
                });
        return stuff.getConnStatement();
    }

    private String authPost() {
        userInfo userInfo = new userInfo(context);
        //stuff = new stuffMain(context);
        Map<String, String> info = userInfo.getInfo();
        OkHttpUtils.post()
                .url("https://edu3.v.zzu.edu.cn/ssss/wlogin.dll/login")
                .addParams("uid", info.get("username"))
                .addParams("upw", info.get("password"))
                .addParams("ver6", stuff.getCaptcha())
                .addParams("smbtn", "%B5%C7%C2%BC%D6%A3%D6%DD%B4%F3%D1%A7WLAN")
                .addParams("pos_6", "rj")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("|postErr|",e.toString());
                    }

                    @Override
                    public void onResponse(String dom, int i) {
                        String url =dom.split("'")[1];
                        Log.i("|connUrl|",url);
                        OkHttpUtils
                                .get()
                                .url(url)
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int i) {
                                        Log.e("|postErr|",e.toString());
                                    }

                                    @Override
                                    public void onResponse(String dom, int i) {
                                        Log.i("|postResponse|",dom.substring(0,50));
                                        stuff.setConnStatement("FinishPost");
                                    }

                                });
                    }
                });
        return stuff.getConnStatement();
    }

    private static void initCodes() {
        codes.add("0");
        codes.add("0");
        codes.add("1100001111100000011110011000110011110011111111001111111001111111001111111001111111001111111001111111000000001100000000011111111111111111111111111111111111111111");
        codes.add("1100001111000000011100111100111111110011111110001111000001111100000111111110001111111100111111110011000000011110000011111111111111111111111111111111111111111111");
        codes.add("1111000111111100011111100001111111100111110010011110011001110011100111000000001100000000111111100111111000001111100000011111111111111111111111111111111111111111");
        codes.add("0000000011100000111110011111111001111111100000111110000001111001100011111111001111111100110011100011000000011110000011111111111111111111111111111111111111111111");
        codes.add("1111000011110000001110001111111001111111000000111100000001110001100011001111001100111100111001100011100000011111000011111111111111111111111111111111111111111111");
        codes.add("0000000011000000001100111000111111100011111110011111111001111111000111111100111111110011111110001111111001111111100111111111111111111111111111111111111111111111");
        codes.add("0100001111100110011100111100110011110011000110001110000001111000000111001111001100111100110011110011100000011111000011111111111111111111111111111111111111111111");
        codes.add("1100001111110000011100011000110011110011001111001100011000111000000011110000001111111001111111000111000000111100001111111111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111111100000111100000001111111100111100000011000000001100011100110011100011000000000110000100011111111111111111111111111111111111111111");
        codes.add("0011111111001111111100111111110010000111000000001100011100010011111001001111100100111110010001110001000000001100100001111111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111111100000011100000000100011110010011111001001111111100111111110001111001100000000111100000111111111111111111111111111111111111111111");
        codes.add("1111110001111111000111111110011100001001100000000100011100010011111001001111100100111110010001110000100000000011000010001111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111111100000111000000001100111110010000000000000000000100111111110001111001100000000111000000111111111111111111111111111111111111111111");
        codes.add("1110000001100000000111001111110000000011000000001111001111111100111111110011111111001111111000111111000000001100000000111111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111111100001000100000000100011100010011111001001111100100111110010001110001100000000111000010011111111001111111100111000000111100000111");
        codes.add("0001111111000111111110011111111001000011100000000110001110011001111001100111100110011110011001111001000011000000001100001111111111111111111111111111111111111111");
        codes.add("1111001111111100111111111111111000001111100000111111110011111111001111111100111111110011111111001111100000000110000000011111111111111111111111111111111111111111");
        codes.add("1111001111111100111111111111110000000111000000011111111001111111100111111110011111111001111111100111111110011111111001111111100111111110011100000011110000001111");
        codes.add("0001111111000111111100011111111001100001100110000110010011111000011111100001111110010011111001100111000111000000011100001111111111111111111111111111111111111111");
        codes.add("0");
        codes.add("1111111111111111111111111111110000110001000000000000110010000011001100001100110000110011000011001100000100010000010001001111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111110001000011000000000110001110011000111001100111100110001110010001111001000011000000000100001111111111111111111111111111111111111111");
        codes.add("0");
        codes.add("1111111111111111111111111111110010000111000000000100011100010011111001001111100100111110010001110001000000001100100001110011111111001111111100001111110000111111");
        codes.add("1111111111111111111111111111111100001000100000000000011100010011111001001111100100111110010001111001100000000111000010011111111001111111100111111000001111100000");
        codes.add("1111111111111111111111111111110000100011000000000111000110011100111111110011111111001111111100111111000000001100000000111111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111111110000001100000000110011110011000011111110000001111111000010001111001100000000110000001111111111111111111111111111111111111111111");
        codes.add("1100111111110011111111001111110000000011000000001111001111111100111111110011111111001111111100110001110000000111100001111111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111110001110001000111000110011110011001111001100111100110011110011001110001100000000011001110001111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111110000110000000111000000111110011001110011100111001111001001111100100111111000111111100011111111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111110001110000000111000000111110010011111001001000100110001000111000100011100111001110011100111111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111110000110000000011000011001100111110001111111100111111100001111100110011000011000000001100001111111111111111111111111111111111111111");
        codes.add("1111111111111111111111111111110001110000000111000000111110011001111001100111001111001001111100100011110000011111100011111110001111111001111100000001110000000111");
        codes.add("1111111111111111111111111111111000000001100000000110001100111111100111111100011111100111111000111001100000000110000000011111111111111111111111111111111111111111");
    }

    private static String choose(String str) {
        int length = 0, max = 0;
        String number = "";
        initCodes();
        for (int n = 0; n < 36; n++) {
            for (int i = 0; i < 160; i++) {
                if (codes.get(n).length() != 1)
                    length += (codes.get(n).charAt(i) == str.charAt(i)) ? 1 : 0;
            }
            if (length > max) {
                max = length;
                number = String.valueOf((n < 10) ? (String.valueOf(n)) : ((char) (n + 87)));
            }
            length = 0;

        }
        return String.valueOf(number);
    }


}
