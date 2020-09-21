import java.util.Scanner;

//СИАОД, Лабораторная работа №1
//Задача №2
//Считалочка

//Класс-узел для хранения ребенка
class Node{
	public int id;		//Порядковый номер ребенка
	public Node next;	//Ссылка на следующего ребенка
	
	//Конструктор для создания ребенка
	public Node(int number) {
		id = number;
		next = null;
	}
}

//Класс - кольцевой список.
class CycledList{
	private Node root;		//Корневой узел
	private int size;		//Кол-во детей в круге
	
	//Конструктор для создания пустого кольцевого списка
	public CycledList() {
		root = null;
		size = 0;
	}
	
	//Добавление ребенка в круг
	public void AddKid(int id) {
		//Список изначально пуст
		if (size == 0) {
			//Устанавливаем корневой узел
			root = new Node(id);
			//Он должен ссылаться на самого себя
			root.next = root;
		} //В списке уже были элементы
			else {
			
			//В сurr будет храниться ссылка на элемент, который предшествует первому
			//Либо будет хранить первого
			Node curr = root;
			while (curr.next != root)
				curr = curr.next;
			
			//Случай, когда список состоит из 1 элемента
			if (curr == root) {
				//Создаем новый узел и замыкаем список
				Node newKid = new Node(id);
				root.next = newKid;
				newKid.next = root;
			} else {
				//В списке более 1 элемента
				Node newKid = new Node(id);
				curr.next = newKid;
				newKid.next = root;
			}
		}
		//Увеличиваем кол-во детей в круге
		size++;
	}
	
	//Метод, использовавшийся для отладки. Нигде более не вызывается
	//Выводит детей в круге, начиная с некоторого start
	public void show(Node start) {
		Node curr = start;
		int count = size;
		while (count != 0) {
			System.out.print(curr.id + "->");
			curr = curr.next;
			count--;
		}
		System.out.println();
	}
	
	//Метод для выбывания каждого К-ого ребенка
	public void removeK(int K) {
		Node curr = root, prev = curr;
		System.out.print("Порядок выбывания детей: ");
		int id = 1;
		//Движемся в кольце до тех пор, пока не выбыдет
		//каждый ребенок
		while (size != 0) {
			//Попался К-й ребенок
			if (id == K) {
				//Перенаправляем ссылки
				prev.next = curr.next;
				curr.next = null;
				//Выводим выбывшего ребенка
				System.out.print(curr.id + " ");
				//Переходим к следующему
				curr = prev.next;
				size--;
				id = 1;
			}
			
			//Список оказался пуст - можно выходить
			if (size == 0) break;
			//Двигаемся дальше
			prev = curr;
			curr = curr.next;
			id++;
		}
		System.out.println("\n__________________");
	}
}

//Главный класс
public class CountingKids {
	
	public static void main(String[] args) {
		
		System.out.println("Введите К. Каждый К-й ребенок будет удален.");
		Scanner in = new Scanner(System.in);
		int K = -1;
		
		//Проверка ввода
		while (K <= 1) {
			try {
				K = Integer.valueOf(in.nextLine());
				
				if (K <= 1) throw new NumberFormatException();
			} catch (NumberFormatException e) {
				System.out.println("Повторите ввод!");
			}
		}
		
		//Создание кольцевого списка
		CycledList list = new CycledList();
		for (int i = 1; i <= 64; i++) {
			
			//Заполнение кольцевого списка
			for (int j = 1; j <= i; j++)
				list.AddKid(j);
			
			//Удаление всех К-ых детей
			System.out.println(i + " ребенка в круге:");
			list.removeK(K);
		}
		
		in.close();
	}
}
