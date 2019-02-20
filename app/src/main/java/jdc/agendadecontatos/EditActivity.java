package jdc.agendadecontatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private ContatoInfo contato;

    private ImageButton foto;
    private EditText nome;
    private EditText ref;
    private EditText email;
    private EditText fone;
    private EditText endereco;

    private Button salvar;

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

        salvar = findViewById(R.id.btnSalvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contato.setNome(nome.getText().toString());
                contato.setRef(ref.getText().toString());
                contato.setEmail(email.getText().toString());
                contato.setTelefone(fone.getText().toString());
                contato.setEndereco(endereco.getText().toString());

                if (contato.getNome().equals("")) {
                    Toast.makeText(EditActivity.this, "É necessário um nome para salvar", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent();
                i.putExtra("contato", contato);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
