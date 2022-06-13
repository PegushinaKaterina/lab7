package katya.common.state;

import katya.common.entites.WeaponType;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Scanner;

public abstract class State {
    private LocalDate creationDate;
    private String name;
    private int x;
    private int y;
    private Boolean realHero;
    private boolean hasToothpick;
    private Double impactSpeed;
    private String soundtrackName;
    private Integer minutesOfWaiting;
    private WeaponType weaponType;
    private Boolean cool;
    private Scanner scanner;
    private ArrayDeque<String> errors = new ArrayDeque<>();

    public State(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void generateHumanBeingFields();

    public boolean isCorrect() {
        return errors.isEmpty();
    }

    public void errorHandler() {
        while (!errors.isEmpty()) {
            System.out.println(errors.remove());
        }
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public void setRealHero(Boolean realHero) {
        this.realHero = realHero;
    }

    public boolean getHasToothpick() {
        return hasToothpick;
    }

    public Double getImpactSpeed() {
        return impactSpeed;
    }

    public void setImpactSpeed(Double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public void setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
    }

    public Integer getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public void setMinutesOfWaiting(Integer minutesOfWaiting) {
        this.minutesOfWaiting = minutesOfWaiting;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Boolean getCool() {
        return cool;
    }

    public void setCool(Boolean cool) {
        this.cool = cool;
    }

    public void setCreationDate() {
        this.creationDate = LocalDate.now();
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public ArrayDeque<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayDeque<String> errors) {
        this.errors = errors;
    }
}
