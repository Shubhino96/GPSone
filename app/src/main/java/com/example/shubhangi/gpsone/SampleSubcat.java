package com.example.shubhangi.gpsone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SampleSubcat extends AppCompatActivity {
    RecyclerView rv;
    List<SubCategory> ls;
    String url="http://app.connect247.co.in:8080/connect247";
    public static final MediaType MEDIA_TYPE =
            MediaType.parse("application/json");
    final OkHttpClient client=new OkHttpClient();
    ArrayList<Category> catLevel1=new ArrayList<>();
    ArrayList<Category> catLevel2=new ArrayList<>();
    ArrayList<Category> catLevel3=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_subcat);
        rv=(RecyclerView)findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ls=new ArrayList<>();
        final ArrayList<Category> categories=new ArrayList<>();
        final Request request = new Request.Builder()
                .url(url + "/ws/category/list")
                .get()
                .header("Content-Type", "application/json")
                .build();
        try {
            Log.i("resp6", "r32");
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String mMessage = e.getMessage().toString();
                    Log.i("msk", mMessage);
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String mMessage = response.body().string();
                    JSONArray ja = null;
                    try {
                        ja = new JSONArray(mMessage);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject catobj = ja.getJSONObject(i);
                            String name = catobj.getString("categoryName");
                            String image = catobj.getString("imageURL");
                            boolean active = catobj.getBoolean("isActive");
                            int parent = catobj.getInt("parentCategory");
                            int id = catobj.getInt("categogyId");
                            Category c = new Category(id, name, image, active, parent);
                            JSONArray cat1 = catobj.getJSONArray("categoryList");
                            if (cat1 != null) {
                                ArrayList<Category> cl = new ArrayList<>();
                                for (int j = 0; j < cat1.length(); j++) {
                                    JSONObject obj = cat1.getJSONObject(j);
                                    String n = obj.getString("categoryName");
                                    int id1 = obj.getInt("categogyId");
                                    String url = obj.getString("imageURL");
                                    boolean act = obj.getBoolean("isActive");
                                    int p = obj.getInt("parentCategory");
                                    Category c1 = new Category(id1, n, url, act, p);
                                    cl.add(c1);
                                    c1.setCat(null);
                                    c1.setSubCategoryClassificationList(null);
                                    catLevel1.add(c1);
                                    c.setCat(cl);
                                    JSONArray cat2 = obj.getJSONArray("subCategoryList");
                                    if (cat2 != null) {
                                        ArrayList<Category> c2 = new ArrayList<>();
                                        for (int k = 0; k < cat2.length(); k++) {
                                            JSONObject object = cat2.getJSONObject(k);
                                            String na = object.getString("categoryName");
                                            int id2 = object.getInt("categogyId");
                                            String ur = object.getString("imageURL");
                                            boolean acti = object.getBoolean("isActive");
                                            int par = object.getInt("parentCategory");
                                            Category ca2 = new Category(id2, na, ur, acti, par);
                                            c2.add(ca2);
                                            ca2.setCat(null);
                                            ca2.setSubCategoryList(null);
                                            c1.setSubCategoryList(c2);
                                            catLevel2.add(ca2);
                                            JSONArray cat3 = object.getJSONArray("subCategoryClassificationList");
                                            if (cat3 != null) {
                                                ArrayList<Category> c3 = new ArrayList<>();
                                                for (int l = 0; l < cat3.length(); l++) {
                                                    JSONObject ob = cat3.getJSONObject(l);
                                                    String nam = ob.getString("categoryName");
                                                    Log.i("n", nam);
                                                    int id3 = ob.getInt("categogyId");
                                                    String iurl = ob.getString("imageURL");
                                                    boolean iacti = ob.getBoolean("isActive");
                                                    int pa = ob.getInt("parentCategory");
                                                    Category ca3 = new Category(id3, nam, iurl, iacti, pa);
                                                    c3.add(ca3);
                                                    ca3.setCat(null);
                                                    ca3.setSubCategoryList(null);
                                                    ca3.setSubCategoryClassificationList(null);
                                                    catLevel3.add(ca3);
                                                    ca2.setSubCategoryClassificationList(c3);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            c.setSubCategoryList(null);
                            c.setSubCategoryClassificationList(null);
                            categories.add(c);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ls.add(new SubCategory("Cooks",R.drawable.cooks));
        ls.add(new SubCategory("AC Service and Repair",R.drawable.ac_service_and_repair));
        SubCategoryAdapter subadapter=new SubCategoryAdapter(ls);
        rv.setAdapter(subadapter);
    }
}
