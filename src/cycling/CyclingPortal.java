package cycling;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Compiling, functional implementor of the CyclingPortalInterface interface.
 *
 * @author Toby Slump and James Cracknell
 * @version 1.0
 * 03/2022
 */
public class CyclingPortal implements CyclingPortalInterface {
    private LinkedList<Race> listOfRaces = new LinkedList<>();
    private LinkedList<Team> listOfTeams = new LinkedList<>();
    private int[] staticAttributes = new int[5];
    @Override
    public int[] getRaceIds() {
        
        int[] RaceIDs = new int[listOfRaces.size()];

        for (int i = 0; i < listOfRaces.size(); i++) {
            RaceIDs[i] = listOfRaces.get(i).getRaceID();
        }

        return RaceIDs;

    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        // exception handling
        for (Race race : listOfRaces) {
            if (Objects.equals(race.getRaceName(), name)) { // if name already used
                throw new IllegalNameException
                        ("The given name has already been used on a race. Names must be unique./");
            }
        }

        if (name == null) { //if name null
            throw new InvalidNameException("Name must not be null");
        } else {
            if (name.length() > 30 || name.contains(" ")
                    || name.equals("")) { //if name does not meet specified criteria
                throw new InvalidNameException
                        ("Name must not be empty, must be a single word and cannot be over 30 characters");
            }
        }


        listOfRaces.add(new Race(name, description));
        assert (listOfRaces.getLast().getRaceID() == Race.getNextRaceID())
                : "Race has not been created with correct ID";
        return listOfRaces.getLast().getRaceID();

    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        for (Race RaceObj : listOfRaces) {
            if (RaceObj.getRaceID() == raceId) {
                return RaceObj.viewRaceDetails();
            }
        }

        throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        int prevListLength = listOfRaces.size();
        for (int i = 0; i < listOfRaces.size(); i++){
            Race RaceObj = listOfRaces.get((i));
            if (RaceObj.getRaceID() == raceId){
              listOfRaces.remove(RaceObj);
            }
        }

        if (prevListLength == listOfRaces.size()){
            throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
        }
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        for (Race RaceObj : listOfRaces) {
            if (RaceObj.getRaceID() == raceId) {
                return RaceObj.getNumberOfStages();
            }
        }
        throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
                              StageType type)
            throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        // exception handling
        for (Race race : listOfRaces) { //what should x be?
            for (int j = 0; j < race.getStageIDs().length; j++) {
                if (Objects.equals(race.getStageNames()[j], stageName)) { // what should y be? // if name already used
                    throw new IllegalNameException
                            ("The given name has already been used on a stage. Names must be unique.");
                }
            }
        }


        if (stageName == null) { //if name null
            throw new InvalidNameException("Name must not be null");
        } else {
            if (stageName.length() > 30 || stageName.contains(" ")
                    || stageName.equals("")) { //if name does not meet specified criteria
                throw new InvalidNameException
                        ("Name must not be empty, must be a single word and cannot be over 30 characters");
            }
        }

        if (length < 5) { // Length has to be greater than 5kms
            throw new InvalidLengthException("Length must be more than 5kms");
        }


        for (Race RaceObj : listOfRaces) {
            if (RaceObj.getRaceID() == raceId) {
                return RaceObj.addStage(stageName, description, length,
                        startTime, type);
            }
        }

        throw new IDNotRecognisedException("No race found with ID: " + raceId);
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        for (Race RaceObj : listOfRaces) {
            if (RaceObj.getRaceID() == raceId) {
                return RaceObj.getOrderedStageIDs();
            }
        }
        throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        for (Race raceObj : listOfRaces) {
            for (int j = 0; j < raceObj.getStageIDs().length; j++) {
                if (raceObj.getStageIDs()[j] == stageId) {
                    return raceObj.getStageLength(stageId);
                }
            }
        }
        throw new IDNotRecognisedException("Couldn't find stage with ID: " + stageId);
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        boolean hasDeleted = false;

        for (Race raceObj : listOfRaces) {
            for (int j = 0; j < raceObj.getStageIDs().length; j++) {
                if (raceObj.getStageIDs()[j] == stageId) {
                    raceObj.removeStageByID(stageId);
                    hasDeleted = true;
                }
            }
        }

