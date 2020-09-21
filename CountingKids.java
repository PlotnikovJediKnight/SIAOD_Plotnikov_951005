import java.util.Scanner;

//�����, ������������ ������ �1
//������ �2
//����������

//�����-���� ��� �������� �������
class Node{
	public int id;		//���������� ����� �������
	public Node next;	//������ �� ���������� �������
	
	//����������� ��� �������� �������
	public Node(int number) {
		id = number;
		next = null;
	}
}

//����� - ��������� ������.
class CycledList{
	private Node root;		//�������� ����
	private int size;		//���-�� ����� � �����
	
	//����������� ��� �������� ������� ���������� ������
	public CycledList() {
		root = null;
		size = 0;
	}
	
	//���������� ������� � ����
	public void AddKid(int id) {
		//������ ���������� ����
		if (size == 0) {
			//������������� �������� ����
			root = new Node(id);
			//�� ������ ��������� �� ������ ����
			root.next = root;
		} //� ������ ��� ���� ��������
			else {
			
			//� �urr ����� ��������� ������ �� �������, ������� ������������ �������
			//���� ����� ������� �������
			Node curr = root;
			while (curr.next != root)
				curr = curr.next;
			
			//������, ����� ������ ������� �� 1 ��������
			if (curr == root) {
				//������� ����� ���� � �������� ������
				Node newKid = new Node(id);
				root.next = newKid;
				newKid.next = root;
			} else {
				//� ������ ����� 1 ��������
				Node newKid = new Node(id);
				curr.next = newKid;
				newKid.next = root;
			}
		}
		//����������� ���-�� ����� � �����
		size++;
	}
	
	//�����, ���������������� ��� �������. ����� ����� �� ����������
	//������� ����� � �����, ������� � ���������� start
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
	
	//����� ��� ��������� ������� �-��� �������
	public void removeK(int K) {
		Node curr = root, prev = curr;
		System.out.print("������� ��������� �����: ");
		int id = 1;
		//�������� � ������ �� ��� ���, ���� �� �������
		//������ �������
		while (size != 0) {
			//������� �-� �������
			if (id == K) {
				//�������������� ������
				prev.next = curr.next;
				curr.next = null;
				//������� ��������� �������
				System.out.print(curr.id + " ");
				//��������� � ����������
				curr = prev.next;
				size--;
				id = 1;
			}
			
			//������ �������� ���� - ����� ��������
			if (size == 0) break;
			//��������� ������
			prev = curr;
			curr = curr.next;
			id++;
		}
		System.out.println("\n__________________");
	}
}

//������� �����
public class CountingKids {
	
	public static void main(String[] args) {
		
		System.out.println("������� �. ������ �-� ������� ����� ������.");
		Scanner in = new Scanner(System.in);
		int K = -1;
		
		//�������� �����
		while (K <= 1) {
			try {
				K = Integer.valueOf(in.nextLine());
				
				if (K <= 1) throw new NumberFormatException();
			} catch (NumberFormatException e) {
				System.out.println("��������� ����!");
			}
		}
		
		//�������� ���������� ������
		CycledList list = new CycledList();
		for (int i = 1; i <= 64; i++) {
			
			//���������� ���������� ������
			for (int j = 1; j <= i; j++)
				list.AddKid(j);
			
			//�������� ���� �-�� �����
			System.out.println(i + " ������� � �����:");
			list.removeK(K);
		}
		
		in.close();
	}
}
