package red.lisgar.corresponsal.entidades;

public class Cliente {
    private int id_cliente;
    private String nombre_cliente;
    private String cedula_cliente;
    private String saldo_cliente;
    private String pin_cliente;
    private String cuenta_cliente;
    private String cvv_cliente;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getCedula_cliente() {
        return cedula_cliente;
    }

    public void setCedula_cliente(String cedula_cliente) {
        this.cedula_cliente = cedula_cliente;
    }

    public String getSaldo_cliente() {
        return saldo_cliente;
    }

    public void setSaldo_cliente(String saldo_cliente) {
        this.saldo_cliente = saldo_cliente;
    }

    public String getPin_cliente() {
        return pin_cliente;
    }

    public void setPin_cliente(String pin_cliente) {
        this.pin_cliente = pin_cliente;
    }

    public String getCuenta_cliente() {
        return cuenta_cliente;
    }

    public void setCuenta_cliente(String cuenta_cliente) {
        this.cuenta_cliente = cuenta_cliente;
    }

    public String getCvv_cliente() {
        return cvv_cliente;
    }

    public void setCvv_cliente(String cvv_cliente) {
        this.cvv_cliente = cvv_cliente;
    }

    public String getFecha_exp_cliente() {
        return fecha_exp_cliente;
    }

    public void setFecha_exp_cliente(String fecha_exp_cliente) {
        this.fecha_exp_cliente = fecha_exp_cliente;
    }

    private String fecha_exp_cliente;
}
