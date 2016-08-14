package xyz.agala.czzuwlan;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Galaxy on 2016/7/5.
 */

class userInfo {
    private Context context;
    userInfo(Context context){
        super();
        this.context=context;
    }
    void save(String username, String password){
        SharedPreferences pf=context.getSharedPreferences("zzuwlanInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=pf.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
    }
    void remove(){
        SharedPreferences pf=context.getSharedPreferences("zzuwlanInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=pf.edit();
        editor.putString("username","");
        editor.putString("password","");
        editor.commit();
    }
    Map<String,String> getInfo(){
        SharedPreferences pfr=context.getSharedPreferences("zzuwlanInfo",Context.MODE_PRIVATE);
        Map<String,String> info=new HashMap<>();
        info.put("username",pfr.getString("username",""));
        info.put("password",pfr.getString("password",""));
        return info;
    }
}
