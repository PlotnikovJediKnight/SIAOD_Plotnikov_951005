import java.util.InputMismatchException;
import java.util.Scanner;

//СИАОД, Лабораторная работа №1
//Задача №4
//Телефонные номера абонентов и спецслужб

//Класс-узел для хранения номера телефона
class Node{
	public String phoneNumber;
	public Node next;
	public Node prev;
	
	public Node(String number) {
		phoneNumber = number;
		next = null;
		prev = null;
	}
}


//Класс для двусвязного неотсортированного списка
class DoubleLinkedList{
	private Node first;		//Ссылка на первый элемент списка
	private Node last;		//Ссылка на последний элемент списка
	private int size;		//Кол-во элементов в списке
	
	//Конструтор для создания пустого списка
	public DoubleLinkedList() {
		first = null;
		last = null;
		size = 0;
	}
	
	//Метод для добавления нового телефонного номера
	public void addNewNode(Node obj) {
		//Список пуст - назначаем и начало, и конец
		if (size == 0) {
			first = obj;
			last = obj;
		} //Иначе список не пуст
			else{
			//Движемся до самого конца списка
			Node curr = first, prev = curr;
			while (curr != null) {
				prev = curr;
				curr = curr.next;
			}
			//Присоединяем новый элемент
			obj.prev = prev;
			prev.next = obj;
			//Меняем последнего
			last = obj;
		}
		//Увеличиваем кол-во
		size++;
	}
	
	//Метод для вывода списка справа налево
	public void showFromRightToLeft() {
		if (size == 0) { System.out.println("Список пуст!"); return; }
		Node curr = last;
		while (curr != null) {
			System.out.println(curr.phoneNumber + " ");
			curr = curr.prev;
		}
		System.out.println();
	}
	
	
	//Метод для заполнения сортирующегося списка номерами абонентов
	public void fillOrderedList(OrderedList obj) {
		if (size == 0) { System.out.println("Нечем заполнить отсоортированный список"); return; }
		Node curr = last;
		while (curr != null) {
			if (curr.phoneNumber.matches("\\d{7}"))
				obj.addNewNode(new Node(curr.phoneNumber));
			curr = curr.prev;
		}
	}
}


//Класс-отсортированный список
class OrderedList{
	//Корень списка
	private Node first;
	//Текущий размер списка
	private int size;
		
		//Конструктор для создания пустого списка
		public OrderedList() {
			first = null;
			size = 0;
		}
		
		//Метод для добавления нового человека в список
		public void addNewNode(Node obj) {
			//Если список пуст, то выбираем новый корень
			if (size == 0) 
				first = obj;
			else {
				//Становимся в начало
				Node curr = first, prev = curr;
				//Обеспечиваем лексикографический порядок вставки
				while (curr != null && (curr.phoneNumber.compareTo(obj.phoneNumber) < 0) ) {
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
		
		//Метод вывода всего списка
		public void show() {
			if (size == 0) System.out.println("Список пуст!");
			else {
				int index = 1;
				Node curr = first;
				
				while (curr != null) {
						System.out.println(index + ". " + curr.phoneNumber);
						curr = curr.next;
						index++;
				}
			}
		}
}




public class SecondPhoneNumbers {
	
	private static Scanner in = new Scanner(System.in);
	
	private static boolean checkNumber(String s) {
		return s.matches("\\d{3}") || s.matches("\\d{7}");
	}
	
	
	
	public static void main(String[] args){
		
		//Создаем двусвязный неотсортированный список
		DoubleLinkedList list = new DoubleLinkedList();
		int N = -1;
		//Делаем проверку ввода
		while (N <= 0) {
			try {
				System.out.println("Введите кол-во телефонных номеров в двунаправленном неупорядоченном списке:");
				N = Integer.valueOf(in.nextLine());
				if (N <= 0) throw new InputMismatchException();
			} catch (InputMismatchException | NumberFormatException e) {
				System.out.println("Повторите ввод!");
			}
		}
		
		//Запрашиваем N мобильных телефонов
		for (int i = 0; i < N; i++) {
			System.out.println("Введите номер телефона:");
			String s = in.nextLine();
			if (checkNumber(s))
				list.addNewNode(new Node(s));
			else
				System.out.println("Возможны телефонные номера либо спецслужб (три цифры), либо абонентов (семь цифр)!");
				
		}
		
		//Вывод двусвязного списка
		System.out.println("Исходный неотсортированный двусвязный список:");
		list.showFromRightToLeft();
		//Создание сортирующегося списка
		OrderedList ordList = new OrderedList();
		System.out.println("Отсортированный список только с телефонами абонентов:");
		//Создание сортирующегося списка
		list.fillOrderedList(ordList);
		//Вывод списка
		ordList.show();
	}
}
