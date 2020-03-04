package xzydemo.demo.domain;

public class ClassInfor {
    private String id;
    private String ClassName;
    private String descn;
    private String people;
    private String Classtime;

    private ClassInfor(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getClasstime() {
        return Classtime;
    }

    public void setClasstime(String classtime) {
        Classtime = classtime;
    }
}
