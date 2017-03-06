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
 * JobBuilder.java
 *
 * User: Michael Meunier <a href="mailto:michael.zxcv@gmail.com">michael.zxcv@gmail.com</a>
 * Created: 3/03/2017 1:37 PM
 *
 */

package com.skilld.kubernetes;

import com.skilld.kubernetes.JobConfiguration;

import io.fabric8.kubernetes.api.model.extensions.Job;
import io.fabric8.kubernetes.api.model.extensions.JobStatus;
import io.fabric8.kubernetes.api.model.LocalObjectReference;
import io.fabric8.kubernetes.api.model.Container;

public class JobBuilder {
	public Job build(JobConfiguration configuration) {
		io.fabric8.kubernetes.api.model.extensions.JobBuilder jobBuilder = new io.fabric8.kubernetes.api.model.extensions.JobBuilder()
			.withNewMetadata()
				.withName(configuration.getName())
				.withNamespace(configuration.getNamespace())
			.endMetadata()
			.withNewSpec()
				.withNewSelector()
				.withMatchLabels(configuration.getLabels())
				.endSelector()
				.withParallelism(configuration.getParallelism())
				.withCompletions(configuration.getCompletions())
				.withNewTemplate()
					.withNewMetadata()
						.withLabels(configuration.getLabels())
					.endMetadata()
					.withNewSpec()
						.withRestartPolicy(configuration.getRestartPolicy())
						.addNewContainer()
							.withName(configuration.getName())
							.withImage(configuration.getImage())
						.endContainer()
					.endSpec()
				.endTemplate()
			.endSpec();
		Long activeDeadlineSeconds = null;
		if(null != configuration.getActiveDeadlineSeconds()){
			activeDeadlineSeconds = configuration.getActiveDeadlineSeconds();
			jobBuilder
				.editSpec()
					.withActiveDeadlineSeconds(activeDeadlineSeconds)
				.endSpec();
		}
		if(null != configuration.getImagePullSecrets()){
			jobBuilder
				.editSpec()
					.editTemplate()
						.editSpec()
							.withImagePullSecrets(configuration.getImagePullSecrets())
						.endSpec()
					.endTemplate()
				.endSpec();
		}
		if(null != configuration.getNodeSelector()) {
			jobBuilder
				.editSpec()
					.editTemplate()
						.editSpec()
							.withNodeSelector(configuration.getNodeSelector())
						.endSpec()
					.endTemplate()
				.endSpec();
		}

		Container container = jobBuilder.getSpec().getTemplate().getSpec().getContainers().get(0);
		if(null != configuration.getCommand()) {
			container.setCommand(configuration.getCommand());
		}
		if(null != configuration.getArguments()) {
			container.setArgs(configuration.getArguments());
		}
		jobBuilder
			.editSpec()
				.editTemplate()
					.editSpec()
						.withContainers(container)
					.endSpec()
				.endTemplate()
			.endSpec();
		return jobBuilder.build();
	}
}
