package model;

public class Professor {
    private int id;
    private Curso curso;
    private Disciplina disciplina;
    private String nome;
    private int titulo_docente;

    public Professor(){

    }

    public Professor(int id, Curso curso, Disciplina disciplina, String nome, int titulo_docente){
        this.id = id;
        this.curso = curso;
        this.disciplina= disciplina;
        this.nome = nome;
        this.titulo_docente = titulo_docente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTitulo_docente() {
        return titulo_docente;
    }

    public void setTitulo_docente(int titulo_docente) {
        this.titulo_docente = titulo_docente;
    }

    @Override public String toString(){
        return "Professor{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", titulo_docente=" + titulo_docente +
            ", curso=" + (curso != null ? curso.getNome() : "null") +
            ", disciplina=" + (disciplina != null ? disciplina.getNome() : "null") +
            '}';
    }
}