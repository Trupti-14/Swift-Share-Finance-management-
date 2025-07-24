package bufferFinTech;
import java.util.*;
class Contact {

    String name;
    String contactNumber;
    public Contact(String name, String contactNumber) {

        this.name = name;
        this.contactNumber = contactNumber;

    }

}
class Member {

    String name;
    String contactNumber;
    double amountDue;
    boolean isPaid;
    public Member(String name, String contactNumber) {

        this.name = name;

        this.contactNumber = contactNumber;

        this.amountDue = 0.0;

        this.isPaid = false;

    }

}
class PaymentSystem {

    private List<Member> members = new ArrayList<>();

    private double totalAmount;

    private List<Contact> contactList = Arrays.asList(

        new Contact("Riya", "1234567890"),

        new Contact("Rhea", "2345678901"),

        new Contact("Chaitrali", "3456789012"),

        new Contact("Diya", "4567890123"),

        new Contact("Neha", "5678901234"),
        
        new Contact ("Harshada" , "7889654321"),
        
        new Contact("Laxmi" , "9087897654"),
        
        new Contact("Bhoomi" , "2314567890"),
        
        new Contact("Trupti", "5674783456"),
        
        new Contact ("Vedika" , "7234541234")

    );
    
    private String getValidContactNumber(Scanner scanner) {

        while (true) {

            System.out.print("Contact Number: ");

            String contactNumber = scanner.nextLine().trim();

            if (isValidContactNumber(contactNumber)) {

                return contactNumber;

            } else {

            	System.out.println(" ");

                System.out.println("Invalid contact number."
                		+ "\n Try again.");

            }

        }

    }
    private boolean isValidContactNumber(String number) 
    {

        return (number.matches("\\d{11}") && number.startsWith("0")) ||

               (number.matches("\\d{10}") && !number.startsWith("0"));

    }

