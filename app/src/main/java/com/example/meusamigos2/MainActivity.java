package com.example.meusamigos2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean lista_deletados = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();

        if (lista_deletados) {
            configurarRecyclerView(0);
        } else {
            configurarRecyclerView(1);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                findViewById(R.id.include_amigos_listagem).setVisibility(View.INVISIBLE);
            }
        });

        final TextView titulo = (TextView) findViewById(R.id.txtTitulo);

        final FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lista_deletados) {
                    lista_deletados = !lista_deletados;
                    findViewById(R.id.include_amigos_deletados).setVisibility(View.INVISIBLE);
                    findViewById(R.id.include_amigos_listagem).setVisibility(View.VISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    fab2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CC0000")));
                    fab2.setImageResource(R.drawable.ic_delete_48);
                    configurarRecyclerView(1);
                } else {
                    lista_deletados = !lista_deletados;
                    findViewById(R.id.include_amigos_deletados).setVisibility(View.VISIBLE);
                    findViewById(R.id.include_amigos_listagem).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                    fab2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0000FF")));
                    fab2.setImageResource(R.drawable.ic_list_alt_48);
                    configurarRecyclerView(0);
                }
            }
        });

        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtNome = (EditText)findViewById(R.id.edtNome);
                EditText edtCelular = (EditText)findViewById(R.id.edtCelular);

                String nome = edtNome.getText().toString();
                String celular = edtCelular.getText().toString();
                int status = 0;

                AmigoDAO dao = new AmigoDAO(getBaseContext());
                boolean ok;

                if (amigoEditado != null)
                {
                    ok = dao.salvar (amigoEditado.getId(), nome, celular, 0);
                }
                else
                {
                    ok = dao.salvar(nome, celular, status);
                }

                if(ok)
                {
                    Amigo amigo = dao.retornarUltimoAmigo();

                    if (amigoEditado != null)
                    {
                        adapter.atualizarAmigo(amigo);
                        amigoEditado = null;
                    }
                    else
                    {
                        adapter.adicionarAmigo(amigo);
                    }

                    edtNome.setText("");
                    edtCelular.setText("");

                    Snackbar.make(view, "Amigo salvo com sucesso!", Snackbar.LENGTH_LONG)
                            .setAction("Inclusão", null).show();

                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                    findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    findViewById(R.id.include_amigos_listagem).setVisibility(View.VISIBLE);

                }
                else
                {
                    Snackbar.make(view, "Erro ao gravar os dados do Amigo ["+nome+"]", Snackbar.LENGTH_LONG)
                            .setAction("Inclusão", null).show();
                }
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
                findViewById(R.id.include_amigos_listagem).setVisibility(View.VISIBLE);
            }
        });

        Button btnFinalizar = (Button)findViewById(R.id.btnFinalizar);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra("amigo"))
        {
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            findViewById(R.id.include_amigos_listagem).setVisibility(View.INVISIBLE);

            amigoEditado = (Amigo) intent.getSerializableExtra("amigo");
            EditText edtNome = (EditText)findViewById(R.id.edtNome);
            EditText edtCelular = (EditText)findViewById(R.id.edtCelular);

            edtNome.setText(amigoEditado.getNome());
            edtCelular.setText(amigoEditado.getCelular());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    RecyclerView recyclerView;
    AmigoAdapter adapter;
    AmigoAdapterDeletados adapterDeletados;

    private void configurarRecyclerView (int i)
    {
        if (i == 0) {
            recyclerView = (RecyclerView) findViewById(R.id.rcvAmigosDeletados);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            AmigoDAO dao = new AmigoDAO(this);
            adapterDeletados = new AmigoAdapterDeletados(dao.retornarAmigos(i));
            recyclerView.setAdapter(adapterDeletados);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        } else {
            recyclerView = (RecyclerView) findViewById(R.id.rcvAmigos);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            AmigoDAO dao = new AmigoDAO(this);
            adapter = new AmigoAdapter(dao.retornarAmigos(1));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
    }


    Amigo amigoEditado = null;

    private int getIndex(Spinner spinner, String meuAmigo)
    {
        int indice = 0;
        int i = 0;

        for (i = 0; (i < spinner.getCount()) && (!spinner.getItemAtPosition(i).toString().equalsIgnoreCase(meuAmigo)); i++ );

        if (i == spinner.getCount())
        {
            indice = i;
        }

        return indice;
    }
}