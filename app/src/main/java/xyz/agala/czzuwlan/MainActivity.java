package xyz.agala.czzuwlan;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText username,password;
    private userInfo userInfo;
    private authOnce authOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInfo=new userInfo(this);
        authOnce=new authOnce(this);

        //get SSID
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        TextView textView = (TextView) findViewById(R.id.ssid);
        assert textView != null;
        textView.setText(wifiInfo.getSSID());
        //get name&pass
        username=(EditText) this.findViewById(R.id.username);
        password=(EditText) this.findViewById(R.id.password);
        //save name&pass
        Map<String,String> info=userInfo.getInfo();
        username.setText(info.get("username"));
        password.setText(info.get("password"));

        Button btn=(Button) this.findViewById(R.id.authIt);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authIt();
            }
        });
        Button btn1=(Button) this.findViewById(R.id.autoAuth);
        assert btn1 != null;
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }
    public void saveOrremove(View v){
        CheckBox ck=(CheckBox) this.findViewById(R.id.saveInfo);

        assert ck != null;
        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String uString=username.getText().toString();
                String pString=password.getText().toString();
                if(isChecked)
                    userInfo.save(uString,pString);
                else
                    userInfo.remove();
            }
        });
    }
    private void authIt() {
        TextView textView = (TextView) findViewById(R.id.ssid);
        assert textView != null;
        if (textView.getText().toString().indexOf("zzuwlan")>0){
            authOnce.auth();
            Toast.makeText(getApplicationContext(), "应该是连上了...",
                Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "连尼玛, 不是 zzuwlan!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void test(){
        authOnce.test();
    }
}
