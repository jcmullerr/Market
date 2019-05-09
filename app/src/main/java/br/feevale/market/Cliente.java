package br.feevale.market;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {
    private long Id;
    private String Nome;
    private String Endereco;
    private String Tipo;
    private String email;

    public Cliente(){

    }

    protected Cliente(Parcel in) {
        Id = in.readLong();
        Nome = in.readString();
        Endereco = in.readString();
        Tipo = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(Nome);
        dest.writeString(Endereco);
        dest.writeString(Tipo);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
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

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
