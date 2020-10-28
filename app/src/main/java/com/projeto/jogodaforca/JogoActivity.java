package com.projeto.jogodaforca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class JogoActivity extends Activity {

    public static final String EXTRA_QUANTIDADE = "0";
    public static final String EXTRA_PALAVRA = "palavra";

    private String mensagemToast = "Vazio";
    private int qtdTentativas = 0;
    private StringBuilder stringBuilder = new StringBuilder();
    private char[] palavraCorreta;
    private char[] palavraUsuario;

    private TextView tentativas;
    private EditText letra;
    private EditText palavra;
    private Button btnLetra;
    private Button btnPalavra;
    private TextView txtPalavra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        tentativas = (TextView) findViewById(R.id.txtTentativas);
        txtPalavra = (TextView) findViewById(R.id.txtPalavra);
        letra = (EditText) findViewById(R.id.editLetra);
        palavra = (EditText) findViewById(R.id.editPalavra);

        Intent intentReceiver = getIntent();

        String auxPalavra = intentReceiver.getExtras().get(EXTRA_PALAVRA).toString();
        String strTentativas = intentReceiver.getExtras().get(EXTRA_QUANTIDADE).toString();

        qtdTentativas = Integer.parseInt(strTentativas);

        palavraCorreta = new char[auxPalavra.toCharArray().length];

        for (int i = 0; i < auxPalavra.toCharArray().length; i++) {
            char letra = auxPalavra.toCharArray()[i];

            palavraCorreta[i] = letra;
        }

        palavraUsuario = new char[palavraCorreta.length];

        StringBuilder palavraHint = new StringBuilder();

        for (int i = 0; i < palavraCorreta.length; i++) {
            palavraHint.append("_");
            palavraUsuario[i] = '_';
        }

        txtPalavra.setText(String.valueOf(palavraHint));

        tentativas.setText(String.valueOf((qtdTentativas)));
    }

    public void onClickLetra(View view) {
        char letraTentativa = letra.getText().toString().toLowerCase().charAt(0);
        boolean letraErrada = true;

        if (letraTentativa != '\0') {

            for (char letra : palavraCorreta) {
                if (letraTentativa == letra) {
                    mensagemToast = "Letra está na palavra!";
                    insereTentativaLetra(letraTentativa);
                    letraErrada = false;
                }
            }

            if (letraErrada) {
                mensagemToast = "Letra não está na palavra!";
                diminuiTentativas();
                insereErros(letraTentativa);
            }
        }

        verificaTentativa(mensagemToast);
    }

    public void onClickPalavra(View view) {

        char[] strPalavra = palavra.getText().toString().toLowerCase().toCharArray();
        boolean palavraErrada = true;

        if (strPalavra[0] != '\0') {
            if (Arrays.equals(palavraCorreta, strPalavra) ) {
                mensagemToast = "Palavra correta, você acertou parabéns!";
                palavraUsuario = strPalavra;
            }
            else {
                mensagemToast = "Palavra incorreta, você perdeu!";
                qtdTentativas = 0;
            }
        }

        verificaTentativa(mensagemToast);
    }

    public void verificaTentativa(String mensagemToast) {

        palavra.setText("");
        letra.setText("");

        verificaGameOver();
        verificaVitoria();

        Toast.makeText(this, mensagemToast, Toast.LENGTH_SHORT).show();
    }

    private void diminuiTentativas() {
        qtdTentativas--;
        tentativas.setText(String.valueOf(qtdTentativas));
    }

    private void verificaGameOver() {
        if (qtdTentativas == 0) {
            esperaFinalizacao("Acabou suas tentativas, você perdeu!");
        }
    }

    private void esperaFinalizacao(String mensagemToast) {

        btnLetra = (Button) findViewById(R.id.btnLetra);
        btnPalavra = (Button) findViewById(R.id.btnPalavra);

        btnLetra.setEnabled(false);
        btnPalavra.setEnabled(false);

        Toast.makeText(this, mensagemToast, Toast.LENGTH_LONG).show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                enviaTelaPrinciapal();
            }
        }, 3000);
    }

    private void verificaVitoria() {
        if (Arrays.equals(palavraCorreta, palavraUsuario)) {
            qtdTentativas = 0;
            esperaFinalizacao("Palavra correta, você acertou parabéns!");
        }
    }

    private void enviaTelaPrinciapal() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void insereTentativaLetra(char letra) {

        for (int i = 0; i < palavraCorreta.length; i++) {
            if (letra == palavraCorreta[i]) {
                palavraUsuario[i] = palavraCorreta[i];
            } else if (palavraUsuario[i] == '_') {
                palavraUsuario[i] = '_';
            }
        }

        String auxPalavra = String.valueOf(palavraUsuario);

        txtPalavra.setText(auxPalavra);
    }


    private void insereErros(char tentativa) {

        stringBuilder.append(tentativa + "; ");

        TextView textView = (TextView) findViewById(R.id.txtLetrasTentadas);

        textView.setText(stringBuilder);
    }
}