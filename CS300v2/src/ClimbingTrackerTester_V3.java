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
// Persons:
// Online Sources: (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////
public class ClimbingTrackerTester_V3 {
  public static void main(String[] args) {
    runAllTests();
  }

  public static boolean runAllTests() {
    testSendClimb();
    return true;
  }

  /*
   * Create an oversized array of Strings to test the sendClimb() method Add empty,
   * partially-filled, and filled array if you get a return value you do not expect, return
   * **false** return true if all tests have been passed
   */

  public static boolean testSendClimb() {
    // empty array - an empty string array
    String[] emptyClimb = new String[10];
    int emptyClimbSize = 0;

    // partially filled array - a partially filled string array
    String[] partialClimb = {"V2", "V3", "V6", "V1", "V1", "", "", "", "", ""};
    int partialClimbSize = -1;

    // full array - a completely filled array
    String[] fullClimb = {"V1", "V1", "V0", "V0", "V0", "V1", "V2", "V2", "V3", "V4"};
    int fullClimbSize = 10;

    String newGrade = "V0";
    int returnValue = ClimbingTracker_V3.sendClimb(partialClimb, partialClimbSize, newGrade);

    // accurate
    if (returnValue == emptyClimbSize + 1) {
      // if it works properly
      System.out.println("sendClimb worked with climb length " + returnValue);
      return true;
    }
    else {
      //if it does not work properly
      System.out.println("sendClimb failed.");
      return false;
    }

  }

  public static boolean testFailClimb() {
    String[] emptyClimb = new String[10];
    int emptyClimbSize = 0;

    // partially filled array - a partially filled string array
    String[] partialClimb = {"V2", "V3", "V6", "V1", "V1", "", "", "", "", ""};
    int partialClimbSize = 5;

    // full array - a completely filled array
    String[] fullClimb = {"V1", "V1", "V0", "V0", "V0", "V1", "V2", "V2", "V3", "V4"};
    int fullClimbSize = 10;

    String newGrade = "V1";
    int returnValue = ClimbingTracker_V3.sendClimb(emptyClimb, emptyClimbSize, newGrade);

    // accurate
    if (returnValue == emptyClimbSize + 1) {
      // if it works properly
      System.out.println("sendClimb worked with climb length " + returnValue);

    }
    return true;
  }

  public static boolean testGetStats() {
    return true;
  }

  public static boolean testGetHistogram() {
    return true;
  }

}
