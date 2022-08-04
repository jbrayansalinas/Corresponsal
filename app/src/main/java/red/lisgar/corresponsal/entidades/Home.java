package red.lisgar.corresponsal.entidades;

public class Home {

    private int id;
    private int ImgHome;
    private Object clase;
    private String tituloHome;


    public Home(int imgHome, String tituloHome, Object clase) {
        this.ImgHome = imgHome;
        this.tituloHome = tituloHome;
        this.clase = clase;
    }
    public String getTituloHome() {
        return tituloHome;
    }

    public Object getClase() {
        return clase;
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
