package model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private int id;
    private Fase faseId;
    private String codigo;
    private String nome; 
    private int dia_semana;

    public Disciplina(){

    }

    public Disciplina(int id, Fase faseId, String codigo, String nome, int dia_semana){
        this.id = id;
        this.faseId = faseId;
        this.codigo = codigo;
        this.nome = nome;
        this.dia_semana = dia_semana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fase getFaseId() {
        return faseId;
    }

    public void setFaseId(Fase faseId) {
        this.faseId = faseId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getdia_semana() {
        return dia_semana;
    }

    public void setdia_semana(int dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getDia_semana() {
        return String.valueOf(dia_semana);
    }

    public void setDia_semana(String dia_semana) {
        try {
            this.dia_semana = Integer.parseInt(dia_semana);
        } catch (Exception e) {
            this.dia_semana = 0;
        }
    }

    private List<Professor> professores = new ArrayList<>();

    public void addProfessor(Professor p) {
        professores.add(p);
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    @Override public String toString(){
        return "Disciplina{" +
            "id=" + id +
            ", codigo='" + codigo + '\'' +
            ", nome='" + nome + '\'' +
            ", dia_semana=" + dia_semana +
            ", fase=" + (faseId != null ? faseId.getFase() : "null") +
            '}';
    }
}
