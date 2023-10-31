import java.io.*;
import java.util.*;

class Book {
    private String author, title, publisher, bookID;
    private float price;
    private int stock;

    public Book() {
        author = "NULL";
        title = "NULL";
        publisher = "NULL";
        bookID = "NULL";
        price = 0;
        stock = 0;
    }

    public void feeddata() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter author: ");
        author = sc.nextLine();
        System.out.print("Enter title: ");
        title = sc.nextLine();
        System.out.print("Enter publisher: ");
        publisher = sc.nextLine();
        generatebookID();
        System.out.print("Enter price: ");
        price = sc.nextFloat();
        System.out.print("Enter stock: ");
        stock = sc.nextInt();
    }

    public void feeddata(String au, String ti, String pu, int pr, int st) {
        author = au;
        title = ti;
        publisher = pu;
        generatebookID();
        price = pr;
        stock = st;
    }

    public void showdata() {
        System.out.println("Author: " + author);
        System.out.println("Title: " + title);
        System.out.println("Publisher: " + publisher);
        System.out.println("Book ID: " + bookID);
        System.out.println("Price: " + price);
        System.out.println("Stock: " + stock);
    }

    public void generatebookID() {
        Random rand = new Random();
        int num = rand.nextInt(9000000) + 1000000;
        bookID = "B" + num;
    }

    public int storebook() {
        try {
            FileOutputStream fout = new FileOutputStream("bookfile.txt", true);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            fout.close();
            return 1;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public void stock_report() {
        try {
            FileInputStream fin = new FileInputStream("bookfile.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            while (true) {
                Book b = (Book) ois.readObject();
                b.showdata();
            }
        } catch (EOFException e) {
            System.out.println("End of file reached");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void search_title(String ti) {
        try {
            FileInputStream fin = new FileInputStream("bookfile.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            while (true) {
                Book b = (Book) ois.readObject();
                if (b.title.equals(ti)) {
                    b.showdata();
                }
            }
        } catch (EOFException e) {
            System.out.println("End of file reached");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void search_bookID(String bi) {
        try {
            FileInputStream fin = new FileInputStream("bookfile.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            while (true) {
                Book b = (Book) ois.readObject();
                if (b.bookID.equals(bi)) {
                    b.showdata();
                }
            }
        } catch (EOFException e) {
            System.out.println("End of file reached");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete_book(String ti) {
        try {
            FileInputStream fin = new FileInputStream("bookfile.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            FileOutputStream fout = new FileOutputStream("temp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            while (true) {
                Book b = (Book) ois.readObject();
                if (!b.title.equals(ti)) {
                    oos.writeObject(b);
                }
            }
        } catch (EOFException e) {
            System.out.println("End of file reached");
        } catch (Exception e) {
            System.out.println(e);
        }
        File file = new File("bookfile.txt");
        File temp = new File("temp.txt");
        file.delete();
        temp.renameTo(file);
    }

    public void update_data(String ti) {
        try {
            FileInputStream fin = new FileInputStream("bookfile.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            FileOutputStream fout = new FileOutputStream("temp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            while (true) {
                Book b = (Book) ois.readObject();
                if (b.title.equals(ti)) {
                    b.feeddata();
                }
                oos.writeObject(b);
            }
        } catch (EOFException e) {
            System.out.println("End of file reached");
        } catch (Exception e) {
            System.out.println(e);
        }
        File file = new File("bookfile.txt");
        File temp = new File("temp.txt");
        file.delete();
        temp.renameTo(file);
    }

    public void update_stocks(int noc) {
        stock = stock - noc;
    }

    public void buy_book(int noc, String ti) {
        try {
            FileInputStream fin = new FileInputStream("bookfile.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            FileOutputStream fout = new FileOutputStream("temp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            while (true) {
                Book b = (Book) ois.readObject();
                if (b.title.equals(ti)) {
                    System.out.println("\nBOOK FOUND");
                    if (noc < stock) {
                        System.out.println("\nSUFFICIENT COPIES ARE AVAILABLE");
                        b.update_stocks(noc);
                    } else {
                        System.out.println("\nINSUFFICIENT COPIES ARE AVAILABLE");
                    }
                }
                oos.writeObject(b);
            }
        } catch (EOFException e) {
            System.out.println("End of file reached");
        } catch (Exception e) {
            System.out.println(e);
        }
        File file = new File("bookfile.txt");
        File temp = new File("temp.txt");
        file.delete();
        temp.renameTo(file);
    }
}

public class Main {
    public static void main(String[] args) {
        Book b1 = new Book();
        String title;
        int choice;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("\t\t\t\t------------------------------");
            System.out.println("\t\t\t\t|            MENU            |");
            System.out.println("\t\t\t\t|  1. Entry of New Book      |");
            System.out.println("\t\t\t\t|  2. View stock report      |");
            System.out.println("\t\t\t\t|  3. Search For Book        |");
            System.out.println("\t\t\t\t|  4. Delete a record        |");
            System.out.println("\t\t\t\t|  5. Edit a record          |");
            System.out.println("\t\t\t\t|  6. Buy a book             |");
            System.out.println("\t\t\t\t|  7. Exit                   |");
            System.out.println("\t\t\t\t------------------------------");
            System.out.print("\nEnter your Choice : ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    b1.feeddata();
                    b1.storebook();
                    System.out.println("\nRecord stored");
                    break;
                case 2:
                    System.out.println("\nSTOCK REPORT\n");
                    b1.stock_report();
                    break;
                case 3:
                    System.out.println("\nTo search with book's title, press 1\nTo search with Book ID, press 2\n");
                    int num = sc.nextInt();
                    if (num == 1) {
                        System.out.print("\nEnter the title of the book : ");
                        sc.nextLine();
                        title = sc.nextLine();
                        b1.search_title(title);
                    } else if (num == 2) {
                        System.out.print("\nEnter the Book ID : ");
                        String b_ID = sc.nextLine();
                        b1.search_bookID(b_ID);
                    } else {
                        System.out.println("\nInvalid Choice Entered");
                    }
                    break;
                case 4:
                    System.out.print("\nEnter the title of the book which is going to be deleted : ");
                    sc.nextLine();
                    title = sc.nextLine();
                    b1.delete_book(title);
                    break;
                case 5:
                    System.out.print("\nEnter the title of book whose data you want to update : ");
                    sc.nextLine();
                    title = sc.nextLine();
                    b1.update_data(title);
                    break;
                case 6:
                    System.out.print("\nEnter the name of the book which you want to buy : ");
                    sc.nextLine();
                    title = sc.nextLine();
                    System.out.print("\nEnter the number of copies of the book : ");
                    int noc = sc.nextInt();
                    b1.buy_book(noc, title);
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.out.println("\nInvalid Choice Entered");
            }
        }
    }
}
