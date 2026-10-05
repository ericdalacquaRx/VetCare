/**
 * Classe DTO (Data Transfer Object) que representa uma Consulta veterinária
 * no sistema VetCare. Cada consulta está vinculada a um pet (petId).
 *
 * @author VetCare
 */
public class ConsultaDTO {

    private int id;
    private int petId;
    private String dataConsulta;
    private String motivo;
    private String diagnostico;
    private String veterinario;
    private double valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Consulta{id=" + id + ", petId=" + petId + ", dataConsulta=" + dataConsulta
                + ", motivo=" + motivo + ", diagnostico=" + diagnostico
                + ", veterinario=" + veterinario + ", valor=" + valor + "}";
    }
}
