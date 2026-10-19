class Mission{
  private String name;
  private String staff;
  private String type;
  private String objective;
  private int duration;

  private Spacecraft assignedSpacecraft;
  private Resource[] assignedResources = new Resource[100];
  private int resourceCount = 0;

  public Mission(String missionName, String missionStaff, String missionType, String missionObjective, int missionDuration){
      this.name = missionName;
      this.staff = missionStaff;
      this.type = missionType;
      this.objective = missionObjective;
      this.duration = missionDuration;
  }

  public void assignSpacecraft(Spacecraft spacecraft){
      assignedSpacecraft = spacecraft;
      spacecraft.setAvailability(false);
  }

  public void removeSpacecraft(){
      if (assignedSpacecraft != null){
          assignedSpacecraft.setAvailability(true);
          assignedSpacecraft = null;
      } else{
          System.out.println("No assigned spacecraft to remove.");
      }
  }

  public void addResource(Resource resource){
      if (resourceCount < assignedResources.length){
          assignedResources[resourceCount++] = resource;
          resource.decrementQuantity(resource.getQuantity());
      } else{
          System.out.println("maximum number of resources reached.");
      }
  }

  public void removeResource(String resourceName){
      for (int i = 0; i < resourceCount; i++){
          if (assignedResources[i].getName().equalsIgnoreCase(resourceName)) {
              assignedResources[i].incrementQuantity(assignedResources[i].getQuantity());
              assignedResources[i] = assignedResources[--resourceCount];
              assignedResources[resourceCount] = null;
              System.out.println("Resource " + resourceName.toUpperCase() + " removed.");
              return;
          }
      }
      System.out.println("Resource not found.");
  }

  public String getName(){
      return name;
  }

  public Spacecraft getAssignedSpacecraft(){
      return assignedSpacecraft;
  }

  public Resource[] getAssignedResources(){
      return assignedResources;
  }

  public int getResourceCount(){
      return resourceCount;
  }

  public void displayDetails(){
      System.out.println("Mission Name: " + name.toUpperCase());
      System.out.println("Assigned Staff: " + staff.toUpperCase());
      System.out.println("Mission Type: " + type.toUpperCase());
      System.out.println("Mission Objective: " + objective.toUpperCase());
      System.out.println("Mission Duration: " + duration + " days");
      if (assignedSpacecraft != null) {
          System.out.println("Assigned Spacecraft: " + assignedSpacecraft.getName().toUpperCase() + " | Status: " + assignedSpacecraft.getStatus());
      } else {
          System.out.println("No spacecraft assigned.");
      }
      System.out.println("Assigned Resources:");
      if (resourceCount == 0) {
          System.out.println("No resources assigned.");
      } else {
          for (int i = 0; i < resourceCount; i++) {
              System.out.println("Resource: " + assignedResources[i].getName().toUpperCase() + " | Quantity: " + assignedResources[i].getQuantity());
          }
      }
      System.out.println();
  }

}


class Resource extends Association{
  private String name;
  private int quantity;

  public Resource(String resourceName, int resourceQuantity){
      this.name = resourceName;
      this.quantity = resourceQuantity;
  }

  public String getName(){
      return name;
  }

  public int getQuantity(){
      return quantity;
  }

  public void setQuantity(int newQuantity){
      quantity = newQuantity;
  }

  public void incrementQuantity(int amount){
      quantity += amount;
  }

  public void decrementQuantity(int amount){
      quantity -= amount;
  }
}

class Spacecraft{
  private String name;
  private String details;
  private String status;
  private boolean isAvailable;

  public Spacecraft(String spacecraftName, String spacecraftDetails){
      this.name = spacecraftName;
      this.details = spacecraftDetails;
      status = "Available";
      isAvailable = true;
  }

  public void setAvailability(boolean availability){
      isAvailable = availability;
      status = availability ? "Available":"Assigned/Occupied";
  }

  public String getName(){
      return name;
  }

  public String getDetails(){
      return details;
  }

  public String getStatus(){
      return status;
  }

  public boolean isAvailable(){
      return isAvailable;
  }
}