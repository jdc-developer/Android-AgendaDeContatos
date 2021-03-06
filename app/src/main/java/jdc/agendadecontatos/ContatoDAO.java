package jdc.agendadecontatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String TABLE = "Contato";
    private static final String DATABASE = "DadosAgenda";

    public ContatoDAO(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("create table %s (id integer primary key autoincrement," +
                "nome text not null, " +
                "ref text, " +
                "email text, " +
                "endereco text, " +
                "telefone text, " +
                "foto text);", TABLE);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<ContatoInfo> getList(String order) {
        List<ContatoInfo> contatos = new ArrayList<>();
        String query = String.format("select * from %s order by nome %s;", TABLE, order);
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        while (cursor.moveToNext()) {
            ContatoInfo contato = new ContatoInfo();

            contato.setId(cursor.getLong(cursor.getColumnIndex("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            contato.setRef(cursor.getString(cursor.getColumnIndex("ref")));
            contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contato.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            contato.setFoto(cursor.getString(cursor.getColumnIndex("foto")));

            contatos.add(contato);
        }

        cursor.close();

        return contatos;
    }

    public void insert(ContatoInfo contato) {
        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("ref", contato.getRef());
        values.put("email", contato.getEmail());
        values.put("telefone", contato.getTelefone());
        values.put("endereco", contato.getEndereco());
        values.put("foto", contato.getFoto());

        getWritableDatabase().insert(TABLE, null, values);
    }

    public void alter(ContatoInfo contato) {
        ContentValues values = new ContentValues();

        values.put("id", contato.getId());
        values.put("nome", contato.getNome());
        values.put("ref", contato.getRef());
        values.put("email", contato.getEmail());
        values.put("telefone", contato.getTelefone());
        values.put("endereco", contato.getEndereco());
        values.put("foto", contato.getFoto());

        String[] args = {contato.getId().toString()};
        getWritableDatabase().update(TABLE, values, "id=?", args);
    }

    public void delete(ContatoInfo contato) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {contato.getId().toString()};
        db.delete(TABLE, "id=?", args);
    }
}
