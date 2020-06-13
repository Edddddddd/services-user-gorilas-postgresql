package cl.gorilas.user.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Usuarios entity.
 */
public class UsuariosDTO implements Serializable {

    private Long id;

    private String idGorilas;

    private String nombreCompleto;

    private String correoElectronico;

    private String documento;

    private Long abono;

    private String estado;

    private Integer turnosDisponibles;

    private String turnosDisponiblesLeyenda;

    private String turnosDisponiblesLeyendaHtml;

    private String abonosVencimientoHtml;

    private Instant fechaProxAptoFisico;

    private Instant fechaProxAptoFisicoString;

    private Float diasRestantes;

    private Boolean tieneDeuda;

    private Integer saldo;

    private String domicilio;

    private String barrio;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdGorilas() {
        return idGorilas;
    }

    public void setIdGorilas(String idGorilas) {
        this.idGorilas = idGorilas;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Long getAbono() {
        return abono;
    }

    public void setAbono(Long abono) {
        this.abono = abono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getTurnosDisponibles() {
        return turnosDisponibles;
    }

    public void setTurnosDisponibles(Integer turnosDisponibles) {
        this.turnosDisponibles = turnosDisponibles;
    }

    public String getTurnosDisponiblesLeyenda() {
        return turnosDisponiblesLeyenda;
    }

    public void setTurnosDisponiblesLeyenda(String turnosDisponiblesLeyenda) {
        this.turnosDisponiblesLeyenda = turnosDisponiblesLeyenda;
    }

    public String getTurnosDisponiblesLeyendaHtml() {
        return turnosDisponiblesLeyendaHtml;
    }

    public void setTurnosDisponiblesLeyendaHtml(String turnosDisponiblesLeyendaHtml) {
        this.turnosDisponiblesLeyendaHtml = turnosDisponiblesLeyendaHtml;
    }

    public String getAbonosVencimientoHtml() {
        return abonosVencimientoHtml;
    }

    public void setAbonosVencimientoHtml(String abonosVencimientoHtml) {
        this.abonosVencimientoHtml = abonosVencimientoHtml;
    }

    public Instant getFechaProxAptoFisico() {
        return fechaProxAptoFisico;
    }

    public void setFechaProxAptoFisico(Instant fechaProxAptoFisico) {
        this.fechaProxAptoFisico = fechaProxAptoFisico;
    }

    public Instant getFechaProxAptoFisicoString() {
        return fechaProxAptoFisicoString;
    }

    public void setFechaProxAptoFisicoString(Instant fechaProxAptoFisicoString) {
        this.fechaProxAptoFisicoString = fechaProxAptoFisicoString;
    }

    public Float getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(Float diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public Boolean isTieneDeuda() {
        return tieneDeuda;
    }

    public void setTieneDeuda(Boolean tieneDeuda) {
        this.tieneDeuda = tieneDeuda;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuariosDTO usuariosDTO = (UsuariosDTO) o;
        if (usuariosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuariosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsuariosDTO{" +
            "id=" + getId() +
            ", idGorilas='" + getIdGorilas() + "'" +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", abono=" + getAbono() +
            ", estado='" + getEstado() + "'" +
            ", turnosDisponibles=" + getTurnosDisponibles() +
            ", turnosDisponiblesLeyenda='" + getTurnosDisponiblesLeyenda() + "'" +
            ", turnosDisponiblesLeyendaHtml='" + getTurnosDisponiblesLeyendaHtml() + "'" +
            ", abonosVencimientoHtml='" + getAbonosVencimientoHtml() + "'" +
            ", fechaProxAptoFisico='" + getFechaProxAptoFisico() + "'" +
            ", fechaProxAptoFisicoString='" + getFechaProxAptoFisicoString() + "'" +
            ", diasRestantes=" + getDiasRestantes() +
            ", tieneDeuda='" + isTieneDeuda() + "'" +
            ", saldo=" + getSaldo() +
            ", domicilio='" + getDomicilio() + "'" +
            ", barrio='" + getBarrio() + "'" +
            "}";
    }
}
