import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Notebook {
    private String brand;
    private String model;
    private int ram;
    private int hdd;
    private String os;
    private String color;
    private double price;

    public Notebook(String brand, String model, int ram, int hdd, String os, String color, double price) {
        this.brand = brand;
        this.model = model;
        this.ram = ram;
        this.hdd = hdd;
        this.os = os;
        this.color = color;
        this.price = price;
    }

    public enum Criteria {
        brand,
        model,
        ram,
        hdd,
        os,
        color,
        price;
    }

    public static Set<Notebook> filterNotebooks(Set<Notebook> notebooks, Map<Criteria, Predicate<Notebook>> criteria) {
        Set<Notebook> filteredNotebooks = new HashSet<>(notebooks);
        criteria.forEach((key, value) -> filteredNotebooks.removeIf(n -> !value.test(n)));
        return filteredNotebooks;
    }

    public static void main(String[] args) {
        Set<Notebook> notebooks = new HashSet<>();
        notebooks.add(new Notebook("Apple", "Macbook Pro", 16, 512, "macOS", "Silver", 2399.0));
        notebooks.add(new Notebook("Dell", "XPS 15", 16, 1000, "Windows", "Black", 1599.0));
        notebooks.add(new Notebook("HP", "Pavilion 14", 8, 512, "Windows", "Blue", 899.0));

        try (Scanner scanner = new Scanner(System.in)) {
            Map<Criteria, Predicate<Notebook>> filters = new HashMap<>();

            System.out.println("Введите цифры, соответствующие необходимым критериям, разделенные пробелами:\n"
                            + "1 - ОЗУ\n"
                            + "2 - Объем ЖД\n"
                            + "3 - Операционная система\n"
                            + "4 - Цвет");

            scanner.nextLine();
            String[] choices = scanner.nextLine().split(" ");

            for (String choice : choices) {
                Criteria selectedCriteria;
                switch (Integer.parseInt(choice)) {
                    case 1:
                        selectedCriteria = Criteria.ram;
                        System.out.print("Введите минимальный объем ОЗУ (в ГБ): ");
                        int ram = scanner.nextInt();
                        filters.put(selectedCriteria, n -> n.ram >= ram);
                        break;
                    case 2:
                        selectedCriteria = Criteria.hdd;
                        System.out.print("Введите минимальный объем ЖД (в ГБ): ");
                        int hdd = scanner.nextInt();
                        filters.put(selectedCriteria, n -> n.hdd >= hdd);
                        break;
                    case 3:
                        selectedCriteria = Criteria.os;
                        System.out.print("Введите операционную систему: ");
                        scanner.nextLine();
                        String os = scanner.nextLine();
                        filters.put(selectedCriteria, n -> n.os.equalsIgnoreCase(os));
                        break;
                    case 4:
                        selectedCriteria = Criteria.color;
                        System.out.print("Введите цвет: ");
                        scanner.nextLine();
                        String color = scanner.nextLine();
                        filters.put(selectedCriteria, n -> n.color.equalsIgnoreCase(color));
                        break;
                    default:
                        System.out.println("Введен неверный номер критерия. Попробуйте еще раз.");
                        return;
                }
            }

            Set<Notebook> filteredNotebooks = filterNotebooks(notebooks, filters);
            for (Notebook notebook : filteredNotebooks) {
                System.out.println(notebook.brand + " " + notebook.model + " " + notebook.ram + "GB " + notebook.hdd + "GB " + notebook.os + " " + notebook.color + " $" + notebook.price);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}