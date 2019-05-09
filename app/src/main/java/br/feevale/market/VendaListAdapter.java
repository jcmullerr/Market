package br.feevale.market;

        import android.content.Context;
        import android.database.Cursor;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

public class VendaListAdapter extends BaseAdapter {
    CadDatabase db;
    Context ctx;
    LayoutInflater inflater;
    long id;

    public VendaListAdapter(Context ct,CadDatabase db,long id){
        inflater = LayoutInflater.from(ct);
        this.db = db;
        this.id = id;
    }
    @Override
    public int getCount() {
        try{
            return db.getVendas(id).size();
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            return db.getVendas(id).get(position);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        try {
            return db.getVendas(id).get(position).getId();
        }catch (Exception e){
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.venda_list_item, parent, false);
        TextView txtNome = (TextView) v.findViewById(R.id.textNomeVenda);
        TextView txtValor = (TextView) v.findViewById(R.id.textvalorVenda);

        try {
            Venda vv = db.getVendas(id).get(position);
            if (vv.getComida() != null) {
                txtNome.setText(vv.getBebida().getNome());
                txtValor.setText(String.valueOf(vv.getComida().getValor()));
                System.out.println(vv.getCliente().getNome());
                System.out.println(vv.getComida().getNome());
            }else{
                txtNome.setText(vv.getComida().getNome());
                txtValor.setText(String.valueOf(vv.getBebida().getValor()));
                System.out.println(vv.getCliente().getNome());
                System.out.println(vv.getBebida().getNome());
            }
        }catch(Exception e){
            System.out.println("deu ruim");
        }
        return v;
    }
}

