//Плотников Владислав, гр. 951005
//Лабораторная работа №2

//Класс-узел
class Person{
    private String name;     //Имя человека
    private int     age;     //Возраст человека
    private double mark;     //Средний балл за экзамен
    public Person next;		 //Ссылка на следующего человека
    //Конструктор для инициализации
    public Person(String name, int age, double mark) {
        this.name = name;
        this.age = age;
        this.mark = mark;
        next = null;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
            sb.append("Имя: ");
            sb.append(name);
            sb.append("\n");
            sb.append("Возраст: ");
            sb.append(age);
            sb.append("\nОценка: ");
            sb.append(mark);
            sb.append("\n");
        return sb.toString();
    }
    //Геттеры
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public double getMark() {
        return mark;
    }
}
//Класс-список
class MyList {
    private Person head;      //Стартовый узел списка
    private long size;        //Размер списка
    //Возвращаем начало списка
    public Person getHead() {
        return head;
    }
    //Возвращаем размер списка
    public long getSize() {
        return size;
    }
    //Конструктор для создания пустого списка
    public MyList() {
        head = null;
        size = 0;
    }
    //Добавление нового узла в список
    public void pushBack(String name, int age, double mark) {
        //Если список пуст, то просто запоминаем новое начало
        if (head == null) {
            //Создание нового узла
            Person newNode = new Person(name, age, mark);
            //Запоминаем начало
            head = newNode;
        } else {
            //Устанавливаем текущий на начало
            Person curr = head;
            //Двигаемся, покуда не дойдем до конца
            while (curr.next != null)
                curr = curr.next;
            //Создаем новый узел
            Person newNode = new Person(name, age, mark);
            //Присоединяем к концу списка
            curr.next = newNode;
        }
        //Увеличиваем размер списка
        size++;
    }
    //Тоже добавление
    public void pushBack(Person p) {
        pushBack(p.getName(), p.getAge(), p.getMark());
    }
    //Метод для отображения хранимых в списке людей
    public void showList() {
        Person curr = head;
        while (curr != null) {
            System.out.print(curr.getName() + " ");
            curr = curr.next;
        }
        System.out.println();
    }
    //Удаление элемента из списка по ключу
    public void delete(String key) {
        Person curr = head;
        if (curr == null) {
            System.out.println("Nothing to delete! - " + key);
            return;
        }
        //Меняем голову
        if (curr.getName().equals(key)) {
            head = head.next;
        } else {
            //Замена в середине списка или в конце
            Person prev = curr;
            while (curr != null && !curr.getName().equals(key)) {
                prev = curr;
                curr = curr.next;
            }
            if (curr != null) {
                prev.next = curr.next;
                curr.next = null;
            } else {
                System.out.println("Nothing to delete! - " + key);
                return;
            }
        }
        size--;
    }
    //Метод для поиска человека в списке по имени
    public Person find(String key){
        Person curr = head;
        while (curr != null) {
            if (curr.getName().equals(key)) break;
            curr = curr.next;
        }
        return curr;
    }
}
//Класс-хэш-таблица
class HashTable {
    private MyList[] hashArray;     //Массив списков
    private int arraySize;          //Кол-во списков в хэш-таблице
    //Конструктор для создания хэш-таблицы
    public HashTable(int size) {
        arraySize = size;
        //Выделяем памятл для size списков
        hashArray = new MyList[arraySize];
        //Выделяем память под сами списки
        for (int i = 0; i < arraySize; i++)
            hashArray[i] = new MyList();
    }
    //Метод для вывода содержимого хэш-таблицы
    public void showTable() {
        System.out.println("Хэш-таблица:");
        for (int i = 0; i < arraySize; i++) {
            System.out.print(i + ": ");
            hashArray[i].showList();
        }
    }
    //"Моя" хэш-функция
    public int getMyHashCode(String key) {
        key = key.toLowerCase();
        int hashVal = 0;
        for(int i = 0; i < key.length(); i++){
            hashVal = (hashVal * 33 + key.charAt(i)) % arraySize;
        }
        return hashVal;
    }
    //Метод для вставки человека в хэш-таблицу
    public void insert(Person p) {
        String key = p.getName();
        //Получаем хэш-код
        int hashVal = getMyHashCode(key);
        //Вставляем человека в нужный список
        hashArray[hashVal].pushBack(p);
    }
    //Метод для удаления человека из хэш-таблицы
    public void delete(String key) {
        //Получаем хэш-код
        int hashVal = getMyHashCode(key);
        //Удаляем чевелока из хэш-таблицы
        hashArray[hashVal].delete(key);
    }
    //Метод для поиска человека в хэш-таблице
    public Person find(String key) {
        //Получаем хэш-код
        int hashVal = getMyHashCode(key);
        //Ищем человека
        Person p = hashArray[hashVal].find(key);
        //Возвращаем то, что нашли
        return p;
    }
}

public class Main {
    public static void main(String[] args){
        //Для наиболее эффективного вычисления хэш-кода, размер хэш-таблицы
        //обычно берут как простое число
        HashTable table = new HashTable(13);

        //Заполняем хэш-таблицу данными о людях
        table.insert(new Person("Юлик", 14, 4.3));
        table.insert(new Person("Юлия", 66, 4.3));
        table.insert(new Person("Юлий", 35, 1.2));
        table.insert(new Person("Владислав", 19, 2));
        table.insert(new Person("Артемий", 21, 3));
        table.insert(new Person("Мария", 17, 3.4));
        table.insert(new Person("Алексей", 40, 4.9));
        table.insert(new Person("Максим", 14, 4.3));
        table.insert(new Person("Петр", 14, 4.3));
        table.insert(new Person("Евгений", 11, 4.3));
        table.insert(new Person("Евгения", 10, 4.3));
        table.insert(new Person("Илья", 5, 4.3));
        table.showTable();

        //Ищем в таблице людей, в том числе, которых нет в таблице. Если человек
        //в таблице не найден, выводится null, иначе - информация о человеке
        System.out.println("========================================================");
            String names[] = {  "Юлик", "Юлия", "Фридрих", "Бен", "Владислав", "Артемий", "Марина",
                                "Мария", "Леша", "Алексей", "Максиммилиан", "Максим", "Петр", "Евгений",
                                "Евгения", "Илюша", "Илья"};
            for (String name : names)
                System.out.println(table.find(name));
        System.out.println("========================================================");

        //Пытаемся удалять людей. Если подходящий человек не найден, то сообщаем об этом
        for (String name : names)
            table.delete(name);
        //Выводим хэш-таблицу после удаления
        table.showTable();
        //Удаляем последнего оставшегося
        table.delete("Юлий");
        //Выводим хэш-таблицу еще раз
        table.showTable();
    }
}
