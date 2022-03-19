package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;

public class CyclingPortal implements MiniCyclingPortalInterface {
    private LinkedList<Race> ListOfRaces = new LinkedList<>();
    private LinkedList<Team> ListOfTeams = new LinkedList<>();

    @Override
    public int[] getRaceIds() {
        // TODO Auto-generated method stub
        int RaceIDs[] = new int[ListOfRaces.size()];

        for (int i = 0; i < ListOfRaces.size(); i++) {
            RaceIDs[i] = ListOfRaces.get(i).getRaceID();
        }

        return RaceIDs;

    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        // TODO Auto-generated method stub

        // exception handling
        for (int i = 0; i < ListOfRaces.size(); i++) {
            if (ListOfRaces.get(i).getRaceName() == name) { // if name already used
                throw new IllegalNameException("The given name has already been used on a race. Names must be unique./");
            }
        }

        if (name == null) { //if name null
            throw new InvalidNameException("Name must not be null");
        } else {
            if (name.length() > 30 || name.contains(" ") || name == "") { //if name does not meet specified criteria
                throw new InvalidNameException("Name must not be empty, must be a single word and cannot be over 30 characters");
            }
        }

        ListOfRaces.add(new Race(name, description));
        return ListOfRaces.getLast().getRaceID();

    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race RaceObj = ListOfRaces.get((i));
            if (RaceObj.getRaceID() == raceId){
                return RaceObj.viewRaceDetails();
            }
        }

        throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        int prevListLength = ListOfRaces.size();

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race RaceObj = ListOfRaces.get((i));
            if (RaceObj.getRaceID() == raceId){
              ListOfRaces.remove(RaceObj);
            }
        }

        if (prevListLength == ListOfRaces.size()){
            throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
        }
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race RaceObj = ListOfRaces.get(i);
            if (RaceObj.getRaceID() == raceId){
                return RaceObj.getNumberOfStages();
            }
        }
        throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
                              StageType type)
            throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        // TODO Auto-generated method stub

        // exception handling
        for (int i = 0; i < ListOfRaces.size() ; i++) { //what should x be?
            for (int j = 0; j < ListOfRaces.get(i).getStageIDs().length ; j++) {
                if (ListOfRaces.get(i).getStageNames()[j] == stageName) { // what should y be? // if name already used
                    throw new IllegalNameException("The given name has already been used on a stage. Names must be unique.");
                }
            }
        }


        if (stageName == null) { //if name null
            throw new InvalidNameException("Name must not be null");
        } else {
            if (stageName.length() > 30 || stageName.contains(" ") || stageName == "") { //if name does not meet specified criteria
                throw new InvalidNameException("Name must not be empty, must be a single word and cannot be over 30 characters");
            }
        }

        if (length < 5) { // Length has to be greater than 5kms
            throw new InvalidLengthException("Length must be more than 5kms");
        }


        for (int i = 0; i < ListOfRaces.size(); i++){
            Race RaceObj = ListOfRaces.get(i);
            if (RaceObj.getRaceID() == raceId){
                return RaceObj.addStage(stageName, description, length,
                        startTime, type);
            }
        }

        throw new IDNotRecognisedException("No race found with ID: " + raceId);
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race RaceObj = ListOfRaces.get(i);
            if (RaceObj.getRaceID() == raceId){
                return RaceObj.getStageIDs();
            }
        }
        throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race raceObj = ListOfRaces.get(i);
            for (int j = 0; j < raceObj.getStageIDs().length; j++){
                if (raceObj.getStageIDs()[j] == stageId){
                    return raceObj.getStageLength(stageId);
                }
            }
        }
        throw new IDNotRecognisedException("Couldn't find stage with ID: " + stageId);
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        boolean hasDeleted = false;

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race raceObj = ListOfRaces.get(i);

            for (int j = 0; j < raceObj.getStageIDs().length; j++){
                if (raceObj.getStageIDs()[j] == stageId){
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
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race raceObj = ListOfRaces.get(i);

            for (int j = 0; j < raceObj.getStageIDs().length; j++){
                if (raceObj.getStageIDs()[j] == stageId) {
                    if (raceObj.getStageState(stageId) == "waiting for results") {
                        throw new InvalidStageStateException("Stage is in invalid state: " + raceObj.getStageState(stageId));
                    }
                    if (0 > location || location > raceObj.getStageLength(stageId) - length) {
                        throw new InvalidLocationException("The starting location of the climb is invalid. It must start and end within the stage.");
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
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race raceObj = ListOfRaces.get(i);

            for (int j = 0; j < raceObj.getStageIDs().length; j++){
                if (raceObj.getStageIDs()[j] == stageId){
                    if (raceObj.getStageState(stageId) == "waiting for results") {
                        throw new InvalidStageStateException("Stage is in invalid state: " + raceObj.getStageState(stageId) );
                    }
                    if (0 > location || location > raceObj.getStageLength(stageId)) {
                        throw new InvalidLocationException("The starting location of the climb is invalid. It must start within the stage.");
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
        // TODO Auto-generated method stub
        boolean hasDeleted = false;

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race raceObj = ListOfRaces.get(i);

            for (int j = 0; j < raceObj.getStageIDs().length; j++){
                for (int k = 0; k < raceObj.getSegmentIds(j).length; k++) {
                    if (raceObj.getSegmentIds(j)[k] == segmentId) {
                        if (raceObj.getStageState(raceObj.getStageIDs()[j]) == "waiting for results") {
                            throw new InvalidStageStateException("Stage is in invalid state: " + (raceObj.getStageState(raceObj.getStageIDs()[j]) ));
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
        // TODO Auto-generated method stub
        boolean hasConcluded = false;

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race raceObj = ListOfRaces.get(i);

            for (int j = 0; j < raceObj.getNumberOfStages(); j++){
                if (raceObj.getStageIDs()[j] == stageId){
                    if (raceObj.getStageState(stageId) == "waiting for results") {
                        throw new InvalidStageStateException("Stage is in invalid state: " + raceObj.getStageState(stageId));
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
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++) {
            Race raceObj = ListOfRaces.get(i);

            for (int j = 0; j < raceObj.getStageIDs().length; j++) {
                if (raceObj.getStageIDs()[j] == stageId){
                    return raceObj.getSegmentIds(j);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++) {
            if (ListOfRaces.get(i).getRaceName() == name) { // if name already used
                throw new IllegalNameException("The given name has already been used on a race. Names must be unique./");
            }
        }

        if (name == null) { //if name null
            throw new InvalidNameException("Name must not be null");
        } else {
            if (name.length() > 30 || name.contains(" ") || name == "") { //if name does not meet specified criteria
                throw new InvalidNameException("Name must not be empty, must be a single word and cannot be over 30 characters");
            }
        }

        ListOfTeams.add(new Team(name, description));
        return ListOfTeams.getLast().getTeamID();

    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        boolean hasRemoved = false;
        for (int i = 0; i < ListOfTeams.size(); i++){
            Team teamObj = ListOfTeams.get(i);

            if (teamObj.getTeamID() == teamId){
                ListOfTeams.remove(teamObj);
                hasRemoved = true;
            }
        }
        if (!hasRemoved) {
            throw new IDNotRecognisedException("No team found with ID: " + teamId);
        }
    }

    @Override
    public int[] getTeams() {
        // TODO Auto-generated method stub
        int[] teamIds = new int[ListOfTeams.size()];

        for (int i = 0; i < ListOfTeams.size(); i++){
            teamIds[i] = ListOfTeams.get(i).getTeamID();
        }

        return teamIds;
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfTeams.size(); i++){
            if (ListOfTeams.get(i).getTeamID() == teamId){
                return ListOfTeams.get(i).getRiderIds();
            }
        }

        throw new IDNotRecognisedException("No team found with Id:" + teamId);
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth)
            throws IDNotRecognisedException, IllegalArgumentException {
        // TODO Auto-generated method stub
        if (yearOfBirth < 1900) {
            throw new IllegalArgumentException("Year of birth is less than 1900.");
        } else if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }


        for (int i = 0; i < ListOfTeams.size(); i++){
            if (ListOfTeams.get(i).getTeamID() == teamID){
                ListOfTeams.get(i).addRider(name, yearOfBirth);
                return ListOfTeams.get(i).getNewRiderID();
            }
        }

        throw new IDNotRecognisedException("No team found with ID: " + teamID);
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        boolean hasRemoved = false;

        for (int i = 0; i < ListOfTeams.size(); i++){
            for (int j = 0; j < ListOfTeams.get(i).getRiderIds().length; j++){
                if (ListOfTeams.get(i).getRiderIds()[j] == riderId){
                    ListOfTeams.get(i).removeRider(riderId);
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
        // TODO Auto-generated method stub
        // james DuplicatedResultException race.getRiderResults
        boolean hasRegistered = false;

        for (int i = 0; i < ListOfRaces.size(); i++){
            for (int j = 0; j < ListOfRaces.get(i).getNumberOfStages(); j++){
                if (ListOfRaces.get(i).getStageIDs()[j] == stageId){
                        if (!ListOfRaces.get(i).isRiderInResults(stageId, riderId)){
                            throw new DuplicatedResultException("Rider already has results");
                    }

                    if (checkpoints.length != ListOfRaces.get(i).getListOfSegmentsFromStage(stageId).size() + 2){
                        throw new InvalidCheckpointsException("Invalid length of checkpoints");
                    }
                    if (ListOfRaces.get(i).getStageState(stageId) != "waiting for results") {
                        throw new InvalidStageStateException("Stage is in invalid state: " + ListOfRaces.get(i).getStageState(stageId));
                    }
                    ListOfRaces.get(i).registerRiderResultsInStage(stageId, riderId, checkpoints);
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
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            for (int j = 0; j < ListOfRaces.get(i).getNumberOfStages(); j++){
                if (ListOfRaces.get(i).getStageIDs()[j] == stageId){
                    if (!ListOfRaces.get(i).isRiderInResults(stageId, riderId)){
                        throw new IDNotRecognisedException
                                ("No rider with ID: " + riderId + " results found in stage with ID: " + stageId);
                    }
                    return ListOfRaces.get(i).getRiderResults(stageId, riderId);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            for (int j = 0; j < ListOfRaces.get(i).getNumberOfStages(); j++){
                if (ListOfRaces.get(i).getStageIDs()[j] == stageId){
                    if (!ListOfRaces.get(i).isRiderInResults(stageId, riderId)){
                        throw new IDNotRecognisedException
                                ("No rider with ID: " + riderId + " results found in stage with ID: " + stageId);
                    }
                    return ListOfRaces.get(i).getRiderAdjustedElapsedResults(stageId, riderId);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        boolean hasDeleted = false;

        for (int i = 0; i < ListOfRaces.size(); i++){
            for (int j = 0; j < ListOfRaces.get(i).getNumberOfStages(); j++){
                if (ListOfRaces.get(i).getStageIDs()[j] == stageId){
                    if (!ListOfRaces.get(i).isRiderInResults(stageId, riderId)){
                        throw new IDNotRecognisedException
                                ("No rider with ID: " + riderId + " results found in stage with ID: " + stageId);
                    }
                    ListOfRaces.get(i).deleteRidersResults(stageId, riderId);
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
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            for (int j = 0; j < ListOfRaces.get(i).getNumberOfStages(); j++){
                if (ListOfRaces.get(i).getStageIDs()[j] == stageId){
                    return ListOfRaces.get(i).getRidersRankInStage(stageId);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException { //james
        // Get the number of points obtained by each rider in a stage.
        int [] ridersRanked = getRidersRankInStage((stageId));
        int [] riderPoints = new int [ridersRanked.length]; // create new array of riders to store points
        for (int i = 0; i < ridersRanked.length; i++){ // for loops through riders
            riderPoints[i] = ListOfRaces.get(i).getPointsFromStage(stageId, i);//get points for stage // add current riders point to array of rider points
        }



        // works in stage of race. so get to stage from race.
        // uses getRidersRankInStage to get ranks of riders
        // works in for loop to go through list of riders
        // calculate the correct amount of points and add to the array of rider points
        // return array of rider points
        // at end, stage not recognised

        // sprints seperately?
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException { //james
        //Get the number of mountain points obtained by each rider in a stage.

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void eraseCyclingPortal() {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

    }

}
