/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {


  EditText username;
  EditText password;
  TextView loginorsignuptextview;
  Button signuporloginbutton;
  boolean loginmodeactive=false;

  public void redirectuser()
  {
    if(ParseUser.getCurrentUser()!=null)
    {
      Intent intent=new Intent(this,UserActivity.class);
      startActivity(intent);
    }
  }

  public void signuporlogin(View view)
  {
    if(loginmodeactive)
    {
      ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if(e==null)
          {
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            redirectuser();
          }
          else
          {
            String message=e.getMessage();
            if(message.toLowerCase().contains("java"))
            {
              message=e.getMessage().substring(e.getMessage().indexOf(" "));
            }
            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

          }
        }
      });
    }
    else {
      ParseUser parseUser = new ParseUser();
      parseUser.setUsername(username.getText().toString());
      parseUser.setPassword(password.getText().toString());
      parseUser.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            Toast.makeText(getApplicationContext(), "Signup Successful", Toast.LENGTH_SHORT).show();
            redirectuser();
          }
          else {
            String message=e.getMessage();
            if(message.toLowerCase().contains("java"))
            {
              message=e.getMessage().substring(e.getMessage().indexOf(" "));
            }
            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

          }
        }
      });
    }
  }

  public void toggleloginmode(View view)
  {
    if(loginmodeactive)
    {
      loginmodeactive=false;
      loginorsignuptextview.setText("OR,LOGIN");
      signuporloginbutton.setText("SIGNUP");
    }
    else {
      loginmodeactive=true;
      loginorsignuptextview.setText("OR,SIGNUP");
      signuporloginbutton.setText("LOGIN");
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("WHATSAPP3:LOGIN");
    username=(EditText) findViewById(R.id.username);
    password=(EditText) findViewById(R.id.password);
    loginorsignuptextview=(TextView) findViewById(R.id.signuporlogintextview);
    signuporloginbutton=(Button) findViewById(R.id.signuploginbutton);

    redirectuser();
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}