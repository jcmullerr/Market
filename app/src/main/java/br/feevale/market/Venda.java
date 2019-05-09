package br.feevale.market;

public class Venda {
    private long Id;
    private Bebida Bebida;
    private Comida Comida;
    private Cliente Cliente;
    private double Valor;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public br.feevale.market.Bebida getBebida() {
        return Bebida;
    }

    public void setBebida(br.feevale.market.Bebida bebida) {
        Bebida = bebida;
    }

    public br.feevale.market.Comida getComida() {
        return Comida;
    }

    public void setComida(br.feevale.market.Comida comida) {
        Comida = comida;
    }

    public br.feevale.market.Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(br.feevale.market.Cliente cliente) {
        Cliente = cliente;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }
}
