package io.jenkins.plugins.github.release;

import hudson.FilePath;
import hudson.Util;
import hudson.remoting.RemoteInputStream;
import hudson.remoting.VirtualChannel;
import jenkins.MasterToSlaveFileCallable;
import hudson.remoting.RemoteInputStream.Flag;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class UploadAsset implements Serializable {
  static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

  public String contentType = DEFAULT_CONTENT_TYPE;

  @DataBoundConstructor
  public UploadAsset(String filePath) {
    setFilePath(filePath);
  }


  @DataBoundSetter
  public void setContentType(String contentType) {
    String c = Util.fixEmptyAndTrim(contentType);
    this.contentType = null == c ? DEFAULT_CONTENT_TYPE : c;
  }

  public String filePath;

  @DataBoundSetter
  public void setFilePath(String filePath) {
    this.filePath = Util.fixEmptyAndTrim(filePath);
  }

  public InputStream toStream(FilePath workspace) throws IOException, InterruptedException {
    final String localFilePath = this.filePath;
    RemoteInputStream result = workspace.act(new MasterToSlaveFileCallable<RemoteInputStream>() {
        @Override
        public RemoteInputStream invoke(File f, VirtualChannel channel) throws IOException, InterruptedException {
            FileInputStream input = new FileInputStream(workspace.child(localFilePath).getRemote());
            return new RemoteInputStream(input, Flag.GREEDY);
        }
    });
    return result;
  }

  public boolean isMissing(FilePath workspace) {
      try {
          FilePath file = workspace.child(this.filePath);
          return !file.exists() || file.isDirectory();
      } catch (IOException | InterruptedException e) {
          // If an exception occurs, assume the file is missing
          return true;
      }
  }
}
