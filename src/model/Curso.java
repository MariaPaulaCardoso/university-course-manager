package model;

import java.util.Date;
import java.util.List;

public class Curso {
    private int id;
    private String nome;
    private Date dataProcessamento;
    private String periodoInicial;
    private String periodoFinal;
    private int sequenciaArquivo;
    private String versaoLayout;


    //opcional
    private List<Fase> fases;

    //Construtor
    public Curso(){

    }

    public Curso (String nome, Date dataProcessamento, String periodoInicial, String periodoFinal, int sequenciaArquivo, String versaoLayout){
        this.nome = nome;
        this.dataProcessamento = dataProcessamento;
        this.periodoInicial = periodoInicial;
        this.periodoFinal = periodoFinal;
        this.sequenciaArquivo = sequenciaArquivo;
        this.versaoLayout = versaoLayout;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(String periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    public String getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(String periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    public int getSequenciaArquivo() {
        return sequenciaArquivo;
    }

    public void setSequenciaArquivo(int sequenciaArquivo) {
        this.sequenciaArquivo = sequenciaArquivo;
    }

    public String getVersaoLayout() {
        return versaoLayout;
    }

    public void setVersaoLayout(String versaoLayout) {
        this.versaoLayout = versaoLayout;
    }

    public List<Fase> getFases() {
        return fases;
    }

    public void setFases(List<Fase> fases) {
        this.fases = fases;
    }

    @Override public String toString(){
        return "Curso{" + 
        "id=" + id +
        ", nome=" + nome + "/" +
        ", dataProcessamento=" + dataProcessamento +
        ", periodoInicial='" + periodoInicial + '\'' +
        ", periodoFinal='" + periodoFinal + '\'' +
        ", sequenciaArquivo=" + sequenciaArquivo +
        ", versaoLayout='" + versaoLayout + '\'' +
        '}';
    }

    
}
