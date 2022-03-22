import cycling.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;

public class CyclingPortalInterfaceTestApp2 {

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

        cyclingportal.addStageToRace(1, "stage1", "test stage",
                5, LocalDateTime.now(), StageType.FLAT);
        cyclingportal.addStageToRace(1, "stage2", "test stage",
                9, LocalDateTime.now(), StageType.HIGH_MOUNTAIN);
        cyclingportal.addStageToRace(1, "stage3", "test stage",
                6.9, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN);
        cyclingportal.addStageToRace(1, "stage4", "test stage",
                7.2, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN);

        cyclingportal.concludeStagePreparation(1);

        cyclingportal.createTeam("team1", "test team");
        cyclingportal.createRider(1, "testkid1", 1969);
        cyclingportal.createRider(1, "testkid2", 1970);
        cyclingportal.createRider(1, "testkid3", 1971);
        cyclingportal.createRider(1, "testkid4", 1972);
        cyclingportal.createRider(1, "testkid5", 1973);
        cyclingportal.createRider(1, "testkid6", 1974);

        LocalTime[] riderTimes =
                {LocalTime.ofSecondOfDay(2), LocalTime.ofSecondOfDay(15)};
        cyclingportal.registerRiderResultsInStage(1, 1, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(1), LocalTime.ofSecondOfDay(13)};
        cyclingportal.registerRiderResultsInStage(1, 2, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(5), LocalTime.ofSecondOfDay(20)};
        cyclingportal.registerRiderResultsInStage(1, 3, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(14)};
        cyclingportal.registerRiderResultsInStage(1, 4, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(4), LocalTime.ofSecondOfDay(21)};
        cyclingportal.registerRiderResultsInStage(1, 5, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(2), LocalTime.ofSecondOfDay(23)};
        cyclingportal.registerRiderResultsInStage(1, 6, riderTimes);

        System.out.println("""
                
                rider 1 results""");
        System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1, 1)));
        System.out.println("Adjusted time: " +cyclingportal.getRiderAdjustedElapsedTimeInStage(1,1));
        System.out.println("""
                
                rider 2 results""");
        System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1, 2)));
        System.out.println("Adjusted time: " +cyclingportal.getRiderAdjustedElapsedTimeInStage(1,2));
        System.out.println("""
                
                rider 3 results""");
        System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1, 3)));
        System.out.println("Adjusted time: " +cyclingportal.getRiderAdjustedElapsedTimeInStage(1,3));
        System.out.println("""
                
                rider 4 results""");
        System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1, 4)));
        System.out.println("Adjusted time: " +cyclingportal.getRiderAdjustedElapsedTimeInStage(1,4));
        System.out.println("""
                
                rider 5 results""");
        System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1, 5)));
        System.out.println("Adjusted time: " +cyclingportal.getRiderAdjustedElapsedTimeInStage(1,5));
        System.out.println("""
                
                rider 6 results""");
        System.out.println(Arrays.toString(cyclingportal.getRiderResultsInStage(1, 6)));
        System.out.println("Adjusted time: " +cyclingportal.getRiderAdjustedElapsedTimeInStage(1,6));


        System.out.println(Arrays.toString(cyclingportal.getRidersRankInStage(1)));
        System.out.println(Arrays.toString(cyclingportal.getRankedAdjustedElapsedTimesInStage(1)));



    }
}
