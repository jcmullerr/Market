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
import android.widget.ListView;
import android.widget.Toast;

public class ClientesActivity extends AppCompatActivity {
    ListView ListaClientes;
    CadDatabase db;
    ClienteListAdapter adap;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.btnEditar:
                AdapterView.AdapterContextMenuInfo menuuInfo;
                menuuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                int index = menuuInfo.position;

                Cliente c = db.getClientes().get(index);
                Intent n = new Intent(getBaseContext(),CadastroCliente.class);
                n.putExtra("cliente",c);
                n.putExtra("estado","edicao");
                startActivity(n);
                adap.notifyDataSetChanged();

                break;

            case R.id.btnExcluir:

                AdapterView.AdapterContextMenuInfo menuInfo;
                menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int i = menuInfo.position;
                db.Delete(db.getClientes().get(i).getId(), CadDatabase.ClienteTable.TABLE_NAME);

                Toast.makeText(this,"Cliente excluido com sucesso",Toast.LENGTH_LONG).show();

                adap.notifyDataSetChanged();

                break;

            case R.id.btnVisualizar:
                AdapterView.AdapterContextMenuInfo info;
                info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int pos = info.position;
                Intent a = new Intent(getBaseContext(),ListaVendasActivity.class);
                a.putExtra("codigo",String.valueOf(db.getClientes().get(pos).getId()));
                startActivity(a);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater min = getMenuInflater();
        min.inflate(R.menu.crud_menu,menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes_main);
        ListaClientes = (ListView)findViewById(R.id.Lista_Clientes);
        db = new CadDatabase(this);
        adap = new ClienteListAdapter(getBaseContext(),db);
        ListaClientes.setAdapter(adap);
        ListaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent n = new Intent(getBaseContext(),ListaProdutosActivity.class);
                n.putExtra("estado","inclusao");
                n.putExtra("cliente",db.getClientes().get(position));
                startActivity(n);
            }
        });
        registerForContextMenu(ListaClientes);
    }
    public void Adicionar(View v){
        Intent n = new Intent(getBaseContext(),CadastroCliente.class);
        n.putExtra("estado","insercao");
        startActivity(n);
        adap.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adap.notifyDataSetChanged();
    }
}
