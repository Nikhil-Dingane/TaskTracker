import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;
import java.util.Collections;
import java.util.Iterator;

class AppDriver {
    public static void main(String Args[]) throws IOException {
        System.out.println("\n\n" + Task_Tracker_Banner);
        
        BufferedReader bufferReader =  new BufferedReader(new InputStreamReader(System.in));

        TaskEntry taskEntry = null;

        TaskList taskList = null;

        ListDisplayManager listDisplayManager = null;

        FileReader fileReader = null;

        LocalDate localDate = LocalDate.now();
       
        while(true) {
            getMainMenu();
            System.out.println("\nPlease Enter Your Choice :");
            int Choice = getChoice(1,4);
            File todaysFile = new File(getTodaysFileName());

            switch(Choice) {
                case 1:
                    createOrGetTaskListMenu();
                    System.out.println("\nPlease Enter Your Choice :");
                    int createTodaysTask = getChoice(1,2);
                    boolean todaysTask = true;
                    if(createTodaysTask < 1 || createTodaysTask > 2) {
                        createTodaysTask = getChoice(1,2);
                    }
                    switch(createTodaysTask) {
                        case 1:
                            taskList = new TaskList();
                            taskList.setListDate(localDate);
                            try {
                                todaysFile.createNewFile();
                            } catch(IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(taskList);
                            writeInTodaysFile(taskList);
                            System.out.println("\nTodays Task List Created Successfully");
                        break;
                        case 2:
                            if(todaysFile.length() == 0) {
                                System.out.println("\nYou not Created Todays Task List.");
                                taskList = new TaskList();
                                taskList.setListDate(localDate);
                                todaysFile.createNewFile();
                                System.out.println(taskList);
                                writeInTodaysFile(taskList);
                                System.out.println("\nWe Initialised New Task List for Today");
                                break;
                            } else {
                                fileReader = new FileReader(getTodaysFileName());
                                taskList = fileReader.readList();
                                System.out.println(taskList);
                            }
                        break;
                            
                    }
                        
                    while(todaysTask == true) {
                            getTodaysListMenu();
                            System.out.println("\nPlease Enter Your Choice :");
                            int todaysTaskChoice = getChoice(1,4);

                            switch(todaysTaskChoice) {
                                case 1:
                    	            taskEntry = getEntryInfo();
                 	            if(taskList.add(taskEntry)) {
                        	        System.out.println(taskList);
                                        writeInTodaysFile(taskList);
                    	            } else {
                        	        System.out.println("Unable to Insert Tasks");
                    	            }
                                break;

                                case 2: 
                                    System.out.println(taskList);
                                    if(taskList != null){
                                        System.out.println("\nPlease Enter Your Task Number to Update :");
                    	                int taskNumberForEdit = getChoice(0,taskList.getListSize())-1;
                                        System.out.println("Enter End Time :");
                                        LocalTime newEndTime = getLocalTime();
                                        taskEntry.setEndTime(newEndTime);
                                        System.out.println("Enter Description :");
                                        String Description = bufferReader.readLine();
                                        taskEntry.setDescription(Description);
                                        taskList.update(taskNumberForEdit,taskEntry);
                                        System.out.println(taskList);
                                        writeInTodaysFile(taskList);
                                    } else {
                                        System.out.println("Your Todays Task List is Empty.");
                                    }
                                break;

                                case 3:
                                    System.out.println(taskList);
                                    if(taskList != null) {
                                        System.out.println("\nPlease Enter Your Task Number :");
                    	                int taskNumberForDelete = getChoice(0,taskList.getListSize())-1;
                                        taskList.delete(taskNumberForDelete);
                                        System.out.println(taskList);
                                        writeInTodaysFile(taskList);
                                    } else {
                                        System.out.println("Your Todays Task List is Empty");
                                    }
                                break;

                                case 4:
                                    writeInTodaysFile(taskList);
                                    todaysTask =false;
                                break;
                            } 
                        } 
                break;
                
                case 2: 
                    printShowTaskListInfo();
                    System.out.println("\nPlease Enter Your Choice :");
                    int showListChoice = getChoice(1,4);
                    if(getMonthFileName().length() == 0) {
                        System.out.println("Your File is Empty" + getMonthFileName());
                    }
                    fileReader = new FileReader(getMonthFileName());
                    List<TaskList> monthTasks = fileReader.readLists();
                    listDisplayManager = new ListDisplayManager(monthTasks);

                    switch(showListChoice) {
                        case 1:
                            listDisplayManager.showListAll();
                        break;
                        		
		                case 2:
                            System.out.println("Enter Date of Task List That You are Looking For :");
                            LocalDate Date = getLocalDate();
                            listDisplayManager.showListByDate(Date);
                        break;

                        case 3:
                            listDisplayManager.showYesterdaysList();
                        break;

		        case 4:
		            System.out.println("\nPlease Enter From Which Date Do You Want to get Tasks :");
                            LocalDate fromDate = getLocalDate();
                            System.out.println("\nPlease Enter Last Date of Tasks You are Looking For :");
                            LocalDate toDate = getLocalDate();
                            listDisplayManager.showListFromToDate(fromDate,toDate);
                        break;
                    }
                break;

                case 3:
                    printRemoveListsInfo();
                    System.out.println("\nPlease Enter Your Choice :");
                    int RemoveListsChoice = getChoice(1,2);
                    if(getMonthFileName().length() == 0) {
                        System.out.println("Your File is Empty" + getMonthFileName());
                    }
                    fileReader = new FileReader(getMonthFileName());
                    TaskList listOfTask = null;
                    List<TaskList> listsOfTasks = fileReader.readLists();
                    
		    switch(RemoveListsChoice) {
                        case 1:
                            System.out.println("\nPlease Enter Date That You Want to Remove :");
                            LocalDate removeBySingleDate = getLocalDate();
                            
			    for(TaskList list : listsOfTasks) {
                                if(list.getListDate().equals(removeBySingleDate)) {
                                    listOfTask = list;
                                    listsOfTasks.remove(listOfTask);
                                    System.out.println("\nTask List Removed Successfully");
                                } else {
                                    System.out.println("\nYour Searched Date Task List Not Found!!!");
                                }
                            }
                            WriteInMonthFile(listsOfTasks);
                            
                        break;

                        case 2:
                            System.out.println("\nPlease Enter Starting Date :");
                            LocalDate fromDate = getLocalDate();

                            System.out.println("\nPlease Enter Ending Date :");
                            LocalDate toDate = getLocalDate();

                            for(TaskList list : listsOfTasks) {
                                if(list.getListDate().compareTo(fromDate) >= 0 && list.getListDate().compareTo(toDate) <= 0) {
                                    listOfTask = list;
                                    listsOfTasks.remove(listOfTask);
                                    System.out.println("\nTask List From :" + fromDate + "To :" + toDate + " Removed Successfully");
                                } else {
                                    System.out.println("\nYour Task List Not Found !!!");
                                }
                            }
                            WriteInMonthFile(listsOfTasks);
                        break;                            
                     }
                break;

                case 4:
                    File monthFile = new File(getMonthFileName());
                    boolean monthFileCreation;
                    System.out.println("\nDo You Finished Creating Todays List ?(Yes or No)");
                    String finishOrNot = bufferReader.readLine();
                    List<TaskList> taskLists = new ArrayList<TaskList>();
                    if(finishOrNot.equalsIgnoreCase("Yes")) {
                          monthFileCreation = monthFile.createNewFile();
                          if(monthFileCreation) {
                              taskLists.add(taskList);
                              WriteInMonthFile(taskLists);
                              System.out.println("\n\n******************** Thank You For Using Task-Tracker ********************");
                              System.exit(0);
                          } else {
                              fileReader = new FileReader(getMonthFileName());
                              taskLists = fileReader.readLists();
                              taskLists.add(taskList);
        	              WriteInMonthFile(taskLists);
                              System.out.println("\n\n******************** Thank You For Using Task-Tracker ********************");
                              System.exit(0);
                          }
                    } else if(finishOrNot.equalsIgnoreCase("No")) {
                        System.out.println("\nOnly Todays File Updated :" +getTodaysFileName());
        	        System.out.println("\n\n******************** Thank You For Using Task-Tracker ********************");
                        System.exit(0);
                    } else {
			System.out.println("Your Input is Wrong!!!");
		    }
		break;
	     }
        }
        
    }
    
    public static void getMainMenu() {
        System.out.println("\n=============================== Main Menu ===============================");
        System.out.println("*                                                                       *");
        System.out.println("*               Press (1) Create or Get Todays Task List                *");
        System.out.println("*               Press (2) Show Task Lists                               *");
        System.out.println("*               Press (3) Remove Task Lists                             *");
        System.out.println("*               Press (4) Exit.                                         *");
        System.out.println("*                                                                       *");
        System.out.println("=========================================================================");
    }
    
    public static int getChoice(int lowerBound, int upperBound) throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        int Choice = 0;
        int lowBound = lowerBound;
        int upBound = upperBound;
        while(true) {
               try {
		   Choice = Integer.parseInt(bufferReader.readLine());
                   if(Choice < lowerBound || Choice > upperBound) {
                       System.out.println("Your Input is Wrong");
                       System.out.println("Plese Enter Valid Inputs Between (" + lowBound + "-" + upBound + ") Again");
                    }
             } catch (NumberFormatException e) {
                  System.out.println("Please Enter Only Number.");
             } catch (Exception e) {
	          e.printStackTrace();
             } 
            return Choice;
        }
    }

