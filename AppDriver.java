import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.DateTimeException;
import java.lang.ArrayIndexOutOfBoundsException;

class AppDriver {
    public static void main(String Args[]) throws IOException {
        System.out.println("\n\n" + Task_Tracker_Banner);
        
        BufferedReader bufferReader =  new BufferedReader(new InputStreamReader(System.in));
        TaskEntry taskEntry = null;
        TaskList taskList = null;
        ListDisplayManager listDisplayManager = null;
        FileReader fileReader = null;
        File todaysFile = null;
       
        while(true) {
            getMainMenu();
            System.out.println("\nPlease Enter Your Choice :");
            int Choice = getChoice(1,4);

            switch(Choice) {
                case 1:
                	if(checkTodaysFileExistOrNot()==false) {
                		todaysFile = new File(getTodaysFileName());
                		todaysFile.createNewFile();
                		System.out.println("\nTodays Task List Created Successfully");
                	}
                	if(getTodaysFileName().length() != 0) {
                		fileReader = new FileReader(getTodaysFileName());
                		taskList = fileReader.readList();
					}
                	
                	
                	boolean todaysTask = true;
                	
                	while(todaysTask == true) {
                		System.out.println(taskList);
                        getTodaysListMenu();                        
                        System.out.println("\nPlease Enter Your Choice :");
                        int todaysTaskChoice = getChoice(1,4);

                        switch(todaysTaskChoice) {
                            case 1:
                	            taskEntry = getEntryInfo();
                	            boolean valid = true;
                	            for(TaskEntry tempTaskEntry : taskList.getTaskEntryList()) {
            	            		if(tempTaskEntry.getEndTime().compareTo(tempTaskEntry.getStartTime()) >=0) {
            	            			valid = false;
            	            			break;
            	            		}
            	            	}
            	            	if(!valid) {
            	                    System.out.println("\nEnter Starting Time :");
            	            		taskEntry.setStartTime(getLocalTime());
            	            		System.out.println("\nEnter Ending Time :");
            	            		taskEntry.setEndTime(getLocalTime());
            	            	}
                	            
                	            if(taskList.add(taskEntry)) {
                                    writeInTodaysFile(taskList);
                	            } else {
                	            	System.out.println("Unable to Insert Tasks");
                	            }
                	            break;

                            case 2: 
                                if(taskList.getTaskEntryList().size() > 0){
                                	System.out.println("\nPlease Enter Your Task Number to Update :");
                                	int taskNumberForEdit = getChoice(1,taskList.getTaskEntryList().size());
                                	taskEntry = taskList.getTaskEntryList().get(taskNumberForEdit-1);
                                	updateListMenu();
                                	int updateChoice = getChoice(1, 2);
                                	if(updateChoice == 1) {
                                		LocalTime newEndTime;
                                		while(true) {
                                			System.out.println("Enter End Time :");
                                            newEndTime = getLocalTime();
                                            if(startingAndEndingTimeValidator(taskEntry.getStartTime(), newEndTime)) {
                                            	break;
                                            }
                                		}
                          
                                        taskEntry.setEndTime(newEndTime);
                                        break;
                                        
                                	} else if (updateChoice == 2) {
                                		System.out.println("Enter Description :");
                                        String Description = bufferReader.readLine();
                                        taskEntry.setDescription(Description);
                                        break;
									}                             	
                                	
                                    taskList.update(taskNumberForEdit,taskEntry);
                                    writeInTodaysFile(taskList);
                                } else {
                                    System.out.println("\nYour Todays Task List is Empty\n");
                                }
                                break;

                            case 3:
                                if(taskList.getTaskEntryList().size() > 0) {
                                    System.out.println("\nPlease Enter Your Task Number :");
                	                int taskNumberForDelete = getChoice(1,taskList.getTaskEntryList().size() + 1);
                                    taskList.delete(taskNumberForDelete - 1);
                                    writeInTodaysFile(taskList);
                                } else {
                                    System.out.println("\nYour Todays Task List is Empty\n");
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
                    List<TaskList> listsOfTaskLists = null;
                    
                    switch(RemoveListsChoice) {
                        case 1:
                            System.out.println("\nPlease Enter Date That You Want to Remove :");
                            LocalDate removeBySingleDate = getLocalDate();
                            System.out.println(removeBySingleDate);
                            System.out.println(removeBySingleDate.getMonth()+"_"+removeBySingleDate.getYear()+".ser");
                            fileReader = new FileReader(removeBySingleDate.getMonth()+"_"+removeBySingleDate.getYear()+".ser");
                            listsOfTaskLists = fileReader.readLists();
                            System.out.println(listsOfTaskLists);
                            boolean found = false;
                            for(TaskList list : listsOfTaskLists) {
                                if((list!=null) && (list.getListDate().equals(removeBySingleDate))) {
                                    listsOfTaskLists.remove(list);
                                    found = true;
                                    break;
                                } 
                            }
                            System.out.println(found == true?"Task List Successfully removed":"Task List Did Not Found");
                            WriteInMonthFile(listsOfTaskLists);
                            
                            break;

                        case 2:
                        	System.out.println("Enter The Month:");
                        	int monthNum = getChoice(1, 12);
                        	System.out.println("Enter the Year");
                        	int year = getChoice(2020,LocalDate.now().getYear());
                        	
                        	File monthFileFile = new File(Month.of(monthNum)+"_"+year+".ser");
                        	if(monthFileFile.exists()) {
                        		monthFileFile.delete();
                        		System.out.println("File Successfully Deleted Of Name: " + Month.of(monthNum)+"_"+year+".ser");
                        	} else {
                        		System.out.println("File Does Not Exist With Name: "+Month.of(monthNum)+"_"+year+".ser");
                        	}
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
        System.out.println("*               Press (1) Create / Manage Todays Task List              *");
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
                    } else {
						break;
					}
               	} catch (NumberFormatException e) {
                  System.out.println("Please Enter Only Number.");
               	} catch (Exception e) {
               		e.printStackTrace();
               	}
        }
        return Choice;
    }

    public static String getTodaysFileName(){
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

    public static LocalTime getLocalTime(String cancelValue) throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        LocalTime localTime = null;
	    System.out.println("Note: The Time is 24 Hours");
        int Hour = 0, Minute = 0;
        while(true) {
            if(cancelValue.equalsIgnoreCase("Cancel")) {
                return null;
            } else {
                try {
                    String Time= bufferReader.readLine();  
                    String TimeConvertor[] = Time.split(":");
                    Hour = Integer.parseInt(TimeConvertor[0]);
                    Minute = Integer.parseInt(TimeConvertor[1]);
                    return LocalTime.of(Hour, Minute);
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("ERROR : Please Enter Time in Following Format (HH : MM) :");
                } catch(DateTimeException e) {
                    System.out.println("ERROR : Please Enter Valid Time (0-23 : 0:59) in Format (HH:MM):");
                } catch (NumberFormatException e) {
                    System.out.println("ERROR : Please Enter Only Numbers In Format (HH:MM) :");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeInTodaysFile(TaskList taskList) throws IOException {
        FileWriter fileWriter = new FileWriter(getTodaysFileName());
        try {
            fileWriter.writeList(taskList);
            System.out.println("\nChanges Successfully Saved To The File: " + getTodaysFileName()+"\n");
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
    
    public static String getMonthFileNameCustom(Month month, Year year) throws IOException {
        String fileNameYear = Integer.toString(year.getValue());
        String fileName = month + "_" + fileNameYear + ".ser";
        return fileName;
    }

    public static LocalDate getLocalDate() throws IOException {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        int DateDay = 0;
        int month;
        int Year = 0;
        while(true) {
            try {
                String Date = bufferReader.readLine();
                String formatDate[] = Date.split("/");
                DateDay = Integer.parseInt(formatDate[0]);
                month = Integer.parseInt(formatDate[1]);
                Year = Integer.parseInt(formatDate[2]);
                return LocalDate.of(Year,month,DateDay);
           } catch(ArrayIndexOutOfBoundsException e){
               System.out.println("\nERROR : Please Enter Date in Format(DD/MM/YYYY) Again :");
           } catch(DateTimeException e) {
                System.out.println("\nERROR : Please Enter Valid Date in Format(DD/MM/YYYY) :");
           } catch(NumberFormatException e) {
                System.out.println("\nERROR : Please Enter Only Numbers in Format(DD/MM/YYYY) :");
           } catch(Exception e) {
               e.printStackTrace();
           }
       }
    }

    public static void printRemoveListsInfo() {
        System.out.println("\n\n**************************** Remove Menu ********************************");
        System.out.println("| |                                                                   | |");
        System.out.println("| |             Press (1) to Remove Task Lists By Single Date         | |");
        System.out.println("| |             Press (2) to Remove By Month                          | |");
        System.out.println("| |                                                                   | |");
        System.out.println("*************************************************************************");
    }
    
    public static void updateListMenu() {
    	System.out.println("\nPlease Enter Your Choice :");
        System.out.println("\n\n**************************** Update Entry******************************");
        System.out.println("| |                                                                   | |");
        System.out.println("| |             Press (1) End Time                                    | |");
        System.out.println("| |             Press (2) Description								  | |");
        System.out.println("| |                                                                   | |");
        System.out.println("*************************************************************************");
    }

    public static void WriteInMonthFile(List<TaskList> taskLists) throws IOException {
        FileWriter fileWriter = new FileWriter(getMonthFileName());
        try {
            fileWriter.writeLists(taskLists);
            System.out.println("\nChanges Successfully Written In The File :" + getMonthFileName()+"\n");
        } catch (Exception e) {
            System.out.println("Unable to Write In File");
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }
    }
    
    public static boolean startingAndEndingTimeValidator(LocalTime startTime, LocalTime endTime) throws IOException {
        int timeStatus = startTime.compareTo(endTime);
        if(timeStatus >= 0 ) {
        	return false;
        } else {
        	return true;
        }
    }
    
    public static boolean checkTodaysFileExistOrNot() {
    	String todaysFileName = getTodaysFileName();
    	File todaysFile = new File(todaysFileName);
    	return todaysFile.exists();
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
