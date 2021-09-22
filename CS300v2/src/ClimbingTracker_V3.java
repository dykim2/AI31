
//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: The Climb Tracker
// Course: CS 300 Fall 2020
//
// Author: Charles Huai
// Email: huai2@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Dong-Yon Kim
// Partner Email: adkim5@wisc.edu
// Partner Lecturer's Name: Mouna Kacem
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// X Write-up states that pair programming is allowed for this assignment.
// X We have both read and understand the course Pair Programming Policy.
// X We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: N/A (besides me and Dong-Yon)
// Online Sources: (identify each by URL and describe how it helped)
// https://learn.zybooks.com/zybook/WISCCOMPSCI300Fall2021/chapter/1/section/7 (Zybooks - helped us
// understand how the oversize arrays are used)
// The java 11 API (character, string, and Integer)
// (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Character.html)
// (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html)
// (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Integer.html)
// The Character API helped us learn how to use the Character.isDigit() method.
// The String API helped us learn how to use the subString() method.
// The Integer API helped us learn how to use the Integer.parseInt() method, alongside Zybooks.
//
///////////////////////////////////////////////////////////////////////////////
// import java.util.Scanner;
/**
 * 
 * This class tracks a list of successful and failed climbs, allowing for the addition of more
 * climbs.
 *
 */
public class ClimbingTracker_V3 {
  /**
   * Adds the given climb to the array of successful climbs, pending the climb is valid and there is
   * room for it. Step 1: check if the array can hold the climb Step 2: Check if the climb grade is
   * valid Step 3: Add the grade to the array Step 4: Update and return the size of the new oversize
   * array
   * 
   * @param send    is the current oversize array of successful climbs
   * @param numSend is the current length of the oversize array send
   * @param grade   is the completed climb's grade
   * @return the new length of the oversize array send, or if the array is not changed, the original
   *         size of the array
   */
  public static int sendClimb(String[] send, int numSend, String grade) {
    return addToClimbsArray(send, numSend, grade);
  }

  /**
   * Adds the given climb to the array of failed climbs, pending the climb is valid and there is
   * room for it. Step 1: check if the array can hold the climb Step 2: Check if the climb grade is
   * valid Step 3: Add the grade to the array Step 4: Update and return the size of the new oversize
   * array
   * 
   * @param fail    is the current oversize array of failed climbs
   * @param numSend is the current length of the oversize array fail
   * @param grade   is the completed climb's grade
   * @return the new length of the oversize array send, or if the array is not changed, the original
   *         size of the array
   */
  public static int failClimb(String[] fail, int numFail, String grade) {
    return addToClimbsArray(fail, numFail, grade);
  }

  /**
   * 
   * @param send
   * @param numSend
   * @param fail
   * @param numFail
   * @param historyLength
   * @return a String object that contains the list of climbs and the average climb grade.
   */
  public static String getStats(String[] send, int numSend, String[] fail, int numFail,
      int historyLength) {
    return null;
  }

  /**
   * I was thinking we either sort the array and search the sorted array or we use a for loop to
   * examine every item in the arrays starting with the fails, making sure to use a switch to
   * compare or something so we can update the histogram accordingly (or we can check the number of
   * failed and successful climbs for each grade since those are limited with a final constant and
   * use the number to make the array).
   * 
   * @param send
   * @param numSend
   * @param fail
   * @param numFail
   * @return A String object with
   */
  public static String getHistogram(String[] send, int numSend, String[] fail, int numFail) {
    if ((send == null && fail == null) || (send.length == 0 && fail.length == 0)
        || (numSend == 0 && numFail == 0)) {
      return "Error: there is no data to display.";
    }
    return null;
  }

  /**
   * Tests the given climb grade to see whether it is a valid clime grade or not. Step 1: Is the
   * string null? Step 2: Is the length 2? Step 3: Is the first character a v? Step 4: Is the second
   * character a number? Step 5: Is the grade number non negative? Step 6: Is the second character
   * between 0 and 7, inclusive? If all of these are met, the climb is valid, and we return true. If
   * not we return false.
   * 
   * @param climb is the grade that we want to test for validity
   * @return true if the climb is a valid climb, false if it is not
   */
  private static boolean isClimbValid(String climb) {

    if (climb == null || climb.length() != 2) {
      return false;
    }

    if (climb.charAt(0) != 'V' || !Character.isDigit(climb.charAt(1))) {
      return false;
    }

    int climbGradeNumber = Integer.parseInt(climb.substring(1));
    if (climbGradeNumber < 0 || climbGradeNumber > 7) {
      return false;
    }
    return true;
  }

  /**
   * can you fill this javadoc out charles sometime before 5:30 monday, at least basically? if not
   * dont worry about it we can fill it out
   * i added numClimb < 0 in case a negative is processed for numClimb the climb length
   * @param climbs
   * @param numClimb
   * @param grade
   * @return
   */
  private static int addToClimbsArray(String[] climbs, int numClimb, String grade) {

    if (numClimb > climbs.length || !isClimbValid(grade) || numClimb < 0) {
      return numClimb;
    }

    climbs[numClimb] = grade;
    return numClimb + 1;
  }
}