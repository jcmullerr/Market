package br.feevale.market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ComidaListAdapter extends BaseAdapter {
    CadDatabase db;
    Context ctx;
    LayoutInflater inflater;

    public ComidaListAdapter(Context ct,CadDatabase db){
        inflater = LayoutInflater.from(ct);
        this.db = db;
    }
    @Override
    public int getCount() {
        try {
            return db.getComidas().size();
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            return db.getComidas().get(position);
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public long getItemId(int position) {
        try {
            return db.getComidas().get(position).getId();
        }catch (Exception e){
            return -1;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.produto_list_item, parent, false);
        TextView txtNome = (TextView) v.findViewById(R.id.textNome);
        TextView txtTipo = (TextView) v.findViewById(R.id.textTipo);
        TextView txtValor = (TextView) v.findViewById(R.id.textPreco);

        try{
            Comida c = db.getComidas().get(position);
            txtNome.setText(c.getNome());
            txtValor.setText("R$"+c.getValor());
            if (c.getTipo().equals("Q")){
                txtTipo.setText("Quente");
            }else{
                txtTipo.setText("Fria");
            }
        }catch(Exception e){

        }


        return v;
    }
}
