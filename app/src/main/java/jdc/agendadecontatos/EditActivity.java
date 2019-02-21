package jdc.agendadecontatos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private ContatoInfo contato;

    private View layout;

    private ImageButton foto;
    private EditText nome;
    private EditText ref;
    private EditText email;
    private EditText fone;
    private EditText endereco;

    private Button salvar;

    private final int CAMERA = 1;
    private final int GALERIA = 2;

    private final String IMAGE_DIR = "/fotosContatos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        contato = getIntent().getParcelableExtra("contato");

        layout = findViewById(R.id.layout);

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

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaImagem();
            }
        });

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

    private void alertaImagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        builder.setTitle("Selecione a fonte da imagem");

        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tirarFoto();
            }
        });
        builder.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                galeria();
            }
        });

        builder.show();
    }

    private void tirarFoto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            showCamera();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            Snackbar.make(layout, "É necessário permitir para utilizar a câmera!",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(EditActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CAMERA);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA);
        }
    }

    private void showCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, CAMERA);
    }

    private void galeria() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestGaleriaPermission();
        } else {
            showGaleria();
        }
    }

    private void requestGaleriaPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Snackbar.make(layout, "É necessário permitir para utilizar a galeria!",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(EditActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            GALERIA);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    GALERIA);
        }
    }

    private void showGaleria() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALERIA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tirarFoto();
                }
                break;
            case GALERIA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galeria();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_CANCELED || data == null) {
            return;
        }
        if (requestCode == GALERIA) {
            Uri contentURI = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                contato.setFoto(saveImage(bitmap));
                foto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            contato.setFoto(saveImage(bitmap));
            foto.setImageBitmap(bitmap);
        }
    }

    private String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        File directory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIR);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            File file = new File(directory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            file.createNewFile();
            FileOutputStream output = new FileOutputStream(file);
            output.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
            output.close();
            return file.getAbsolutePath()
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
