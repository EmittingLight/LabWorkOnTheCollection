import java.util.*;


public class Main {
    public static void main(String[] args) {
        List<Product> list1 = new ArrayList<>();
        List<Product> list2 = new ArrayList<>();

        initData(list1, list2);

        long startTime = System.currentTimeMillis();
        List<Product> duplicates = findDuplicatesNaive(list1, list2);
        long endTime = System.currentTimeMillis();
        System.out.println("Затраченное время (простое): " + (endTime - startTime) / 1000 + " seconds");

        startTime = System.currentTimeMillis();
        duplicates = findDuplicates(list1, list2);
        endTime = System.currentTimeMillis();
        System.out.println("Затраченное время (эффективное): " + (endTime - startTime) / 1000 + " seconds");

    }

    public static void initData(List<Product> list1, List<Product> list2) {
        final int NUMBER_OF_PRODUCTS = 100000;
        Set<Product> uniqueProducts = new HashSet<>(NUMBER_OF_PRODUCTS);
        Random random = new Random();

        // Заполнение первой коллекции уникальными продуктами
        while (uniqueProducts.size() < NUMBER_OF_PRODUCTS) {
            Product product = new Product();
            product.setName("Результат" + uniqueProducts.size());
            product.setCategory("Категория" + random.nextInt(10));
            product.setInternalCode(new byte[]{(byte) random.nextInt(256)});
            uniqueProducts.add(product);
        }
        list1.addAll(uniqueProducts);

        // Добавление дубликатов во вторую коллекцию
        Set<Product> duplicateProducts = new HashSet<>(1000);
        for (int i = 0; i < 1000; i++) {
            duplicateProducts.add(list1.get(i));
        }
        for (int i = 0; i < NUMBER_OF_PRODUCTS / 100; i++) {
            list2.addAll(duplicateProducts);
        }
        list2.addAll(uniqueProducts);
    }

    // Первая реализация - простой перебор
    public static List<Product> findDuplicatesNaive(Collection<Product> list1, Collection<Product> list2) {
        List<Product> duplicates = new ArrayList<>();
        for (Product product1 : list1) {
            for (Product product2 : list2) {
                if (product1.equals(product2)) {
                    duplicates.add(product1);
                }
            }
        }
        return duplicates;
    }

    // Вторая реализация - более эффективный способ
    public static List<Product> findDuplicates(Collection<Product> list1, Collection<Product> list2) {
        Set<Product> uniqueProducts = new HashSet<>(list1);
        List<Product> duplicates = new ArrayList<>();

        for (Product product : list2) {
            if (!uniqueProducts.add(product)) {
                duplicates.add(product);
            }
        }
        return duplicates;
    }
}


/*
 Лабораторная работа на коллекции (Middle уровень) (обязательное)
Вы устроились на позицию Java Developer и вам пришла задача на доработку системы заказчика.
Вам достался финальный класс Product, который используется в коде заказчика в разных местах, то есть не можете наследоваться от него и менять его нельзя.

public final class Product {
    private String name;
    private String category;
    private byte[] internalCode;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public byte[] getInternalCode() {
        return internalCode;
    }
    public void setInternalCode(byte[] internalCode) {
        this.internalCode = internalCode;
    }
}
Глядя на этот класс, вы понимаете, что это POJO (Plain Old Java Object), но кривоватый,
в нем нет переопределенных методов equals() и hashCode(), что должно вас навести на мысль о потенциальных проблемах при работе с коллекциями.

Тем не менее что есть, то есть. Менять нельзя, читаете задачу дальше.

И есть две коллекции, которые хранят Продукты

final int NUMBER_OF_PRODUCTS = 100000;
List<Product> list1 = new ArrayList<>(NUMBER_OF_PRODUCTS);
List<Product> list2 = new ArrayList<>(NUMBER_OF_PRODUCTS);
1. Напишите метод initData(), который бы заполнил первую коллекцию list1 100 000 (сто тысяч) уникальными Продуктами.
А вторую коллекцию заполнил бы хитрее, ее длина тоже должна по итогу составить 100 000 элементов, но в ней должно быть 1000 дубликатов из первой коллекции.
Так мы эмулируем получение данных из Базы данных или файловой системы.
То есть в результате и list1 и list2 должны иметь размер в 100 000.

2. Напишите две реализации метода, который нашел бы дубликаты в коллекциях и вернул бы список с дубликатами (продукты, которые есть в обеих коллекциях).

public static List<Product> findDuplicates(Collection<Product> list1, Collection<Product> list2);

Первая реализация должна тупо перебирать коллекции в цикле и сравнивать Продукты на равенство. Если они идентичны – то добавлять в результирующую коллекцию.
Обратите внимание, что простым перебором выполнение метода может занять около 5 минут.

Вторая реализация должна выполнять требования к производительности: на среднем компьютере выполняться не более пары секунд.

Одинаковыми считаем продукты, у которых совпадают все 3 поля: name, category, internalCode.

Используйте только стандартные классы Java SE.

Вы должны замерить средствами Java выполнение каждого метода и вывести на экран.
Для этого до и после вызова метода сохраните текущее время в миллисекундах
long stamp = System.currentTimeMillis();
разницу разделите на 1000, чтобы получить время работы метода в секундах.

Сдайте работу преподавателю.


P.S. Требований к учету "прогрева", средней статистике и точным характеристикам железа, на котором производятся исследования в данном задании нет. Важно показать понимание работы с коллекциями.
 */