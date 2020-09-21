import java.util.InputMismatchException;
import java.util.Scanner;


//СИАОД, Лабораторная работа №1
//Задача №3
//Задача про телефонную станцию


//Класс-человек
class Person{
	private String name;		//Имя
	private String surname;		//Фамилия
	private String patronym;	//Отчество
	private String phoneNumber;	//Номер телефона
	
	public Person next;			//Ссылка на следующего
	
	//Конструктор
	public Person(String name, String surname, String patronym, String phoneNumber) throws InputMismatchException {
		this.name = name;
		this.surname = surname;
		this.patronym = patronym;
		//Проверка ввода номера телефона
		if (phoneNumber.matches("\\d{7}"))
			this.phoneNumber = phoneNumber;
		else
			throw new InputMismatchException();
		next = null;
	}
	
	
	public Person(Person obj) {
		this.name = obj.name;
		this.surname = obj.surname;
		this.patronym = obj.patronym;
		this.phoneNumber = obj.phoneNumber;
		next = null;
	}
	
	//Геттеры и сеттеры
	////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
	public String getPatronym() {
		return patronym;
	}
	public void setPatronym(String patronym) {
		this.patronym = patronym;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
}



//Класс одностороннего списка
class List{
	//Корень списка
	private Person first;
	//Текущий размер списка
	private int size;
	
	//Конструктор для создания пустого списка
	public List() {
		first = null;
		size = 0;
	}
	
	//Метод для добавления нового человека в список
	public void addNewPerson(Person obj) {
		//Если список пуст, то выбираем новый корень
		if (size == 0) 
			first = obj;
		else {
			//Становимся в начало
			Person curr = first, prev = curr;
			//Обеспечиваем лексикографический порядок вставки
			while (curr != null && (curr.getSurname().compareTo(obj.getSurname()) < 0   ||  
								    (curr.getSurname().compareTo(obj.getSurname()) == 0 && curr.getName().compareTo(obj.getName()) < 0) 	||
									(curr.getSurname().compareTo(obj.getSurname()) == 0 && curr.getName().compareTo(obj.getName()) == 0 && curr.getPatronym().compareTo(obj.getPatronym()) < 0    			)    ) ) {
				//Передвигаемся вперед по списку
				prev = curr;
				curr = curr.next;
			}
			
			//Вставка перед "начальным" элементом
			if (curr == first) {
				obj.next = first;
				first = obj;
			} else {
			//Вставка где-то в середине списка (конце)
				obj.next = curr;
				prev.next = obj;
			}
		}
		//Увеличиваем размер
		size++;
	}
	
	
	//Метод для нахождения человека с номером телефона
	public Person findPersonByNumber(String phoneNumber) {
		//Становимся в начало
		Person curr = first;
		//Идем до самого конца
		while (curr != null) {
			//Прерываем цикл, если нашли того человека
			if (curr.getPhoneNumber().equals(phoneNumber)) break;
			curr = curr.next;
		}
		//Если оказались в конце списка, значит такого человека нет. Возвращаем null
		if (curr == null) return null;
		else
		//Возвращаем человека
			return new Person(curr);
	}
	
	//Получаем список телефоных номеров по фамилии
	public String[] getPhoneNumbersBySurname(String surname) {
		//Заводим массив
		String arr[] = new String[size];
		int ind = 0;
		
			//Заполняем его
			Person curr = first;
			while (curr != null) {
				if (curr.getSurname().equals(surname)) {
					arr[ind] = new String(curr.getPhoneNumber());
					ind++;
				}
				curr = curr.next;
			}
		//Возвращаем
		return arr;
	}
	
	//Метод вывода всего списка
	public void show() {
		if (size == 0) System.out.println("Список пуст!");
		else {
			int index = 1;
			Person curr = first;
			
			while (curr != null) {
					System.out.println(index + ". " + curr.getSurname() + " " + curr.getName() + " " + curr.getPatronym() + " " + curr.getPhoneNumber());
					curr = curr.next;
					index++;
			}
		}
	}
}

public class FirstPhoneNumbers {
	
	private static Scanner in = new Scanner(System.in);
	
	//Метод для вывода всех номеров телефонов
	private static void printAllPhones(String[] arr) {
		if (arr.length == 0) {
			System.out.println("Телефонов на данную фамилию не зарегистрировано!");
		} else
		if (arr[0] == null) {
			System.out.println("Телефонов на данную фамилию не зарегистрировано!");
		} else {
			System.out.println("Найдены следующие телефоны:");
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == null) break;
				
				System.out.println(arr[i]);
			}
		}
		System.out.println();
	}
	
	//Вспомогательный метод для прочтения телефонного номера
	private static String getPhoneNumber() {
		System.out.println("Введите номер телефона:");
		String buf;
		buf = in.nextLine();
		if (buf.matches("\\d{7}"))
			return buf;
		else
			throw new NumberFormatException();
			
	}
	
	//Чтение полей очередного создаваемого человека
	private static Person instantiatePerson() {
		String name, surname, patronym, phoneNumber;
		
		System.out.println("Введите имя человека: ");
			name = in.nextLine();
		System.out.println("Введите фамилию человека: ");
			surname = in.nextLine();
		System.out.println("Введите отчество человека: ");
			patronym = in.nextLine();
		System.out.println("Введите номер телефона человека: ");
			phoneNumber = in.nextLine();
			
		Person obj = new Person(name, surname, patronym, phoneNumber);
		return obj;
	}
	
	//Вывод меню
	private static void printMenu() {
		System.out.println("Меню:");
		System.out.println("1. Добавить человека в список");
		System.out.println("2. Вывод всего спика");
		System.out.println("3. Найти человека по номеру телефона");
		System.out.println("4. Найти номера телефонов по фамилии");
		System.out.println("5. Выход из программы\n");
	}
	
	//Метод main
	public static void main(String[] args) {
		
		printMenu();
		int command = -1;
		List list = new List();
		
		while (true) {
			try {
				System.out.println("Введите команду: ");
				command = Integer.valueOf(in.nextLine());
				
				switch (command) {
				
					case 1:
					list.addNewPerson(instantiatePerson());
						break;
					
					case 2:
					list.show();
						break;
					
					case 3:
					Person obj = list.findPersonByNumber(getPhoneNumber());
					if (obj == null)
							System.out.println("Человек с таким номером телефона не найден!");
						else 
							System.out.println("Найден человек с таким номером телефона: " + obj.getSurname());
						break;
					
					case 4:
					System.out.println("Введите фамилию:");
					printAllPhones(list.getPhoneNumbersBySurname(in.nextLine()));
						break;
					
					case 5:
					in.close();
					System.exit(0);
						break;
				}
					
				
			} catch (InputMismatchException | NumberFormatException a) {
				System.out.println("Проверьте корректность ввода!");
			}
		
		}
		
		
		
	}
	
}