    public static String getTodaysFileName() throws IOException {
        LocalDate currentDate = LocalDate.now();
	int Date = currentDate.getDayOfMonth();
	Month currentMonth = currentDate.getMonth();
	int Year = currentDate.getYear();
	String fileNameYear = Integer.toString(Year);
        String fileName = Date + "_" + currentMonth + "_" + fileNameYear + ".ser";
        return fileName;
    }

    public static void createOrGetTaskListMenu() {
        System.out.println("\n\n*********************** Create Or Get List Menu *************************");
        System.out.println("| |                                                                   | |");
        System.out.println("| |             Press (1) to Create New Todays Task List              | |");
        System.out.println("| |             Press (2) to Get Todays Task List                     | |");
        System.out.println("| |                                                                   | |");
        System.out.println("*************************************************************************");
    }
    public static void getTodaysListMenu() {
        System.out.println("\n\n******************* Todays List Modification Menu ***********************");
        System.out.println("| |                                                                   | |");
        System.out.println("| |             Press (1) Add new Task                                | |");
        System.out.println("| |             Press (2) Edit Todays Task                            | |");
        System.out.println("| |             Press (3) Remove Todays Task                          | |");
        System.out.println("| |             Press (4) Main Menu                                   | |");
        System.out.println("| |                                                                   | |");
        System.out.println("*************************************************************************");
    }

