package br.feevale.market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BebidaListAdapter extends BaseAdapter {
    CadDatabase db;
    Context ctx;
    LayoutInflater inflater;

    public BebidaListAdapter(Context ct,CadDatabase db){
        inflater = LayoutInflater.from(ct);
        this.db = db;
    }
    @Override
    public int getCount() {
            return db.getBebidas().size();
    }

    @Override
    public Object getItem(int position) {
            return db.getBebidas().get(position);
    }

    @Override
    public long getItemId(int position) {
            return db.getBebidas().get(position).getId();    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.produto_list_item, parent, false);
        TextView txtNome = (TextView) v.findViewById(R.id.textNome);
        TextView txtTipo = (TextView) v.findViewById(R.id.textTipo);
        TextView txtValor = (TextView) v.findViewById(R.id.textPreco);

        try{
            Bebida b = db.getBebidas().get(position);
            txtNome.setText(b.getNome());
            txtValor.setText("R$"+b.getValor());
            if (b.getAlcolica().equals("S")){
                txtTipo.setText("Alcolica");
            }else{
                txtTipo.setText("Não alcolica");
            }
        }catch(Exception e){
            System.out.println("Deu ruim");
        }
        System.out.println("chegou");

        return v;
    }
}
