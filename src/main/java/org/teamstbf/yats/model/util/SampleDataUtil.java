package org.teamstbf.yats.model.util;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.ReadOnlyTaskManager;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.Timing;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.item.UniqueItemList.DuplicatePersonException;
import org.teamstbf.yats.model.tag.UniqueTagList;

public class SampleDataUtil {

	public static Event[] getSampleEvents() {
		try {
			return new Event[] {
					new Event(new Title("Vascular Medicine Research"),
							new Location("Hospital"),
							new Periodic("none"),
							new Timing("6:00pm"),
							new Timing("8:00pm"),
							new Description("Research to be extended and continued to "
									+ "alleviate carcinogenic vascularity"),
							new UniqueTagList("research")),
					new Event(new Title("Grocery Shopping"),
							new Location("FoodCourt"),
							new Periodic("monthly"),
							new Timing("10:00pm"),
							new Timing("2:00pm"),
							new Description("Buy some food"),
							new UniqueTagList("necessitires")),
					new Event(new Title("Vscan Access Mid Term"),
							new Location("School"),
							new Periodic("daily"),
							new Timing("2:30pm"),
							new Timing("2:30pm"),
							new Description("To revise on p value mathematical induction"),
							new UniqueTagList("school", "learn")),
					new Event(new Title("Project Milestone Documentation"),
							new Location("School"),
							new Periodic("weekly"),
							new Timing("4:00pm"),
							new Timing("5:00pm"),
							new Description("prepare project doucments"),
							new UniqueTagList("school","learn"))
			};
		} catch (IllegalValueException e) {
			throw new AssertionError("sample data cannot be invalid", e);
		}
	}

	public static ReadOnlyTaskManager getSampleTaskManager() {
		try {
			TaskManager sampleTM = new TaskManager();
			for (Event sampleTask : getSampleEvents()) {
				sampleTM.addEvent(sampleTask);
			}
			return sampleTM;
		} catch (DuplicatePersonException e) {
			throw new AssertionError("sample data cannot contain duplicate persons", e);
		}
	}
	/*
	new Event(new Title("Restructuring of sustainability revision"),
	new Deadline("30/05/2030"), new Timing("5:00pm"),
	new Description("Mid term test is going to be difficult!"),
	new UniqueTagList("midterm")),
	new Event(new Title("Japanese N1 exam practice"), new Deadline("23/06/2017"), new Timing("11:15am"),
	new Description("To brush up on the usage of particles in certain situations"),
	new UniqueTagList("japanese"))
	 */
}