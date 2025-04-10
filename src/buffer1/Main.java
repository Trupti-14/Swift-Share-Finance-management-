package buffer1;

import java.util.*;

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



    public void collectMemberInfo(int numMembers) {

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < numMembers; i++) {

            System.out.println("\nEnter details for member " + (i + 1) + ":");

            System.out.print("\nName: ");

            String name = scanner.nextLine();

            System.out.print("\nContact Number : 91+ ");

            String contactNumber = scanner.nextLine();



            members.add(new Member(name, contactNumber));

        }

    }



    public void displayMemberData() {

        System.out.println("\n      Members' Information:");

        System.out.println("--------------------------------------");

        System.out.printf("%-15s %-15s\n", "Name", "Contact Number");

        System.out.println("--------------------------------------");

        for (Member member : members) {

            System.out.printf("%-15s %-15s\n", member.name , member.contactNumber);

        }

    }



    public void allocateAmount() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\n\nEnter the total amount to be paid: ");

        totalAmount = scanner.nextDouble();

        scanner.nextLine();  // Consume the newline



        System.out.print("\nIs the amount to be paid the same by all members (yes/no)? ");

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

                    System.out.print("Enter amount for " + member.name + ": ");

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

        System.out.println("        \nAmount Allocation:");

        System.out.println("--------------------------------------");

        System.out.printf("%-15s %-10s %-10s\n", "Name", "Amount", "Status");

        System.out.println("--------------------------------------");

        for (Member member : members) {

            System.out.printf("%-15s %-10.2f %-10s\n", member.name, member.amountDue, "Unpaid");

        }

    }

    public void collectPayments() {

        Scanner scanner = new Scanner(System.in);

        boolean allPaid = false;

        while (!allPaid) {

            allPaid = true;

            for (Member member : members) {

                if (!member.isPaid) {

                	System.out.println();
                    System.out.print("\n"+member.name + ",do you want to pay the amount? (yes/no): ");

                    String response = scanner.nextLine();



                    if (response.equalsIgnoreCase("yes")) {

                        member.isPaid = true; // Automatically mark as paid if they say "yes"

                        System.out.println(member.name + " has paid.");

                    } else {

                        System.out.println(member.name + " has not paid yet.");

                        allPaid = false;  // If any member hasn't paid, continue the loop

                    }

                }

            }

            // If anyone is unpaid, allow the group to pay on behalf of others

            if (!allPaid) {

                handleUnpaidMembers(scanner);

            }

            displayPaymentStatus();

        }

    }

    private void handleUnpaidMembers(Scanner scanner) {

        for (Member member : members) {

            if (!member.isPaid) {

                System.out.println("\n" + member.name + " has not paid yet.");

                System.out.print("\nDo any group members want to pay on behalf of " + member.name + "? (yes/no): ");

                String response = scanner.nextLine();



                if (response.equalsIgnoreCase("yes")) {

                    // Let the group select a person to pay on behalf of the unpaid member

                    System.out.print("\nSelect a person to pay on behalf of " + member.name + " by their name or contact number:");

                    String searchTerm = scanner.nextLine();



                    Member selectedPayer = searchMember(searchTerm);

                    if (selectedPayer != null) {

                        System.out.print("\nDoes " + selectedPayer.name + " agree to pay on behalf of " + member.name + "? (yes/no): ");

                        String agreeResponse = scanner.nextLine();



                        if (agreeResponse.equalsIgnoreCase("yes")) {

                            selectedPayer.isPaid = true;  // The selected member will be marked as paid

                            member.isPaid = true;  // The unpaid member's status will also be marked as paid

                            System.out.println("\n"+selectedPayer.name + " has paid on behalf of " + member.name);

                        } else {

                            System.out.println("\n"+selectedPayer.name + " does not agree to pay.");

                        }

                    } else {

                        System.out.println("\nNo member found with that name, ID, or contact number.");

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

        return null;  // Return null if no member matches the search term

    }



    public void displayPaymentStatus() {

        System.out.println("\n        Payment Status:");

        System.out.println("--------------------------------------");

        System.out.printf("%-15s %-10s %-10s\n", "Name", "Amount", "Status");

        System.out.println("--------------------------------------");

        for (Member member : members) {

            String status = member.isPaid ? "Paid" : "Not Paid";

            System.out.printf("%-15s %-10.2f %-10s\n", member.name, member.amountDue, status);

        }

    }



    public void transferFunds() {

        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.print("\nEnter the sender's unique ID: ");

        String senderID = scanner.nextLine();
        
        System.out.print("\nEnter the sender's contact number: 91+ ");

        String senderContact = scanner.nextLine();


        System.out.println("\nTotal amount has been transferred to sender ");

    }

}



public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of members in the group : ");

        int numMembers = scanner.nextInt();

        scanner.nextLine();  // Consume the newline

        PaymentSystem paymentSystem = new PaymentSystem();

        paymentSystem.collectMemberInfo(numMembers);

        paymentSystem.displayMemberData();

        paymentSystem.allocateAmount();

        paymentSystem.collectPayments();  // Collect payment confirmations (whether they want to pay or not)

        paymentSystem.transferFunds();

    }

}
