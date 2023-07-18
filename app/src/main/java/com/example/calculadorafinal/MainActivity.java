package com.example.calculadorafinal;

import static org.mozilla.javascript.Context.enter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView Operacaoview, Resultadosview, Operrador;
    MaterialButton buttonAC, buttonVirgula;
    MaterialButton buttonDividir, buttonX, buttonMenos, buttonMais, buttonIgual, buttonfx;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Operacaoview = findViewById(R.id.Operacao);
        Resultadosview = findViewById(R.id.ResultadoHistorico);
        Operrador = findViewById(R.id.Operrador);

        assignId(R.id.button_AC);
        assignId(R.id.button_Virgula);
        assignId(R.id.button_0);
        assignId(R.id.button_1);
        assignId(R.id.button_2);
        assignId(R.id.button_3);
        assignId(R.id.button_4);
        assignId(R.id.button_5);
        assignId(R.id.button_6);
        assignId(R.id.button_7);
        assignId(R.id.button_8);
        assignId(R.id.button_9);
        assignId(R.id.button_Mais);
        assignId(R.id.button_Menos);
        assignId(R.id.button_Igual);
        assignId(R.id.button_X);
        assignId(R.id.button_Dividir);
        assignId(R.id.button_FX);
    }


    void assignId(int id){
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
    MaterialButton button = (MaterialButton) v;
    String buttonText = button.getText().toString();

//    ResultadoHistoricoview.setText(buttonText);
    String PorCalcular = Operacaoview.getText().toString();

    if (buttonText.equals("+")){
            int a = Integer.parseInt(Operacaoview.getText().toString());
            Resultadosview.setText(String.valueOf(a));
            Operrador.setText(buttonText);
            Operacaoview.setText(String.valueOf(0));
            return;
    }

    if (buttonText.equals("-")){
            Operrador.setText(buttonText);
            int a = Integer.parseInt(Operacaoview.getText().toString());
            Resultadosview.setText(String.valueOf(a));
            Operacaoview.setText(String.valueOf(0));
            return;
    }

    if (buttonText.equals("AC")){
        Operacaoview.setText("");
        Resultadosview.setText("0");
        Operrador.setText("");
        return;
    }


    if (buttonText.equals("=")){
        int a = Integer.parseInt(Resultadosview.getText().toString());
        int b = Integer.parseInt(Operacaoview.getText().toString());

            int res1 = a + b;
            Resultadosview.setText(String.valueOf(res1));
            Operacaoview.setText("");
            return;
    }

        Operacaoview.setText("nao calculou");
        PorCalcular = PorCalcular + buttonText;//concatenar os valores na view de operacoes
        Operacaoview.setText(PorCalcular); //apresentar os valores por ser calculados na textview de operacoes

    String Resultadofinal = getResult(PorCalcular) ;


    //se o resultado final for diferente de "erro" o sistema ira apresentar o resultad da nossa operacao
        //caso nao, o sistema ira apresntar o erro, nao podendo dessa forma apresentar o resultado final da nosaa operacao de calculo
    if (!Resultadofinal.equals("ERRO!!!!!!!!!!!!!!!")){
        Resultadosview.setText(Resultadofinal);
    }
    }


    /*METODO PARA FAZER O CALCULO E GERIR ERROS DE CALCULO, usando o try catch*/
    String getResult(String data){

        try {
            org.mozilla.javascript.Context context = enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String ResultadoFinal = (String) /*para fazer equivaller os dados inseridos na variavel ResultadoFinal como String*/ context.evaluateString(scriptable,data,"javascript", 1, null);

            //implemntando um if para remocao da casa decimal
            if (ResultadoFinal.endsWith(".0")){
                ResultadoFinal = ResultadoFinal.replace(".0","");
            }

            return ResultadoFinal;
        }catch (Exception e){
            return "ERRO!!!!!!!!!!!!!!!";
        }
    }
}