package katya.client.generators;

import katya.common.entites.Coordinates;
import katya.common.entites.HumanBeing;
import katya.common.entites.WeaponType;
import katya.common.state.State;
import katya.common.util.CheckBoolean;
import katya.common.util.Validator;

import java.util.Scanner;

public class ConsoleGeneratorHumanBeing extends State {

    public ConsoleGeneratorHumanBeing(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void generateHumanBeingFields() {
        setValue("Введите имя ",
                this::setName);
        setValue("Введите координату X, "
                        + "значение должно быть целым числом не больше " + Coordinates.X_MAX,
                this::setX);
        setValue("Введите координату Y, " + "значение должно быть целым числом",
                this::setY);
        setValue("Это реальный герой или нет? " + "значение должно быть Да или Нет",
                this::setRealHero);
        setValue("У героя есть зубочистка? " + "значение должно быть Да или Нет",
                this::setHasToothpick);
        setValue("Введите скорость удара, "
                        + "значение должно быть вещественным числом и больше " + HumanBeing.IMPACT_SPEED_MIN,
                this::setImpactSpeed);
        setValue("Введите название саундтрека ",
                this::setSoundtrackName);
        setValue("Введите время ожидания, " + "значение должно быть целым числом",
                this::setMinutesOfWaiting);
        setValue("Введите тип оружия, "
                        + "допустимые значения: \n" + WeaponType.show() + "регистр должен сохраняться",
                this::setWeaponType);
        setValue("Машина крутая? "
                        + "значение должно быть Да или Нет. Если хотите оставить это значение, пустым нажмите enter",
                this::setCar);
    }

    public void setValue(String message, Runnable runnable) {
        System.out.println(message);
        boolean isRunning = true;
        while (isRunning) {
            try {
                runnable.run();
                isRunning = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + "\n Повторите ввод");
            }
        }
    }


    public void setName() throws IllegalArgumentException {
        super.setName(new Validator<String>(super.getScanner())
                .withCheckingNull(false)
                .getValue());
    }

    public void setX() throws IllegalArgumentException {
        super.setX(new Validator<Integer>(super.getScanner())
                .withCheckingNull(false)
                .withCheckingFunction(Integer::parseInt, "значение координаты X должно быть целым числом")
                .withCheckingPredicate(arg -> (int) arg < Coordinates.X_MAX,
                        "Значение координаты X должно быть не больше " + Coordinates.X_MAX)
                .getValue());
    }

    public void setY() throws IllegalArgumentException {
        super.setY(new Validator<Integer>(super.getScanner())
                .withCheckingNull(false)
                .withCheckingFunction(Integer::parseInt, "значение координаты Y должно быть целым числом")
                .getValue());
    }

    public void setRealHero() throws IllegalArgumentException {
        super.setRealHero(new Validator<Boolean>(super.getScanner())
                .withCheckingNull(false)
                .withCheckingFunction(CheckBoolean::checkBoolean, "значение \"Это реальный герой\" должно быть Да или Нет")
                .getValue());
    }

    public void setHasToothpick() throws IllegalArgumentException {
        super.setHasToothpick(new Validator<Boolean>(super.getScanner())
                .withCheckingNull(false)
                .withCheckingFunction(CheckBoolean::checkBoolean, "значение \"У человека есть зубочистка\" должно быть Да или Нет")
                .getValue());
    }

    public void setImpactSpeed() throws IllegalArgumentException {
        super.setImpactSpeed(new Validator<Double>(super.getScanner())
                .withCheckingNull(false)
                .withCheckingFunction(Double::parseDouble, "значение скорости удара должно быть вещественным числом")
                .withCheckingPredicate(arg -> (Double) arg > HumanBeing.IMPACT_SPEED_MIN,
                        "Значение скорости удара должно быть больше " + HumanBeing.IMPACT_SPEED_MIN)
                .getValue());

    }

    public void setSoundtrackName() throws IllegalArgumentException {
        super.setSoundtrackName(new Validator<String>(super.getScanner())
                .withCheckingNull(false)
                .getValue());
    }

    public void setMinutesOfWaiting() throws IllegalArgumentException {
        super.setMinutesOfWaiting(new Validator<Integer>(super.getScanner())
                .withCheckingNull(false)
                .withCheckingFunction(Integer::parseInt, "значение времени ожидания должно быть целым числом")
                .getValue());
    }

    public void setWeaponType() throws IllegalArgumentException {
        super.setWeaponType(new Validator<WeaponType>(super.getScanner())
                .withCheckingNull(false)
                .withCheckingFunction(WeaponType::valueOf,
                        "тип оружия должен быть из списка: \n" + WeaponType.show() + "Регистр должен сохраняться")
                .getValue());

    }

    public void setCar() throws IllegalArgumentException {
        super.setCool(new Validator<Boolean>(super.getScanner())
                .withCheckingNull(true)
                .withCheckingFunction(CheckBoolean::checkBoolean,
                        "значение \"У человека есть крутая машина\" должно быть Да или Нет, или быть пустым")
                .getValue());
    }
}
