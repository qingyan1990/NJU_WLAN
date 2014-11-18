package me.aiyanxu.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class MyActivity extends ActionBarActivity {
    public String SETTING_PREF = "SETTING_Pref";
    private EditText nameEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final Button button = (Button) findViewById(R.id.login_btn);
        nameEdit = (EditText) findViewById(R.id.user_name);
        passwordEdit = (EditText) findViewById(R.id.user_password);

        SharedPreferences settings = getSharedPreferences(SETTING_PREF,0);
        nameEdit.setText(settings.getString("username",""));
        passwordEdit.setText(settings.getString("password",""));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nameEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                System.out.println("+++" + username);
                System.out.println("---" + password);
                try {
                    MessageBox(new WLANLogin().execute(username,password).get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
//                button.setText(R.string.button_response);
//                MessageBox("hello");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected  void onStop(){
        super.onStop();

        SharedPreferences settings = getSharedPreferences(SETTING_PREF,0);
        settings.edit()
        .putString("username",nameEdit.getText().toString().trim())
        .putString("password",passwordEdit.getText().toString().trim())
        .commit();
    }

    public void MessageBox(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
