package model;

public class ResultadoLote {

    private Mecanico mecanico;
    private double porcentajeErrorLambda;
    private double porcentajeErrorO2;
    private double porcentajeErrorN2O;
    private double porcentajeErrorCO2;
    private double porcentajeErrorGeneral;
    private boolean aprobadoPrimeraRevision;
    private String mensajeEstado;
    private String detalleCondicion;

    public ResultadoLote(
            Mecanico mecanico,
            double porcentajeErrorLambda,
            double porcentajeErrorO2,
            double porcentajeErrorN2O,
            double porcentajeErrorCO2,
            double porcentajeErrorGeneral,
            boolean aprobadoPrimeraRevision,
            String mensajeEstado,
            String detalleCondicion) {
        this.mecanico = mecanico;
        this.porcentajeErrorLambda = porcentajeErrorLambda;
        this.porcentajeErrorO2 = porcentajeErrorO2;
        this.porcentajeErrorN2O = porcentajeErrorN2O;
        this.porcentajeErrorCO2 = porcentajeErrorCO2;
        this.porcentajeErrorGeneral = porcentajeErrorGeneral;
        this.aprobadoPrimeraRevision = aprobadoPrimeraRevision;
        this.mensajeEstado = mensajeEstado;
        this.detalleCondicion = detalleCondicion;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public double getPorcentajeErrorLambda() {
        return porcentajeErrorLambda;
    }

    public double getPorcentajeErrorO2() {
        return porcentajeErrorO2;
    }

    public double getPorcentajeErrorN2O() {
        return porcentajeErrorN2O;
    }

    public double getPorcentajeErrorCO2() {
        return porcentajeErrorCO2;
    }

    public double getPorcentajeErrorGeneral() {
        return porcentajeErrorGeneral;
    }

    public boolean isAprobadoPrimeraRevision() {
        return aprobadoPrimeraRevision;
    }

    public String getMensajeEstado() {
        return mensajeEstado;
    }

    public String getDetalleCondicion() {
        return detalleCondicion;
    }
}