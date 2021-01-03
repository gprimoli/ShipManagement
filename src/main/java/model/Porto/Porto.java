package model.Porto;

import model.Area.Area;

public class Porto {
    private final String localcode;
    private final String nome;
    private final Area area;

    public Porto(String localcode, String nome, Area area) {
        this.localcode = localcode;
        this.nome = nome;
        this.area = area;
    }

    public String getLocalcode() {
        return localcode;
    }

    public String getNome() {
        return nome;
    }

    public Area getArea() {
        return area;
    }
}
