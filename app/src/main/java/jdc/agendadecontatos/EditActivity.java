package jdc.agendadecontatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditActivity extends AppCompatActivity {

    private ContatoInfo contato;

    private ImageButton foto;
    private EditText nome;
    private EditText ref;
    private EditText email;
    private EditText fone;
    private EditText endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        contato = getIntent().getParcelableExtra("contato");

        foto = findViewById(R.id.ftContato);
        nome = findViewById(R.id.nomeContato);
        endereco = findViewById(R.id.endContato);
        fone = findViewById(R.id.foneContato);
        email = findViewById(R.id.emailContato);
        ref = findViewById(R.id.refContato);

        nome.setText(contato.getNome());
        ref.setText(contato.getRef());
        email.setText(contato.getEmail());
        fone.setText(contato.getTelefone());
        endereco.setText(contato.getEndereco());
    }
}
