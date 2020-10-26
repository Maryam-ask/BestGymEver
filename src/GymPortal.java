import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Maryam Askari
 * Date: 10/18/2020
 * Time: 1:32 PM
 * Project: IntelliJ IDEA
 */
public class GymPortal {
    // Path adresser (att läsa fil från och skriva ut fil i)
    private static final String customersPath = "D:\\programming projects\\Java\\Gym_Inlämningsuppgift2\\src\\Customers.txt";
    private static final String directoryPath = "D:\\programming projects\\Java\\Gym_Inlämningsuppgift2\\src\\";

    private String firstName;
    private String lastName;

    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // En metod för att lösa in filen
    public String readFile(String path) {
        File file = new File(path);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder printer = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                printer.append(line).append("\n"); // Addera varje rad från filen till Stringen printer
            }
            reader.close();

            return printer.toString();
        } catch (IOException ex) {
            return null;
        }
    }


    public ArrayList<String> getRegistrationList() {
        ArrayList<String> registrationList = new ArrayList<>();
        String[] perLine = readFile(customersPath).split("\n");

        // Addera varje 2 rader i en arraylist element
        for (int i = 0; i < perLine.length; i += 2) {
            registrationList.add(perLine[i] + "\n" + perLine[i + 1]);
        }

        return registrationList;
    }

    // En metod som addera personerna som har tillgång att använda gymet till en lista
    public ArrayList<String> getAllowedPerson() {
        ArrayList<String> allowedPerson = new ArrayList<>();

        for (String s : getRegistrationList()) {
            // att avskilja namn och efternamn som ligger i en list elementen

            String firstName = s.substring(s.indexOf(" ") + 1, s.lastIndexOf(" ")),
                    lastName = s.substring(s.lastIndexOf(" ") + 1, s.indexOf("\n"));

            if (s.contains(firstName + " " + lastName) && isAllowed(firstName, lastName)) {
                allowedPerson.add(s);
            }
        }

        return allowedPerson;
    }

    // att avskilja datumen som ligger i en list elementen med hjälp av namn och efternam
    public LocalDate getDate(String firstName, String lastName) {
        String[] arrDate = new String[3];

        for (String l : getRegistrationList()) {
            if (l.contains(firstName + " " + lastName)) {
                arrDate = l.substring(l.indexOf("\n") + 1).split("-");
            }
        }
        // arrDate[0] = år , arrDate[1]= månad , arrDate[2] = dag
        return LocalDate.of(Integer.parseInt(arrDate[0]), Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[2]));
    }

    // En metod som undersöker om medlemskapet gäller för mindre ett år eller inte
    public boolean isAllowed(String firstName, String lastName) {
        LocalDate now = LocalDate.now();
        LocalDate date = getDate(firstName, lastName);

        int customDays = date.getDayOfYear();
        int nowDays = now.getDayOfYear();

        int counter = 0;

        if (date.getYear() == now.getYear()) {
            counter = nowDays - customDays;
        } else if (date.getMonthValue() >= now.getMonthValue() &&
                (now.getYear() - date.getYear() == 1)) {
            counter = nowDays + (365 - customDays);
        } else {
            counter = 366;
        }

        return counter <= 365;
    }

    // att skapa en fil för personlige tränaren
    public void createUniqueFile(String event) throws IOException {
        String read;

        if (readFile(directoryPath + firstName + ".txt") == null) {
            read = "";
        } else {
            read = readFile(directoryPath + firstName + ".txt");
        }

        for (String s : getAllowedPerson()) {
            if (s.contains(firstName + " " + lastName)) {

                File file = new File(directoryPath + firstName + ".txt");

                PrintWriter writer = new PrintWriter(new FileWriter(file));

                writer.println(read +
                        s.substring(0, s.indexOf("\n")) + "\n" +
                        LocalDate.now() + "\n" + event);

                writer.flush();
                writer.close();
            }
        }
    }

    // att skapa en fil för reseptionen
    public void createReceptionFile() throws IOException {
        String read;

        if (readFile(directoryPath + "Reception.txt") == null) { // om filen inte existerar
            read = "";
        } else {
            read = readFile(directoryPath + "Reception.txt"); // om filen existerar läser filen
        }

        for (String s : getAllowedPerson()) {
            if (s.contains(firstName + " " + lastName)) {

                File file = new File(directoryPath + "Reception.txt"); // skapa filen

                PrintWriter writer = new PrintWriter(new FileWriter(file));

                writer.println(read +
                        s.substring(0, s.indexOf("\n")) + "\n" +
                        LocalDate.now());

                writer.flush();
                writer.close();
            }
        }
    }
}
