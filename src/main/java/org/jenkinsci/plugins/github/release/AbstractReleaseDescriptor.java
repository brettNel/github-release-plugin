package org.jenkinsci.plugins.github.release;

import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import hudson.model.Item;
import hudson.security.ACL;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.kohsuke.stapler.AncestorInPath;

public abstract class AbstractReleaseDescriptor extends StepDescriptor {
  public ListBoxModel doFillCredentialIdItems(@AncestorInPath Item context) {


    if (context == null && !Jenkins.get().hasPermission(Jenkins.ADMINISTER) ||
        context != null && !context.hasPermission(Item.EXTENDED_READ)) {
      return new StandardListBoxModel();
    }

    return new StandardListBoxModel()
        .includeEmptyValue()
        .includeAs(ACL.SYSTEM, context, StringCredentials.class);
  }

}
