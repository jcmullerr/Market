package br.feevale.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroComida extends AppCompatActivity {
    EditText txtNome;
    EditText txtValidade;
    EditText txtValor;
    EditText txtCalorias;
    Switch swTipo;
    CadDatabase db;
    Comida Editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_comida);
        txtNome = (EditText) findViewById(R.id.editNome);
        txtValidade = (EditText) findViewById(R.id.editValidade);
        txtValor = (EditText) findViewById(R.id.editValor);
        txtCalorias = (EditText) findViewById(R.id.editCalorias);
        swTipo = (Switch) findViewById(R.id.switchTemperatura);
        db = new CadDatabase(this);
        Intent n = getIntent();
        if (n.getParcelableExtra("comida") != null) {
            Editar = n.getParcelableExtra("comida");
        }
        GerarComida();
    }

    public void AdicionarComida(View v) throws Exception {
        Intent n = getIntent();
        if (n.getStringExtra("estado") == null) {
            Comida c = new Comida();
            c.setNome(txtNome.getText().toString());
            c.setValor(Double.parseDouble(txtValor.getText().toString()));
            c.setCalorias(Integer.parseInt(txtCalorias.getText().toString()));
            c.setValidade(txtValidade.getText().toString());
            if (swTipo.isChecked()) {
                System.out.println("quente");
                c.setTipo("Q");
            } else {
                System.out.println("frio");
                c.setTipo("F");
            }
            long id = db.addComida(c);
        } else {
            Editar.setNome(txtNome.getText().toString());
            Editar.setValor(Double.parseDouble(txtValor.getText().toString()));
            Editar.setCalorias(Integer.parseInt(txtCalorias.getText().toString()));

            Editar.setValidade(txtValidade.getText().toString());
            if (swTipo.isChecked()) {
                Editar.setTipo("Q");
            } else {
                Editar.setTipo("F");
            }
            db.UpdateComidaScript(Editar);
        }
        finish();
    }

    public void Cancela(View v) {
        finish();
    }

    public void GerarComida() {
        if(Editar != null){
            txtNome.setText(Editar.getNome());
            txtCalorias.setText(String.valueOf(Editar.getCalorias()));
            txtValidade.setText(Editar.getValidade());
            txtValor.setText(String.valueOf(Editar.getValor()));
           if(Editar.getTipo().equals("Q")){
               swTipo.setChecked(true);
           }else{
               swTipo.setChecked(false);
           }
        }
    }
}
