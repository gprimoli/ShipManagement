package model.CompagniaBroker;

public class CompagniaBroker {
    private final String codFiscale;
    private final String nome;
    private final String telefono;
    private final String sedeLegale;
    private final String sitoWeb;

    public CompagniaBroker(String codFiscale, String nome, String telefono, String sedeLegale, String sitoWeb) {
        this.codFiscale = codFiscale;
        this.nome = nome;
        this.telefono = telefono;
        this.sedeLegale = sedeLegale;
        this.sitoWeb = sitoWeb;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getSedeLegale() {
        return sedeLegale;
    }

    public String getSitoWeb() {
        return sitoWeb;
    }
}
