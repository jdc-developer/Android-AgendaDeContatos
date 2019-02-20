package jdc.agendadecontatos;

import android.os.Parcel;
import android.os.Parcelable;

public class ContatoInfo implements Parcelable{

    private String nome = "";
    private String ref = "";
    private String email = "";
    private String telefone = "";
    private String endereco = "";
    private String foto = "";

    public ContatoInfo() {}

    private ContatoInfo(Parcel parcel) {
        String[] data = new String[6];
        parcel.readStringArray(data);

        setNome(data[0]);
        setRef(data[1]);
        setEmail(data[2]);
        setTelefone(data[3]);
        setEndereco(data[4]);
        setFoto(data[5]);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                getNome(), getRef(), getEmail(), getTelefone(), getEndereco(), getFoto()
        });
    }

    public static final Parcelable.Creator<ContatoInfo> CREATOR = new Parcelable.Creator<ContatoInfo>() {

        @Override
        public ContatoInfo createFromParcel(Parcel source) {
            return new ContatoInfo(source);
        }

        @Override
        public ContatoInfo[] newArray(int size) {
            return new ContatoInfo[size];
        }
    };
}
