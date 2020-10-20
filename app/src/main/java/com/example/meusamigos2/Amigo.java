package com.example.meusamigos2;

import java.io.Serializable;

public class Amigo implements Serializable
{
    private int id;
    private String nome;
    private String celular;
    private int status;

    public Amigo (int id, String nome, String celular, int status)
    {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.status = status;
    }

    public int getId()
    {
        return this.id;
    }

    public String getNome()
    {
        return this.nome;
    }

    public String getCelular()
    {
        return this.celular;
    }

    public int getStatus()
    {
        return this.status;
    }

    @Override
    public boolean equals(Object o)
    {
        return this.id == ((Amigo)o).id;
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }
}
