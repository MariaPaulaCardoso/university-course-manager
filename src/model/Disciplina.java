package model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private int id;
    private Fase faseId;
    private String codigo;
    private String nome; 
    private int carga_horaria;

    public Disciplina(){

    }

    public Disciplina(int id, Fase faseId, String codigo, String nome, int carga_horaria){
        this.id = id;
        this.faseId = faseId;
        this.codigo = codigo;
        this.nome = nome;
        this.carga_horaria = carga_horaria;
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

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
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
            ", carga_horaria=" + carga_horaria +
            ", fase=" + (faseId != null ? faseId.getFase() : "null") +
            '}';
    }
}
