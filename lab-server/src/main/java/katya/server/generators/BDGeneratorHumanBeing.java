package katya.server.generators;

import katya.common.entites.Coordinates;
import katya.common.entites.HumanBeing;
import katya.common.entites.WeaponType;
import katya.common.state.State;
import katya.common.util.CheckBoolean;
import katya.common.util.Parser;
import katya.common.util.Validator;

import java.util.Scanner;

public class BDGeneratorHumanBeing extends State {
    private String[] stringHumanBeing;

    public BDGeneratorHumanBeing(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void generateHumanBeingFields() {
        while (collectionSet.next()) {
            String stringWeaponType = collectionSet
                    .getString("weaponType");
            WeaponType weaponType = WeaponType.valueOf(stringWeaponType);
            super.setId(collectionSet.getLong("id"));
            super.setCreationDate(collectionSet.getDate("creationDate").toLocalDate());
            super.setName(collectionSet.getString("name"));
            super.setX(collectionSet.getInt("x"));
            super.setY(collectionSet.getInt("y"));
            super.setRealHero(collectionSet.getBoolean("real_hero"));
            super.setHasToothpick(collectionSet.getBoolean("has_toothpick"));
            super.setImpactSpeed(collectionSet.getDouble("impact_speed"));
            super.setSoundtrackName(collectionSet.getString("soundtrack_name"));
            super.setMinutesOfWaiting(collectionSet.getInt("minutes_of_waiting"));
            super.setWeaponType(weaponType);
            super.setCool(collectionSet.getBoolean("cool"));
        }
    }

    @Override
    public boolean isCorrect() {
        return getErrors().isEmpty();
    }

    public void errorHandler() {
        while (!getErrors().isEmpty()) {
            System.out.println(getErrors().remove());
        }
    }
}


