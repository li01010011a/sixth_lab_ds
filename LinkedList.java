import java.io.*;
import java.util.*;

public class LinkedList {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		ListItem head = new ListItem("hell", null);
		init(head);
		printMenu();
		int user_choice = in.nextInt();
		while (user_choice!=4) {
			proccessUserChoice(user_choice, head, in);
			printMenu();
			user_choice = in.nextInt();
		}
		proccessUserChoice(user_choice, head, in);

		String fileName = "file.txt", data="hell";
		int pos=getPosFromUser(in);
		show(head);
		boolean b = writeToFile(head, fileName);
		System.out.println("Statement that file was saved successfully is: \u001b[33m\u001b[7m" + b + "\u001b[0m");

		head = addFirst( head, "hello");
		head = addFirst( head, "hello1");
		head = addFirst( head, "hello11");
		head = addLast( head, "hello1331");
		show(head);
		b = writeToFile(head, fileName);
		System.out.println("Statement that file was saved successfully is: \u001b[33m\u001b[7m" + b + "\u001b[0m");
		head = delete(head, 3);
		show(head);
		head = insert(head, "asdfasdf", 3);
		show(head);
		int count = count(head);
		System.out.println(count);
		head = readFromFile(head, fileName);
		sortList(head);
		show(head);
	}

	private static void sortList(ListItem head) {
		System.out.println("Sorting: ");
		while (head != null) {
			if (head.data.compareTo(head.next.data)==1) { // 1st bigger
				swap(head,head.next);
			}
		}
	}

	private static void swap(ListItem head, ListItem next) {
		ListItem temp = head;
		head = next;
		next = head;
	}

	private static void init(ListItem head) {
		for (int i=0;i<5;i++) {
			head = addFirst( head, "hello"+Integer.toString(i));
		}
	}

	private static void proccessUserChoice(int user_choice, ListItem head, Scanner in) {
		switch (user_choice) {
        case 1:
        	System.out.println("Type line to be added: ");
        	String data = in.nextLine();
        	add(head, data);
            break;
        case 2:
        	System.out.println("Delete.");
        	delete(head, 3);
            break;
        case 3:
        	System.out.println("Show");
        	show(head);
            break;
        case 4:
        	exit();
        	break;
        default:
            System.out.println("Invalid choice. The program will be finished.");
            return;
		}
	}

	private static void exit() {
		System.out.println("Good bye, User");
	}

	private static void printMenu() {
		System.out.println("1) Add item\n2)Delete List\n3)Show List\n4)Exit");
	}

	private static int getPosFromUser(Scanner in) {
		System.out.println("\u001b[33m\u001b[7mEnter a pos number(where you want to place/read data to/from): \u001b[0m");
		return in.nextInt();
	}

	private static ListItem readFromFile(ListItem head, String fileName) {
		System.out.println("readFromFile function");
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
    		while (head != null) {
    			System.out.println(br.readLine());
    			head = head.next;
    		}
    		br.close();
            return head;
        }
        catch (IOException e) {
            return null;
        }
	}

	private static int count(ListItem head) {
		int count = 0;
		while (head != null) {
			count++;
			head = head.next;
		}
		return count;
	}

	private static ListItem insert(ListItem head, String data, int pos) {
		if (pos==0||head==null) return addFirst(head,data);
		head.next = insert(head.next,data,pos-1);
		return head;
	}
	
	private static ListItem addLast(ListItem head, String data) {
		if (head == null) return new ListItem(data,null);
		head.next = addLast(head.next,data);
		return head;
	}
	
	private static ListItem addFirst(ListItem head, String data) {
		head = new ListItem(data,head);
		return head;
	}

	private static ListItem add(ListItem head, String data) {
		if (head==null) return new ListItem(data,null);
		head.next = add(head.next,data);
		return head;
	}

	private static ListItem delete(ListItem head, int pos) {
		//head=null;
		if (pos==0 || head==null) return deleteFirst(head);
		head.next = delete(head.next, pos-=1);
		return head;
	}
	
	private static ListItem deleteFirst(ListItem head) {
		if (head!=null) return head.next;
		return null;
	}
	
	private static ListItem deleteLast(ListItem head) {
		if (head==null||head.next==null) return null;
		head.next = deleteLast(head.next);
		return head;
	}

	private static boolean writeToFile(ListItem head, String fileName) {
		try {
            PrintWriter file = new PrintWriter(new FileWriter(fileName));            
    		while (head != null) {
    			file.println(head.data);
    			head = head.next;
    		}
    		file.close();
            return true;
        }
        catch (IOException e) {
            return false;
        }
	}

	private static void show(ListItem head) {
		while (head != null) {
			System.out.println(head.data);
			head = head.next;
		}
	}
		
}

class ListItem{
	String data;
	ListItem next;
	ListItem(String data, ListItem next){
		this.data = data;
		this.next = next;
	}
}