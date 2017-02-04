import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CliTool {
	public static void main(String args[]) throws IOException {
		//create otput files
		ProcessData.process_data();
		//Run CLI tool
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter User Name");
		String usrname=sc.next();
		System.out.println("Ente Choice\n1.Expense Repoprt\n2.Is a Student/Hobbies \n3.Compatibility");
		int choice=sc.nextInt();
		BufferedReader br=null;
		String line="";
		if(choice==1)
		{
			br=new BufferedReader(new FileReader("output_user/expense_"+usrname+".csv"));
			//System.out.println("Month\tExpense\tEarnings");
			while ((line = br.readLine()) != null) {
				//Month,Expense,Earnings
				String data[]=line.split(",");
				System.out.println(data[0]+"\t"+data[1]+"\t"+data[2]);
            }
		}
		if(choice==2)
		{
			br=new BufferedReader(new FileReader("output_user/hobbies_"+usrname+".csv"));
			while ((line = br.readLine()) != null) {
				//Month,Expense,Earnings
				//String data[]=line.split(",");
				System.out.println(line);
            }
		}
		if(choice==3)
		{
			br=new BufferedReader(new FileReader("output_user/match_"+usrname+".csv"));
			System.out.println("Compatibilities with:");
			while ((line = br.readLine()) != null) {
				//Month,Expense,Earnings
				//String data[]=line.split(",");
				System.out.println(line);
            }
		}
		
	}

}
