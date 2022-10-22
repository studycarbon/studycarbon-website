package cn.studycarbon.domain;

public class StudyCarbonInfo {
    private int id;

    private String about;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "StudyCarbonInfo{" +
                "id=" + id +
                ", about='" + about + '\'' +
                '}';
    }
}
