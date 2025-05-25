package model;

public class Fase {

    private Curso curso;

    private int id;
    private String fase;
    private int qtd_disciplinas;
    private int qtd_professores;
    
    public Fase(){

    }

    public Fase(int id, String fase, int qtd_disciplinas, int qtd_professores, Curso curso){
        this.id = id;
        this.fase = fase;
        this.qtd_disciplinas = qtd_disciplinas;
        this.qtd_professores = qtd_professores;

        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public int getQtd_disciplinas() {
        return qtd_disciplinas;
    }

    public void setQtd_disciplinas(int qtd_disciplinas) {
        this.qtd_disciplinas = qtd_disciplinas;
    }

    public int getQtd_professores() {
        return qtd_professores;
    }

    public void setQtd_professores(int qtd_professores) {
        this.qtd_professores = qtd_professores;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override public String toString(){
        return "Fase{" +
            "fase='" + fase + '\'' +
            ", qtd_disciplinas=" + qtd_disciplinas +
            ", qtd_professores=" + qtd_professores +
            ", curso=" + (curso != null ? curso.getNome() : "null") +
            '}';
    }

    
}
