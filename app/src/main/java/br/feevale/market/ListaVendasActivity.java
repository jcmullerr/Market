package br.feevale.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListaVendasActivity extends AppCompatActivity {
    TextView txtValorSomado;
    ListView ListaProdutos;
    CadDatabase db;
    VendaListAdapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vendas);
        txtValorSomado = (TextView) findViewById(R.id.textValorSomado);
        ListaProdutos = (ListView) findViewById(R.id.ListaVendas);
        db = new CadDatabase(this);
        Intent n  =  getIntent();
        adap = new VendaListAdapter(this,db,Long.parseLong(n.getStringExtra("codigo")));
        ListaProdutos.setAdapter(adap);
        txtValorSomado.setText("R$"+String.valueOf(db.getSoma(Long.parseLong(n.getStringExtra("codigo")))));
    }
    public void Fechar(View v){
        finish();
    }
}