    public static TaskEntry getEntryInfo() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        boolean timeValidator;
        
	System.out.println("\nEnter Starting Time :");
        LocalTime startTime = getLocalTime();                 
        System.out.println("\nEnter Ending Time :");
        LocalTime endTime = getLocalTime();        
        timeValidator = startingAndEndingTimeValidator(startTime, endTime);
        if(timeValidator == false) {
            System.out.println("\nYour Starting and Ending Time is Wrong!!!");
            System.out.println("\nNote: Starting Time is Always Less Than Ending Time!!!");
            System.out.println("\nPlease Enter Both Times Again!!!");
            System.out.println("\nEnter Starting Time :");
            startTime = getLocalTime();                 
            System.out.println("\nEnter Ending Time :");
            endTime = getLocalTime();
            timeValidator = startingAndEndingTimeValidator(startTime, endTime);
        } 
        TaskEntry taskEntry = new TaskEntry();
        taskEntry.setStartTime(startTime);
        taskEntry.setEndTime(endTime);
        System.out.println("\nEnter Type (Task/Learning) :");
        taskEntry.setType(bufferReader.readLine());
        System.out.println("\nEnter Description What You did for Your " + taskEntry.getType() + " :");
        taskEntry.setDescription(bufferReader.readLine());
        return taskEntry;                                                       
    }

    public static LocalTime getLocalTime() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Note: The Time is 24 Hours");
        int Hour = 0, Minute = 0;
        while(true) {
            try {
                System.out.println("Enter Hour :");
                Hour = Integer.parseInt(bufferReader.readLine());  
                System.out.println("Enter Minute :");
                Minute =  Integer.parseInt(bufferReader.readLine());
                return LocalTime.of(Hour,Minute);
           } catch(DateTimeException e) {
                System.out.println("Please Enter Valid Hours : (00-23), Minutes : (00-59) Again:");
           } catch (NumberFormatException e) {
                System.out.println("Please Enter Only Number :");
           } catch(Exception e) {
                e.printStackTrace();
           }
       }
    }

    public static void writeInTodaysFile(TaskList taskList) throws IOException {
        FileWriter fileWriter = new FileWriter(getTodaysFileName());
        try {
            fileWriter.writeList(taskList);
            System.out.println("Data Successfully gets Loaded In File :" + getTodaysFileName());
        } catch (Exception e) {
            System.out.println("Unable to Write In File");
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }
    }

    public static void printShowTaskListInfo() {
        System.out.println("\n\n************************* Show Tasks Menu *******************************");
        System.out.println("| |                                                                   | |");
        System.out.println("| |             Press (1) to Show All Tasks                           | |");
        System.out.println("| |             Press (2) to Show Task By Date                        | |");
        System.out.println("| |             Press (3) to Show Yesterday's Task                    | |");
        System.out.println("| |             Press (4) to Show All Tasks From Date to End Date     | |");
        System.out.println("| |             Press (5) to Show Tasks by Month Wise                 | |");
        System.out.println("| |                                                                   | |");
        System.out.println("*************************************************************************");
    }

    public static String getMonthFileName() throws IOException {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        int Year = currentDate.getYear();
        String fileNameYear = Integer.toString(Year);
        String fileName = currentMonth + "_" + fileNameYear + ".ser";
        return fileName;
    }

    public static LocalDate getLocalDate() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.println("Enter Date of Day :");
                int Date = Integer.parseInt(bufferReader.readLine());
                System.out.println("Enter Month :");
                int Month = Integer.parseInt(bufferReader.readLine());
                System.out.println("Enter Year :");
                int Year = Integer.parseInt(bufferReader.readLine());
                return LocalDate.of(Year,Month,Date);
           } catch(DateTimeException e) {
                System.out.println("Please Enter Valid Date:(1-31), Month:(1-12) Year(2000-2300) :");
           } catch(NumberFormatException e) {
                System.out.println("Please Enter Only Number With Valid Date:(1-31), Month:(1-12), Year(2000-2300):");
           } catch(Exception e) {
               e.printStackTrace();
           }
       }
    }

    public static void printRemoveListsInfo() {
        System.out.println("\n\n**************************** Remove Menu ********************************");
        System.out.println("| |                                                                   | |");
        System.out.println("| |             Press (1) to Remove Task Lists By Single Date         | |");
        System.out.println("| |             Press (2) to Remove Task Lists From Date to To Date   | |");
        System.out.println("| |                                                                   | |");
        System.out.println("*************************************************************************");
    }

    public static void WriteInMonthFile(List<TaskList> taskLists) throws IOException {
        FileWriter fileWriter = new FileWriter(getMonthFileName());
        try {
            fileWriter.writeLists(taskLists);
            System.out.println("Data SuccessFully Loaded In File :" + getMonthFileName());
        } catch (Exception e) {
            System.out.println("Unable to Write In File");
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }
    }
    
    public static boolean startingAndEndingTimeValidator(LocalTime startTime, LocalTime endTime) throws IOException {
        int timeStatus = startTime.compareTo(endTime);
        boolean result = false;
        if(timeStatus < 0 || timeStatus == 0) {
            result = true;
        }
        return result;
    }
    public static final String Task_Tracker_Banner =
    "#################################################################################################################################################\n" +
    "#                                                                                                                                               #\n" +
    "# ###########   #########       ########   #     ###         ###########   ######     ########       #######   #     ###   #########   ######   #\n" +                                                                               
    "#      #       #         #     #           #    #                 #       #      #   #        #     #          #    #     #           #      #  #\n" +    
    "#      #       #         #    #            #   #                  #       #      #   #        #    #           #   #      #           #      #  #\n" +
    "#      #       #         #    #            #  #                   #       #     #    #        #   #            #  #       #           #     #   #\n" +
    "#      #       #         #     #           # #                    #       #    #     #        #   #            # #        #           #    #    #\n" + 
    "#      #       ###########      ######     ##         #####       #       #####      ##########   #            ##          #########  #####     #\n" +
    "#      #       #         #            #    # #                    #       #   #      #        #   #            # #        #           #   #     #\n" +
    "#      #       #         #             #   #  #                   #       #    #     #        #   #            #  #       #           #    #    #\n" +
    "#      #       #         #             #   #   #                  #       #     #    #        #    #           #   #      #           #     #   #\n" +
    "#      #       #         #            #    #    #                 #       #      #   #        #     #          #    #     #           #      #  #\n" +
    "#      #       #         #    ########     #     ###              #       #       #  #        #      #######   #     ###   #########  #       # #\n" +
    "#                                                                                                                                               #\n" +
    "#################################################################################################################################################\n";
}
