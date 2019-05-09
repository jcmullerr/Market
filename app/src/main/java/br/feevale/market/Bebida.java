package br.feevale.market;

import android.os.Parcel;
import android.os.Parcelable;

public class Bebida implements Parcelable {
    private long Id;
    private String Nome;
    private double Valor;
    private String Alcolica;
    private int Volume;

    public Bebida(){

    }

    protected Bebida(Parcel in) {
        Id = in.readLong();
        Nome = in.readString();
        Valor = in.readDouble();
        Alcolica = in.readString();
        Volume = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(Nome);
        dest.writeDouble(Valor);
        dest.writeString(Alcolica);
        dest.writeInt(Volume);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bebida> CREATOR = new Creator<Bebida>() {
        @Override
        public Bebida createFromParcel(Parcel in) {
            return new Bebida(in);
        }

        @Override
        public Bebida[] newArray(int size) {
            return new Bebida[size];
        }
    };

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }
    public String getAlcolica(){
        return  this.Alcolica;
    }
    public void setAlcolica(String alcolica){
        this.Alcolica = alcolica;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int volume) {
        Volume = volume;
    }
}
