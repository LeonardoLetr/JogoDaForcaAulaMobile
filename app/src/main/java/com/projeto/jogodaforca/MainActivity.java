package com.projeto.jogodaforca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    /*Leonardo Rodrigues RA: 00101457*/
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickIniciaJogo(View view) {

        Intent intent = new Intent(this, JogoActivity.class);

        EditText palavra = (EditText) findViewById(R.id.editLetra);
        EditText qtd = (EditText) findViewById(R.id.editPalavra);

        String strPalavra = palavra.getText().toString().toLowerCase();
        String strQtd = qtd.getText().toString();

        intent.putExtra(JogoActivity.EXTRA_PALAVRA, strPalavra.toLowerCase());
        intent.putExtra(JogoActivity.EXTRA_QUANTIDADE, strQtd);

        startActivity(intent);
    }

}
