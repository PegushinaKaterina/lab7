package katya.common.state;

import katya.common.entites.HumanBeing;
import katya.common.entites.HumanBeingBuilder;

public class GeneratorHumanBeing {
    private HumanBeing humanBeing = null;
    private State state;

    public void changeState(State state) {
        this.state = state;
    }

    public void generateHumanBeing() {
        state.generateHumanBeingFields();
        state.setCreationDate();
        if (state.isCorrect()) {
            this.humanBeing = new HumanBeing(new HumanBeingBuilder()
                    .withDate(state.getCreationDate())
                    .withName(state.getName())
                    .withCoordinates(state.getX(), state.getY())
                    .withRealHero(state.getRealHero())
                    .withHasToothpick(state.getHasToothpick())
                    .withImpactSpeed(state.getImpactSpeed())
                    .withSoundtrackName(state.getSoundtrackName())
                    .withMinutesOfWaiting(state.getMinutesOfWaiting())
                    .withWeaponType(state.getWeaponType())
                    .withCar(state.getCool()));
        } else {
            state.errorHandler();
            throw new IllegalArgumentException("Генерация прошла неуспешно");
        }
    }

    public HumanBeing getHumanBeing() {
        return humanBeing;
    }
}
