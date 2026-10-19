import java.util.Scanner;

public class Association{
    private static Scanner space = new Scanner(System.in);

    private static Mission[] missions = new Mission[100];
    private static Spacecraft[] spacecrafts = new Spacecraft[100];
    private static Resource[] resources = new Resource[100];
    private static ResearchProject[] researchProj = new ResearchProject[100];
    private static int spacecraftCount, resourceCount, missionCount, researchCount = 0;

    private static String userRole;

    public static void main(String[] args){
        System.out.println("\n=+=+= SPACE AGENCY MANAGEMENT SYSTEM =+=+=");
        userLogin();

        while(true){
            switch (userRole){
                case "Admin":
					System.out.println("\n==== ADMIN MENU ====");
                    System.out.println("\n1. Manage Missions");
                    System.out.println("2. Manage Spacecraft");
                    System.out.println("3. Exit \n");

                    System.out.print("Enter choice number (1-3): ");
                    int choice = space.nextInt();
                    space.nextLine();

                    switch (choice) {
                        case 1:
                            manageMissionsMenu();
                            break;

                        case 2:
                            manageSpacecraftMenu();
                            break;

                        case 3:
							progExit();

                        default:
                            System.out.println("Invalid input. Please enter an integer from 1 to 3 only.");
                        }
                            break;

                case "Spacecraft Engineer":
                    manageSpacecraftMenu();
                    break;

                case "Mission Planner":
                    manageMissionsMenu();
                    break;

                case "Resource Manager":
                    manageResourcesMenu();
                    break;

                case "Scientist/Researcher":
                    manageResearchProjsMenu();
                    break;

                default:
                    System.out.println("You are not authrorised to take any any action.");
            }
        }
    }

    public static void progExit(){
        while (true){
            System.out.println("\nExiting... Thank you!");
            System.exit(0);
        }
    }

    private static void userLogin(){
        System.out.println("\n==== LOG IN ====\n");
        System.out.println("1. Admin");
        System.out.println("2. Spacecraft Engineer");
        System.out.println("3. Mission Planner");
        System.out.println("4. Resource Manager");
        System.out.println("5. Scientist/Researcher");

        System.out.print("\nSelect option (enter 1-6): ");

        int roleChoice = space.nextInt();
        space.nextLine();

        switch(roleChoice) {
            case 1:
                userRole = "Admin";
                break;

            case 2:
                userRole = "Spacecraft Engineer";
                break;

            case 3:
                userRole = "Mission Planner";
                break;

            case 4:
                userRole = "Resource Manager";
                break;

            case 5:
                userRole = "Scientist/Researcher";
                break;

            default:
            System.out.println("Invalid input. Please enter an integer from 1 to 6 only.");
            userLogin();
        }
        System.out.println("\nLogged in as: " + userRole.toUpperCase());
    }

    public static void manageMissionsMenu(){
        while (true) {
            System.out.println("\n==== Manage Missions ====\n");
            System.out.println("1. Create New Mission");
            System.out.println("2. Add/Remove Assigned Spacecraft");
            System.out.println("3. Add/Remove Assigned Resources");
            System.out.println("4. Display Missions");

            if (userRole.equals("Admin")){
                System.out.println("5. Back to Admin Menu");
            } else{
                System.out.println("5. Exit");
            }

            System.out.print("\nEnter choice number (1-5): ");
            int missionChoice = space.nextInt();
            space.nextLine();

            switch (missionChoice) {
                case 1:
                    createMission();
                    break;

                case 2:
                    modifyAssignedSpacecraft();
                    break;

                case 3:
                    modifyAssignedResources();
                    break;

                case 4:
                    displayAllMissions();
                    break;

                case 5:
                    if (userRole.equals("Admin")){
                        return;
                    } else{
						progExit();
                    }

                default:
                    System.out.println("Invalid input. Please enter an integer from 1 to 5 only.");
            }
        }
    }

