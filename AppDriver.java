import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Month;
import java.io.InputStreamReader;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.time.DateTimeException;


class AppDriver {
    public static void main(String Args[]) throws IOException {
        System.out.println("\n\n" + Task_Tracker_Banner);

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        
        TaskEntry taskEntry = new TaskEntry();

        TaskList taskList = new TaskList();

        List<TaskList> taskLists = new ArrayList<TaskList>();

        ListDisplayManager listDisplayManager = new ListDisplayManager(taskLists);

        FileReader fileReader = null;
        
        while(true) {
            System.out.println("\n\n Press (1) Create Today's Task List ");
            System.out.println(" Press (2) Edit/Remove Task List");
            System.out.println(" Press (3) Show Task List");
            System.out.println(" Press (4) To Get Todays File");
            System.out.println(" Press (5) Exit");
            System.out.println("\nPlease Enter Your Choice :");
            int Choice = getChoice(1,5);
			
		switch (Choice) {
                	case 1: 
                    	   System.out.println("******************************* Add New Entry *******************************");
                    	   taskEntry = getEntryInfo();
                 	   if(taskList.add(taskEntry)) {
                        	System.out.println("****************************** Added New Entry ******************************");
                        	System.out.println(taskEntry.toString());
                    	   } else {
                        	System.out.println("Unable to Insert Tasks");
                    	   }
                    	   taskLists.add(taskList);
                    	   writeInTodaysFile(taskList);
                    	break;

                	case 2:
                    	   printEditRemoveInfo();
                    	   System.out.println("\nPlease Enter Your Choice: ");
                    	   int editRemoveChoice = getChoice(1,2);
                    		
                    	   
                    		
			   switch(editRemoveChoice) { 
                           	case 1:

                            	   System.out.println("****************************** Update Task ******************************");
                                   System.out.println("\nPlease Enter Your Task Number :");
                    	           int taskNumberForEdit = getChoice(1,10)-1;
                            	   System.out.println("Enter End Time :");
                            	   LocalTime newEndTime = getLocalTime();
                            	   taskEntry.setEndTime(newEndTime);
                            	   System.out.println("Enter Description :");
                            	   String Description = bufferReader.readLine();
                            	   taskEntry.setDescription(Description);
                            	   taskList.update(taskNumberForEdit,taskEntry);
                            	   System.out.println("************************* Task Updated Successfully **************************");
                            	   System.out.println(taskEntry.toString());
                            	   taskLists.add(taskList);
                                   writeInTodaysFile(taskList);
                                   
                            	break;
                        	case 2: 
                            	   System.out.println("****************************** Remove Record ******************************");
                                   System.out.println("\nPlease Enter Your Task Number :");
                    	           int taskNumberForDelete = getChoice(1,10)-1;
                            	   taskList.delete(taskNumberForDelete);
                            	   System.out.println("************************** Task Removed Successfully **************************");
                            	   taskLists.add(taskList);
                                   writeInTodaysFile(taskList);
                            	break;
                    	   } 
                           
                   
                    	break;
                
                	case 3:
                    	   printShowTaskListInfo();
                    	   System.out.println("\nPlease Enter Your Choice :");
                    	   int showListChoice = getChoice(1,4);
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
                        
				case 5:
	                           System.out.println("\nPlease Enter Month Number :");
                                   int Month = getChoice(1,31);
                            	   System.out.println("\nPlease Enter Year :");
                            	   int Year = getChoice(2020,2300);
              			   listDisplayManager.showListMonthWise(Month,Year);
              		        break;
                    	   }    
		       break;

	               case 4:
	                  try{
	                    fileReader = new FileReader(getTodaysFileName());
	                    TaskList todaysTasks = fileReader.readList();
	                    System.out.println(todaysTasks);
	                  } catch (Exception e) {
                             System.out.println("Unable to read File!!");
	                     e.printStackTrace();
	                  } finally {
                             fileReader.close();
                          }
                       break;

	               case 5:
                         System.out.println("\nDo You want to Finish ?(Yes or No)");
                         String finishOrNot = bufferReader.readLine();
                         if(finishOrNot.equalsIgnoreCase("Yes")) {
        	             WriteInMonthFile(taskLists);
        	             System.out.println("******************** Thank You For Using Task-Tracker ********************");
                             System.exit(0);
                         } else {
                            System.exit(0);
                         }
                      break;
            }
	}
    }

    
    public static void WriteInMonthFile(List<TaskList> taskLists) throws IOException{
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
    public static String getMonthFileName() throws IOException {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        int Year = currentDate.getYear();
        String fileNameYear = Integer.toString(Year);
        String fileName = currentMonth + "_" + fileNameYear + ".ser";
        File file = new File("fileName");
        return fileName;
    }

     public static String getTodaysFileName() throws IOException {
        LocalDate currentDate = LocalDate.now();
	int Date = currentDate.getDayOfMonth();
	Month currentMonth = currentDate.getMonth();
	int Year = currentDate.getYear();
	String fileNameYear = Integer.toString(Year);
        String fileName = Date + "_" + currentMonth + "_" + fileNameYear + ".ser";
        File file = new File("fileName");
        return fileName;
    }

    public static int getChoice(int lowerBound, int upperBound) throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        int Choice = 0;
        int lowBound = lowerBound;
        int upBound = upperBound;
        while(true) {
            try {
		Choice = Integer.parseInt(bufferReader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Please Enter Only Number.");
            } catch (Exception e) {
		e.printStackTrace();
            } 
            if(Choice < lowerBound && Choice > upperBound) {
               getChoice(lowBound,upBound);
            }
           return Choice;
        }
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
                System.out.println("Please Enter Valid Hours(00-23)");
           } catch (NumberFormatException e) {
                System.out.println("Please Enter Only Number.");
           } catch(Exception e) {
                e.printStackTrace();
          }
       }
    }


    public static LocalDate getLocalDate() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.println("Enter Day Date :");
                int Date = Integer.parseInt(bufferReader.readLine());
                System.out.println("Enter Month :");
                int Month = Integer.parseInt(bufferReader.readLine());
                System.out.println("Enter Year :");
                int Year = Integer.parseInt(bufferReader.readLine());
                return LocalDate.of(Year,Month,Date);
           } catch(DateTimeException e) {
                System.out.println("Please Enter Valid Date:(1-31), Month:(1-12) Year(2000-2300");
           } catch(NumberFormatException e) {
                System.out.println("Please Enter Only Number With Valid Date:(1-31), Month:(1-12), Year(2000-2300");
           } catch(Exception e) {
               e.printStackTrace();
           }
       }
    }

    public static TaskEntry getEntryInfo() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        TaskEntry taskEntry = new TaskEntry();
	System.out.println("Enter Starting Time :");
        taskEntry.setStartTime(getLocalTime());
        System.out.println("\nEnter Ending Time :");
        taskEntry.setEndTime(getLocalTime());
        System.out.println("\nEnter Type (Task/Learning) :");
        taskEntry.setType(bufferReader.readLine());
        System.out.println("\nEnter Description What You did for Your " + taskEntry.getType() + " :");
        taskEntry.setDescription(bufferReader.readLine());
        return taskEntry;
    }
    
    public static void printEditRemoveInfo() {
        System.out.println("\nPress (1) Edit Todays Task");
        System.out.println("Press (2) Remove Task");
    }

    public static void printShowTaskListInfo() {
        System.out.println("\n\nPress (1) to Show All Tasks");
        System.out.println("Press (2) to Show Task By Date");
        System.out.println("Press (3) to Show Yesterday's Task");
        System.out.println("Press (4) to Show All Tasks From Date to End Date");
        System.out.println("Press (5) to Show Tasks by Month Wise");
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
