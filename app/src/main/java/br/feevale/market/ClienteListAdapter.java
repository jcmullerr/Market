package br.feevale.market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClienteListAdapter extends BaseAdapter {
    CadDatabase db;
    Context ctx;
    LayoutInflater inflater;

    public ClienteListAdapter(Context ct,CadDatabase db){
        inflater = LayoutInflater.from(ct);
        this.db = db;
    }
    @Override
    public int getCount() {
        return db.getClientes().size();
    }

    @Override
    public Object getItem(int position) {
        return db.getClientes().get(position);
    }

    @Override
    public long getItemId(int position) {
        return db.getClientes().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.cliente_list_item, parent, false);
        TextView txtNome = (TextView) v.findViewById(R.id.textNome);
        TextView txtTipo = (TextView) v.findViewById(R.id.textTipo);

        Cliente c = db.getClientes().get(position);
        txtNome.setText(c.getNome());
        if (c.getTipo().equals("B")){
            txtTipo.setText("Bar");
        }else{
            txtTipo.setText("Restaurante");
        }

        return v;
    }
}
