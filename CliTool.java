import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CliTool {
	public static void main(String args[]) throws IOException {
		// create otput files
		ProcessData.process_data();
		// Run CLI tool
		xyz: while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Choice\n1.Expense Repoprt\n2.Is a Student/Hobbies \n3.Compatibility \n4.Exit");
			int choice = sc.nextInt();
			BufferedReader br = null;
			String line = "";
			try {
				switch (choice) {
				case 1:
					System.out.println("Enter User Name");
					String usrname = sc.next();
					br = new BufferedReader(new FileReader("output_expenses/expense_" + usrname + ".csv"));
					// System.out.println("Month\tExpense\tEarnings");
					while ((line = br.readLine()) != null) {
						// Month,Expense,Earnings
						String data[] = line.split(",");
						System.out.println(data[0] + "\t" + data[1] + "\t" + data[2]);
					}
					break;
				case 2:
					System.out.println("Enter User Name");
					usrname = sc.next();
					br = new BufferedReader(new FileReader("output_hobbies/hobbies_" + usrname + ".csv"));
					while ((line = br.readLine()) != null) {
						// Month,Expense,Earnings
						// String data[]=line.split(",");
						System.out.println(line);
					}
					break;
				case 3:
					System.out.println("Enter User Name");
					usrname = sc.next();
					br = new BufferedReader(new FileReader("output_match/match_hobbies_" + usrname + ".csv"));
					System.out.println("Compatibilities with:");
					while ((line = br.readLine()) != null) {
						// Month,Expense,Earnings
						// String data[]=line.split(",");
						System.out.println(line);
					}
					break;
				case 4:
					break xyz;
				default:
					System.out.println("invalid input");
				}
				br.close();
			} catch (Exception e) {
				System.out.println("invalid user");
			}
		}
	}
}
