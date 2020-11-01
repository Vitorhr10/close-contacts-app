package com.example.meusamigos2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class AmigoDAO {
    private final String TABLE_AMIGOS = "Amigos";
    private DbGateway gw;

    public AmigoDAO (Context context)
    {
        gw = DbGateway.getInstance(context);
    }


    public boolean salvar(String nome, String celular, Integer status)
    {
        return salvar(0, nome, celular, status);
    }

    public boolean salvar(int id, String nome, String celular, Integer status)
    {
        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("Celular", celular);
        cv.put("Status", status);

        if (id > 0)
        {
            if (status == 0) {
                cv.put("Status", status = 2);
                return gw.getDatabase().update(TABLE_AMIGOS, cv, "ID = ?", new String[]{id + ""}) > 0;
            } else {
                cv.put("Status", status);
                return gw.getDatabase().update(TABLE_AMIGOS, cv, "ID = ?", new String[]{id + ""}) > 0;
            }
        }
        else
        {
            cv.put("Status", status = 1);
            return gw.getDatabase().insert(TABLE_AMIGOS, null, cv) > 0;
        }
    }

    public boolean recuperarAmigo(int id, int status)
    {
        ContentValues cv = new ContentValues();
        cv.put("Status", status = 2);
        return gw.getDatabase().update(TABLE_AMIGOS, cv, "ID = ?", new String[] { id + ""}) > 0;
    }

    public boolean deletarFicticio(int id, int status)
    {
        ContentValues cv = new ContentValues();
        cv.put("Status", status = 0);
        return gw.getDatabase().update(TABLE_AMIGOS, cv, "ID = ?", new String[] { id + ""}) > 0;
    }


    public boolean deletar(int id)
    {
        return gw.getDatabase().delete(TABLE_AMIGOS, "ID = ?", new String[] { id + ""}) > 0;
    }

    public List<Amigo> retornarAmigos(int i) {
        List<Amigo> amigos = new ArrayList<>();

        if (i == 0) {
            Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Amigos where Status = 0", null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String nome = cursor.getString(cursor.getColumnIndex("Nome"));
                String celular = cursor.getString(cursor.getColumnIndex("Celular"));
                int status = cursor.getInt(cursor.getColumnIndex("Status"));
                amigos.add(new Amigo(id, nome, celular, status));
            }
            cursor.close();
        } else {
            Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Amigos where Status > 0", null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String nome = cursor.getString(cursor.getColumnIndex("Nome"));
                String celular = cursor.getString(cursor.getColumnIndex("Celular"));
                int status = cursor.getInt(cursor.getColumnIndex("Status"));
                amigos.add(new Amigo(id, nome, celular, status));
            }
            cursor.close();
        }

        return amigos;
    }


    public Amigo retornarUltimoAmigo()
    {
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Amigos order by id desc", null);

        if (cursor.moveToFirst())
        {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            String celular = cursor.getString(cursor.getColumnIndex("Celular"));
            int status = cursor.getInt(cursor.getColumnIndex("Status"));
            cursor.close();
            return new Amigo(id, nome, celular, status);
        }

        return null;
    }
}
