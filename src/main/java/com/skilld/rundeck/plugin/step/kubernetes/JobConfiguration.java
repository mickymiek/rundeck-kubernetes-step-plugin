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

//import com.skilld.kubernetes.MJobBuilder;

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
    public String getImage() {
        return conf.get("image").toString();
    }

    public String getImagePullSecrets() {
        return conf.get("imagePullSecrets").toString();
    }

    public String getCommand() {
        return conf.get("command").toString();
    }

    public String getArguments() {
        return conf.get("arguments").toString();
    }

    public String getNodeSelector() {
        return conf.get("nodeSelector").toString();
    }

    public String getNamespace() {
        return conf.get("namespace").toString();
    }

    public String getActiveDeadlineSeconds() {
        return conf.get("activeDeadlineSeconds").toString();
    }

    public String getRestartPolicy() {
        return conf.get("restartPolicy").toString();
    }

    public String getCompletions() {
        return conf.get("completions").toString();
    }

    public String getParallelism() {
        return conf.get("parallelism").toString();
    }

    /* Setters */
    public void setImage(String image) {

    }

    public void setImagePullSecrets(String imagePullSecrets) {

    }

    public void setCommand(String command) {

    }

    public void setArguments(String arguments) {

    }

    public void setNodeSelector(String nodeSelector) {

    }

    public void setNamespace(String namespace) {

    }

    public void setActiveDeadline(String activeDeadline) {

    }

    public void setRestartPolicy(String restartPolicy) {

    }

    public void setCompletions(String completions) {

    }

    public void setParallelism(String parallelism) {

    }
}
