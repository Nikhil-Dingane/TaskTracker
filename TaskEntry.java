import java.io.Serializable;
import java.time.LocalTime;

@SuppressWarnings("serial")
public class TaskEntry implements Serializable,Cloneable {
	private LocalTime startTime;
	private LocalTime endTime;
	private String type;
	private String description;

	public TaskEntry() {

	}

	public TaskEntry(LocalTime startTime, LocalTime endTime, String type, String description) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
		this.description = description;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return String.format("%10s %10s   %-15s %s", startTime.toString(), endTime.toString(), type, description);
	}

	public boolean equals(Object obj) {
		TaskEntry taskEntry = (TaskEntry) obj;

		if (taskEntry != null && taskEntry instanceof TaskEntry) {
			if ((taskEntry.startTime == this.startTime) && (taskEntry.endTime == this.endTime)
					&& (taskEntry.type == this.type)) {
				return true;
			}
		}

		return false;
	}
	
	 public Object clone() throws CloneNotSupportedException { 
		 TaskEntry taskEntryCopy = (TaskEntry)super.clone(); 
		 taskEntryCopy.startTime = LocalTime.of(startTime.getHour(),startTime.getMinute());
		 taskEntryCopy.endTime = LocalTime.of(endTime.getHour(),endTime.getMinute());
		 taskEntryCopy.type = new String(this.type);
		 taskEntryCopy.description = new String(this.description);
		 return taskEntryCopy; 
	 } 
	
}
