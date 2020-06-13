package cl.gorilas.user.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Usuarios entity. This class is used in UsuariosResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /usuarios?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UsuariosCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idGorilas;

    private StringFilter nombreCompleto;

    private StringFilter correoElectronico;

    private StringFilter documento;

    private LongFilter abono;

    private StringFilter estado;

    private IntegerFilter turnosDisponibles;

    private StringFilter turnosDisponiblesLeyenda;

    private StringFilter turnosDisponiblesLeyendaHtml;

    private StringFilter abonosVencimientoHtml;

    private InstantFilter fechaProxAptoFisico;

    private InstantFilter fechaProxAptoFisicoString;

    private FloatFilter diasRestantes;

    private BooleanFilter tieneDeuda;

    private IntegerFilter saldo;

    private StringFilter domicilio;

    private StringFilter barrio;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdGorilas() {
        return idGorilas;
    }

    public void setIdGorilas(StringFilter idGorilas) {
        this.idGorilas = idGorilas;
    }

    public StringFilter getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(StringFilter nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public StringFilter getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(StringFilter correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public StringFilter getDocumento() {
        return documento;
    }

    public void setDocumento(StringFilter documento) {
        this.documento = documento;
    }

    public LongFilter getAbono() {
        return abono;
    }

    public void setAbono(LongFilter abono) {
        this.abono = abono;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
    }

    public IntegerFilter getTurnosDisponibles() {
        return turnosDisponibles;
    }

    public void setTurnosDisponibles(IntegerFilter turnosDisponibles) {
        this.turnosDisponibles = turnosDisponibles;
    }

    public StringFilter getTurnosDisponiblesLeyenda() {
        return turnosDisponiblesLeyenda;
    }

    public void setTurnosDisponiblesLeyenda(StringFilter turnosDisponiblesLeyenda) {
        this.turnosDisponiblesLeyenda = turnosDisponiblesLeyenda;
    }

    public StringFilter getTurnosDisponiblesLeyendaHtml() {
        return turnosDisponiblesLeyendaHtml;
    }

    public void setTurnosDisponiblesLeyendaHtml(StringFilter turnosDisponiblesLeyendaHtml) {
        this.turnosDisponiblesLeyendaHtml = turnosDisponiblesLeyendaHtml;
    }

    public StringFilter getAbonosVencimientoHtml() {
        return abonosVencimientoHtml;
    }

    public void setAbonosVencimientoHtml(StringFilter abonosVencimientoHtml) {
        this.abonosVencimientoHtml = abonosVencimientoHtml;
    }

    public InstantFilter getFechaProxAptoFisico() {
        return fechaProxAptoFisico;
    }

    public void setFechaProxAptoFisico(InstantFilter fechaProxAptoFisico) {
        this.fechaProxAptoFisico = fechaProxAptoFisico;
    }

    public InstantFilter getFechaProxAptoFisicoString() {
        return fechaProxAptoFisicoString;
    }

    public void setFechaProxAptoFisicoString(InstantFilter fechaProxAptoFisicoString) {
        this.fechaProxAptoFisicoString = fechaProxAptoFisicoString;
    }

    public FloatFilter getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(FloatFilter diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public BooleanFilter getTieneDeuda() {
        return tieneDeuda;
    }

    public void setTieneDeuda(BooleanFilter tieneDeuda) {
        this.tieneDeuda = tieneDeuda;
    }

    public IntegerFilter getSaldo() {
        return saldo;
    }

    public void setSaldo(IntegerFilter saldo) {
        this.saldo = saldo;
    }

    public StringFilter getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(StringFilter domicilio) {
        this.domicilio = domicilio;
    }

    public StringFilter getBarrio() {
        return barrio;
    }

    public void setBarrio(StringFilter barrio) {
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
        final UsuariosCriteria that = (UsuariosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idGorilas, that.idGorilas) &&
            Objects.equals(nombreCompleto, that.nombreCompleto) &&
            Objects.equals(correoElectronico, that.correoElectronico) &&
            Objects.equals(documento, that.documento) &&
            Objects.equals(abono, that.abono) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(turnosDisponibles, that.turnosDisponibles) &&
            Objects.equals(turnosDisponiblesLeyenda, that.turnosDisponiblesLeyenda) &&
            Objects.equals(turnosDisponiblesLeyendaHtml, that.turnosDisponiblesLeyendaHtml) &&
            Objects.equals(abonosVencimientoHtml, that.abonosVencimientoHtml) &&
            Objects.equals(fechaProxAptoFisico, that.fechaProxAptoFisico) &&
            Objects.equals(fechaProxAptoFisicoString, that.fechaProxAptoFisicoString) &&
            Objects.equals(diasRestantes, that.diasRestantes) &&
            Objects.equals(tieneDeuda, that.tieneDeuda) &&
            Objects.equals(saldo, that.saldo) &&
            Objects.equals(domicilio, that.domicilio) &&
            Objects.equals(barrio, that.barrio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idGorilas,
        nombreCompleto,
        correoElectronico,
        documento,
        abono,
        estado,
        turnosDisponibles,
        turnosDisponiblesLeyenda,
        turnosDisponiblesLeyendaHtml,
        abonosVencimientoHtml,
        fechaProxAptoFisico,
        fechaProxAptoFisicoString,
        diasRestantes,
        tieneDeuda,
        saldo,
        domicilio,
        barrio
        );
    }

    @Override
    public String toString() {
        return "UsuariosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idGorilas != null ? "idGorilas=" + idGorilas + ", " : "") +
                (nombreCompleto != null ? "nombreCompleto=" + nombreCompleto + ", " : "") +
                (correoElectronico != null ? "correoElectronico=" + correoElectronico + ", " : "") +
                (documento != null ? "documento=" + documento + ", " : "") +
                (abono != null ? "abono=" + abono + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (turnosDisponibles != null ? "turnosDisponibles=" + turnosDisponibles + ", " : "") +
                (turnosDisponiblesLeyenda != null ? "turnosDisponiblesLeyenda=" + turnosDisponiblesLeyenda + ", " : "") +
                (turnosDisponiblesLeyendaHtml != null ? "turnosDisponiblesLeyendaHtml=" + turnosDisponiblesLeyendaHtml + ", " : "") +
                (abonosVencimientoHtml != null ? "abonosVencimientoHtml=" + abonosVencimientoHtml + ", " : "") +
                (fechaProxAptoFisico != null ? "fechaProxAptoFisico=" + fechaProxAptoFisico + ", " : "") +
                (fechaProxAptoFisicoString != null ? "fechaProxAptoFisicoString=" + fechaProxAptoFisicoString + ", " : "") +
                (diasRestantes != null ? "diasRestantes=" + diasRestantes + ", " : "") +
                (tieneDeuda != null ? "tieneDeuda=" + tieneDeuda + ", " : "") +
                (saldo != null ? "saldo=" + saldo + ", " : "") +
                (domicilio != null ? "domicilio=" + domicilio + ", " : "") +
                (barrio != null ? "barrio=" + barrio + ", " : "") +
            "}";
    }

}
