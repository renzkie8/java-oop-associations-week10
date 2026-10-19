class Spacecraft {
  private String name;
  private String details;
  private String status;
  private boolean isAvailable;

  // Constructor
  public Spacecraft(String spacecraftName, String spacecraftDetails){
      this.name = spacecraftName;
      this.details = spacecraftDetails;
      status = "Available";
      isAvailable = true;
  }

  // Method to set the spacecraft availability status
  // This is used by the Mission class to reflect whether a spacecraft is assigned to a mission
  public void setAvailability(boolean availability){
      isAvailable = availability;
      status = availability ? "Available":"Assigned/Occupied";
  }

  // Getters for spacecraft details
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
