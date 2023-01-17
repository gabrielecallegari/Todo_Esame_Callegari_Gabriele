package it.itsar.todo_esame_callegari_gabriele;

import java.io.Serializable;

public class ListaTipo implements Serializable {
    private String titolo;
    private boolean check;
    private String id;

    public ListaTipo() {
    }

    public ListaTipo(String titolo, boolean check, String id) {
        this.titolo = titolo;
        this.check = check;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
