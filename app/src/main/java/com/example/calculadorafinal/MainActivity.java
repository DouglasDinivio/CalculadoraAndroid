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

    TextView Operacaoview, Resultadosview;
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

        assignId(buttonAC, R.id.button_AC);
        assignId(buttonVirgula, R.id.button_Virgula);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonMais, R.id.button_Mais);
        assignId(buttonMenos, R.id.button_Menos);
        assignId(buttonIgual, R.id.button_Igual);
        assignId(buttonX, R.id.button_X);
        assignId(buttonDividir, R.id.button_Dividir);
        assignId(buttonfx, R.id.button_FX);
    }



    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    MaterialButton button = (MaterialButton) v;
    String buttonText = button.getText().toString();
//    ResultadoHistoricoview.setText(buttonText);
    String PorCalcular = Operacaoview.getText().toString();
    if (buttonText.equals("AC")){
        Operacaoview.setText("");
        Resultadosview.setText("0");
        return;
    }

    if (buttonText.equals("=")){
        Resultadosview.setText(Operacaoview.getText());
        return;
    }else {
        PorCalcular = PorCalcular + buttonText; //concatenar os valores na view de operacoes
    }
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