package katya.common.entites;

import katya.common.state.GeneratorHumanBeing;

import java.io.Serializable;
import java.time.LocalDate;

public class HumanBeing implements Comparable<HumanBeing>, Serializable {
    public static final Double IMPACT_SPEED_MIN = -484.;
    private static final GeneratorHumanBeing GENERATOR_HUMAN_BEING = new GeneratorHumanBeing();
    private final String name;
    private final Coordinates coordinates;
    private final Boolean realHero;
    private final boolean hasToothpick;
    private final Double impactSpeed;
    private final String soundtrackName;
    private final Integer minutesOfWaiting;
    private final WeaponType weaponType;
    private final Car car;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    public HumanBeing(HumanBeingBuilder humanBeingBuilder) {
        this.id = humanBeingBuilder.getId();
        //this.creationDate = LocalDate.now();
        this.creationDate = humanBeingBuilder.getCreationDate();
        this.name = humanBeingBuilder.getName();
        this.coordinates = humanBeingBuilder.getCoordinates();
        this.realHero = humanBeingBuilder.getRealHero();
        this.hasToothpick = humanBeingBuilder.isHasToothpick();
        this.impactSpeed = humanBeingBuilder.getImpactSpeed();
        this.soundtrackName = humanBeingBuilder.getSoundtrackName();
        this.minutesOfWaiting = humanBeingBuilder.getMinutesOfWaiting();
        this.weaponType = humanBeingBuilder.getWeaponType();
        this.car = humanBeingBuilder.getCar();
    }

    public static GeneratorHumanBeing getGeneratorHumanBeing() {
        return GENERATOR_HUMAN_BEING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean getRealHero() {
        return realHero;
    }

    public boolean getHasToothpick() {
        return hasToothpick;
    }

    public Double getImpactSpeed() {
        return impactSpeed;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public int getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public int compareTo(HumanBeing humanBeing) {
        return name.compareTo(humanBeing.getName());
    }

    @Override
    public String toString() {
        return "Человек с именем " + name + ", id - " + id + "\n"
                + "   Скорость удара: " + impactSpeed
                + ", время ожидания: " + minutesOfWaiting;
        /*
          String stringRealHero;
        if (realHero == true) {
            stringRealHero = "Это реальный человек";
        } else {
            stringRealHero = "Это не реальный человек";
        }
        String stringHasToothpick;
        if (hasToothpick == true) {
            stringHasToothpick = "у него есть зубочистка";
        } else {
            stringHasToothpick = "у него нет зубочистки";
        }
        String stringCar;
        if (car.getCool() == null) {
            stringCar = ", о его машине ничего не известно";
        } else if (car.getCool() == false) {
            stringCar = " и нет крутой машины";
        } else {
            stringCar = " и есть крутая машина";
        }
        return "Человек с именем " + name + ", id - " + id + "\n" +
                "   Дата создания: " + creationDate + ", " +
                "Координаты: " + "x - " + coordinates.getX() + ", y - " +coordinates.getY() + "\n" +
                "   " + stringRealHero + ", " +stringHasToothpick + stringCar + ".\n"+
                "   Скорость удара: " + impactSpeed +
                ", cаундтрек: " + soundtrackName +
                ", время ожидания: " + minutesOfWaiting +
                ", Тип оружия:" + weaponType + "\n";
                */
    }
}

