package red.lisgar.corresponsal.entidades;

public class Transacciones {

    private int id_transaccion;
    private int id_cliente;
    private String id_corresponsal;
    private int id_emisor;
    private String tipo_transaccion;
    private String fecha_transaccion;

    public int getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(int id_transaccion) {
        this.id_transaccion = id_transaccion;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getId_corresponsal() {
        return id_corresponsal;
    }

    public void setId_corresponsal(String id_corresponsal) {
        this.id_corresponsal = id_corresponsal;
    }

    public int getId_emisor() {
        return id_emisor;
    }

    public void setId_emisor(int id_emisor) {
        this.id_emisor = id_emisor;
    }

    public String getTipo_transaccion() {
        return tipo_transaccion;
    }

    public void setTipo_transaccion(String tipo_transaccion) {
        this.tipo_transaccion = tipo_transaccion;
    }

    public String getFecha_transaccion() {
        return fecha_transaccion;
    }

    public void setFecha_transaccion(String fecha_transaccion) {
        this.fecha_transaccion = fecha_transaccion;
    }

    public String getMonto_transaccion() {
        return monto_transaccion;
    }

    public void setMonto_transaccion(String monto_transaccion) {
        this.monto_transaccion = monto_transaccion;
    }

    private String monto_transaccion;

}
