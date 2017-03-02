/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * JobBuild.java
 *
 * User: Jean-Baptiste Guerraz <a href="mailto:jbguerraz@gmail.com">jbguerraz@gmail.com</a>
 * Created: 9/28/2016 1:37 PM
 *
 */

package com.skilld.kubernetes;

import com.skilld.rundeck.plugin.step.kubernetes.JobConfiguration;

import com.dtolabs.rundeck.core.common.Framework;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.core.plugins.configuration.*;
import com.dtolabs.rundeck.plugins.step.StepPlugin;
import com.dtolabs.rundeck.core.execution.workflow.steps.StepException;
import com.dtolabs.rundeck.plugins.step.PluginStepContext;

import io.fabric8.kubernetes.api.model.extensions.Job;
import io.fabric8.kubernetes.api.model.extensions.JobBuilder;
import io.fabric8.kubernetes.api.model.extensions.JobStatus;
import io.fabric8.kubernetes.api.model.LocalObjectReference;
import io.fabric8.kubernetes.api.model.Container;

import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



public class MJobBuilder {

	private List<String> buildInput(String _input, Map<String, String> options){
		for (Map.Entry<String, String> option : options.entrySet()){
			_input = _input.replace("${" + option.getKey() + "}", option.getValue());
		}
		List<String> input = new ArrayList<String>();
		Matcher inputParts = Pattern.compile("(\"(?:.(?!(?<!\\\\)\"))*.?\"|'(?:.(?!(?<!\\\\)'))*.?'|\\S+)").matcher(_input);
		while(inputParts.find()) {
			input.add(inputParts.group(1).replaceAll("(?<![\\\\])\"",""));
		}
		return input;
	}

	public JobBuilder build(PluginStepContext context, Map<String, Object> configuration, Map<String, String> labels, String jobName, String namespace) {
        JobConfiguration conf = new JobConfiguration(configuration);
		JobBuilder jobBuilder = new JobBuilder()
			.withNewMetadata()
				.withName(jobName)
				.withNamespace(namespace)
			.endMetadata()
			.withNewSpec()
				.withNewSelector()
				.withMatchLabels(labels)
				.endSelector()
				.withParallelism(Integer.valueOf(configuration.get("parallelism").toString()))
				.withCompletions(Integer.valueOf(configuration.get("completions").toString()))
				.withNewTemplate()
					.withNewMetadata()
						.withLabels(labels)
					.endMetadata()
					.withNewSpec()
						.withRestartPolicy(configuration.get("restartPolicy").toString())
						.addNewContainer()
							.withName(jobName)
							.withImage(configuration.get("image").toString())
						.endContainer()
					.endSpec()
				.endTemplate()
			.endSpec();
		Long activeDeadlineSeconds = null;
		if(null != configuration.get("activeDeadlineSeconds")){
			activeDeadlineSeconds = Long.valueOf(configuration.get("activeDeadlineSeconds").toString());
			jobBuilder
				.editSpec()
					.withActiveDeadlineSeconds(activeDeadlineSeconds)
				.endSpec();
		}
		if(null != configuration.get("imagePullSecrets")){
			jobBuilder
				.editSpec()
					.editTemplate()
						.editSpec()
							.withImagePullSecrets(new LocalObjectReference(configuration.get("imagePullSecrets").toString()))
						.endSpec()
					.endTemplate()
				.endSpec();
		}
		if(null != configuration.get("nodeSelector")) {
			jobBuilder
				.editSpec()
					.editTemplate()
						.editSpec()
							.withNodeSelector((HashMap<String, String>) Arrays.asList(configuration.get("nodeSelector").toString().split(",")).stream().map(s -> s.split("=")).collect(Collectors.toMap(e -> e[0], e -> e[1])))
						.endSpec()
					.endTemplate()
				.endSpec();
		}

		Container container = jobBuilder.getSpec().getTemplate().getSpec().getContainers().get(0);
		if(null != configuration.get("command")) {
			container.setCommand(buildInput(configuration.get("command").toString(), context.getDataContext().get("option")));
		}
		if(null != configuration.get("arguments")) {
			container.setArgs(buildInput(configuration.get("arguments").toString(), context.getDataContext().get("option")));
		}
		jobBuilder
			.editSpec()
				.editTemplate()
					.editSpec()
						.withContainers(container)
					.endSpec()
				.endTemplate()
			.endSpec();
		return jobBuilder;
	}

}