        if (!hasDeleted){
            throw new IDNotRecognisedException("Couldn't find stage with ID: " + stageId);
        }
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
                                          Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
            InvalidStageTypeException {
        for (Race raceObj : listOfRaces) {
            for (int j = 0; j < raceObj.getStageIDs().length; j++) {
                if (raceObj.getStageIDs()[j] == stageId) {
                    if (Objects.equals(raceObj.getStageState(stageId), "waiting for results")) {
                        throw new InvalidStageStateException
                                ("Stage is in invalid state: " + raceObj.getStageState(stageId));
                    }
                    if (0 > location || location > raceObj.getStageLength(stageId)) {
                        throw new InvalidLocationException
                                ("The starting location of the climb is invalid. It must start and end within the stage.");
                    }
                    if (raceObj.getStageType(stageId) == StageType.TT) {
                        throw new InvalidStageTypeException("Segments cannot be added to time trial stages");
                    }
                    return raceObj.addClimbToStage(stageId, location, type,
                            averageGradient, length);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
            InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        for (Race raceObj : listOfRaces) {
            for (int j = 0; j < raceObj.getStageIDs().length; j++) {
                if (raceObj.getStageIDs()[j] == stageId) {
                    if (raceObj.getStageState(stageId).equals("waiting for results")) {
                        throw new InvalidStageStateException
                                ("Stage is in invalid state: " + raceObj.getStageState(stageId));
                    }
                    if (0 > location || location > raceObj.getStageLength(stageId)) {
                        throw new InvalidLocationException
                                ("The starting location of the climb is invalid. It must start within the stage.");
                    }
                    if (raceObj.getStageType(stageId) == StageType.TT) {
                        throw new InvalidStageTypeException("Segments cannot be added to time trial stages");
                    }
                    return raceObj.addSprintToStage(stageId, location);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
        boolean hasDeleted = false;

        for (Race raceObj : listOfRaces) {
            for (int j = 0; j < raceObj.getStageIDs().length; j++) {
                for (int k = 0; k < raceObj.getSegmentIds(raceObj.getStageIDs()[j]).length; k++) {
                    if (raceObj.getSegmentIds(raceObj.getStageIDs()[j])[k] == segmentId) {
                        if (raceObj.getStageState(raceObj.getStageIDs()[j]).equals("waiting for results")) {
                            throw new InvalidStageStateException
                                    ("Stage is in invalid state: " + (raceObj.getStageState(raceObj.getStageIDs()[j])));
                        }
                        raceObj.removeSegmentById(j, segmentId);
                        hasDeleted = true;
                    }
                }
            }
        }

        if (!hasDeleted) {
            throw new IDNotRecognisedException("Couldn't find segment with ID: " + segmentId);
        }
    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        boolean hasConcluded = false;

        for (Race raceObj : listOfRaces) {
            for (int j = 0; j < raceObj.getNumberOfStages(); j++) {
                if (raceObj.getStageIDs()[j] == stageId) {
                    if (Objects.equals(raceObj.getStageState(stageId), "waiting for results")) {
                        throw new InvalidStageStateException
                                ("Stage is in invalid state: " + raceObj.getStageState(stageId));
                    }
                    raceObj.concludeStatePreparation(stageId);
                    hasConcluded = true;
                }
            }
        }

        if (!hasConcluded){
            throw new IDNotRecognisedException("No stage found with Id: " + stageId);
        }

    }

    @Override
    public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
        for (Race raceObj : listOfRaces) {
            for (int j = 0; j < raceObj.getStageIDs().length; j++) {
                if (raceObj.getStageIDs()[j] == stageId) {
                    return raceObj.getSegmentIds(stageId);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        for (Race race : listOfRaces) {
            if (Objects.equals(race.getRaceName(), name)) { // if name already used
                throw new IllegalNameException("The given name has already been used on a race. Names must be unique./");
            }
        }

        if (name == null) { //if name null
            throw new InvalidNameException("Name must not be null");
        } else {
            if (name.length() > 30 || name.contains(" ") || name.equals("")) { //if name does not meet specified criteria
                throw new InvalidNameException
                        ("Name must not be empty, must be a single word and cannot be over 30 characters");
            }
        }

        listOfTeams.add(new Team(name, description));
        assert (listOfTeams.getLast().getTeamID() == Team.getNextTeamID())
                : "Team was not created with correct ID";
        return listOfTeams.getLast().getTeamID();

    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        boolean hasRemoved = false;
        for (int i = 0; i < listOfTeams.size(); i++){
            Team teamObj = listOfTeams.get(i);

            if (teamObj.getTeamID() == teamId){
                listOfTeams.remove(teamObj);
                hasRemoved = true;
            }
        }
        if (!hasRemoved) {
            throw new IDNotRecognisedException("No team found with ID: " + teamId);
        }
    }

    @Override
    public int[] getTeams() {
        int[] teamIds = new int[listOfTeams.size()];

        for (int i = 0; i < listOfTeams.size(); i++){
            teamIds[i] = listOfTeams.get(i).getTeamID();
        }

        return teamIds;
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        for (Team team : listOfTeams) {
            if (team.getTeamID() == teamId) {
                return team.getRiderIds();
            }
        }

        throw new IDNotRecognisedException("No team found with Id:" + teamId);
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth)
            throws IDNotRecognisedException, IllegalArgumentException {
        if (yearOfBirth < 1900) {
            throw new IllegalArgumentException("Year of birth is less than 1900.");
        } else if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }


        for (Team team : listOfTeams) {
            if (team.getTeamID() == teamID) {
                team.addRider(name, yearOfBirth);
                assert (team.getNewRiderID() == Rider.getNextRiderID())
                        : "Rider was not created with correct ID";
                return team.getNewRiderID();
            }
        }

        throw new IDNotRecognisedException("No team found with ID: " + teamID);
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        boolean hasRemoved = false;

        for (Team team : listOfTeams) {
            for (int j = 0; j < team.getRiderIds().length; j++) {
                if (team.getRiderIds()[j] == riderId) {
                    team.removeRider(riderId);
                    for (Race race : listOfRaces){
                        race.deleteAllRiderResults(riderId);
                    }
                    hasRemoved = true;
                }
            }
        }

        if (!hasRemoved){
            throw new IDNotRecognisedException("No rider found with ID: " + riderId);
        }
    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
            throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
            InvalidStageStateException {
        boolean hasRegistered = false;

        for (Race race : listOfRaces) {
            for (int j = 0; j < race.getNumberOfStages(); j++) {
                if (race.getStageIDs()[j] == stageId) {
                    if (race.isRiderInResults(stageId, riderId)) {
                        throw new DuplicatedResultException("Rider already has results");
                    }

                    if (checkpoints.length != race.getNumberOfSegmentsInStage(stageId) + 2) {
                        throw new InvalidCheckpointsException("Invalid length of checkpoints");
                    }
                    if (!Objects.equals(race.getStageState(stageId), "waiting for results")) {
                        throw new InvalidStageStateException
                                ("Stage is in invalid state: " + race.getStageState(stageId));
                    }
                    race.registerRiderResultsInStage(stageId, riderId, checkpoints);
                    hasRegistered = true;
                }
            }
        }
        if (!hasRegistered){
            throw new IDNotRecognisedException("No rider found with ID: " + riderId);
        }
    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        for (Race race : listOfRaces) {
            for (int j = 0; j < race.getNumberOfStages(); j++) {
                if (race.getStageIDs()[j] == stageId) {
                    if (!race.isRiderInResults(stageId, riderId)) {
                        throw new IDNotRecognisedException
                                ("No rider with ID: " + riderId + " results found in stage with ID: " + stageId);
                    }
                    LocalTime[] results = race.getRiderResults(stageId, riderId);
                    LocalTime[] resultsWithElapsedTime = new LocalTime[results.length + 1];
                    LocalTime elapsedTime =
                            LocalTime.ofSecondOfDay(results[results.length - 1].toSecondOfDay()
                                    - results[0].toSecondOfDay());
                    System.arraycopy(results, 0, resultsWithElapsedTime, 0, results.length);
                    resultsWithElapsedTime[results.length] = elapsedTime;
                    return resultsWithElapsedTime;
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        for (Race race : listOfRaces) {
            for (int j = 0; j < race.getNumberOfStages(); j++) {
                if (race.getStageIDs()[j] == stageId) {
                    if (!race.isRiderInResults(stageId, riderId)) {
                        throw new IDNotRecognisedException
                                ("No rider with ID: " + riderId + " results found in stage with ID: " + stageId);
                    }
                    return race.getRiderAdjustedElapsedResults(stageId, riderId);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        boolean hasDeleted = false;

        for (Race race : listOfRaces) {
            for (int j = 0; j < race.getNumberOfStages(); j++) {
                if (race.getStageIDs()[j] == stageId) {
                    if (!race.isRiderInResults(stageId, riderId)) {
                        throw new IDNotRecognisedException
                                ("No rider with ID: " + riderId + " results found in stage with ID: " + stageId);
                    }
                    race.deleteRidersResults(stageId, riderId);
                    hasDeleted = true;
                }
            }
        }

        if (!hasDeleted){
            throw new IDNotRecognisedException("No stage found with ID: " + riderId);
        }
    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        for (Race race : listOfRaces) {
            for (int j = 0; j < race.getNumberOfStages(); j++) {
                if (race.getStageIDs()[j] == stageId) {
                    return race.getRidersRankInStage(stageId);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        for (Race race : listOfRaces) {
            for (int j = 0; j < race.getNumberOfStages(); j++) {
                if (race.getStageIDs()[j] == stageId) {
                    return race.getRankedAdjustedElapsedTimesInStage(stageId);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        // Get the number of points obtained by each rider in a stage.
        int [] ridersRanked = getRidersRankInStage((stageId));
        boolean pointsCalculated = false;
        if (ridersRanked.length == 0){ // if ID is not recognised, return will be empty
            throw new IDNotRecognisedException("Stage ID not recognised");
        }
        int [] riderPoints = new int [ridersRanked.length]; // create new array of riders to store points
        for (Race race : listOfRaces) { // loop through races
            if (!pointsCalculated) { // if data for points has not been collected
                for (int j = 0; j < ridersRanked.length; j++) { // loops through riders
                    riderPoints[j] = race.getPointsFromStage(stageId, j, ridersRanked[j]); // add current riders point to array of rider points
                    if (riderPoints[j] != 0) { // data has been returned
                        pointsCalculated = true; // will not get data again
                    }
                }
            }
        }
        return riderPoints;
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        //Get the number of mountain points obtained by each rider in a stage.
        int [] ridersRanked = getRidersRankInStage((stageId));
        boolean pointsCalculated = false;
        if (ridersRanked.length == 0){ // if ID is not recognised, return will be empty
            throw new IDNotRecognisedException("Stage ID not recognised");
        }
        int [] riderPoints = new int [ridersRanked.length]; // create new array of riders to store points
        for (Race race : listOfRaces) { // loop through races
            if (!pointsCalculated) { // if data for points has not been collected
                for (int j = 0; j < ridersRanked.length; j++) { // for loops through riders
                    riderPoints[j] = race.getMountainPointsFromStage(stageId, ridersRanked[j]); // add current riders point to array of rider points
                    if (riderPoints[j] != 0) { // data has been returned
                        pointsCalculated = true; // will not get data again
                    }
                }
            }
        }


        return riderPoints;
    }

    @Override
    public void eraseCyclingPortal() {
        listOfRaces.clear();
        listOfTeams.clear();
        Race.setNextRaceID(0);
        Stage.setNextStageID(0);
        SprintSegment.setNextSegmentID(0);
        Rider.setNextRiderID(0);
        Team.setNextTeamID(0);
    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        String fileNameUsed = filename;
        if (!filename.endsWith(".ser")){
            fileNameUsed = filename + ".ser";
        }

        staticAttributes[0] = Race.getNextRaceID();
        staticAttributes[1] = Stage.getNextStageID();
        staticAttributes[2] = SprintSegment.getNextSegmentID();
        staticAttributes[3] = Team.getNextTeamID();
        staticAttributes[4] = Rider.getNextRiderID();

        try (ObjectOutputStream out = new ObjectOutputStream(new
                FileOutputStream(fileNameUsed))) {
            out.writeObject(listOfRaces);
            out.writeObject(listOfTeams);
            out.writeObject(staticAttributes);
        }catch (IOException e){
            throw new IOException("Couldn't save objects");
        }

    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        String fileNameUsed = filename;
        if (!filename.endsWith(".ser")){
            fileNameUsed = filename + ".ser";
        }

        try (ObjectInputStream in = new ObjectInputStream(new
                FileInputStream(fileNameUsed))) {
            Object obj = in.readObject();
            if (obj instanceof LinkedList<?> loadedRace) {
                for (Object race : loadedRace) {
                    listOfRaces.add((Race) race);
                }
            }
            obj = in.readObject();
            if (obj instanceof LinkedList<?> loadedTeam) {
                for (Object team : loadedTeam){
                    listOfTeams.add((Team) team);
                }
            }
            obj = in.readObject();
            if (obj instanceof int[])
                staticAttributes = (int[]) obj;
            Race.setNextRaceID(staticAttributes[0]);
            Stage.setNextStageID(staticAttributes[1]);
            SprintSegment.setNextSegmentID(staticAttributes[2]);
            Team.setNextTeamID(staticAttributes[3]);
            Rider.setNextRiderID(staticAttributes[4]);
        }catch (IOException e){
            throw new IOException();
        }catch (ClassNotFoundException e){
            throw new ClassNotFoundException("");
        }
    }

    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException {
        boolean hasRemoved = false;

        for (int i = 0; i < listOfRaces.size(); i++){
            if (Objects.equals(listOfRaces.get(i).getRaceName(), name)){
                listOfRaces.remove(i);
                hasRemoved = true;
            }
        }

        if (!hasRemoved){
            throw new NameNotRecognisedException();
        }

    }

    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        for (Race race : listOfRaces) {
            if (race.getRaceID() == raceId) {
                return race.getGeneralClassificationTimes();
            }
        }

        throw new IDNotRecognisedException("No race found with ID: " + raceId);
    }

    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        int[] ridersPoints;
        // adjusted lap time not just lap time
        for (Race race : listOfRaces) {
            if (race.getRaceID() == raceId) {
                ridersPoints = race.getRidersOverallPoints(); //get points for race
                return ridersPoints;
            }
        }
        throw new IDNotRecognisedException("No race found with ID: " + raceId);
        
    }

    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        int[] ridersPoints;
        for (Race race : listOfRaces) {
            if (race.getRaceID() == raceId) {
                ridersPoints = race.getRidersOverallMountainPoints(); //get points for race
                return ridersPoints;
            }
        }
        throw new IDNotRecognisedException("No race found with ID: " + raceId);
    }

    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        for (Race race : listOfRaces) {
            if (race.getRaceID() == raceId) {
                return race.getRidersGeneralClassificationRank();
            }
        }

        throw new IDNotRecognisedException("No race found with ID: " + raceId);
    }

    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        int[] ridersPoints = getRidersPointsInRace(raceId); //gets list of riders points
        int[] ridersRank = getRidersGeneralClassificationRank(raceId);
        if (ridersPoints.length == 0){ //if no riders results were returned then race ID is invalid
            throw new IDNotRecognisedException("Race ID is not recognised.");
        }
        int[][] combinedRiders = new int[ridersRank.length][2];
        //ridersRank[i] is the rider ID with the score in ridersPoints[i]
        for (int i = 0; i < ridersRank.length; i++) { // create 2d array to allow for sorting
            combinedRiders[i][0] = ridersRank[i];
            combinedRiders[i][1] = ridersPoints[i];
        }
      // sort based on time
        Arrays.sort(combinedRiders, (first, second) -> {
            if (first[1] < second[1]) return 1;
            else return -1;
        });
        for (int i = 0; i < ridersRank.length; i++) {
            ridersPoints[i] = combinedRiders[i][0]; // make array of sorted IDs
        }
        return ridersPoints;
    }

    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        int[] ridersPoints = getRidersMountainPointsInRace(raceId); //gets list of riders points
        int[] ridersRank = getRidersGeneralClassificationRank(raceId);
        if (ridersPoints.length == 0){ //if no riders results were returned then race ID is invalid
            throw new IDNotRecognisedException("Race ID is not recognised.");
        }
        int[][] combinedRiders = new int[ridersRank.length][2];
        //ridersRank[i] is the rider ID with the score in ridersPoints[i]
        for (int i = 0; i < ridersRank.length; i++) { // create 2d array to allow for sorting
            combinedRiders[i][0] = ridersRank[i];
            combinedRiders[i][1] = ridersPoints[i];
        }
        // sort based on time
        Arrays.sort(combinedRiders, (first, second) -> {
            if (first[1] < second[1]) return 1;
            else return -1;
        });
        for (int i = 0; i < ridersRank.length; i++) {
            ridersPoints[i] = combinedRiders[i][0]; // make array of sorted IDs
        }

        return ridersPoints;
    }

}