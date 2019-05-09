package br.feevale.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class CadastroBebida extends AppCompatActivity {
    EditText txtNome;
    EditText txtVolume;
    EditText txtValor;
    Switch swAcolica;
    CadDatabase db;
    Bebida Editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CadDatabase(this);
        setContentView(R.layout.activity_cadastro_bebida);
        txtNome = (EditText) findViewById(R.id.editNome);
        txtVolume = (EditText) findViewById(R.id.editVolume);
        txtValor = (EditText) findViewById(R.id.editValor);
        swAcolica = (Switch)findViewById(R.id.switchAcolica);
        Intent n = getIntent();
        if(n.getParcelableExtra("bebida") != null){
            Editar = n.getParcelableExtra("bebida");
            GerarBebida();
        }else{
            System.out.println("jjjjjj");
        }
    }
    public void AdicionarBebida(View v){
        Intent n =  getIntent();
        if( n.getStringExtra("estado") == null) {
            Bebida b = new Bebida();
            b.setNome(txtNome.getText().toString());
            b.setValor(Double.parseDouble(txtValor.getText().toString()));
            b.setVolume(Integer.parseInt(txtVolume.getText().toString()));
            if (swAcolica.isChecked()) {
                b.setAlcolica("S");
            } else {
                b.setAlcolica("N");
            }
            db.addBebida(b);
        }else{
            Editar.setNome(txtNome.getText().toString());
            Editar.setValor(Double.parseDouble(txtValor.getText().toString()));
            Editar.setVolume(Integer.parseInt(txtVolume.getText().toString()));
            if (swAcolica.isChecked()) {
                Editar.setAlcolica("S");
            } else {
                Editar.setAlcolica("N");
            }
            db.UpdateBebidaScript(Editar);
        }
        finish();
    }
    public void Cancelar(View v){
        finish();
    }

    public void GerarBebida(){
        if (Editar != null){
            txtNome.setText(Editar.getNome());
            txtValor.setText(String.valueOf(Editar.getValor()));
            txtVolume.setText(String.valueOf(Editar.getVolume()));
            if(Editar.getAlcolica().equals("S")){
                swAcolica.setChecked(true);
            }else{
                swAcolica.setChecked(false);
            }
        }
    }
}