    public void collectMemberInfo(int numMembers) 
    {

        Scanner scanner = new Scanner(System.in);
       
        for (int i = 0; i < numMembers; i++)
        {

        	String name = null, contactNumber = null ;
        	
             System.out.println("\nEnter details for member " + (i + 1)+".");

             System.out.print("\nDo you want to add from contact list? (yes/no): ");

             String fromList = scanner.nextLine();

            
              if (fromList.equalsIgnoreCase("yes")) 
              {
                System.out.println("\nAdd mambers from contact list.") ;
                System.out.println("\n                    MY CONTACT LIST :");
                System.out.println("---------------------------------------------------------------");
                System.out.printf( "%-18s %-20s %-18s\n","ID", "Name", "Contact Number");
                System.out.println("---------------------------------------------------------------");

                for (int j = 0; j < contactList.size(); j++) 
                {

                    Contact c = contactList.get(j);
                   // System.out.print((j + 1)+ ". "); 
                    System.out.printf("%-18s %-20s %-18s\n", (j+1) ,c.name, c.contactNumber);

                }
                
                System.out.println();
                System.out.println("// Select member's ID") ;
                System.out.print("Select mamber "+(i+1)+" : ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 
                Contact selectedContact = contactList.get(choice - 1);

                name = selectedContact.name;

                contactNumber = selectedContact.contactNumber;

                if (!isValidContactNumber(contactNumber)) 
                {

                    System.out.println("Invalid");

                    contactNumber = getValidContactNumber(scanner);

                }

            } 
            else
            {
            	System.out.print("\nWant to add member other than contact list (Yes / No): ") ;
                String Choice = scanner.next();
                scanner.nextLine() ;
                if(Choice.equalsIgnoreCase("Yes"))
                {
                	System.out.print("\nName: ");
                    name = scanner.next();
                    scanner.nextLine() ;
                    contactNumber = getValidContactNumber(scanner);
                    
                }

            }
          members.add(new Member(name, contactNumber));
        }

    }

    public void displayMemberData() {

    	System.out.println(" ");

        System.out.println("\n                MEMBERS DATA :");

        System.out.println(" ");


        System.out.println("-------------------------------------------------");

        System.out.printf("%-20s %-20s\n", "Name", "Contact Number");

        System.out.println("-------------------------------------------------");

        for (Member member : members) {

            System.out.printf("%-20s %-20s\n", member.name, member.contactNumber);

        }

    }
    public void allocateAmount() {

        Scanner scanner = new Scanner(System.in);

        System.out.println(" ");

        System.out.println(" ");

        System.out.print("Enter the total amount to be paid: ");

        totalAmount = scanner.nextDouble();

        scanner.nextLine();  // Consume the newline

        System.out.println(" ");

        System.out.print("Is the amount to be paid is same by all members (yes/no)? ");

        String sameAmount = scanner.nextLine();



        if (sameAmount.equalsIgnoreCase("yes")) {

            double equalAmount = totalAmount / members.size();

            for (Member member : members) {

                member.amountDue = equalAmount;

            }

        } else {

            double sumAmount = 0.0;

            while (sumAmount != totalAmount) {

                sumAmount = 0.0;

                for (Member member : members) {

                    System.out.print( member.name + ", Enter the amount: ");

                    double amount = scanner.nextDouble();

                    member.amountDue = amount;

                    sumAmount += amount;

                }

                if (sumAmount != totalAmount) {

                    System.out.println("Total amount does not match. Please try again.");

                }

            }

        }

        displayAmountTable();

    }

    public void displayAmountTable() {

        System.out.println("\n                      TABLE :");

        System.out.println(" ");

        System.out.println("------------------------------------------------");

        System.out.printf("%-20s %-10s %-10s\n", "Name", "Amount", "Status");

        System.out.println("------------------------------------------------");

        for (Member member : members) {

            System.out.printf("%-20s %-10.2f %-10s\n", member.name, member.amountDue, "Unpaid");

        }

    }

    public void collectPayments() {

        Scanner scanner = new Scanner(System.in);

        boolean allPaid = false;

        while (!allPaid) {

            allPaid = true;

            for (Member member : members) {

                if (!member.isPaid) {

                	System.out.println(" ");

                	System.out.println(" ");

                    System.out.print(member.name + ", do you want to pay the amount? (yes/no): ");

                    String response = scanner.nextLine();

                    if (response.equalsIgnoreCase("yes")) {

                        member.isPaid = true;

                        System.out.println(member.name + " has paid successfully !!");

                    } else {

                        System.out.println(member.name + " has not paid yet.");

                        allPaid = false;

                    }

                }

            }

            if (!allPaid) {

                handleUnpaidMembers(scanner);

            }
        }
        
        displayPaymentStatus();
    }

    private void handleUnpaidMembers(Scanner scanner) {

        for (Member member : members) {

            if (!member.isPaid) {


                System.out.print("\nDo any group members want to pay on behalf of " + member.name + "? (yes/no): ");

                String response = scanner.nextLine();
                
                if (response.equalsIgnoreCase("yes")) {

                    System.out.print("Select a person to pay on behalf of " + member.name + " (name/contact number): ");

                    String searchTerm = scanner.nextLine();
                    
                    Member selectedPayer = searchMember(searchTerm);

                    if (selectedPayer != null) {

                        System.out.print("\nDoes " + selectedPayer.name + " agree to pay on behalf of " + member.name + "? (yes/no): ");

                        String agreeResponse = scanner.nextLine();
                        
                        if (agreeResponse.equalsIgnoreCase("yes")) {

                            selectedPayer.isPaid = true;

                            member.isPaid = true;

                            System.out.println(selectedPayer.name + " has paid on behalf of " + member.name);

                        } else {

                            System.out.println(selectedPayer.name + " does not agree to pay.");

                        }

                    } else {

                        System.out.println("No member found with that name or contact number.");

                    }

                }

            }

        }

    }



    private Member searchMember(String searchTerm) {

        for (Member member : members) {

            if (member.name.equalsIgnoreCase(searchTerm) ||

                member.contactNumber.equalsIgnoreCase(searchTerm)) {

                return member;

            }

        }

        return null;

    }
    public void displayPaymentStatus() {

        System.out.println("\n              PAYMENT STATUS : ");

        System.out.println("----------------------------------------------------");

        System.out.printf("%-20s %-10s %-10s\n", "Name", "Amount", "Status");

        System.out.println("----------------------------------------------------");

        for (Member member : members) {

            String status = member.isPaid ? "Paid" : "Not Paid";

            System.out.printf("%-20s %-10.2f %-10s\n", member.name, member.amountDue, status);

        }

    }
    public void transferFunds() {

        Scanner scanner = new Scanner(System.in);

        System.out.println(" ");
        
        System.out.println("Money Bag has collected the required amount.");
        
        System.out.print("\nEnter the name of the receiver : ");

        String senderName = scanner.nextLine();

        System.out.print("Enter the receiver's contact number : 91+ ");

        String senderContact = scanner.nextLine();

        System.out.println("Are you sure you want to proceed ??? (yes/no) ") ;
        
        System.out.println("\nTotal amount has been transferred to " + senderName + " successfully :) :) :)");
        System.out.println() ;
        //System.out.println("With the successful transaction, the Money Bag Group has been dissolved !!!");

    }

}
public class BillSplitApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\t\t\t*** WELCOME TO MONEY BAG SYSTEM!!! *** ");

        System.out.println("CREATE GROUP");

        System.out.print("\nEnter total members in the group: ");

        int numMembers = scanner.nextInt();

        scanner.nextLine();  // Consume newline

        PaymentSystem paymentSystem = new PaymentSystem();

        paymentSystem.collectMemberInfo(numMembers);

        paymentSystem.displayMemberData();

        paymentSystem.allocateAmount();

        paymentSystem.collectPayments();

        paymentSystem.transferFunds();

        

    }

}