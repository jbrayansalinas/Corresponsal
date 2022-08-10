package red.lisgar.corresponsal.entidades;

public class Home {

    private int id;
    private int ImgHome;
    private Object clase;
    private String tituloHome;
    private String direccion;
    private String correo;



    public Home(int imgHome, String tituloHome, Object clase, String direccion, String correo) {
        this.ImgHome = imgHome;
        this.tituloHome = tituloHome;
        this.clase = clase;
        this.direccion = direccion;
        this.correo = correo;
    }
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTituloHome() {
        return tituloHome;
    }

    public Object getClase() {
        return clase;
    }
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setClase(Object clase) {
        this.clase = clase;
    }

    public void setTituloHome(String tituloHome) {
        this.tituloHome = tituloHome;
    }

    public int getImgHome() {
        return ImgHome;
    }

    public void setImgHome(int imgHome) {
        ImgHome = imgHome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
