package katya.server.entites;

import katya.common.entites.HumanBeing;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CollectionManager {
    private final Date creationDate;
    private LinkedList<HumanBeing> collectionHumanBeing;

    public CollectionManager() {
        creationDate = new Date();
    }

    public void setHumanBeings(LinkedList<HumanBeing> humanBeings) {
        collectionHumanBeing = humanBeings;
        collectionHumanBeing = collectionHumanBeing
                .stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));
    }


    public LinkedList<HumanBeing> getCollectionHumanBeing() {
        return collectionHumanBeing;
    }

    public String add(HumanBeing humanBeing) {
        collectionHumanBeing.add(humanBeing);
        collectionHumanBeing = collectionHumanBeing
                .stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));
        return "Человек успешно добавлен в коллекцию. ";
    }

    public String countByImpactSpeed(double impactSpeed) {
        if (collectionHumanBeing.isEmpty()) {
            throw new IllegalArgumentException("Коллекция пуста");
        } else {
            int countByImpactSpeed = (int) collectionHumanBeing
                    .stream()
                    .filter(hb -> hb.getImpactSpeed() == impactSpeed).count();
            if (countByImpactSpeed == 0) {
                throw new IllegalArgumentException("Элементов со значением СКОРОСТЬ УДАРА = " + impactSpeed + " не найдено");
            } else {
                return "Количество элементов, у которых значение поля СКОРОСТЬ УДАРА = " + impactSpeed + ": " + countByImpactSpeed;
            }
        }
    }

    public String info() {
        return "Информация о коллекции:"
                + "\nКласс коллекции: " + collectionHumanBeing.getClass().toString()
                + "\nДата создания: " + creationDate
                + "\nРазмер коллекции: " + collectionHumanBeing.size()
                + "\nКласс экземпляров коллекции" + HumanBeing.class;
    }

    public String removeById(Long id) {
        if (collectionHumanBeing.isEmpty()) {
            throw new IllegalArgumentException("Коллекция пуста");
        } else {
            boolean found = collectionHumanBeing.removeIf(hb -> hb.getId().equals(id));
            if (!found) {
                throw new IllegalArgumentException("Элементов со значением id = " + id + " не найдено");
            } else {
                return "Элемент со значением id = " + id + " успешно удален";
            }
        }
    }

    public String removeHead() {
        if (collectionHumanBeing.isEmpty()) {
            throw new IllegalArgumentException("Коллекция пуста");
        } else {
            return "Первый элемент коллекции: " + collectionHumanBeing.poll() + "\nуспешно удален";
        }
    }

    public String removeLover(HumanBeing humanBeing) {
        if (collectionHumanBeing.isEmpty()) {
            throw new IllegalArgumentException("Коллекция пуста");
        } else {
            boolean found = collectionHumanBeing.removeIf(hb -> hb.compareTo(humanBeing) < 0);
            if (!found) {
                throw new IllegalArgumentException("Элементов, меньших, чем заданный, не найдено");
            } else {
                return "Элементы, меньшие, чем заданный, успешно удалены";
            }
        }
    }

    public String sumOfMinutesOfWaiting() {
        if (collectionHumanBeing.isEmpty()) {
            throw new IllegalArgumentException("Коллекция пуста");
        } else {
            AtomicInteger sumOfMinutesOfWaiting = new AtomicInteger();
            collectionHumanBeing.forEach(hb -> sumOfMinutesOfWaiting.addAndGet(hb.getMinutesOfWaiting()));
            return "Сумма значений поля ВРЕМЯ ОЖИДАНИЯ для всех элементов коллекции = " + sumOfMinutesOfWaiting;
        }
    }

    public String update(long id, HumanBeing element) {
        if (collectionHumanBeing.isEmpty()) {
            throw new IllegalArgumentException("Коллекция пуста");
        } else {
            boolean found = false;
            List<HumanBeing> filteredList = collectionHumanBeing.stream().filter(hb -> hb.getId().equals(id)).collect(Collectors.toList());
            if (!filteredList.isEmpty()) {
                element.setId(id);
                collectionHumanBeing.remove(filteredList.get(0));
                collectionHumanBeing.add(element);
                found = true;
            }
            collectionHumanBeing = collectionHumanBeing
                    .stream()
                    .sorted()
                    .collect(Collectors.toCollection(LinkedList::new));
            if (!found) {
                throw new IllegalArgumentException("Элементов со значением id = " + id + " не найдено");
            } else {
                return "Значение элемента коллекции, id которого равен " + id + " успешно обновлено";
            }
        }
    }

    public LinkedList<HumanBeing> getUsersElements(List<Long> idList) {
        LinkedList<HumanBeing> humanBeings = new LinkedList<>();
        for (HumanBeing humanBeing : collectionHumanBeing) {
            if (idList.contains(humanBeing.getId())) {
                humanBeings.add(humanBeing);
            }
        }
        return humanBeings.isEmpty() ? null : humanBeings;
    }

    public LinkedList<HumanBeing> getAlienElements(List<Long> idList) {
        LinkedList<HumanBeing> humanBeings = new LinkedList<>();
        for (HumanBeing humanBeing : collectionHumanBeing) {
            if (!idList.contains(humanBeing.getId())) {
                humanBeings.add(humanBeing);
            }
        }
        return humanBeings.isEmpty() ? null : humanBeings;
    }

    public List<Long> returnIdsOfLower(HumanBeing humanBeing, List<Long> idList) {
        List<HumanBeing> humanBeings = getUsersElements(idList);
        humanBeings = humanBeings.stream().filter(hb -> hb.compareTo(humanBeing) < 0).collect(Collectors.toList());
        List<Long> idListLower = new ArrayList<>();
        for (HumanBeing hb : humanBeings) {
            idListLower.add(hb.getId());
        }
        return idListLower;
    }

    public Long getFirstId(List<Long> idList) {
        for (HumanBeing humanBeing : collectionHumanBeing) {
            if (idList.contains(humanBeing.getId())) {
                return humanBeing.getId();
            }
        }
        return null;
    }
}
