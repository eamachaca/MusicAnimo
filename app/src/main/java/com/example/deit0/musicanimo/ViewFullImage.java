package com.example.deit0.musicanimo;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.samples.vision.face.facetracker.Reconocimiento.FaceTrackerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewFullImage extends AppCompatActivity implements View.OnClickListener{
   private List<Musica> Lista;
    private TextView tvMsg;
    private Button btnPlay;
    private Button btnPause;
    private Button btnStop;
    private Button btnNext;
    private Button btnprevious;
    private SeekBar skSong;
    private TextView tvTime;
    private MediaPlayer mPlayer = null;
    private Handler skHandler = new Handler();
    private String url;
    private static int cons=33486;
    RequestQueue mRequestQueue;
    TelephonyManager manager;
    int i;
    int zise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_image);
        if (!this.getIntent().hasExtra("estado")) {
            Intent intent = new Intent(this, FaceTrackerActivity.class);
            startActivityForResult(intent, cons);
        }else{
            cargarloTodo(this.getIntent());
        }
        //----------- button PLAY



       /* btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mPlayer.isPlaying())
                {
                    mPlayer.start();
                    tvMsg.setText("PLAY");
                }
            }
        });
        //----------- button PAUSE
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mPlayer.isPlaying())
                {
                    mPlayer.pause();
                    tvMsg.setText("PAUSE");
                }
            }
        });

        //----------- button STOP
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlayer != null) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = MediaPlayer.create(ViewFullImage.this, Uri.parse(url));
                    tvMsg.setText(":)");
                }
            }
        });

        //-------------------button Next----------------
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mPlayer.isPlaying())
                {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = MediaPlayer.create(ViewFullImage.this, Uri.parse(url1));
                    mPlayer.start();
                    tvMsg.setText("Next");
                }
            }
        });*/

        //--- Se coloca el tiempo de duracion y se inicia el seek bar

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == cons) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                cargarloTodo(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void cargarloTodo(Intent intent){
        Bundle extras=intent.getExtras();
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String pagina ="http://androidsw1234.esy.es/";
        String estado=(String)extras.get("estado");
        if (estado.equals("consulta2.php"))
        {
            estado=estado+"?imei="+manager.getDeviceId();
        }
        pagina+=estado;
        mRequestQueue = VolleySingleton.getInstance().getmRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                pagina,
                "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        cargarList(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("deito","algun tipo de error");
            }
        });
        mRequestQueue.add(request);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnNext=(Button)findViewById(R.id.btnsiguiente);
        btnprevious=(Button)findViewById(R.id.btnatras);
        skSong = (SeekBar) findViewById(R.id.skSong);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnprevious.setOnClickListener(this);
        tvMsg.setText(estado);
        Lista=new ArrayList<>();
    }


    private void cargarList(JSONObject jsonObject) {
        JSONArray array;
        try {
            array = jsonObject.getJSONArray("result");
            for (int ik = 0; ik < array.length(); ik++) {
                JSONObject objeto = array.getJSONObject(ik);
                String x=objeto.getString("url");
                x=x.replace(" ","%20");
                int y=objeto.getInt("id");
                String z=objeto.getString("nombre");
                Lista.add(new Musica(y,z,x));
            }
            i=0;
            zise=Lista.size();
            url=Lista.get(i).getUrl();
            tvMsg.setText(Lista.get(i).getNombre());
            mPlayer = MediaPlayer.create(ViewFullImage.this, Uri.parse(url));
            tvTime.setText( getHRM(mPlayer.getDuration()) );
            skSong.setMax(mPlayer.getDuration());
            skSong.setProgress(mPlayer.getCurrentPosition());
            //cada segundo se actualiza el estado del seek bar
            skHandler.postDelayed(updateskSong, 1000);
       /* url1=GetAlImages.imageURLs[i+1];*/

// desde


        } catch (JSONException e) {

        }
    }

    //se hace uso de un hilo para actualizar el progreso de la reproducción
    Runnable updateskSong = new Runnable() {
        @Override
        public void run() {
            if (mPlayer==null){
                finish();
                return;
            }
            skSong.setProgress( mPlayer.getCurrentPosition() );
            tvTime.setText( getHRM(mPlayer.getDuration()) + " - " + getHRM(mPlayer.getCurrentPosition()) );
            skHandler.postDelayed(updateskSong, 1000);
        }
    };

    /**
     * Método que convierte milisegundos a Hora:Minuto:Segundo
     * @param milliseconds milliseconds
     * @return String HH:MM:SS
     * */
    private String getHRM(int milliseconds )
    {
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
        return ((hours<10)?"0"+hours:hours) + ":" +
                ((minutes<10)?"0"+minutes:minutes) + ":" +
                ((seconds<10)?"0"+seconds:seconds);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPlayer!=null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer=null;
        Intent intent = new Intent(ViewFullImage.this, SelectActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    /**
     * Cuando la actividad ya no es visible por el usuario,
     * se detiene el reproductor
     * */
 /*   @Override
    protected void onStop() {
        super.onStop();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            //mPlayer = MediaPlayer.create(MainActivity.this, R.raw.musica1);
            mPlayer = MediaPlayer.create(this, Uri.parse(url));
        }
    }*/



    @Override
    public void onClick(View v) {
        if (mPlayer==null)
            return;
        if (v == btnNext) {
            next();
        }

        if(v == btnPause){
           pause();
        }

        if(v == btnPlay){
            play();
        }
        if(v==btnStop){
            stop();
        }
        if(v==btnprevious){
           previous();
        }
    }

    private void previous() {
        mPlayer.stop();
        mPlayer.release();
        i--;
        if(i==-1){
            i=zise-1;
        }

        //url1=GetAlImages.imageURLs[i];
        mPlayer = MediaPlayer.create(ViewFullImage.this, Uri.parse(Lista.get(i).getUrl()));
        mPlayer.start();
        tvMsg.setText(Lista.get(i).getNombre());
    }

    private void play(){
        mPlayer.start();
        tvMsg.setText(Lista.get(i).getNombre());
    }

    private void pause(){
        mPlayer.pause();
        tvMsg.setText("PAUSE");
    }

    private void next(){
        mPlayer.stop();
        mPlayer.release();
        i++;
        if(i==zise){
            i=0;
        }
        //url1=GetAlImages.imageURLs[i];
        mPlayer = MediaPlayer.create(ViewFullImage.this, Uri.parse(Lista.get(i).getUrl()));
        mPlayer.start();
        tvMsg.setText(Lista.get(i).getNombre());
    }
    private void stop(){
        mPlayer.stop();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ViewFullImage.this, Uri.parse(Lista.get(i).getUrl()));
        tvMsg.setText("STOP");
    }

}
