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
 * JobConfiguration.java
 *
 * User: Michaël Meunier <a href="mailto:michael.zxcv@gmail.com">michael.zxcv@gmail.com</a>
 * Created: 03/02/2017 2:33 PM
 *
 */
package com.skilld.rundeck.plugin.step.kubernetes;

import com.dtolabs.rundeck.core.plugins.configuration.*;
import io.fabric8.kubernetes.api.model.LocalObjectReference;

import java.util.*;


public class JobConfiguration {

	private Map<String, Object> conf;

	/* Constructor */
	public JobConfiguration(Map<String, Object> configuration) {
		conf = configuration;
	}

	/* Getters */
	public Object getImage() {
		return conf.get("image");
	}

	public Object getImagePullSecrets() {
		return conf.get("imagePullSecrets");
	}

	public Object getCommand() {
		return conf.get("command");
	}

	public Object getArguments() {
		return conf.get("arguments");
	}

	public Object getNodeSelector() {
		return conf.get("nodeSelector");
	}

	public Object getNamespace() {
		return conf.get("namespace");
	}

	public Object getActiveDeadlineSeconds() {
		return conf.get("activeDeadlineSeconds");
	}

	public Object getRestartPolicy() {
		return conf.get("restartPolicy");
	}

	public Object getCompletions() {
		return conf.get("completions");
	}

	public Object getParallelism() {
		return conf.get("parallelism");
	}

	/* Setters */
	public void setImage(String image, Object val) {
		conf.put(image, val);
	}

	public void setImagePullSecrets(String imagePullSecrets, Object val) {
		conf.put(imagePullSecrets, val);
	}

	public void setCommand(String command, Object val) {
		conf.put(command, val);
	}

	public void setArguments(String arguments, Object val) {
		conf.put(arguments, val);
	}

	public void setNodeSelector(String nodeSelector, Object val) {
		conf.put(nodeSelector, val);
	}

	public void setNamespace(String namespace, Object val) {
		conf.put(namespace, val);
	}

	public void setActiveDeadlineSeconds(String activeDeadlineSeconds, Object val) {
		conf.put(activeDeadlineSeconds, val);
	}

	public void setRestartPolicy(String restartPolicy, Object val) {
		conf.put(restartPolicy, val);
	}

	public void setCompletions(String completions, Object val) {
		conf.put(completions, val);
	}

	public void setParallelism(String parallelism, Object val) {
		conf.put(parallelism, val);
	}
}
