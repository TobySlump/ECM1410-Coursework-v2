import cycling.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
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
			InvalidLocationException, InvalidStageTypeException {
		System.out.println("The system compiled and started the execution...");

		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
//		CyclingPortalInterface portal = new BadCyclingPortal();

		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

		MiniCyclingPortalInterface cyclingportal = new CyclingPortal();

		cyclingportal.createRace("race 1", "test race");
		cyclingportal.createRace("race 2", "test race");
		cyclingportal.createRace("race 3", "test race");
		cyclingportal.createRace("race 4", "test race");

		int[] desiredArray = new int[]{1, 2, 3, 4};
		assert (Arrays.equals(cyclingportal.getRaceIds(),desiredArray))
				: "Races not created correctly";

		assert  (cyclingportal.viewRaceDetails(1) == "test race")
				: "Races not created correctly";

		cyclingportal.removeRaceById(3);
		desiredArray = new int[]{1,2,4};
		assert (Arrays.equals(cyclingportal.getRaceIds(),desiredArray))
				: "Races not created correctly";

		cyclingportal.addStageToRace(2, "stage 1", "test stage",
				5, LocalDateTime.now(), StageType.FLAT);
		cyclingportal.addStageToRace(2, "stage 2", "test stage",
				2.3, LocalDateTime.now(), StageType.HIGH_MOUNTAIN);
		cyclingportal.addStageToRace(2, "stage 3", "test stage",
				6.9, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN);
		cyclingportal.addStageToRace(1, "stage 1", "test stage",
				0.1, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN);

		desiredArray = new int[]{1,2,3};
		assert (Arrays.equals(cyclingportal.getRaceStages(2),desiredArray))
				: "Races not created correctly";

		assert (cyclingportal.getStageLength(4) == 0.1)
				: "Races not created correctly";

		desiredArray = new int[]{1,2};
		cyclingportal.removeStageById(3);
		assert (Arrays.equals(cyclingportal.getRaceStages(2),desiredArray))
				: "Races not created correctly";

		System.out.println(cyclingportal.addCategorizedClimbToStage(1, 4.0, SegmentType.C1, 5.0,6.0));
		System.out.println(cyclingportal.addIntermediateSprintToStage(1, 6.9));


	}
}
