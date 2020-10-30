import java.util.Scanner;

/**
 * Created by Maryam Askari
 * Date: 10/18/2020
 * Time: 1:44 PM
 * Project: IntelliJ IDEA
 */
public class Main {
    public static void main(String[] args) {
        // skapa ett objekt av GymPortal
        GymPortal gym = new GymPortal();
        Scanner input = new Scanner(System.in);

        System.out.println("Full Name : ");
        System.out.println("Full Name :::: ");
        String[] fullName = input.nextLine().split(" ");

        // fullName[0] = namn , fullName[1] =efternamn
        gym.setFullName(fullName[0], fullName[1]);

        try {
            if (gym.isAllowed(fullName[0], fullName[1])) {
                System.out.println(fullName[0] + " " + fullName[1] + " does event :");
                gym.createUniqueFile(input.nextLine());
                gym.createReceptionFile();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println(fullName[0] + " " + fullName[1] + " is not allowed to Enter Gym");
        }
    }
}