package org.youcode.easybank.views;

import org.youcode.easybank.services.MissionAssignmentService;

public class MissionAssignmentView {

    private MissionAssignmentService missionAssignmentService;

    public MissionAssignmentView() {
        missionAssignmentService = new MissionAssignmentService();
    }

   public void displayStats() {
        missionAssignmentService.displayStats();
   }
}
