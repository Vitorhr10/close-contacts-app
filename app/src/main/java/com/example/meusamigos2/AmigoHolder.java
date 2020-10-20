package com.example.meusamigos2;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AmigoHolder extends RecyclerView.ViewHolder{
    public TextView txvAmigo;
    public ImageButton btnEditar;
    public ImageButton btnRemover;

    public AmigoHolder(View itemView)
    {
        super(itemView);
        txvAmigo = (TextView)itemView.findViewById(R.id.txvAmigo);
        btnEditar = (ImageButton)itemView.findViewById(R.id.btnEditar);
        btnRemover = (ImageButton)itemView.findViewById(R.id.btnRemover);
    }
}
