package org.opensrp.common.util;

import java.util.ArrayList;
import java.util.List;

public class WeekBoundariesAndTimestamps {
	public List<String> weekBoundariesAsString;
	public List<Long> weekBoundariesAsTimeStamp;
	
	public WeekBoundariesAndTimestamps(List<String> stringBoundaries, List<Long> timestampBoundaries){
		this.weekBoundariesAsString = stringBoundaries;
		this.weekBoundariesAsTimeStamp = timestampBoundaries;		
	}
}
