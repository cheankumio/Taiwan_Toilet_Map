package klapper.toilet_map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.ClusterManager;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private List<kao_json_data>[] json_data;
    private String mResponse;
    private boolean isLast=false;
    private ClusterManager<MyItem> mClusterManager;
    BoomMenuButton boomMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        boomMenuButton = (BoomMenuButton)findViewById(R.id.boom);
        json_data = new List[10];

        Thread TD = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (isLast!=true) {
                    getJsonData("http://opendata.epa.gov.tw/ws/Data/OTH01102/?$skip="+(count*1000)+"&$top=1000&format=json",count);
                    count += 1;
                }
            }
        });
        //TD.start();



    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mClusterManager = new ClusterManager<MyItem>(this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        LatLng sydney = new LatLng(25.047463,121.520041);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
    }

    public void getJsonData(String url, final int count){
        final String murl = url;
        Thread ts = new Thread(new Runnable() {
            @Override
            public void run() {
                String response = Internet.fun_webGet(murl);
                Gson gson = new Gson();
                json_data[count] = gson.fromJson(response, new TypeToken<List<kao_json_data>>() {
                }.getType());
                if(response.length()<50)isLast=true;
                for(final kao_json_data ks:json_data[count]){
                    Log.d("MYLOG",ks.getName());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LatLng position = new LatLng(Double.parseDouble(ks.getLatitude()),Double.parseDouble(ks.getLongitude()));
                            MyItem newItem = new MyItem(position.latitude,position.longitude);
                            mClusterManager.addItem(newItem);
                        }
                    });
                }
                //Log.d("MYLOG",mResponse);
            }
        });
        ts.start();
        try {
            ts.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void showMenu(View view){

    }

}
