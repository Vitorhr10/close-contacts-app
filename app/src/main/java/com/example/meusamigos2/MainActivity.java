package com.example.meusamigos2;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configurarRecyclerView();

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

    private void configurarRecyclerView ()
    {
        recyclerView = (RecyclerView)findViewById(R.id.rcvAmigos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AmigoDAO dao = new AmigoDAO(this);
        adapter = new AmigoAdapter(dao.retornarAmigos());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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