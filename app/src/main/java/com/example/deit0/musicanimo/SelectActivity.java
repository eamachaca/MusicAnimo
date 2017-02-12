package com.example.deit0.musicanimo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SelectActivity extends AppCompatActivity {
    private final int DURACION_SPLASH = 3000; // 3 segundos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menucamara, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuid1:
                lanzareproduccion();

                break;
            case R.id.menuid2:
                lanzacamara();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void lanzareproduccion(){
        Intent intent = new Intent(this,ViewFullImage.class);
  /*      intent.putExtra("usuario", usuario);*/
        intent.putExtra("estado", "consulta2.php");
      /*  startActivity(new Intent(this, ImageListView.class));*/
        startActivity(intent);

    }
    private void lanzacamara(){
        Intent intent = new Intent(this,ViewFullImage.class);
  /*      intent.putExtra("usuario", usuario);*/
        startActivity(intent);

    }

}
