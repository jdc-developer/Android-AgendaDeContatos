package jdc.agendadecontatos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContactViewHolder>{

    private List<ContatoInfo> contatos;

    public ContatoAdapter(List<ContatoInfo> contatos) {
        this.contatos = contatos;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celula_contato, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
        ContatoInfo contato = contatos.get(i);
        contactViewHolder.nome.setText(contato.getNome());
        contactViewHolder.ref.setText(contato.getRef());
        contactViewHolder.telefone.setText(contato.getTelefone());

        File imgFile = new File(contato.getFoto());
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            contactViewHolder.foto.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        ImageView foto;
        TextView nome;
        TextView ref;
        TextView telefone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.imgContato);
            nome = itemView.findViewById(R.id.txtNome);
            ref = itemView.findViewById(R.id.txtRef);
            telefone = itemView.findViewById(R.id.txtFone);
        }
    }
}