    public static void createMission(){
        System.out.print("\nEnter NAME of Mission: ");
        String missionName = space.nextLine();

        System.out.print("Enter TYPE of Mission (Search, Investigation, Maintenance, etc.): ");
        String missionType = space.nextLine();

        System.out.print("Enter ASSIGNED STAFF: ");
        String missionStaff = space.nextLine();

        System.out.print("Enter MISSION OBJECTIVE/s: ");
        String missionObjective = space.nextLine();

        System.out.print("Enter DURATION of mission (in days): ");
        int missionDuration = space.nextInt();
        space.nextLine();

        //object
        Mission newMission = new Mission(missionName, missionStaff, missionType, missionObjective, missionDuration);

        //assign spacecraft
        System.out.println("\nASSIGN SPACECRAFT\n");

        System.out.println("Available Spacecraft/s: ");
        boolean foundAvailableSpacecraft = false;
        for (int i = 0; i < spacecraftCount; i++){
            if (spacecrafts[i].isAvailable()){
                System.out.println((i+1) + "." + spacecrafts[i].getName().toUpperCase());
                foundAvailableSpacecraft = true;
            }
        }
        if (!foundAvailableSpacecraft){
            System.out.println("No available spacecraft. Please add spacecrafts in Manage Spacecraft menu first.");
        } else{
            System.out.print("\nEnter spacecraft number to assign: ");
            int spacecraftChoice = space.nextInt();
            space.nextLine();

            if (spacecraftChoice >= 1 && spacecraftChoice <=spacecraftCount && spacecrafts[spacecraftChoice - 1].isAvailable()){
                newMission.assignSpacecraft(spacecrafts[spacecraftChoice - 1]);
                System.out.println("Spacecraft " + spacecrafts[spacecraftChoice - 1].getName().toUpperCase() + "successfully assigned.");
            } else{
                System.out.println("Invalid input or spacecraft unavailable.");
            }
        }

        //assign resources
        System.out.println("\nASSIGN RESOURCES\n");

        System.out.println("Available Resource/s: ");
        boolean foundAvailableResources = false;
        for (int i = 0; i < resourceCount; i++){
            if (resources[i].getQuantity() > 0){
                System.out.println((i+1) + "." + resources[i].getName().toUpperCase() + resources[i].getQuantity());
                foundAvailableResources = true;
            }
        }
        if (!foundAvailableResources){
            System.out.println("No available resource/s. Please add spacecrafts in Manage Resource menu first.");
        } else{
            System.out.print("Enter resource name: ");
            String resourceName = space.nextLine();
            System.out.print("Enter quantity: ");
            int resourceQuantity = space.nextInt();
            space.nextLine();

            boolean resourceAssigned = false;
            for (int i = 0; i < resourceCount; i++){
                if (resources[i].getName().equalsIgnoreCase(resourceName) && resources[i].getQuantity() >= resourceQuantity){

                    //object
                    Resource assignedResource = new Resource(resources[i].getName(), resourceQuantity);
                    newMission.addResource(assignedResource);
                    resources[i].decrementQuantity(resourceQuantity);
                    resourceAssigned = true;
                    System.out.println("Resource " + resourceName.toUpperCase() + " | Quantity: " + resourceQuantity + "successfully assigned.");
                }
            }
            if (!resourceAssigned){
                System.out.println("Resource not found or insufficient quantity.");
            }
        }

        //Store Mission Log
        missions[missionCount++] = newMission;
        System.out.println("\nMission " + missionName.toUpperCase() + " created successfully.");
    }

    public static void modifyAssignedSpacecraft(){
        System.out.print("\nEnter mission name to modify: ");
        String missionName = space.nextLine();

        for(int i = 0; i < missionCount; i++){
            if(missions[i].getName().equalsIgnoreCase(missionName)){
                System.out.println("\nCurrent Assigned Spacecraft: " + (missions[i].getAssignedSpacecraft() != null ? missions[i].getAssignedSpacecraft().getName() : "None"));

                System.out.println("\n1. Remove Assigned Spacecraft");
                System.out.println("2. Add New Spacecraft");

                System.out.print("\nEnter choice number (1-2): ");
                int choice = space.nextInt();

                switch (choice){
                    case 1:
                        if (spacecraftCount != 0){
                            missions[i].removeSpacecraft();
                            System.out.println("Assigned spacecraft removed.");
                        } else{
                            System.out.println("No spacecraft to be removed.");
                        }
                        break;

                    case 2:
                        System.out.println("\nAvailable Spacecraft/s:\n");
                        boolean foundAvailableSpacecraft = false;
                        for (int j = 0; j < spacecraftCount; j++) {
                            if (spacecrafts[j].isAvailable()) {
                                System.out.println((j + 1) + ". " + spacecrafts[j].getName());
                                foundAvailableSpacecraft = true;
                            }
                        }

                        if (!foundAvailableSpacecraft) {
                            System.out.println("No available spacecraft. Please add spacecrafts in Manage Spacecraft menu first.");
                        } else {
                            System.out.print("\nEnter spacecraft number to assign: ");
                            int spacecraftChoice = space.nextInt();
                            space.nextLine();

                            if (spacecraftChoice >= 1 && spacecraftChoice <= spacecraftCount && spacecrafts[spacecraftChoice - 1].isAvailable()) {
                                missions[i].assignSpacecraft(spacecrafts[spacecraftChoice - 1]);
                                System.out.println("\nNew spacecraft assigned.");
                            } else {
                                System.out.println("\nInvalid choice or spacecraft not available.");
                            }
                        }
                        break;
                    default:
                        System.out.println("\nInvalid input. Please enter either 1 or 2.");
                }
                return;
            }
        }
        System.out.println("\nMission not found.");
    }

