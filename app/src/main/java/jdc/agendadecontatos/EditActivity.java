package jdc.agendadecontatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditActivity extends AppCompatActivity {

    private ContatoInfo contato;

    private ImageButton foto;
    private EditText nome;
    private EditText endereco;
    private EditText fone;
    private EditText email;
    private EditText ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        foto = findViewById(R.id.ftContato);
        nome = findViewById(R.id.nomeContato);
        endereco = findViewById(R.id.endContato);
        fone = findViewById(R.id.foneContato);
        email = findViewById(R.id.emailContato);
        ref = findViewById(R.id.refContato);
    }
}
