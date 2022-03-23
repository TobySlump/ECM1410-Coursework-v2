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

        //MiniCyclingPortalInterface portal = new BadCyclingPortal();
		CyclingPortalInterface portal = new BadCyclingPortal();

        assert (portal.getRaceIds().length == 0)
                : "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

        CyclingPortalInterface cyclingportal = new CyclingPortal();

        cyclingportal.createRace("race1", "test race");
        cyclingportal.createRace("race2", "test race");
        cyclingportal.createRace("race3", "test race");

        cyclingportal.addStageToRace(1, "stage1", "test stage",
                5, LocalDateTime.now(), StageType.FLAT);
        cyclingportal.addStageToRace(1, "stage2", "test stage",
                9, LocalDateTime.now(), StageType.HIGH_MOUNTAIN);

        cyclingportal.concludeStagePreparation(1);
        cyclingportal.concludeStagePreparation(2);

        cyclingportal.createTeam("team1", "test team");
        cyclingportal.createRider(1, "testkid1", 1969);
        cyclingportal.createRider(1, "testkid2", 1970);
        cyclingportal.createRider(1, "testkid3", 1971);
        cyclingportal.createRider(1, "testkid4", 1972);
        cyclingportal.createRider(1, "testkid5", 1973);
        cyclingportal.createRider(1, "testkid6", 1974);

        LocalTime[] riderTimes =
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(15)};
        cyclingportal.registerRiderResultsInStage(1, 1, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(13)};
        cyclingportal.registerRiderResultsInStage(1, 2, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(20)};
        cyclingportal.registerRiderResultsInStage(1, 3, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(14)};
        cyclingportal.registerRiderResultsInStage(1, 4, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(21)};
        cyclingportal.registerRiderResultsInStage(1, 5, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(23)};
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


        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(16)};
        cyclingportal.registerRiderResultsInStage(2, 1, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(9)};
        cyclingportal.registerRiderResultsInStage(2, 2, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(20)};
        cyclingportal.registerRiderResultsInStage(2, 3, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(14)};
        cyclingportal.registerRiderResultsInStage(2, 4, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(21)};
        cyclingportal.registerRiderResultsInStage(2, 5, riderTimes);

        riderTimes = new LocalTime[]
                {LocalTime.ofSecondOfDay(0), LocalTime.ofSecondOfDay(25)};
        cyclingportal.registerRiderResultsInStage(2, 6, riderTimes);


        System.out.println(Arrays.toString(cyclingportal.getGeneralClassificationTimesInRace(1)));

        System.out.println(Arrays.toString(cyclingportal.getRidersGeneralClassificationRank(1)));

        System.out.println(Arrays.toString(cyclingportal.getRidersMountainPointClassificationRank(1)));



    }
}
