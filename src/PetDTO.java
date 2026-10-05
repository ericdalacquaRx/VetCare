/**
 * Classe DTO (Data Transfer Object) que representa um Pet no sistema VetCare.
 * Cada pet está vinculado a um tutor (tutorId).
 *
 * @author VetCare
 */
public class PetDTO {

    private int id;
    private String nome;
    private String especie;
    private String raca;
    private String dataNascimento;
    private int tutorId;

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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    @Override
    public String toString() {
        return "Pet{id=" + id + ", nome=" + nome + ", especie=" + especie
                + ", raca=" + raca + ", dataNascimento=" + dataNascimento
                + ", tutorId=" + tutorId + "}";
    }
}
