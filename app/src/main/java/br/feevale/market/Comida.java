package br.feevale.market;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Comida implements Parcelable {
    private long Id;
    private String Nome;
    private int Calorias;
    private String Validade;
    private double Valor;
    private String Tipo;

    public Comida(){

    }


    protected Comida(Parcel in) {
        Id = in.readLong();
        Nome = in.readString();
        Calorias = in.readInt();
        Validade = in.readString();
        Valor = in.readDouble();
        Tipo = in.readString();
    }

    public static final Creator<Comida> CREATOR = new Creator<Comida>() {
        @Override
        public Comida createFromParcel(Parcel in) {
            return new Comida(in);
        }

        @Override
        public Comida[] newArray(int size) {
            return new Comida[size];
        }
    };

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

    public int getCalorias() {
        return Calorias;
    }

    public void setCalorias(int calorias) {
        Calorias = calorias;
    }

    public String getValidade() {
        return Validade;
    }

    public void setValidade(String validade) {
        Validade = validade;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(Nome);
        dest.writeLong(Calorias);
        dest.writeString(Validade);
        dest.writeDouble(Valor);
        dest.writeString(Tipo);
    }
}
