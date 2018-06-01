package edu.rosehulman.boutell.colorchooser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

  // In order to pass information you need a Key-Value pair
  public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
  public static final String EXTRA_COLOR = "EXTRA_COLOR";

  // In order to retrun information you need a Request Code
  // Request codes cannot be 0 and must be unique if you have many
  private static final int REQUEST_CODE_INPUT = 1;

  private RelativeLayout mLayout;
  private TextView mTextView;
  private String mMessage = "This is your phone. Please rescue me!";
  private int mBackgroundColor = Color.GREEN;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // DONE: Send an email with the message field as the body
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"fisherds@rose-hulman.edu"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hello from class");
        intent.putExtra(Intent.EXTRA_TEXT, mMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
          startActivity(intent);
        } else {
          Toast.makeText(MainActivity.this, "Email not supported on this device. Sorry", Toast.LENGTH_LONG).show();
        }
      }
    });

    // Capture
    mLayout = (RelativeLayout) findViewById(R.id.content_main_layout);
    mTextView = (TextView) findViewById(R.id.content_main_message);

    // Set color and text
    updateUI();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {

      case R.id.action_info:
        // DONE: Launch a new Info Activity that is a ScrollingActivity.
        Intent infoIntent = new Intent(this, ScrollingActivity.class);
        startActivity(infoIntent);
        return true;
      case R.id.action_change_color:
        // DONE: Launch the InputActivity to get a result
        Intent inputIntent = new Intent(this, InputActivity.class);
        inputIntent.putExtra(EXTRA_MESSAGE, mMessage);
        inputIntent.putExtra(EXTRA_COLOR, mBackgroundColor);
        startActivityForResult(inputIntent, REQUEST_CODE_INPUT);
        return true;

      case R.id.action_settings:
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void updateUI() {
    mTextView.setText(mMessage);
    mLayout.setBackgroundColor(mBackgroundColor);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_INPUT && resultCode == Activity.RESULT_OK) {
      mMessage = data.getStringExtra(EXTRA_MESSAGE);
      mBackgroundColor = data.getIntExtra(EXTRA_COLOR, Color.GRAY);
      updateUI();
    }
  }
}
