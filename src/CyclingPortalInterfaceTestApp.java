import cycling.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws InvalidNameException, IllegalNameException,
			IDNotRecognisedException, InvalidLengthException, InvalidStageStateException,
			InvalidLocationException, InvalidStageTypeException, DuplicatedResultException, InvalidCheckpointsException, IOException, ClassNotFoundException {
		System.out.println("The system compiled and started the execution...");

		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
//		CyclingPortalInterface portal = new BadCyclingPortal();

		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

		MiniCyclingPortalInterface cyclingportal = new CyclingPortal();

		cyclingportal.createRace("race1", "test race");
		cyclingportal.createRace("race2", "test race");
		cyclingportal.createRace("race3", "test race");
		cyclingportal.createRace("race4", "test race");

		//test

		int[] desiredArray = new int[]{1, 2, 3, 4};
		assert (Arrays.equals(cyclingportal.getRaceIds(),desiredArray))
				: "Races not created correctly";

		assert  (cyclingportal.viewRaceDetails(1) == "test race")
				: "Races not created correctly";

		cyclingportal.removeRaceById(3);
		desiredArray = new int[]{1,2,4};
		assert (Arrays.equals(cyclingportal.getRaceIds(),desiredArray))
				: "Races not created correctly";

		cyclingportal.addStageToRace(2, "stage1", "test stage",
				5, LocalDateTime.now(), StageType.FLAT);
		cyclingportal.addStageToRace(2, "stage2", "test stage",
				9, LocalDateTime.now(), StageType.HIGH_MOUNTAIN);
		cyclingportal.addStageToRace(2, "stage3", "test stage",
				6.9, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN);
		cyclingportal.addStageToRace(1, "stage4", "test stage",
				7.2, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN);

		desiredArray = new int[]{1,2,3};
		assert (Arrays.equals(cyclingportal.getRaceStages(2),desiredArray))
				: "Races not created correctly";

		assert (cyclingportal.getStageLength(4) == 7.2)
				: "Races not created correctly";

		desiredArray = new int[]{1,2};
		cyclingportal.removeStageById(3);
		assert (Arrays.equals(cyclingportal.getRaceStages(2),desiredArray))
				: "Races not created correctly";

		System.out.println(cyclingportal.addCategorizedClimbToStage
				(1, 1.1, SegmentType.C1, 5.0,2.0));
		System.out.println(cyclingportal.addIntermediateSprintToStage(1, 2.4));

		//cyclingportal.removeSegment(2);
		System.out.println(Arrays.toString(cyclingportal.getStageSegments(1)));

		cyclingportal.concludeStagePreparation(1);

		System.out.println(cyclingportal.createTeam("team1", "test team"));
		System.out.println(cyclingportal.createRider(1, "testkid1", 1969));
		System.out.println(cyclingportal.createRider(1, "testkid2", 1970));
		System.out.println(cyclingportal.createRider(1, "testkid3", 1971));
		System.out.println(cyclingportal.createRider(1, "testkid4", 1972));
		System.out.println(cyclingportal.createRider(1, "testkid5", 1973));
		System.out.println(cyclingportal.createRider(1, "testkid6", 1974));

		LocalTime[] riderTimes =
				{LocalTime.ofSecondOfDay(2), LocalTime.ofSecondOfDay(15), LocalTime.ofSecondOfDay(47), LocalTime.ofSecondOfDay(90)};
		cyclingportal.registerRiderResultsInStage(1, 1, riderTimes);

		riderTimes = new LocalTime[]
				{LocalTime.ofSecondOfDay(1), LocalTime.ofSecondOfDay(13), LocalTime.ofSecondOfDay(46), LocalTime.ofSecondOfDay(91)};
		cyclingportal.registerRiderResultsInStage(1, 2, riderTimes);

		riderTimes = new LocalTime[]
				{LocalTime.ofSecondOfDay(5), LocalTime.ofSecondOfDay(20), LocalTime.ofSecondOfDay(42), LocalTime.ofSecondOfDay(87)};
		cyclingportal.registerRiderResultsInStage(1, 3, riderTimes);

		System.out.println("Rider Results:");
		System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1, 1)));

		System.out.println(cyclingportal.getRiderAdjustedElapsedTimeInStage(1, 1));

		cyclingportal.deleteRiderResultsInStage(1,3);
		//makes exception
		//System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1,3)));

		riderTimes = new LocalTime[]
				{LocalTime.ofSecondOfDay(5), LocalTime.ofSecondOfDay(20), LocalTime.ofSecondOfDay(45), LocalTime.ofSecondOfDay(95)};
		cyclingportal.registerRiderResultsInStage(1, 4, riderTimes);
		System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1,4)));

		riderTimes = new LocalTime[]
				{LocalTime.ofSecondOfDay(2), LocalTime.ofSecondOfDay(20), LocalTime.ofSecondOfDay(53), LocalTime.ofSecondOfDay(103)};
		cyclingportal.registerRiderResultsInStage(1, 5, riderTimes);
		System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1,5)));

		riderTimes = new LocalTime[]
				{LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(11), LocalTime.ofSecondOfDay(54), LocalTime.ofSecondOfDay(100)};
		cyclingportal.registerRiderResultsInStage(1, 6, riderTimes);
		System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1,6)));

		System.out.println(Arrays.toString(cyclingportal.getRidersRankInStage(1)));

		System.out.println(Arrays.toString(cyclingportal.getRankedAdjustedElapsedTimesInStage(1)));

		cyclingportal.saveCyclingPortal("test");
		cyclingportal.eraseCyclingPortal();
		System.out.println(Arrays.toString(cyclingportal.getRaceIds()));
		cyclingportal.loadCyclingPortal("test");

		System.out.println(Arrays.toString(cyclingportal.getRaceIds()));
		System.out.println(cyclingportal.createRider(1, "testkid7", 1975));
		System.out.println(Arrays.toString(cyclingportal.getRaceIds()));

		System.out.println("Testing the 'points adding' now.");
		System.out.println("stage segments" + Arrays.toString(cyclingportal.getStageSegments(1)));
		System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1, 1)));
		System.out.println(Arrays.toString(cyclingportal.getRidersRankInStage(1)));
		System.out.println(Arrays.toString(cyclingportal.getRidersPointsInStage(1)));
		System.out.println("Mountain segments");
		System.out.println(Arrays.toString(cyclingportal.getRidersMountainPointsInStage(1)));
		// getRidersRankInStage
		// rawRiderResults


	}
}
