import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Scanner;

public class NonBrowserTests {

    @Test(priority = 1)
    public void testReverseNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number for reverse number test check: ");
        int number = sc.nextInt();
        int reversedNumber = reverseNumber(number);

        Assert.assertEquals(reversedNumber, number, "The numbers are not equal.");
    }

    private int reverseNumber(int num) {
        int reversed = 0;
        while (num != 0) {
            reversed = reversed * 10 + num % 10;
            num = num / 10;
        }
        return reversed;
    }
}