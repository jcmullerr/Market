package br.feevale.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class ListaProdutosActivity extends AppCompatActivity {
    ListView ListaProdutos;
    CadDatabase db;
    ComidaListAdapter adapcomida;
    BebidaListAdapter adapBebida;
    Button btn;

    Switch sw;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater min = getMenuInflater();
        min.inflate(R.menu.simple_crud_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnEditarProduto:
                if (sw.isChecked()) {
                    AdapterView.AdapterContextMenuInfo menuuInfo;
                    menuuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                    int index = menuuInfo.position;

                    try {
                        Comida c = db.getComidas().get(index);
                        Intent j = new Intent(getBaseContext(), CadastroComida.class);
                        j.putExtra("comida", c);
                        j.putExtra("estado", "edicao");
                        System.out.println(c.getTipo());
                        startActivity(j);
                        adapcomida.notifyDataSetChanged();
                    } catch (Exception e) {

                    }
                } else {
                    AdapterView.AdapterContextMenuInfo menuuInfo;
                    menuuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                    int index = menuuInfo.position;
                    try {
                        Bebida b = db.getBebidas().get(index);
                        Intent n = new Intent(getBaseContext(), CadastroBebida.class);
                        n.putExtra("bebida", b);
                        n.putExtra("estado", "edicao");
                        startActivity(n);
                        adapBebida.notifyDataSetChanged();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                break;

            case R.id.btnExcluirProduto:

                AdapterView.AdapterContextMenuInfo menuInfo;
                menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int i = menuInfo.position;
                if (sw.isChecked()) {
                    db.Delete(db.getClientes().get(i).getId(), CadDatabase.ComidaTable.TABLE_NAME);
                    adapcomida.notifyDataSetChanged();
                }else{
                    db.Delete(db.getClientes().get(i).getId(), CadDatabase.BebidaTable.TABLE_NAME);
                    adapBebida.notifyDataSetChanged();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        ListaProdutos = (ListView) findViewById(R.id.ListaProdutos);
        db = new CadDatabase(this);
        adapcomida = new ComidaListAdapter(getBaseContext(), db);
        adapBebida = new BebidaListAdapter(getBaseContext(), db);
        sw = (Switch) findViewById(R.id.switchTIpo);
        ListaProdutos.setAdapter(adapBebida);
        btn = (Button) findViewById(R.id.btnAdicionar);
        btn.setText("Adicionar bebida");
        registerForContextMenu(ListaProdutos);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btn.setText("Adicionar comida");
                    ListaProdutos.setAdapter(adapcomida);
                    adapcomida.notifyDataSetChanged();
                } else {
                    btn.setText("Adicionar bebida");
                    ListaProdutos.setAdapter(adapBebida);
                    adapBebida.notifyDataSetChanged();
                }
            }
        });
        ListaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sw.isChecked()) {
                    try {
                        Intent n = getIntent();
                        Cliente cliente = n.getParcelableExtra("cliente");
                        Comida c = db.getComidas().get(position);
                        Venda venda = new Venda();
                        venda.setComida(c);
                        venda.setValor(c.getValor());
                        venda.setCliente(cliente);
                        db.addVenda(venda);
                        Toast.makeText(getBaseContext(),"Venda realizada com sucesso",Toast.LENGTH_LONG);
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    Intent n = getIntent();
                    Cliente cliente = n.getParcelableExtra("cliente");
                    Bebida bebida = db.getBebidas().get(position);
                    Venda venda = new Venda();
                    venda.setBebida(bebida);
                    venda.setValor(bebida.getValor());
                    venda.setCliente(cliente);
                    db.addVenda(venda);
                    Toast.makeText(getBaseContext(),"Venda realizada com sucesso",Toast.LENGTH_LONG);
                    finish();
                }
            }
        });
        try {
            System.out.println(db.getComidas().size());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void AdicionarProduto(View v){
        if (sw.isChecked()){
            Intent n  =  new Intent(getBaseContext(),CadastroComida.class);
            startActivity(n);

        }else{
            Intent n  =  new Intent(getBaseContext(),CadastroBebida.class);
            startActivity(n);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapcomida.notifyDataSetChanged();
        adapBebida.notifyDataSetChanged();
    }
}
