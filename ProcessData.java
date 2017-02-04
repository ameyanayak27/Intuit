import java.util.*;
import java.io.*;

public class ProcessData {
	public static void process_data() {
		// input
		File directory = new File("transaction-data");
		File[] listOfFiles = directory.listFiles();
		// dir for expense report
		File dir_output_expenses = new File("output_expenses");
		dir_output_expenses.mkdir();
		// dir for hobbies report
		File dir_output_hobbies = new File("output_hobbies");
		dir_output_hobbies.mkdir();

		try {
			for (File file : listOfFiles) {
				HashMap<String, Hobbies> hobbies = new HashMap<String, Hobbies>();
				// map for counting matches in hobbies
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
					File out_expenses = new File("output_expenses/expense_" + file.getName().toString());
					File out_hobbies = new File("output_hobbies/hobbies_" + file.getName().toString());
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
							// if the month changes update
							sb_expenses.append(month_change + "," + monthly_expense + "," + earnings + "\n");
							month_change = d[0] + "/" + d[2];
							total_expense += monthly_expense;
							total_earnings += earnings;
							monthly_expense = 0;
							earnings = 0;
						}
						if (!Character.isDigit(expense.charAt(0))) {
							monthly_expense += Double.parseDouble(expense.substring(1));
						} else {
							earnings += Double.parseDouble(expense);
						}
					}
					pw_expenses.print(sb_expenses);
					pw_expenses.close();
					for (Hobbies h : hobbiesCount.keySet())
						if (hobbiesCount.get(h) != 0) {
							sb_hobbies.append(h.toString() + "\n");
						}
					pw_hobbies.print(sb_hobbies);
					pw_hobbies.close();
					scanner.close();

				}
			}
			generateCompatibility();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// break;

	}

	// generate comaptibility report
	public static void generateCompatibility() throws IOException {
		File directory = new File("output_hobbies");
		File[] listOfFiles = directory.listFiles();
		File dir_output_match = new File("output_match");
		dir_output_match.mkdir();
		HashMap<String, String> compatibility = new HashMap<String, String>();
		for (File file : listOfFiles) {
			if (file.getName().contains("hobbies")) {
				File out_match = new File("output_match/match_" + file.getName().toString());
				PrintWriter pw_match = new PrintWriter(out_match);
				out_match.createNewFile();

				Scanner scanner = new Scanner(file);
				scanner.useDelimiter("\\n");
				HashSet<String> hobbies = new HashSet<String>();
				while (scanner.hasNext()) {
					String x = scanner.next();
					if (x.equals("Hobbies"))
						continue;
					else
						hobbies.add(x);
				}
				StringBuffer sb = new StringBuffer();
				sb.append("User,Compatibility\n");
				for (File other_file : listOfFiles) {
					int common_hobbies = 0;
					if (file != other_file && other_file.getName().contains("hobbies")) {
						Scanner scanner2 = new Scanner(other_file);
						scanner2.useDelimiter("\\n");
						String user = other_file.getName().substring(other_file.getName().lastIndexOf("_") + 1,
								other_file.getName().lastIndexOf("."));
						while (scanner2.hasNext()) {
							String hobby = scanner2.next();
							if (hobbies.contains(hobby)) {
								common_hobbies++;
								
							}
						}
						// for every hobby match multiply with 0.1
						sb.append(user + "," + (0.1 * common_hobbies) + "\n");
						scanner2.close();
					}
				}
				scanner.close();
				pw_match.println(sb);
				pw_match.close();
			}
		}

	}

}
