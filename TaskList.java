
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskList implements Serializable{
	List<TaskEntry> taskList = new ArrayList<>();
	LocalDate listDate = java.time.LocalDate.now();
	
	public TaskList() {
		
	}

	public TaskList(List<TaskEntry> taskList, LocalDate listDate) {
		super();
		this.taskList = taskList;
		this.listDate = listDate;
	}
	
	public boolean add(TaskEntry taskEntry) {
		return taskList.add(taskEntry);
	}
	
	public TaskEntry update(int index, TaskEntry taskEntry) {
		return taskList.set(index, taskEntry);
	}	
	
	public TaskEntry delete(int index) {
		return taskList.remove(index);
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder("\n********************************************************************************\n\n");
		str.append(new StringBuilder(String.format("%7s %10s %10s   %-15s %s","Sr. No.","Start Time","End Time","Type","Description")));
		str.append(new StringBuilder("\n\n********************************************************************************\n"));
		int i = 1;
		for(TaskEntry taskEntry : taskList) {
			//str.append(String.format("\n%7d %10s %10s %-15s %s", i, taskEntry.getStartTime().toString(), 				taskEntry.getEndTime().toString(), taskEntry.getType(),taskEntry.getDescription()));
			str.append(String.format("\n%7d ",i));
			str.append(taskEntry);
			i++;
		}
		str.append(new StringBuilder("\n\n********************************************************************************\n"));
		return str.toString();
	}
	
	public List<TaskEntry> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TaskEntry> taskList) {
		this.taskList = taskList;
	}

	public LocalDate getListDate() {
		return listDate;
	}

	public void setListDate(LocalDate listDate) {
		this.listDate = listDate;
	}

	public int getListSize() {
		return taskList.size();
	}
	
	
}
