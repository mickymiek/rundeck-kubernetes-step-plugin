/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Job.java
 *
 * User: Jean-Baptiste Guerraz <a href="mailto:jbguerraz@gmail.com">jbguerraz@gmail.com</a>
 * Created: 9/28/2016 1:37 PM
 *
 */
package com.skilld.kubernetes;

import java.util.List;
import io.fabric8.kubernetes.api.model.JobStatus;
import io.fabric8.kubernetes.api.model.JobCondition;

public class Job {
	public static String getState(io.fabric8.kubernetes.api.model.Job job) {
		String type = null;
		for (JobCondition condition : job.getStatus().getConditions()) {
			type = condition.getType();
			if(type.equals("Complete") || type.equals("Failed")){
				break;
			}
		}
		return type;
	}
}
