package br.feevale.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class CadastroCliente extends AppCompatActivity {
    EditText txtNome;
    EditText txtEndereco;
    EditText txtEmail;
    Switch sw;
    CadDatabase db;
    Cliente Editar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        txtNome = (EditText) findViewById(R.id.editNome);
        txtEmail = (EditText) findViewById(R.id.editEmail);
        txtEndereco = (EditText) findViewById(R.id.editEndereco);
        sw = (Switch) findViewById(R.id.switchTipoCliente);
        db = new CadDatabase(this);
        Intent n = getIntent();
        String estado = n.getStringExtra("estado");
        if(estado != null) {
            if (estado.equals("edicao")) {
                GerarCliente();
            }
        }
    }

    public void GerarCliente(){
        Intent n = getIntent();
        Cliente c = n.getParcelableExtra("cliente");
        txtNome.setText(c.getNome());
        txtEmail.setText(c.getEmail());
        txtEndereco.setText(c.getEndereco());
        if(c.getTipo().equals("B")){
            sw.setChecked(true);
        }else{
            sw.setChecked(false);
        }
        Editar = c;
    }
    public void AdicionarCliente(View v){
        Intent n = getIntent();
        if(n.getStringExtra("estado") != null) {
            if (n.getStringExtra("estado").equals("insercao")) {
                Cliente c = new Cliente();
                c.setNome(txtNome.getText().toString());
                c.setEndereco(txtEndereco.getText().toString());
                c.setEmail(txtEmail.getText().toString());
                if (sw.isChecked()){
                    c.setTipo("B");
                }else{
                    c.setTipo("R");
                }
                db.addCliente(c);
            } else {
                Editar.setNome(txtNome.getText().toString());
                Editar.setEndereco(txtEndereco.getText().toString());
                Editar.setEmail(txtEmail.getText().toString());
                if (sw.isChecked()){
                    Editar.setTipo("B");
                }else{
                    Editar.setTipo("R");
                }
                db.UpdateClienteScript(Editar);
            }
        }

        finish();
    }
    public void Cancelar(View v){
        finish();
    }
}
