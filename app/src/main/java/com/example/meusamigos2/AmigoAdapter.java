package com.example.meusamigos2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AmigoAdapter extends RecyclerView.Adapter<AmigoHolder> {
    private final List<Amigo> amigos;

    public AmigoAdapter(List<Amigo> amigos) {
        this.amigos = amigos;
    }

    @Override
    public AmigoHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new AmigoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.amigos_dados, parent, false));
    }

    private Activity getActivity(View v)
    {
        Context c = v.getContext();

        while (c instanceof ContextWrapper)
        {
            if (c instanceof ContextWrapper)
            {
                return (Activity) c;
            }
            c = ((ContextWrapper)c).getBaseContext();
        }
        return null;
    }

    @Override
    public void onBindViewHolder (final AmigoHolder holder, int posicao)
    {
        holder.txvAmigo.setText(amigos.get(posicao).getNome());
        holder.txvAmigoTel.setText(amigos.get(posicao).getCelular());

        final Amigo amigo = amigos.get(posicao);

        holder.btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("sms", amigo);
                activity.finish();
                activity.startActivity(intent);
            }
        });

        holder.btnZap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("sms", amigo);
                activity.finish();
                activity.startActivity(intent);
            }
        });

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = holder.txvAmigoTel.getText().toString();

                Uri uri = Uri.parse("tel:" + numero);

                Activity activity = getActivity(v);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity.finish();
                activity.startActivity(intent);
            }
        });

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                Activity activity = getActivity(view);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("amigo", amigo);
                activity.finish();
                activity.startActivity(intent);
              }
           }
        );

        holder.btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("Confirmação de Exclusão")
                        .setMessage("Tem certeza de que deseja excluir seu amigo?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                AmigoDAO dao = new AmigoDAO(view.getContext());
                                boolean ok = dao.deletarFicticio(amigo.getId(), amigo.getStatus());

                                if (ok)
                                {
                                    deletarAmigo(amigo);
                                    Snackbar.make(view, "Amigo excluído! :-(((", Snackbar.LENGTH_LONG)
                                            .setAction("Ação", null).show();
                                }
                                else
                                {
                                    Snackbar.make(view, "Erro ao excluir o amigo!", Snackbar.LENGTH_LONG)
                                            .setAction("Ação", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return amigos != null ? amigos.size() : 0;
    }

    public void adicionarAmigo(Amigo amigo)
    {
        amigos.add(amigo);
        notifyItemInserted(getItemCount());
    }

    public void atualizarAmigo(Amigo amigo)
    {
        amigos.set(amigos.indexOf(amigo), amigo);
        notifyItemChanged(amigos.indexOf(amigo));
    }

    public void deletarAmigo (Amigo amigo)
    {
        int posicao = amigos.indexOf(amigo);
        amigos.remove(posicao);
        notifyItemRemoved(posicao);
    }
}
