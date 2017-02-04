import java.util.*;
import java.io.*;

public class ProcessData {
	public static void process_data() {
		File directory = new File("transaction-data");
		File[] listOfFiles = directory.listFiles();
		File dir_output = new File("output_user");
		dir_output.mkdir();
		File f = new File("output_user/expense.csv");
		// f.createNewFile();
		
		HashMap<String, Double[]> mapExpenses = new HashMap<String, Double[]>();

		try {
		for (File file : listOfFiles) {
			HashMap<String, Hobbies> hobbies = new HashMap<String, Hobbies>();

			EnumMap<Hobbies, Integer> hobbiesCount = new EnumMap<Hobbies, Integer>(Hobbies.class);
			for (Hobbies h : Hobbies.values()) {
				for (String keyword : h.getKeywords().split(","))
					hobbies.put(keyword, h);
			}
			for (Hobbies h : Hobbies.values()) {
				hobbiesCount.put(h, 0);
			}
			boolean isStudent = false;
			Scanner scanner;
			
				// f.createNewFile();
				if (file.isFile() && file.toString().endsWith(".csv")) {
					System.out.println("abc");
					File out_expenses = new File("output_user/expense_" + file.getName().toString());
					File out_hobbies = new File("output_user/hobbies_" + file.getName().toString());
					PrintWriter pw_expenses = new PrintWriter(out_expenses);
					PrintWriter pw_hobbies = new PrintWriter(out_hobbies);

					out_expenses.createNewFile();
					out_hobbies.createNewFile();
					StringBuilder sb_expenses = new StringBuilder();
					StringBuilder sb_hobbies = new StringBuilder();
					StringBuilder sb_compatibility = new StringBuilder();
					sb_expenses.append("Month,Expenses,Earnings\n");
					sb_hobbies.append("Hobbies\n");
					String month_change = "";
					
					double monthly_expense = 0, total_expense = 0, earnings = 0, total_earnings = 0;
					scanner = new Scanner(file);
					scanner.useDelimiter(",|\\n");
					while (scanner.hasNext()) {
						String uid = scanner.next();
						// System.out.println(uid);
						String date = scanner.next();
						if (!date.contains("/"))
							continue;
						String activity = scanner.next();
						for (String act : activity.split(" ")) {
							if (hobbies.containsKey(act.toLowerCase()))
								hobbiesCount.put(hobbies.get(act.toLowerCase()),
										hobbiesCount.get(hobbies.get(act.toLowerCase())) + 1);
						}
						String expense = scanner.next();
						String location = scanner.next();
						String d[] = date.split("/");
						if (!month_change.equals(d[0] + "/" + d[2])) {
							// System.out.println(month_change + " " +
							// monthly_expense + " " + earnings);
							sb_expenses.append(month_change + "," + monthly_expense + "," + earnings + "\n");
							month_change = d[0] + "/" + d[2];
							total_expense+=monthly_expense;
							total_earnings += earnings;
							monthly_expense = 0;
							earnings = 0;
						}
						if (!Character.isDigit(expense.charAt(0))) {
							// System.out.println(expense);
							monthly_expense += Double.parseDouble(expense.substring(1));
						} else {
							// System.out.println("ABC");
							earnings += Double.parseDouble(expense);
						}
					}
					String user = file.getName().substring(file.getName().lastIndexOf("_")+1,file.getName().lastIndexOf("."));
					mapExpenses.put(user, new Double[]{total_expense,total_earnings}) ;
					pw_expenses.print(sb_expenses);
					pw_expenses.close();
					// break;
					for(Hobbies h:hobbiesCount.keySet() )
						if(hobbiesCount.get(h)!=0) {
							sb_hobbies.append(h.toString()+"\n");
						}
					pw_hobbies.print(sb_hobbies);
					pw_hobbies.close();
				}
		}
		System.out.println(mapExpenses);
		generateCompatibility(mapExpenses);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// break;

		}
		

	
	public static void generateCompatibility(HashMap<String, Double[]> mapExpenses) throws IOException{
		File directory = new File("output_user");
		File[] listOfFiles = directory.listFiles();
		HashMap<String, String> compatibility = new HashMap<String, String>();
		
		for (File file : listOfFiles) {
			System.out.println(file);
			if(file.getName().contains("hobbies")) {
			File out_match = new File("output_user/match_" + file.getName().toString());
			PrintWriter pw_match = new PrintWriter(out_match);

			out_match.createNewFile();

			Scanner scanner = new Scanner(file);
			scanner.useDelimiter(",|\\n");
			HashSet<String>hobbies = new HashSet<String>();
			while (scanner.hasNext()) {
					if(scanner.next().equals("Hobbies"))
						continue;
					else
						hobbies.add(scanner.next());
						
					
				
			}
			for (File other_file : listOfFiles) {
				int common_hobbies = 0;

				if(file!=other_file) {
					Scanner scanner2 = new Scanner(other_file);
					scanner2.useDelimiter(",");
					String user = other_file.getName().substring(other_file.getName().lastIndexOf("_")+1,other_file.getName().lastIndexOf("."));
					while (scanner2.hasNext()) {
						String hobby = scanner2.next();
						System.out.println(hobby);
						if(hobbies.contains(hobby))
							common_hobbies++;
					}
					if(common_hobbies>3) {
						pw_match.println(user);						
						
					}
					
				}
			}
			pw_match.close();


		}
		}
		
	}


}
