package com.example.meusamigos2;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AmigoHolder extends RecyclerView.ViewHolder{
    public TextView txvAmigo;
    public TextView txvAmigoTel;
    public ImageButton btnTelegran;
    public ImageButton btnZap;
    public ImageButton btnSms;
    public ImageButton btnCall;
    public ImageButton btnRestaurar;
    public ImageButton btnEditar;
    public ImageButton btnRemover;

    public AmigoHolder(View itemView)
    {
        super(itemView);
        txvAmigo = (TextView)itemView.findViewById(R.id.txvAmigo);
        txvAmigoTel = (TextView)itemView.findViewById(R.id.txvAmigoTel);
        btnZap = (ImageButton)itemView.findViewById(R.id.btnZap);
        btnSms = (ImageButton)itemView.findViewById(R.id.btnSms);
        btnCall = (ImageButton)itemView.findViewById(R.id.btnCall);
        btnRestaurar = (ImageButton)itemView.findViewById(R.id.btnRestaurar);
        btnEditar = (ImageButton)itemView.findViewById(R.id.btnEditar);
        btnRemover = (ImageButton)itemView.findViewById(R.id.btnRemover);
    }
}
