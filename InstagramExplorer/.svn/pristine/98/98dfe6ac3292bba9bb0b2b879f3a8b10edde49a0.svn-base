package com.example.duthientan.searchimagetool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duthientan.searchimagetool.Backend.GoogleImageUrl;
import com.example.duthientan.searchimagetool.Backend.Wiki_backend;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ImageSearchOnline extends AppCompatActivity {
    File file = null;
    List<String> urlImage = new ArrayList<String>();
    MultipartEntity entity;
    GoogleImageUrl urlGoogle;
    String urlSearchImage = "";
    ImageView imageView;
    ListView listView;
    Button button;
    Wiki_backend wiki;
    Map<String, String> wikiMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search_online);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        } else
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_search_online, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        do {
            if (requestCode == 1) {
                final String[] key = {""};
                if (data == null) {
                    finish();
                    return;
                }
                Bundle extras = data.getExtras();
                final HttpClient client = new DefaultHttpClient();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView = (ImageView) findViewById(R.id.picS);
                listView = (ListView) findViewById(R.id.listS);
                imageView.setImageBitmap(imageBitmap);
                button = (Button) findViewById(R.id.searchImage);
                String url = "http://images.google.com/searchbyimage/upload";
                file = saveImage(imageBitmap);
                final Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                final HttpPost post = new HttpPost(url);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            entity = new MultipartEntity();
                            entity.addPart("encoded_image", new FileBody(file));
                            entity.addPart("image_url", new StringBody(""));
                            entity.addPart("image_content", new StringBody(""));
                            entity.addPart("filename", new StringBody(""));
                            entity.addPart("hl", new StringBody("en"));
                            entity.addPart("bih", new StringBody(String.valueOf(bitmap.getHeight())));
                            entity.addPart("biw", new StringBody(String.valueOf(bitmap.getWidth())));
                            post.setEntity(entity);
                            HttpResponse response = client.execute(post);
                            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                            String line = "";
                            while ((line = rd.readLine()) != null) {
                                if (line.contains("tbs%3Dsbi")) {
                                    int start = line.indexOf("tbs%3Dsbi") + "tbs%3Dsbi".length() + 1;
                                    int end = line.indexOf("%26hl%3Den%26bih%3D");
                                    key[0] = line.substring(start, end);
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                urlGoogle = new GoogleImageUrl("https://www.google.com/search?hl=en&bih=599&biw=1366&site=imghp&tbs=sbi%3A" + key[0]);
                urlSearchImage = "https://www.google.com" + urlGoogle.getUrlGG(urlGoogle.pageCode()) + "imgsz=xxlarge";
                urlImage = urlGoogle.getLinksImage(urlSearchImage);
                wiki = new Wiki_backend(urlGoogle.key);
                wikiMap = wiki.getMap();
                final String[] array = wikiMap.keySet().toArray(new String[wikiMap.size()]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, array);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String url = wikiMap.get(array[position]);
                        Intent intent = new Intent(ImageSearchOnline.this, WikiWeb.class);
                        intent.putExtra("URL", url);
                        startActivity(intent);
                    }
                });
                final String[] urls = new String[urlImage.size()];
                int count = 0;
                for (String s : urlImage) {
                    urls[count] = s;
                    count++;
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ImageSearchOnline.this, ImageOnline.class);
                        intent.putExtra("Social", "image");
                        intent.putExtra("url", urls);
                        startActivity(intent);
                    }
                });
            }
        } while (false);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File saveImage(Bitmap bitmap) {
        String path = Environment.getExternalStorageDirectory().toString();
        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
        OutputStream fOut = null;
        File tmp = new File(path, "image.jpg");
        try {
            fOut = new FileOutputStream(tmp);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp;
    }

}