    public static void modifyAssignedResources(){
        System.out.print("\nEnter mission name to modify: ");
        String missionName = space.nextLine();

        for (int i = 0; i < missionCount; i++){
            if (missions[i].getName().equalsIgnoreCase(missionName)){
                System.out.println("\nCurrent Assigned Resources:" + (missions[i].getAssignedResources()));

                System.out.println("\n1. Remove Assigned Resource");
                System.out.println("2. Add New Resource");

                System.out.print("\nEnter choice number (1-2): ");
                int choice = space.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("\nEnter resource name to remove: ");
                        String resourceNameRemove = space.nextLine();
                        missions[i].removeResource(resourceNameRemove);
                        System.out.println("Resource " + resourceNameRemove.toUpperCase() + "removed.");
                        break;

                    case 2:
                        System.out.println("\nAvailable Resources:");
                        boolean foundAvailableResources = false;
                        for (int j = 0; j < resourceCount; j++){
                            if (resources[j].getQuantity() > 0){
                                System.out.println((j + 1) + ". " + resources[j].getName().toUpperCase() + " | Quantity: " + resources[j].getQuantity());
                                foundAvailableResources = true;
                            }
                        }

                        if (!foundAvailableResources){
                            System.out.println("\nNo available resources. Please add resources in Manage Resources menu first.");
                        } else {
                            System.out.print("\nEnter resource NAME to add: ");
                            String resourceNameAdd = space.nextLine();
                            space.nextLine();

                            System.out.print("Enter QUANTITY to add: ");
                            int quantity = space.nextInt();

                            boolean resourceAssigned = false;
                            for (int j = 0; j < resourceCount; j++){
                                if (resources[j].getName().equalsIgnoreCase(resourceNameAdd) && resources[j].getQuantity() >= quantity) {

                                    //object
                                    Resource assignedResource = new Resource(resources[j].getName(), quantity);
                                    missions[i].addResource(assignedResource);
                                    resources[j].decrementQuantity(quantity);
                                    resourceAssigned = true;
                                    System.out.println("Resource " + resourceNameAdd.toUpperCase() + " added to mission.");
                                    break;
                                }
                            }

                            if (!resourceAssigned){
                                System.out.println("Resource not found or insufficient quantity.");
                            }
                        }
                        break;
                    default:
                    System.out.println("Invalid input. Please enter either 1 or 2.");
                }
                return;
            }
        }
        System.out.println("\nMission not found.");
    }

    public static void displayAllMissions(){
        System.out.println("\n==== Missions Log ====\n");
        if (missionCount > 0){
            for (int i = 0; i < missionCount; i++){
                missions[i].displayDetails();
                System.out.println("--------------------------------------");
            }
        } else{
            System.out.println("Missions List Empty.");
        }
    }

    public static void manageSpacecraftMenu(){
        while (true){
            System.out.println("\n==== Manage Spacecraft ====\n");
            System.out.println("1. Add New Spacecraft");
            System.out.println("2. Display All Spacecraft");

            if (userRole.equals("Admin")){
                System.out.println("3. Back to Admin Menu");
            } else{
                System.out.println("3. Exit");
            }

            System.out.print("\nEnter choice number (1-3): ");
            int spacecraftChoice = space.nextInt();
            space.nextLine();

            switch (spacecraftChoice){
                case 1:
                    addNewSpacecraft();
                    break;

                case 2:
                    displayAllSpacecraft();
                    break;

                case 3:
                    if (userRole.equals("Admin")){
                        return;
                    } else{
						progExit();
                    }

                default:
                    System.out.println("Invalid input. Please enter an integer from 1 to 3 only.");
            }
        }
    }

    public static void addNewSpacecraft(){
        System.out.print("\nEnter spacecraft name: ");
        String spacecraftName = space.nextLine();

        System.out.print("Enter spacecraft details: ");
        String spacecraftDetails = space.nextLine();

        //object
        spacecrafts[spacecraftCount++] = new Spacecraft(spacecraftName, spacecraftDetails);
        System.out.println("Spacecraft " + spacecraftName.toUpperCase() + " added successfully.");
    }

    public static void displayAllSpacecraft(){
        System.out.println("\n==== All Spacecraft ====");
        if (spacecraftCount > 0){
            for (int i = 0; i < spacecraftCount; i++){
                System.out.println("\nName: " + spacecrafts[i].getName().toUpperCase() + " | Status: " + spacecrafts[i].getStatus().toUpperCase());
                System.out.println("Details: " + spacecrafts[i].getDetails());
                System.out.println("--------------------------------------");
            }
        } else{
            System.out.println("Spacecraft List Empty.");
        }

    }

    public static void manageResourcesMenu(){
        do {
            System.out.println("\n==== Manage Resources ====\n");
            System.out.println("1. Add New Resource");
            System.out.println("2. Display All Resources");
            if (userRole.equals("Admin")){
                System.out.println("3. Back to Admin Menu");
            } else{
                System.out.println("3. Exit");
            }


            System.out.print("\nEnter choice number (1-3): ");
            int resourceChoice = space.nextInt();
            space.nextLine();

            switch (resourceChoice) {
                case 1:
                    addNewResource();
                    break;

                case 2:
                    displayAllResources();
                    break;

                case 3:
                    if (userRole.equals("Admin")){
                        return;
                    } else{
						progExit();
                    }

                default:
                    System.out.println("Invalid input. Please enter an integer from 1 to 3 only.");
            }
        } while (true);
    }

    public static void addNewResource(){
        System.out.print("\nEnter resource name: ");
        String resourceName = space.nextLine();

        System.out.print("Enter quantity: ");
        int resourceQuantity = space.nextInt();

        //object
        resources[resourceCount++] = new Resource(resourceName, resourceQuantity);
        System.out.println("Resource " + resourceName.toUpperCase() + " added successfully.");
    }

    public static void displayAllResources(){
        System.out.println("\n==== All Resources ====");
        if (resourceCount > 0){
            for (int i = 0; i < resourceCount; i++) {
                System.out.println("\nName: " + resources[i].getName() + " | Quantity: " + resources[i].getQuantity());
                System.out.println("------------------------------------------------");
            }
        } else{
            System.out.println("Resource List Empty.");
        }

    }

    public static void manageResearchProjsMenu(){
        while (true) {
            System.out.println("\n==== Manage Research Projects ====\n");
            System.out.println("1. Create New Research Project");
            System.out.println("2. Assign Mission to Research Project");
            System.out.println("3. Display All Research Projects");
            if (userRole.equals("Admin")){
                System.out.println("4. Back to Admin Menu");
            } else{
                System.out.println("4. Exit");
            }

            System.out.print("\nEnter menu number (1-4): ");
            int projectChoice = space.nextInt();
            space.nextLine();

            switch (projectChoice){
                case 1:
                    createNewResearchProject();
                    break;

                case 2:
                    assignMissionToResearchProject();
                    break;

                case 3:
                    displayAllResearchProjects();
                    break;

                case 4:
                    if (userRole.equals("Admin")){
                        return;
                    } else{
						progExit();
                    }

                default:
                    System.out.println("Invalid input. Please enter an integer from 1 to 4 only.");
            }
        }
    }

    public static void createNewResearchProject(){
        System.out.print("\nEnter project name: ");
        String projectName = space.nextLine();

        System.out.print("Enter project details: ");
        String projectDetails = space.nextLine();

        //object
        researchProj[researchCount++] = new ResearchProject(projectName, projectDetails);
        System.out.println("\nResearch project " + projectName.toUpperCase() + " created successfully.");
    }

    public static void assignMissionToResearchProject(){
        System.out.print("\nEnter project name to assign a mission: ");
        String projectName = space.nextLine();

        if (missionCount > 0){
            for (int i = 0; i < missionCount; i++){
                System.out.println("List of Available Missions: ");
                displayAllMissions();
            }
            for (int i = 0; i < researchCount; i++){
                if (researchProj[i].getName().equalsIgnoreCase(projectName)) {
                    System.out.print("\nEnter mission name to assign: ");
                    String missionName = space.nextLine();

                    for (int j = 0; j < missionCount; j++){
                        if (missions[j].getName().equalsIgnoreCase(missionName)) {
                            researchProj[i].assignProjMission(missions[j]);
                            System.out.println("Mission " + missionName.toUpperCase() + " assigned to project " + projectName + ".");
                            return;
                        }
                    }
                    System.out.println("\nMission not found.");
                    return;
                }
            }
            System.out.println("Research project not found.");
        } else{
            System.out.println("No available missions to assign.");
        }

    }

    public static void displayAllResearchProjects(){
        System.out.println("\n==== All Research Projects ====\n");
        if (researchCount > 0){
            for (int i = 0; i < researchCount; i++){
                researchProj[i].displayDetails();
                System.out.println("--------------------------------------");
            }
        } else{
            System.out.println("Research Project List Empty.");
        }
    }
}

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

    class Resource{
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

}