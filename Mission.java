class Mission extends Association {
  // Fields to store mission details
  private String name;
  private String staff;
  private String type;
  private String objective;
  private int duration;
  
  // An associated Spacecraft object (Association relationship)
  // A mission "has-a" spacecraft

  private Spacecraft assignedSpacecraft;
  
  // Constructor
  public Mission(String missionName, String missionStaff, String missionType, String missionObjective, int missionDuration){
      this.name = missionName;
      this.staff = missionStaff;
      this.type = missionType;
      this.objective = missionObjective;
      this.duration = missionDuration;
  }

  // Method to assign a spacecraft to this mission
  // This creates an association between the Mission and Spacecraft classes
  public void assignSpacecraft(Spacecraft spacecraft){
      assignedSpacecraft = spacecraft; // Associate the spacecraft with the mission
      spacecraft.setAvailability(false); // Set spacecraft as occupied
  }

  // Method to remove an assigned spacecraft from this mission
  // This dissociates the spacecraft from the mission
  public void removeSpacecraft(){
      if (assignedSpacecraft != null){
          assignedSpacecraft.setAvailability(true); // Set spacecraft back to available
          assignedSpacecraft = null; // Remove the association
      } else{
          System.out.println("No assigned spacecraft to remove.");
      }
  }

  // Method to display details, including the associated spacecraft
  public void displayDetails(){
      System.out.println("Mission Name: " + name.toUpperCase());
      System.out.println("Assigned Staff: " + staff.toUpperCase());
      System.out.println("Mission Type: " + type.toUpperCase());
      System.out.println("Mission Objective: " + objective.toUpperCase());
      System.out.println("Mission Duration: " + duration + " days");

      // If a spacecraft is assigned, show its details (association demonstrated)
      if (assignedSpacecraft != null) {
          System.out.println("Assigned Spacecraft: " + assignedSpacecraft.getName().toUpperCase() + " | Status: " + assignedSpacecraft.getStatus());
      } else {
          System.out.println("No spacecraft assigned.");
      }
      System.out.println();
  }
}
