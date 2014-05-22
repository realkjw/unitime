/*
 * UniTime 3.5 (University Timetabling Application)
 * Copyright (C) 2014, UniTime LLC, and individual contributors
 * as indicated by the @authors tag.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
*/
package org.unitime.timetable.onlinesectioning.custom;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.unitime.timetable.gwt.shared.SectioningException;
import org.unitime.timetable.gwt.shared.OnlineSectioningInterface.EligibilityCheck;
import org.unitime.timetable.onlinesectioning.OnlineSectioningHelper;
import org.unitime.timetable.onlinesectioning.OnlineSectioningServer;
import org.unitime.timetable.onlinesectioning.model.XCourse;
import org.unitime.timetable.onlinesectioning.model.XSection;
import org.unitime.timetable.onlinesectioning.model.XStudent;

public interface StudentEnrollmentProvider {

	public void checkEligibility(OnlineSectioningServer server, OnlineSectioningHelper helper, EligibilityCheck check, XStudent student) throws SectioningException;
	
	public List<EnrolledSection> getEnrollment(OnlineSectioningServer server, OnlineSectioningHelper helper, XStudent student) throws SectioningException;
	
	public List<EnrollmentFailure> enroll(OnlineSectioningServer server, OnlineSectioningHelper helper, XStudent student, Map<XCourse, List<XSection>> enrollments) throws SectioningException;
	
	public void dispose();
	
	public boolean isAllowWaitListing();
	
	public static class EnrollmentFailure implements Serializable {
		private static final long serialVersionUID = 1L;
		private XCourse iCourse;
		private XSection iSection;
		private String iMessage;
		private boolean iEnrolled;
		
		public EnrollmentFailure(XCourse course, XSection section, String message, boolean enrolled) {
			iCourse = course;
			iSection = section;
			iMessage = message;
			iEnrolled = enrolled;
		}
		
		public XCourse getCourse() { return iCourse; }
		public XSection getSection() { return iSection; }
		public String getMessage() { return iMessage; }
		public boolean isEnrolled() { return iEnrolled; }
		
		public String toString() {
			return getCourse().getCourseName() + " " + getSection().getSubpartName() + " " + getSection().getName(getCourse().getCourseId()) + ": " + getMessage() + (isEnrolled() ? " (e)" : "");
		}
	}
	
	public static class EnrolledSection {
		private String iSubjectArea;
		private String iCourseNubmber;
		private String iSection;
		
		public EnrolledSection(String subject, String course, String section) {
			iSubjectArea = subject; iCourseNubmber = course; iSection = section;
		}
		
		public String getSubjectArea() { return iSubjectArea; }
		public String getCourseNumber() { return iCourseNubmber; }
		public String getSection() { return iSection; }
		
		@Override
		public String toString() {
			return getSubjectArea() + " " + getCourseNumber() + ": " + getSection();
		}
	}
}
