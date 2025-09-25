package kalah;

import com.qualitascorpus.testsupport.IO;

// This class is used as Utility class to validate user's inputs:
public class InputValidation {

    public static boolean isValidInput(String input, int houseCount, IO io) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            io.println("Invalid input. Please choose the house number from 1 to " + houseCount + ".");
            return false;
        }
        return true;
    }

    public static boolean isValidHouseNumber(int houseNumber, int houseCount, IO io) {
        if (houseNumber < 1 || houseNumber > houseCount) {
            io.println("Invalid house number. Please choose the house number from 1 to " + houseCount + ".");
            return false;
        }
        return true;
    }
}
