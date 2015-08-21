package io.github.cdelmas.spike.common.domain;

public class Car {

    private Integer id;
    private String name;

    public Car(String name) {
        this.name = name;
    }

    Car() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
