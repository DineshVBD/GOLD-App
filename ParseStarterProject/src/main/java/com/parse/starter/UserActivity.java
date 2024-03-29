package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ListView userlistview;
    ArrayList<String> users;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("User List");
        users=new ArrayList<String>();

        userlistview=(ListView) findViewById(R.id.userlistview);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,users);
        userlistview.setAdapter(arrayAdapter);

        users.clear();

        ParseQuery<ParseUser> query=ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null)
                {
                    if(objects.size()>0)
                    {
                        for(ParseUser object:objects) {
                            users.add(object.getUsername());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        userlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("username",users.get(i));
                startActivity(intent);
            }
        });


    }
}
