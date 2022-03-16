package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        //test
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        // TODO Auto-generated method stub

        // exception stuff
        ListOfRaces.add(new Race(name, description));
        return ListOfRaces.getLast().getRaceID();
        // test j
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
                return RaceObj.getStages();
            }
        }
        throw new IDNotRecognisedException("Couldn't find race with ID: " + raceId);
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++){
            Race raceObj = ListOfRaces.get(i);
            for (int j = 0; j < raceObj.getStages().length; j++){
                if (raceObj.getStages()[j] == stageId){
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

            for (int j = 0; j < raceObj.getStages().length; j++){
                if (raceObj.getStages()[j] == stageId){
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

            for (int j = 0; j < raceObj.getStages().length; j++){
                if (raceObj.getStages()[j] == stageId){
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

            for (int j = 0; j < raceObj.getStages().length; j++){
                if (raceObj.getStages()[j] == stageId){
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

            for (int j = 0; j < raceObj.getStages().length; j++){
                for (int k = 0; k < raceObj.getSegmentIds(j).length; k++) {
                    if (raceObj.getSegmentIds(j)[k] == segmentId) {
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

    }

    @Override
    public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfRaces.size(); i++) {
            Race raceObj = ListOfRaces.get(i);

            for (int j = 0; j < raceObj.getStages().length; j++) {
                if (raceObj.getStages()[j] == stageId){
                    return raceObj.getSegmentIds(j);
                }
            }
        }

        throw new IDNotRecognisedException("No stage found with ID: " + stageId);
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        // TODO Auto-generated method stub

        ListOfTeams.add(new Team(name, description));
        return ListOfTeams.getLast().getTeamID();

    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < ListOfTeams.size(); i++){
            Team teamObj = ListOfTeams.get(i);

            if (teamObj.getTeamID() == teamId){
                ListOfTeams.remove(teamObj);
            }
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
        return null;
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth)
            throws IDNotRecognisedException, IllegalArgumentException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
            throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
            InvalidStageStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
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
